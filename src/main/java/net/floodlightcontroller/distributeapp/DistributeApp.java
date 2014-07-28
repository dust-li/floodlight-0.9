package net.floodlightcontroller.distributeapp;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openflow.protocol.OFFlowMod;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPacketIn;
import org.openflow.protocol.OFType;
import org.openflow.protocol.action.OFAction;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.counter.ICounterStoreService;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.Set;

import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.TCP;
import net.floodlightcontroller.packet.UDP;
import net.floodlightcontroller.perfmon.IPktInProcessingTimeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributeApp implements IOFMessageListener, IFloodlightModule {

	protected IFloodlightProviderService floodlightProvider;
	protected Set<Long> macAddresses;
	protected static Logger logger;

	protected ICounterStoreService counterStore;
	// more flow-mod defaults
	protected static final short IDLE_TIMEOUT_DEFAULT = 5;
	protected static final short HARD_TIMEOUT_DEFAULT = 0;
	protected static final short PRIORITY_DEFAULT = 100;

	private java.util.Random random;
	
	/*
	 * this map is a structure to store the PACKET_INs a flow has received
	 */
	public static Map<Integer, Integer> flowPacketInCountHashmap = new HashMap<Integer, Integer>();

	// to save the action a flow performs
	public static Map<Integer, List<OFAction>> flowhashActionMap =
			new ConcurrentHashMap<Integer, List<OFAction>>();
	
	private int packetINCount = 0;
	
	@Override
	public String getName() {
		return DistributeApp.class.getSimpleName();
	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		return false;
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		return false;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		return null;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		return null;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context
				.getServiceImpl(IFloodlightProviderService.class);
		macAddresses = new ConcurrentSkipListSet<Long>();
		logger = LoggerFactory.getLogger(DistributeApp.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context) {
//		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);

		IPktInProcessingTimeService pits = context.getServiceImpl(IPktInProcessingTimeService.class);
		// start the test application
		 new TestApplication(pits);
		 
		
		// read our config options
//		Map<String, String> configOptions = context.getConfigParams(this);
//		int port = Integer.parseInt(configOptions.get("DPIServerPort"));

		// start the communication server
		// comServer = new CommunicationServer(port);

	}

	private static void wasteCPU(int time)
	{
		for(int i = 0;i < time * 100;i++)
			Math.random();
	}

	
	@Override
	public net.floodlightcontroller.core.IListener.Command receive(
			IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		Ethernet eth = IFloodlightProviderService.bcStore.get(cntx,
				IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		
//		long startTime=System.currentTimeMillis();   //获取开始时间  
//		// waste cpu
//		wasteCPU(10000);
//		long endTime=System.currentTimeMillis(); //获取结束时间  
//		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

		//this should not show when in performance testing
		System.err.println("get a to controller message");
		
		// we just handle flows that add "to controller" action
		if (((OFPacketIn) msg).getReason() == OFPacketIn.OFPacketInReason.NO_MATCH)
			return Command.CONTINUE;
		
		
		IPacket packet = eth.getPayload();
		if (!(packet instanceof IPv4))
			return Command.CONTINUE;

		if ( !(((IPv4) packet).getPayload() instanceof TCP) &&
				!(((IPv4) packet).getPayload() instanceof UDP)  )
			return Command.CONTINUE;
		
		System.out.println("packet received count: " + packetINCount++);
		
		// handle TCP and UDP packets
		int flowHash = 0;
		int n = 10;
		try {
			flowHash = Utils.getFlowHash((IPv4) packet);
		} catch (Exception e) {
			// this shouldn't happen
			e.printStackTrace();
		}
		
		if (flowPacketInCountHashmap.containsKey(flowHash)) {
			int count = flowPacketInCountHashmap.get(flowHash);
			flowPacketInCountHashmap.put(flowHash, count + 1);
			// we have got enough packets ,remove the to controller action
			if (count == n)
			{
				// TODO  using thread pool
				OFFlowMod fm = (OFFlowMod) floodlightProvider.getOFMessageFactory()
						.getMessage(OFType.FLOW_MOD);
				RemoveToControllerActionThread rcat = 
						new RemoveToControllerActionThread(sw,(OFPacketIn)msg,flowHash, fm);
				rcat.start();
			}
			else
			{
				System.out.println("flow: "+flowHash + " packet in count: " + count);
				System.out.println("flowhashActionMap size: "  + DistributeApp.flowhashActionMap.size());

				return Command.CONTINUE;
			}
		}
		else
		{
			// TODO this won't be deleted from the hashmap
			flowPacketInCountHashmap.put(flowHash, 1);
		}

		return Command.CONTINUE;
	}


}
