package net.floodlightcontroller.distributeapp;

import java.net.Socket;
import java.io.*;

import net.floodlightcontroller.packet.Ethernet;


public class ComunicationHandler{
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	public int hashCode;
	public byte[] payload;
	
	public ComunicationHandler()
	{
		startSocket();
	}
	
	private void startSocket()
	{
		if(socket == null)
			try{
				socket = new Socket(ComunicationSettings.DPIServerAddress,
						ComunicationSettings.DPIServerPort);
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
			}catch(java.net.BindException be)
			{
				System.out.println("the port was used for other application");
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.err.println("ComunicationHandler init failed!");
				System.err.println("server not started!");
			}
	}
	
	/*
	 * this function is executed when a packet in received  
	 */
	public void sendPacket(Ethernet eth)
	{
		System.out.println("get a new packet_in ,I am going to send it to the server!");
		try {
			if(socket == null)
				socket = new Socket(ComunicationSettings.DPIServerAddress,
						ComunicationSettings.DPIServerPort);
			//send the packet size. packet will be smaller than 1460,so int is enough
			byte[] packet = eth.serialize();
			out.writeInt(packet.length);
			// send the data
			out.write(eth.serialize());
			out.flush();
			
			System.out.println("flushed");
			
			int protocol = in.readInt();
			
			
			System.out.println("answers recieved: " + protocol);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}





