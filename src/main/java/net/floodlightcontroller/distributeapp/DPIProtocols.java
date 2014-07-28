package net.floodlightcontroller.distributeapp;

import java.util.*;

/*
 * this class store the protocols the nDPI can recognize
 */
public class DPIProtocols {
	
	public static Map<Integer, String> protocolMap;
	static {
		protocolMap = new HashMap<Integer, String>();
		
		protocolMap.put(0,"NDPI_PROTOCOL_UNKNOWN");
		protocolMap.put(73,"NDPI_PROTOCOL_IP_VRRP");
		protocolMap.put(79,"NDPI_PROTOCOL_IP_IPSEC");
		protocolMap.put(80,"NDPI_PROTOCOL_IP_GRE");
		protocolMap.put(81,"NDPI_PROTOCOL_IP_ICMP");
		protocolMap.put(82,"NDPI_PROTOCOL_IP_IGMP");
		protocolMap.put(83,"NDPI_PROTOCOL_IP_EGP");
		protocolMap.put(84,"NDPI_PROTOCOL_IP_SCTP");
		protocolMap.put(85,"NDPI_PROTOCOL_IP_OSPF");
		protocolMap.put(86,"NDPI_PROTOCOL_IP_IP_IN_IP");
		protocolMap.put(102,"NDPI_PROTOCOL_IP_ICMPV6");
		protocolMap.put(7,"NDPI_PROTOCOL_HTTP");
		protocolMap.put(60,"NDPI_PROTOCOL_HTTP_APPLICATION_VEOHTV");
		protocolMap.put(64,"NDPI_PROTOCOL_SSL_NO_CERT");
		protocolMap.put(91,"NDPI_PROTOCOL_SSL");
		protocolMap.put(110,"NDPI_PROTOCOL_HTTP_APPLICATION_ACTIVESYNC");
		protocolMap.put(130,"NDPI_PROTOCOL_HTTP_CONNECT");
		protocolMap.put(131,"NDPI_PROTOCOL_HTTP_PROXY");
		protocolMap.put(172,"NDPI_PROTOCOL_SOCKS5");
		protocolMap.put(173,"NDPI_PROTOCOL_SOCKS4");
		protocolMap.put(1,"NDPI_PROTOCOL_FTP_CONTROL");
		protocolMap.put(2,"NDPI_PROTOCOL_MAIL_POP");
		protocolMap.put(3,"NDPI_PROTOCOL_MAIL_SMTP");
		protocolMap.put(4,"NDPI_PROTOCOL_MAIL_IMAP");
		protocolMap.put(5,"NDPI_PROTOCOL_DNS");
		protocolMap.put(6,"NDPI_PROTOCOL_IPP");
		protocolMap.put(8,"NDPI_PROTOCOL_MDNS");
		protocolMap.put(9,"NDPI_PROTOCOL_NTP");
		protocolMap.put(10,"NDPI_PROTOCOL_NETBIOS");
		protocolMap.put(11,"NDPI_PROTOCOL_NFS");
		protocolMap.put(12,"NDPI_PROTOCOL_SSDP");
		protocolMap.put(13,"NDPI_PROTOCOL_BGP");
		protocolMap.put(14,"NDPI_PROTOCOL_SNMP");
		protocolMap.put(15,"NDPI_PROTOCOL_XDMCP");
		protocolMap.put(16,"NDPI_PROTOCOL_SMB");
		protocolMap.put(17,"NDPI_PROTOCOL_SYSLOG");
		protocolMap.put(18,"NDPI_PROTOCOL_DHCP");
		protocolMap.put(19,"NDPI_PROTOCOL_POSTGRES");
		protocolMap.put(20,"NDPI_PROTOCOL_MYSQL");
		protocolMap.put(21,"NDPI_PROTOCOL_TDS");
		protocolMap.put(22,"NDPI_PROTOCOL_DIRECT_DOWNLOAD_LINK");
		protocolMap.put(23,"NDPI_PROTOCOL_MAIL_POPS");
		protocolMap.put(24,"NDPI_PROTOCOL_APPLEJUICE");
		protocolMap.put(25,"NDPI_PROTOCOL_DIRECTCONNECT");
		protocolMap.put(26,"NDPI_PROTOCOL_SOCRATES");
		protocolMap.put(27,"NDPI_PROTOCOL_WINMX");
		protocolMap.put(28,"NDPI_PROTOCOL_VMWARE");
		protocolMap.put(29,"NDPI_PROTOCOL_MAIL_SMTPS");
		protocolMap.put(30,"NDPI_PROTOCOL_FILETOPIA");
		protocolMap.put(31,"NDPI_PROTOCOL_IMESH");
		protocolMap.put(32,"NDPI_PROTOCOL_KONTIKI");
		protocolMap.put(33,"NDPI_PROTOCOL_OPENFT");
		protocolMap.put(34,"NDPI_PROTOCOL_FASTTRACK");
		protocolMap.put(35,"NDPI_PROTOCOL_GNUTELLA");
		protocolMap.put(36,"NDPI_PROTOCOL_EDONKEY");
		protocolMap.put(37,"NDPI_PROTOCOL_BITTORRENT");
		protocolMap.put(38,"NDPI_PROTOCOL_EPP");
		protocolMap.put(47,"NDPI_PROTOCOL_XBOX");
		protocolMap.put(48,"NDPI_PROTOCOL_QQ");
		protocolMap.put(49,"NDPI_PROTOCOL_MOVE");
		protocolMap.put(50,"NDPI_PROTOCOL_RTSP");
		protocolMap.put(51,"NDPI_PROTOCOL_MAIL_IMAPS");
		protocolMap.put(52,"NDPI_PROTOCOL_ICECAST");
		protocolMap.put(53,"NDPI_PROTOCOL_PPLIVE");
		protocolMap.put(54,"NDPI_PROTOCOL_PPSTREAM");
		protocolMap.put(55,"NDPI_PROTOCOL_ZATTOO");
		protocolMap.put(56,"NDPI_PROTOCOL_SHOUTCAST");
		protocolMap.put(57,"NDPI_PROTOCOL_SOPCAST");
		protocolMap.put(58,"NDPI_PROTOCOL_TVANTS");
		protocolMap.put(59,"NDPI_PROTOCOL_TVUPLAYER");
		protocolMap.put(61,"NDPI_PROTOCOL_QQLIVE");
		protocolMap.put(62,"NDPI_PROTOCOL_THUNDER");
		protocolMap.put(63,"NDPI_PROTOCOL_SOULSEEK");
		protocolMap.put(65,"NDPI_PROTOCOL_IRC");
		protocolMap.put(66,"NDPI_PROTOCOL_AYIYA");
		protocolMap.put(67,"NDPI_PROTOCOL_UNENCRYPED_JABBER");
		protocolMap.put(68,"NDPI_PROTOCOL_MSN");
		protocolMap.put(69,"NDPI_PROTOCOL_OSCAR");
		protocolMap.put(70,"NDPI_PROTOCOL_YAHOO");
		protocolMap.put(71,"NDPI_PROTOCOL_BATTLEFIELD");
		protocolMap.put(72,"NDPI_PROTOCOL_QUAKE");
		protocolMap.put(74,"NDPI_PROTOCOL_STEAM");
		protocolMap.put(75,"NDPI_PROTOCOL_HALFLIFE2");
		protocolMap.put(76,"NDPI_PROTOCOL_WORLDOFWARCRAFT");
		protocolMap.put(77,"NDPI_PROTOCOL_TELNET");
		protocolMap.put(78,"NDPI_PROTOCOL_STUN");
		protocolMap.put(87,"NDPI_PROTOCOL_RTP");
		protocolMap.put(88,"NDPI_PROTOCOL_RDP");
		protocolMap.put(89,"NDPI_PROTOCOL_VNC");
		protocolMap.put(90,"NDPI_PROTOCOL_PCANYWHERE");
		protocolMap.put(92,"NDPI_PROTOCOL_SSH");
		protocolMap.put(93,"NDPI_PROTOCOL_USENET");
		protocolMap.put(94,"NDPI_PROTOCOL_MGCP");
		protocolMap.put(95,"NDPI_PROTOCOL_IAX");
		protocolMap.put(96,"NDPI_PROTOCOL_TFTP");
		protocolMap.put(97,"NDPI_PROTOCOL_AFP");
		protocolMap.put(98,"NDPI_PROTOCOL_STEALTHNET");
		protocolMap.put(99,"NDPI_PROTOCOL_AIMINI");
		protocolMap.put(100,"NDPI_PROTOCOL_SIP");
		protocolMap.put(101,"NDPI_PROTOCOL_TRUPHONE");
		protocolMap.put(103,"NDPI_PROTOCOL_DHCPV6");
		protocolMap.put(104,"NDPI_PROTOCOL_ARMAGETRON");
		protocolMap.put(105,"NDPI_PROTOCOL_CROSSFIRE");
		protocolMap.put(106,"NDPI_PROTOCOL_DOFUS");
		protocolMap.put(107,"NDPI_PROTOCOL_FIESTA");
		protocolMap.put(108,"NDPI_PROTOCOL_FLORENSIA");
		protocolMap.put(109,"NDPI_PROTOCOL_GUILDWARS");
		protocolMap.put(111,"NDPI_PROTOCOL_KERBEROS");
		protocolMap.put(112,"NDPI_PROTOCOL_LDAP");
		protocolMap.put(113,"NDPI_PROTOCOL_MAPLESTORY");
		protocolMap.put(114,"NDPI_PROTOCOL_MSSQL");
		protocolMap.put(115,"NDPI_PROTOCOL_PPTP");
		protocolMap.put(116,"NDPI_PROTOCOL_WARCRAFT3");
		protocolMap.put(117,"NDPI_PROTOCOL_WORLD_OF_KUNG_FU");
		protocolMap.put(118,"NDPI_PROTOCOL_MEEBO");
		protocolMap.put(121,"NDPI_PROTOCOL_DROPBOX");
		protocolMap.put(125,"NDPI_PROTOCOL_SKYPE");
		protocolMap.put(127,"NDPI_PROTOCOL_DCERPC");
		protocolMap.put(128,"NDPI_PROTOCOL_NETFLOW");
		protocolMap.put(129,"NDPI_PROTOCOL_SFLOW");
		protocolMap.put(132,"NDPI_PROTOCOL_CITRIX");
		protocolMap.put(136,"NDPI_PROTOCOL_SKYFILE_PREPAID");
		protocolMap.put(137,"NDPI_PROTOCOL_SKYFILE_RUDICS");
		protocolMap.put(138,"NDPI_PROTOCOL_SKYFILE_POSTPAID");
		protocolMap.put(139,"NDPI_PROTOCOL_CITRIX_ONLINE");
		protocolMap.put(141,"NDPI_PROTOCOL_WEBEX");
		protocolMap.put(144,"NDPI_PROTOCOL_VIBER");
		protocolMap.put(146,"NDPI_PROTOCOL_RADIUS");
		protocolMap.put(147,"NDPI_PROTOCOL_WINDOWS_UPDATE");
		protocolMap.put(148,"NDPI_PROTOCOL_TEAMVIEWER");
		protocolMap.put(150,"NDPI_PROTOCOL_LOTUS_NOTES");
		protocolMap.put(151,"NDPI_PROTOCOL_SAP");
		protocolMap.put(152,"NDPI_PROTOCOL_GTP");
		protocolMap.put(153,"NDPI_PROTOCOL_UPNP");
		protocolMap.put(154,"NDPI_PROTOCOL_LLMNR");
		protocolMap.put(155,"NDPI_PROTOCOL_REMOTE_SCAN");
		protocolMap.put(156,"NDPI_PROTOCOL_SPOTIFY");
		protocolMap.put(158,"NDPI_PROTOCOL_H323");
		protocolMap.put(159,"NDPI_PROTOCOL_OPENVPN");
		protocolMap.put(160,"NDPI_PROTOCOL_NOE");
		protocolMap.put(161,"NDPI_PROTOCOL_CISCOVPN");
		protocolMap.put(162,"NDPI_PROTOCOL_TEAMSPEAK");
		protocolMap.put(163,"NDPI_PROTOCOL_TOR");
		protocolMap.put(164,"NDPI_PROTOCOL_SKINNY");
		protocolMap.put(165,"NDPI_PROTOCOL_RTCP");
		protocolMap.put(166,"NDPI_PROTOCOL_RSYNC");
		protocolMap.put(167,"NDPI_PROTOCOL_ORACLE");
		protocolMap.put(168,"NDPI_PROTOCOL_CORBA");
		protocolMap.put(169,"NDPI_PROTOCOL_UBUNTUONE");
		protocolMap.put(170,"NDPI_PROTOCOL_WHOIS_DAS");
		protocolMap.put(171,"NDPI_PROTOCOL_COLLECTD");
		protocolMap.put(174,"NDPI_PROTOCOL_RTMP");
		protocolMap.put(175,"NDPI_PROTOCOL_FTP_DATA");
		protocolMap.put(185,"NDPI_PROTOCOL_PANDO");
		protocolMap.put(39,"NDPI_CONTENT_AVI");
		protocolMap.put(40,"NDPI_CONTENT_FLASH");
		protocolMap.put(41,"NDPI_CONTENT_OGG");
		protocolMap.put(42,"NDPI_CONTENT_MPEG");
		protocolMap.put(43,"NDPI_CONTENT_QUICKTIME");
		protocolMap.put(44,"NDPI_CONTENT_REALMEDIA");
		protocolMap.put(45,"NDPI_CONTENT_WINDOWSMEDIA");
		protocolMap.put(46,"NDPI_CONTENT_MMS");
		protocolMap.put(157,"NDPI_CONTENT_WEBM");
		protocolMap.put(119,"NDPI_SERVICE_FACEBOOK");
		protocolMap.put(120,"NDPI_SERVICE_TWITTER");
		protocolMap.put(122,"NDPI_SERVICE_GMAIL");
		protocolMap.put(123,"NDPI_SERVICE_GOOGLE_MAPS");
		protocolMap.put(124,"NDPI_SERVICE_YOUTUBE");
		protocolMap.put(126,"NDPI_SERVICE_GOOGLE");
		protocolMap.put(133,"NDPI_SERVICE_NETFLIX");
		protocolMap.put(134,"NDPI_SERVICE_LASTFM");
		protocolMap.put(135,"NDPI_SERVICE_GROOVESHARK");
		protocolMap.put(140,"NDPI_SERVICE_APPLE");
		protocolMap.put(142,"NDPI_SERVICE_WHATSAPP");
		protocolMap.put(143,"NDPI_SERVICE_APPLE_ICLOUD");
		protocolMap.put(145,"NDPI_SERVICE_APPLE_ITUNES");
		protocolMap.put(149,"NDPI_SERVICE_TUENTI");
		protocolMap.put(176,"NDPI_SERVICE_WIKIPEDIA");
		protocolMap.put(177,"NDPI_SERVICE_MSN");
		protocolMap.put(178,"NDPI_SERVICE_AMAZON");
		protocolMap.put(179,"NDPI_SERVICE_EBAY");
		protocolMap.put(180,"NDPI_SERVICE_CNN");
		protocolMap.put(181,"NDPI_SERVICE_DROPBOX");
		protocolMap.put(182,"NDPI_SERVICE_SKYPE");
		protocolMap.put(183,"NDPI_SERVICE_VIBER");
		protocolMap.put(184,"NDPI_SERVICE_YAHOO");
    }
}
