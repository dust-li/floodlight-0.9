package net.floodlightcontroller.distributeapp;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;

import org.openflow.protocol.OFFlowMod;
import org.openflow.protocol.OFMatch;
import org.openflow.protocol.OFPacketIn;
import org.openflow.protocol.OFPacketOut;
import org.openflow.protocol.OFPort;
import org.openflow.protocol.OFStatisticsRequest;
import org.openflow.protocol.OFType;
import org.openflow.protocol.action.OFAction;
import org.openflow.protocol.action.OFActionOutput;
import org.openflow.protocol.statistics.OFFlowStatisticsReply;
import org.openflow.protocol.statistics.OFFlowStatisticsRequest;
import org.openflow.protocol.statistics.OFStatistics;
import org.openflow.protocol.statistics.OFStatisticsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * when we got enough packet_in packets, we send a flowMod to stop
 * sending packets to the controller
 */
class RemoveToControllerActionThread extends Thread {
	private IOFSwitch sw;
	private OFPacketIn pi;
	private FloodlightContext cntx;
	private OFFlowMod fm;
	
	private int flowHash;
		
	protected static Logger logger = 
			LoggerFactory.getLogger(RemoveToControllerActionThread.class);


	public RemoveToControllerActionThread(IOFSwitch sw, OFPacketIn pi,
			int flowHash, OFFlowMod fm) {
		this.sw = sw;
		this.pi = pi;
//		this.cntx = cntx;
		this.flowHash = flowHash;
		this.fm = fm;
	}

	@Override
	public void run() {
//		removeToControllerAction();
		     
		dierectRemoveToControllerAction(flowHash);
	}
	
	/**
	 *  directly remove the "to controller" action without request the flow table
	 *  on the switch. The packet must be a TCP or UDP
	 */
	private void dierectRemoveToControllerAction(int flowHash)
	{
		// get match
		OFMatch match = new OFMatch();
		match.loadFromPacket(pi.getPacketData(), pi.getInPort());
        Integer wildcard_hints = ((Integer) sw
                .getAttribute(IOFSwitch.PROP_FASTWILDCARDS)).intValue()
                    & ~OFMatch.OFPFW_DL_TYPE
                    // 5 tuple
                    & ~OFMatch.OFPFW_NW_DST_MASK
                    & ~OFMatch.OFPFW_NW_SRC_MASK
                    & ~OFMatch.OFPFW_NW_PROTO
                    & ~OFMatch.OFPFW_TP_DST
                    & ~OFMatch.OFPFW_TP_SRC;
        match.setWildcards(wildcard_hints);

		OFActionOutput toControllerAction = new OFActionOutput(
				OFPort.OFPP_CONTROLLER.getValue(), (short) 0xffff);
		
		if(!DistributeApp.flowhashActionMap.containsKey(flowHash))
			return ;
		
		System.out.println("found a flow");

		List<OFAction> actions = DistributeApp.flowhashActionMap.get(flowHash);
		if(actions.contains(toControllerAction))
			actions.remove(toControllerAction);
		
		fm.setBufferId(OFPacketOut.BUFFER_ID_NONE)
		 .setCookie(flowHash)
		 .setCommand(OFFlowMod.OFPFC_MODIFY)
		 .setMatch(match)
		 .setActions(actions)
		 .setLengthU(OFFlowMod.MINIMUM_LENGTH+ actions.size()
		   * OFActionOutput.MINIMUM_LENGTH);
		
		// write the flow
		 try {
			sw.write(fm, cntx);
			logger.info("write a new flow mod to delete the to controller action");
		} catch (IOException e) {
			logger.error("write flow failed");
			e.printStackTrace();
		}
		 // we need to modify the flow immediately
		sw.flush();
		
		DistributeApp.flowhashActionMap.remove(flowHash);
}
	
	
	// request and delete
	private void removeToControllerAction()
	{
		// get match
		OFMatch match = new OFMatch();
		match.loadFromPacket(pi.getPacketData(), pi.getInPort());

		OFActionOutput toControllerAction = new OFActionOutput(
				OFPort.OFPP_CONTROLLER.getValue(), (short) 0xffff);

		// get flow with the match on the switch
		List<OFStatistics> flowList = getSwitchFlow(sw, match);
		
		// find the flow we need to modify
		for(OFStatistics offlow : flowList)
		{
			if(!(offlow instanceof OFFlowStatisticsReply))
			{
				logger.error("the OFStatistics is not OFFlowStatisticsReply");
				continue;
			}
			List<OFAction> actions = ((OFFlowStatisticsReply)offlow).getActions();
			if(actions.contains(toControllerAction))
			{
				// delete the to controller action
				actions.remove(toControllerAction);
				
				 fm.setBufferId(OFPacketOut.BUFFER_ID_NONE)
				 /*
				  * The cookie field is an opaque data value that is set by the controller.
				  *  It is not used in any matching functions, and thus does not need to 
				  *  reside in hardware.
				  *  The value -1 (0xffffffffffffffff) is reserved and must not be used.
				  *  It is required that when command is OFPC_MODIFY or OFPC_MODIFY_STRICT
				  *   that matched flows have their cookie field updated appropriately.
				  */
//				 .setCookie(((OFFlowStatisticsReply)offlow).getCookie())
				 .setCommand(OFFlowMod.OFPFC_MODIFY)
				 .setMatch(match)
				 .setActions(actions)
				 .setLengthU(OFFlowMod.MINIMUM_LENGTH+ actions.size()
						 * OFActionOutput.MINIMUM_LENGTH);
				 
				 // write the flow
				 try {
					sw.write(fm, cntx);
					logger.info("write a new flow mod to delete the to controller action");
				} catch (IOException e) {
					logger.error("write flow failed");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 // we need the result immediately TODO delete this
				sw.flush();
			}
		}
	}

	protected List<OFStatistics> getSwitchFlow(IOFSwitch sw, OFMatch match)
	{
		Future<List<OFStatistics>> future;
		List<OFStatistics> values = null;
		if (sw != null) {
            OFStatisticsRequest req = new OFStatisticsRequest();
            req.setStatisticType(OFStatisticsType.FLOW);
            int requestLength = req.getLengthU();

            OFFlowStatisticsRequest specificReq = new OFFlowStatisticsRequest();            
            // only 5 tuple are considered
            Integer wildcard_hints = ((Integer) sw
                    .getAttribute(IOFSwitch.PROP_FASTWILDCARDS)).intValue()
	                    & ~OFMatch.OFPFW_DL_TYPE
	                    // 5 tuple
	                    & ~OFMatch.OFPFW_NW_DST_MASK
	                    & ~OFMatch.OFPFW_NW_SRC_MASK
	                    & ~OFMatch.OFPFW_NW_PROTO
	                    & ~OFMatch.OFPFW_TP_DST
	                    & ~OFMatch.OFPFW_TP_SRC;

            specificReq.setMatch(match.setWildcards(wildcard_hints.intValue()));
            
            specificReq.setOutPort(OFPort.OFPP_NONE.getValue());
            specificReq.setTableId((byte) 0xff);
            req.setStatistics(Collections.singletonList((OFStatistics)specificReq));
            requestLength += specificReq.getLength();

			req.setLengthU(requestLength);
			try {
				future = sw.getStatistics(req);
				values = future.get(10, TimeUnit.SECONDS);
			} catch (Exception e) {
				logger.error("Failure retrieving statistics from switch "
						+ sw, e);
			}
			if(!values.isEmpty())
			{
				assert(values.size() == 1);				
				logger.info("we got a flow table match our match");
			}
		}
		return values;
	}
}











