package net.floodlightcontroller.distributeapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.util.MACAddress;

public class CommunicationServer implements Runnable{
	
	// map<flowHash,protocol>
	// TODO this should be implemented with storage
	private Map<Integer,Integer> flowProtocolMap =  new HashMap<Integer,Integer>();
	// hashmap store the packethash to flowhash. <packethash,flowhash>
	private Map<Integer,Integer> packetFlowMap = new HashMap<Integer,Integer>();
	
	// store the threads
	public List<WriteThread> writeList = new ArrayList<WriteThread>();
	public List<ReadThread> readList = new ArrayList<ReadThread>();
	
	private int port;
	private Ethernet eth = new Ethernet();
	private ServerSocket serverSocket;
	private Object sendLock = new Object();
	private boolean threadShouldExit = false;
	
	private int packetAskedToSend = 0;
	private int packetSended = 0;
	private int packetReceived = 0;
	
	public CommunicationServer(int port)
	{
		this.port = port;
		Thread ServerThread = new Thread(this);
		ServerThread.start();
	}
	
	@Override
	public void run()
	{
		startServerSocket();
	}
	
	private void startServerSocket()
	{
		serverSocket = null;
		Socket clientSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("[Server]: we start the server socket");
			while(true)
			{
				clientSocket = serverSocket.accept();
				System.out.println("[Server]: New Client comming,start a new thread!");
				DataInputStream is = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
				
				// create a write thread
				WriteThread wt = new WriteThread(os);
				new Thread(wt).start();
				
				// create read thread
				ReadThread rt = new ReadThread(is);
				new Thread(rt).start();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				// close the client socket
				if(clientSocket != null)
					clientSocket.close();
			} catch(IOException e) { e.printStackTrace(); }
		}
	}
	
	public void sendPacket(Ethernet ethnet)
	{
		synchronized(sendLock)
		{
			synchronized(eth){
				this.eth = ethnet;
			}
			sendLock.notify();
			packetAskedToSend++;
		}
		System.out.println("packetAskedToSend: "+ packetAskedToSend);
	}
	
	
	private class WriteThread implements Runnable
	{
		public DataOutputStream out;
		private Ethernet ethnetPakcet;

		public WriteThread(DataOutputStream out)
		{
			this.out = out;
		}
		
		@Override
		public void run()
		{
			// write to client when data is available
			while(!threadShouldExit)
			{
				try {
					synchronized(sendLock){
						sendLock.wait();
						// we need the 
						synchronized(eth){
							ethnetPakcet = eth;
						}
					}
					packetSended ++;
					System.out.println("[Server]: packetSended: "+ packetSended);

					System.out.println("[Server]: we are waked up");
					
					// only send ipv4 packets
					IPacket pkt =(IPacket) ethnetPakcet.getPayload();
					if(!(pkt instanceof IPv4))
					{
						continue;
					}
					IPv4 ipv4Packet = (IPv4)pkt;
					
					// store the packet hash
					MACAddress srcMac =  ethnetPakcet.getSourceMAC();
					MACAddress dstMac = ethnetPakcet.getDestinationMAC();
					int srcIP = ipv4Packet.getSourceAddress();
					int dstIP = ipv4Packet.getDestinationAddress();
					byte IPprotocol =  ipv4Packet.getProtocol();
					
					StringBuilder sb5Tuple = new StringBuilder();
					sb5Tuple.append(srcMac.toString() + dstMac.toString());
					sb5Tuple.append(srcIP);
					sb5Tuple.append(dstIP);
					sb5Tuple.append(IPprotocol);
										
					// data available, send it out
					byte[] packet = ethnetPakcet.serialize();
					
					// TODO maybe we should calculate the eth.serilize().hashcode(); 
					packetFlowMap.put(ethnetPakcet.hashCode(), sb5Tuple.hashCode());

					out.writeInt(packet.length);
					// write data
					out.write(packet);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
	}
	
	// this is a read thread to read data from client
	private class ReadThread implements Runnable
	{
		private DataInputStream din;

		public ReadThread(DataInputStream in)
		{
			this.din = in;
		}
		
		@Override
		public void run()
		{
			while(!threadShouldExit)
			{
				try
				{
					int packetHash = din.readInt();
					int packetProtocol = din.readInt();
					// TODO to deal with different result in the same flow
					// may be a priority of different protocols
					int flowHash = packetFlowMap.get(packetHash);
					flowProtocolMap.put(flowHash, packetProtocol);
					
					packetReceived++;
					System.out.println("[Server]: packet received: " + packetReceived);
					System.out.println("[Server]: got a protocol :" + packetProtocol);
					
				} catch(Exception ex)
				{
					ex.printStackTrace();
					try {
						din.close();
					} catch (IOException e) {e.printStackTrace();}
					return ;
				}
			}
		}
	}
}
















