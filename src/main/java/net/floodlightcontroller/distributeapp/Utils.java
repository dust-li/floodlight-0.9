package net.floodlightcontroller.distributeapp;

import net.floodlightcontroller.packet.IPacket;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.TCP;
import net.floodlightcontroller.packet.UDP;

public class Utils {
	
	// int to a  byte[4]
	 public static byte[] intToByteArray(int i) {   
		  byte[] result = new byte[4];   
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
	}
	 
	 // 4 bytes to a integer
	 public static int byteArrayToInt(byte[] b)
	 {
		 int result = 0;
		 result = b[0] + b[1] * (1 << 8) + b[2] * (1 << 16) + b[3] * (1 << 24);
		 return result;
	 }

	/*
	 *  get a flow hash from a packet
	 *  源IP地址，目的IP地址，源端口，目的端口，和传输层协议号
	 *  @param eth. the IPv4 packet
	 *  @return. the 5 tuple hash of the packet
	 */
	public static int getFlowHash(IPv4 ipPacket) throws Exception
	{
		if(!(ipPacket instanceof IPv4))
			throw new Exception("Packet not IP packet");
		
		byte l4Protocol = ipPacket.getProtocol();
		if(l4Protocol != IPv4.PROTOCOL_TCP && l4Protocol != IPv4.PROTOCOL_UDP)
			throw new Exception("Packet not TCP or UDP packet");
		
		int srcIP = ipPacket.getSourceAddress();
		int dstIP = ipPacket.getDestinationAddress();
		
		short srcPort,dstPort;
		IPacket l4Packet;
		if(l4Protocol == IPv4.PROTOCOL_TCP)
		{
			l4Packet = ipPacket.getPayload();
			srcPort = ((TCP)l4Packet).getSourcePort();
			dstPort = ((TCP)l4Packet).getDestinationPort();
		}
		else
		{
			l4Packet = ipPacket.getPayload();
			srcPort = ((UDP)l4Packet).getSourcePort();
			dstPort = ((UDP)l4Packet).getDestinationPort();
		}

		StringBuilder sb5Tuple = new StringBuilder();
		sb5Tuple.append(srcIP);
		sb5Tuple.append(dstIP);
		sb5Tuple.append(srcPort);
		sb5Tuple.append(dstPort);
		sb5Tuple.append(l4Protocol);
		
		return sb5Tuple.toString().hashCode();
	}
	
}
