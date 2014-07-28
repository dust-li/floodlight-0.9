package net.floodlightcontroller.DPIAPP;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.floodlightcontroller.packet.*;

public class packetFlow {

	public final int protocolCounter = 170;
	public final int lowerAddress;
	public final int higherAddress;
	public final short lowerPort;
	public final short higherPort;
	public final List<IPv4> packetList;

	public String protocol;
	public int packetNum;
	public int bytes;

	public packetFlow(int firstAddress, int secondAddress, short firstPort,
			short secondPort) {
		int lowerAddress = firstAddress < secondAddress ? firstAddress
				: secondAddress;
		int higherAddress = firstAddress > secondAddress ? firstAddress
				: secondAddress;
		short lowerPort = firstPort < secondPort ? firstPort : secondPort;
		short higherPort = firstPort > secondPort ? firstPort : secondPort;
		this.protocol = "UNKNOW";
		this.lowerAddress = lowerAddress;
		this.higherAddress = higherAddress;
		this.lowerPort = lowerPort;
		this.higherPort = higherPort;
		this.packetList = new ArrayList<IPv4>();
		this.packetNum = 0;
		this.bytes = 0;
	}

	public boolean search(int firstAddress, int secondAddress, short firstPort,
			short secondPort) {
		int lowerAddress = firstAddress < secondAddress ? firstAddress
				: secondAddress;
		int higherAddress = firstAddress > secondAddress ? firstAddress
				: secondAddress;
		short lowerPort = firstPort < secondPort ? firstPort : secondPort;
		short higherPort = firstPort > secondPort ? firstPort : secondPort;
		if (this.lowerAddress == lowerAddress
				&& this.higherAddress == higherAddress
				&& this.lowerPort == lowerPort && this.higherPort == higherPort) {
			return true;
		}
		return false;
	}

	public void addPacket(IPv4 packet) {
		this.packetList.add(packet);
		this.packetNum++;
	}

	public boolean portDetection() {
		// System.out.print("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
		switch (this.lowerPort) {
		case 21:
			this.protocol = "FTP";
			return true;
		case 22:
			this.protocol = "SSH";
			return true;
		case 23:
			this.protocol = "TELNET";
			return true;
		case 25:
			this.protocol = "SMTP";
			return true;
		case 53:
			this.protocol = "DNS";
			return true;
		case 79:
			this.protocol = "FINGER";
			return true;
		case 80:
			this.protocol = "HTTP";
			return true;
		case 109:
			this.protocol = "POP3";
			return true;
		case 993:
			this.protocol = "IMAP";
			return true;
		default:
			break;
		}
		if (this.protocol == "UNKNOW") {
			switch (this.higherPort) {
			case 21:
				this.protocol = "FTP";
				return true;
			case 22:
				this.protocol = "SSH";
				return true;
			case 23:
				this.protocol = "TELNET";
				return true;
			case 25:
				this.protocol = "SMTP";
				return true;
			case 53:
				this.protocol = "DNS";
				return true;
			case 79:
				this.protocol = "FINGER";
				return true;
			case 80:
				this.protocol = "HTTP";
				return true;
			case 109:
				this.protocol = "POP3";
				return true;
			case 993:
				this.protocol = "IMAP";
				return true;
			default:
				break;
			}
		}
		return false;
	}

	public boolean detectionPacket() {
		int packetIndex;
		int protocolIndex;
		IPv4 IPv4Packet;
		TCP tcpPacket;
		UDP udpPacket;
		String patternStr;
		byte[] matcherStr;

		packetIndex = packetList.size() - 1;
		IPv4Packet = packetList.get(packetIndex);
		if (IPv4Packet.getProtocol() == IPv4.PROTOCOL_TCP) {
			tcpPacket = (TCP) IPv4Packet.getPayload();
			protocolIndex = 0;
			patternStr = "^.?.?\\x02.+\\x03$";
			matcherStr = tcpPacket.getPayload().serialize();
			if (REJNI.Match(patternStr, matcherStr, 2) == true) {
				System.out.println("YES!");
			} else {
				System.out.println("NO!");
			}
		} else {
			udpPacket = (UDP) IPv4Packet.getPayload();
			protocolIndex = 0;
			// while((protocolIndex<protocolList.list.size())&&(this.protocol.equals("UNKNOW"))){
			patternStr = "^.?.?\\x02.+\\x03$";
			matcherStr = udpPacket.getPayload().serialize();
			if (REJNI.Match(patternStr, matcherStr, 2) == true) {
				System.out.println("HahahahahahahaYES!");
			} else {
				System.out.println("wuwuwuuwuwuwuNO!");
			}

		}

		return false;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String pro) {
		this.protocol = pro;
	}

	public void printFlow() {
		String higherAddress = IPv4.fromIPv4Address(this.higherAddress);
		String lowerAddress = IPv4.fromIPv4Address(this.lowerAddress);
		System.out.print(higherAddress + ": " + this.higherPort + "---->"
				+ lowerAddress + ": " + this.lowerPort + "     "
				+ this.protocol + "      " + this.packetList.size() + "\n");
	}
}
