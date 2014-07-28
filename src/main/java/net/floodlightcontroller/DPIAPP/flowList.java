package net.floodlightcontroller.DPIAPP;

import java.util.ArrayList;
import java.util.List;

import net.floodlightcontroller.packet.*;

public class flowList {
	public static List<packetFlow> list=new ArrayList<packetFlow>();
	public static List<Ethernet> ethList=new ArrayList<Ethernet>();
	
	public static int IPPacket=0;
	public static int IPBytes=0;
	public static int searchFlow(int firstAddress,int secondAddress,short firstPort,short secondPort){
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if(list.get(i).search(firstAddress, secondAddress, firstPort, secondPort)==true)
					return i;
			}
		}
		packetFlow tempFlow=new packetFlow(firstAddress, secondAddress, firstPort, secondPort);
		list.add(tempFlow);
		return list.size()-1;
	}
	public static void addPacket(IPv4 packet,int i){
		list.get(i).addPacket(packet);
	}
	public static void add(Ethernet eth){
		ethList.add(eth);
	}
	
	public static void detectionFlow(){
		long startTime,endTime;
		int protocolIndex;
		IPv4 IPv4Packet;
		TCP tcpPacket;UDP udpPacket;
		byte[] matcherStr;
		String patternStr;
		startTime=System.currentTimeMillis();
		Ethernet eth=new Ethernet();
		int firstAddress,secondAddress;
		short firstPort,secondPort;
		int flowIndex;

		for(int i=0;i<100000;i++){
			eth=ethList.get(i);
			if(eth.getEtherType()==Ethernet.TYPE_IPv4){
				flowList.IPPacket++;
				IPv4Packet=(IPv4)eth.getPayload();
				firstAddress=IPv4Packet.getSourceAddress();
				secondAddress=IPv4Packet.getDestinationAddress();
				if(IPv4Packet.getProtocol()==IPv4.PROTOCOL_TCP){
					tcpPacket=(TCP)IPv4Packet.getPayload();
					if(tcpPacket.getPayload()!=null){
						firstPort=tcpPacket.getSourcePort();
						secondPort=tcpPacket.getDestinationPort();
						flowIndex=flowList.searchFlow(firstAddress,secondAddress,firstPort,secondPort);
						flowList.addPacket(IPv4Packet, flowIndex);
						if(flowList.list.get(flowIndex).getProtocol().equals("UNKNOW")){
								protocolIndex=0;
								while(protocolIndex < protocolList.list.size() && flowList.list.get(flowIndex).getProtocol().equals("UNKNOW")){
									patternStr=protocolList.list.get(protocolIndex).getPattern();
									//patternStr="^.?.?\\x02.+\\x03$";
									matcherStr=tcpPacket.getPayload().serialize();
									//flowList.list.get(flowIndex).bytes+=matcherStr.length;
									//flowList.IPBytes+=matcherStr.length;
									if(REJNI.Match(patternStr,matcherStr,matcherStr.length)==true){
										flowList.list.get(flowIndex).setProtocol(protocolList.list.get(protocolIndex).getName());
										break;
										}
									//else{System.out.print("No!\n");}
									protocolIndex++;
								}
						}
					}								
				}
				if(IPv4Packet.getProtocol()==IPv4.PROTOCOL_UDP){
					udpPacket=(UDP)IPv4Packet.getPayload();
					if(udpPacket.getPayload()!=null){
						firstPort=udpPacket.getSourcePort();
						secondPort=udpPacket.getDestinationPort();
						flowIndex=flowList.searchFlow(firstAddress,secondAddress,firstPort,secondPort);
						flowList.addPacket(IPv4Packet, flowIndex);
						if(flowList.list.get(flowIndex).getProtocol().equals("UNKNOW")){
								protocolIndex=0;						
								while((protocolIndex<protocolList.list.size())&&(flowList.list.get(flowIndex).getProtocol().equals("UNKNOW"))){
									patternStr=protocolList.list.get(protocolIndex).getPattern();
									//patternStr="^.?.?\\x02.+\\x03$";
									matcherStr=udpPacket.getPayload().serialize();
									//flowList.list.get(flowIndex).bytes+=matcherStr.length;
									//flowList.IPBytes+=matcherStr.length;
									if(REJNI.Match(patternStr,matcherStr,matcherStr.length)==true){											
										flowList.list.get(flowIndex).setProtocol(protocolList.list.get(protocolIndex).getName());
										break;
									}
									protocolIndex++;								
							}
						}
					}
				}
			}
		}
		endTime=System.currentTimeMillis();
		System.out.println("******************The run time is  "+(endTime-startTime)+"ms****************************\n");
		flowList.printResult();
	}
	public static void printList(){
		int flowIndex;
		for(flowIndex=0;flowIndex<list.size();flowIndex++){
			System.out.print("Flow "+flowIndex+": ");
			list.get(flowIndex).printFlow();
		}
		//System.out.print("Total packet number is----->"+packetnum+"\n");
		//System.out.print("Total pakcktIn packet number is----->"+my+"\n");
	}
	public static void printResult(){
		System.out.println("Traffic contains");
		System.out.println("        IP Packet:"+flowList.IPPacket+"   of   "+ethList.size()+"packets total");
		System.out.println("        IP bytes:"+flowList.IPBytes);
		System.out.println("        Unique flows:"+flowList.list.size());
		System.out.println("Detected protocols");
		flowList.printProtocols();
	}
	public static void printProtocols(){
		String tempName;
		List<Detectedpro> ll=new ArrayList<Detectedpro>();
		Detectedpro temp=new Detectedpro();
		temp.name=flowList.list.get(0).getProtocol();
		temp.flows=1;;
		temp.packetNum=flowList.list.get(0).packetNum;
		ll.add(temp);
		int index;
		for(int i=1;i<flowList.list.size();i++){
			tempName=flowList.list.get(i).getProtocol();
			for(index=0;index<ll.size();index++)
				if(ll.get(index).name.equals(tempName))
					break;
			if(index==ll.size()){
				Detectedpro tempp=new Detectedpro();
				tempp.name=tempName;
				tempp.flows=1;
				tempp.packetNum=flowList.list.get(i).packetNum;
				ll.add(tempp);
			}
			else{
				ll.get(index).flows++;
				ll.get(index).packetNum+=flowList.list.get(i).packetNum;
			}
		}
		for(index=0;index<ll.size();index++){
			System.out.println("        "+ll.get(index).name+"        packets:"+ll.get(index).packetNum+"        bytes:0        flows:"+ll.get(index).flows);
		}
	}
}
