package net.floodlightcontroller.distributeapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.DPIAPP.REJNI;
import net.floodlightcontroller.DPIAPP.protocolList;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.TCP;
import net.floodlightcontroller.packet.UDP;
import net.floodlightcontroller.perfmon.CumulativeTimeBucket;
import net.floodlightcontroller.perfmon.IPktInProcessingTimeService;
import net.floodlightcontroller.perfmon.OneComponentTime;
import net.floodlightcontroller.perfmon.PktInProcessingTime;


public class TestApplication {
	
//	private Socket socket = null;

	public TestApplication(IPktInProcessingTimeService pits)
	{
//		try {
//			socket = new Socket(ComunicationSettings.DPIServerAddress,
//					ComunicationSettings.DPIServerPort);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Thread clientThread = new Thread(new ClientThread(socket));
//		clientThread.start();
		
		// monitor the performance of the modules
		Thread  perfMonitorThread = new Thread(new PerfMonitorThread(pits));
		perfMonitorThread.start();
	}
}


class PerfMonitorThread implements Runnable
{
	IPktInProcessingTimeService pits = null;
	protected static Logger log = LoggerFactory.getLogger(TestApplication.class);

	
	public PerfMonitorThread(IPktInProcessingTimeService pits)
	{
		this.pits = pits;
		pits.setEnabled(true);
	}
	
	@Override
	public void run() {
		Collection<OneComponentTime> componentTime;
		while (true) {
			
			// test monitor
			CumulativeTimeBucket ctb = pits.getCtb();
			
			if(ctb != null)
			{
				componentTime = ctb.getModules();
				for (OneComponentTime ct : componentTime) {
					String name = ct.getCompName();
					int pktCount = ct.getPktCnt();
					long aveTime = ct.getAvgProcTimeNs();
					long maxTime = ct.getMaxProcTimeNs();
					long minTime = ct.getMinProcTimeNs();

					log.info("{}, pktCount:{}, average:{}, max:{}, min: {}",
							new Object[] { name, pktCount, aveTime, maxTime,
									minTime });
				}
				log.info("");
			}

			try {
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}


/**
 * 
 *  socket for transfer packet to the applications
 *
 */
class ClientThread implements Runnable
{
	private Socket clientSocket;
	private DataInputStream is = null;
	private DataOutputStream os = null;

	public ClientThread(Socket client)
	{
		this.clientSocket = client;
		
		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		while(true)
		{
			connect();
		}
	}
	
	
	public void connect()
	{
		try{
			
			int packetLength;
			packetLength = is.readInt();
			System.out.println("[Client]: we got the packet length :" + packetLength );
			
			byte[] packetBytes = new byte[packetLength];
			
			//this packetBytes is the packet payload in the packet_in
			int bytesRead = is.read(packetBytes);

			if(packetLength != bytesRead)
				System.out.println("[Client]: should be:" + packetLength +
						"but only read: "+ bytesRead);
			else
				System.out.println("[Client]: packet was read successfully");
			
			
			Ethernet eth = new Ethernet();
			eth.deserialize(packetBytes, 0, packetLength);
			
			int packetHash = eth.hashCode();
			
			System.out.println("[Client]: we are going to do DPI...");
			int protocol = doDPI(eth);
			
			// write to the controller
			os.writeInt(packetHash);
			os.writeInt(protocol);
			
			System.out.println("[Client]: result sended");
			
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	/*
	 *  this function do a deep packet inspection and return the packet type as integer
	 *  
	 *  @param eth: the Ethernet instance of a Ethernet packet
	 *  @return: return the protocol index. if not found, return 0
	 */
	public int doDPI(Ethernet eth)
	{
		int protocolIndex;
		IPv4 IPv4Packet;

		byte[] matcherStr;
		String patternStr;

		if(eth.getEtherType()==Ethernet.TYPE_IPv4){
			
			IPv4Packet=(IPv4)eth.getPayload();
			if(IPv4Packet.getProtocol() != IPv4.PROTOCOL_TCP && 
					IPv4Packet.getProtocol() != IPv4.PROTOCOL_UDP)
				return 0;
			
			// l3Payload: tcp or udp header + data
			IPacket l3Payload = IPv4Packet.getPayload();
			// l4Payload: tcp/udp data
			IPacket l4Payload;
			// only deal with tcp or udp packets
			if(IPv4Packet.getProtocol() == IPv4.PROTOCOL_TCP)
				l4Payload =  (TCP)l3Payload.getPayload();
			else
				l4Payload = (UDP)l3Payload.getPayload();

			if(l4Payload != null)
			{
				protocolIndex=0;
				while(protocolIndex < protocolList.list.size())
				{
					patternStr=protocolList.list.get(protocolIndex).getPattern();
					matcherStr=l4Payload.serialize();
					// this is a real DPI. i.e. doing regular express find
					if(REJNI.Match(patternStr,matcherStr,matcherStr.length)==true)
					{
						return protocolIndex;
					}
				}
			}
		}
		return 0;
	}


	
	
//	private int doDPI(Ethernet eth) throws Exception
//	{
//		// store the packet hash
//		MACAddress srcMac =  eth.getSourceMAC();
//		MACAddress dstMac = eth.getDestinationMAC();
//		IPacket pkt = (IPacket) eth.getPayload();
//			
//		if(!(pkt instanceof IPv4))
//			throw new Exception();
//		
//		IPv4 ipv4Packet = (IPv4) eth.getPayload();
//		int srcIP = ipv4Packet.getSourceAddress();
//		int dstIP = ipv4Packet.getDestinationAddress();
//		byte IPprotocol =  ipv4Packet.getProtocol();
//		
//		StringBuilder sb5Tuple = new StringBuilder();
//		sb5Tuple.append(srcMac.toString() + dstMac.toString());
//		sb5Tuple.append(srcIP);
//		sb5Tuple.append(dstIP);
//		sb5Tuple.append(IPprotocol);
//		
//		try{
//			Thread.sleep(1000);
//		} catch(Exception e) { e.printStackTrace();}
//		
//		int flowHash = sb5Tuple.hashCode();
//		
//		// QQLive
//		return 61;
//	}
}












