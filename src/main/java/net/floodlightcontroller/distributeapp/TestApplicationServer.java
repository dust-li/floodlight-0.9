package net.floodlightcontroller.distributeapp;

import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import java.io.*;

import net.floodlightcontroller.packet.Ethernet;


public class TestApplicationServer implements Runnable{
	private int port;
	private static int packetHandledCount;
	
	public List<HandlerThread> handlerList;
	
	public TestApplicationServer(int serverPort)
	{
		port = serverPort;
	}
	
	@Override
	public void run()
	{
		try{
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("TestApplicationServer thread started!!!!!");
			while(true)
			{
				Socket client = serverSocket.accept();
				System.out.println("New Client comming,start a new thread!");
			    handlerList.add(new HandlerThread(client));
			}
		}catch(Exception ex)
		{
			System.out.println("Server Exception:" + ex.getMessage());
		}
	}

	/*
	 * client handler.
	 * actually only one client we need to handle
	 */
	class HandlerThread implements Runnable
	{
		private Socket socket;
		
		public HandlerThread(Socket client)
		{
			socket = client;
			Thread clientHandler = new Thread(this);
			clientHandler.start();
		}
		public void run()
		{
			int hashCode;
			DataInputStream is = null;
			DataOutputStream os = null;
			try {
				is = new DataInputStream(socket.getInputStream());
				os = new DataOutputStream(socket.getOutputStream());
				while(true)
				{
					// wait for a little time(0.1s) and  just send the result back;
					int packetLength = is.readInt();
					byte[] packetByte = new byte[packetLength];
					//this packetByte is the packet payload in the packet_in
					int bytesRead = is.read(packetByte);
					if(packetLength != bytesRead)
						System.out.println("should be:" + packetLength +
								"but only read: "+ bytesRead);
					
					// print the packet in hex
					System.out.println("Server: we got a message from the client!");
					System.out.println("packet size: " + packetLength);
					System.out.println(byteArrayToHex(packetByte));
					
					hashCode = packetByte.hashCode();
					
					int protocol = doDPI(packetByte);
					
					os.writeInt(protocol);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/*
		 * fake doing DPI , we just return a answer here
		 */
		private int doDPI(byte[] packet)
		{
			Ethernet eth = new Ethernet();
			eth.deserialize(packet, 0, packet.length);
			// doing DPI
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			packetHandledCount++;
			
			return 100;		//SIP 
		}
		
		/*
		 * 
		 */
		private String byteArrayToHex(byte[] a) {
			   StringBuilder sb = new StringBuilder();
			   for(byte b: a)
			      sb.append(String.format("%02x", b&0xff));
			   return sb.toString();
			}
	}

}
