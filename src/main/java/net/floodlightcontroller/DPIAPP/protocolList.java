package net.floodlightcontroller.DPIAPP;

import java.util.ArrayList;
import java.util.List;

public class protocolList 
{
	public static List<protocolElem> list=new ArrayList<protocolElem>();
	//public static RingBuffer<String>proList=new RingBuffer<String>(String.class,protocolList.list.size());
	public static protocolElem createElem(String name,int weight,String pattern){
		protocolElem temp=new protocolElem(name,weight,pattern);
		return temp;
	}
	
	public static void initProtocol(){
		protocolElem elem=new protocolElem("",1,"");
		debugString debugStr=new debugString();
		//[0-9]//
		String pat09="3[0-9]";
		//[a-z]//
		String pataz="6[1-9a-f]|7[0-9a]";
		//1-9//
		String pat19="3[1-9]";
		//\x01-?//
		String pat1="0[1-9a-f]|[123][0-9a-f]";
		//a-z 0-9//
		String pat09az=pat09+"|"+pataz;
		//\x01-?a-z
		String pat2=pat1+"|"+pataz;
		//******[ -~]******//
		String pat3="[23456][0-9a-f]|7[0-9]|7[a-e]";
		//******[!-~]******//
		String pat4="2[1-9]|3[a-f]|[3456][0-9a-f]|7[0-9]|7[a-e]";
		String str5="[026789ef][0-9a-f]|[3a][012]";
		if(debugStr.BAO==true){
			elem=createElem("bao",1,"0101050a");
			list.add(elem);
		}
		if(debugStr. AIM==true){
			elem=createElem("aim",1,"(\\*0[12].*030b|\\*01.?.?.?.?01)|flapon|toc_signon.*0x");
			list.add(elem);
		}
		if(debugStr. AIMWEBCONTENT==true){
			elem=createElem("aimwebcontent",1,"user-agent:aim/");
			list.add(elem);
		}
		if(debugStr. APPLEJUICE==true){
			elem=createElem("applejuice",1,"^ajprot0d0a");
			list.add(elem);
		}
		if(debugStr. ARES==true){
			elem=createElem("ares",1,"^\\x03[]Z].?.?\\x05$");
			list.add(elem);
		}
		if(debugStr. ARMAGETRON==true){
			elem=createElem("armagetron",1,"YCLC_E|CYEL");
			list.add(elem);
		}
		if(debugStr. BATTLEFIELD1942==true){
			elem=createElem("battlefield1942",1,"^\\x01\\x11\\x10\\|\\xf8\\x02\\x10\\x40\\x06");
			list.add(elem);
		}
		if(debugStr. BATTLEFIELD2==true){
			elem=createElem("battlefield2",1,"^(\\x11\\x20\\x01...?\\x11|\\xfe\\xfd.?.?.?.?.?.?(\\x14\\x01\\x06|\\xff\\xff\\xff))|[]\\x01].?battlefield2");
			list.add(elem);
		}
		if(debugStr. BATTLEFIELD2142==true){
			elem=createElem("battlefield2142",1,"^(\\x11\\x20\\x01\\x90\\x50\\x64\\x10|\\xfe\\xfd.?.?.?\\x18|[\\x01\\].?battlefield2)");
			list.add(elem);
		}
		if(debugStr. BGP==true){
			elem=createElem("bgp",1,"^^\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff\\xff..?\\x01[\\x03\\x04]");
			list.add(elem);
		}
		if(debugStr. BIFF==true){
			elem=createElem("biff",1,"^[a-z][a-z0-9]+@[1-9][0-9]+$");
			list.add(elem);
		}
		if(debugStr. BITTORRENT==true){
			//     $
			//^(\x13bittorrent protocol|azver\x01$|get /scrape\?info_hash=get /announce\?info_hash=|get /client/bitcomet/|GET /data\?fid=)|d1:ad2:id20:|\x08'7P\)[RP]
			elem=createElem("bittorrent",1,"^(\\x13bittorrent protocol|azver\\x01$|get /scrape\\?info_hash=get /announce\\?info_hash=|get /client/bitcomet/|GET /data\\?fid=)|d1:ad2:id20:|\\x08'7P\\)[RP]");
			list.add(elem);
		}
		if(debugStr. CHIKKA==true){
			elem=createElem("chikka",1,"^CTPv1\\.[123] Kamusta.*\\x0d\\x0a$");
			list.add(elem);
		}
		if(debugStr. CIMD==true){
			elem=createElem("cimd",1,"\\x02[0-4][0-9]:[0-9]+.*\\x03$");
			list.add(elem);
		}
		if(debugStr. CISCOVPN==true){
			elem=createElem("ciscovpn",1,"^\\x01\\xf4\\x01\\xf4");
			list.add(elem);
		}
		if(debugStr. CITRIX==true){
			elem=createElem("citrix",1,"\\x32\\x26\\x85\\x92\\x58");
			list.add(elem);
		}
		if(debugStr. COUNTERSTRIKE_SOURCE==true){
			elem=createElem("counterstrike",1,"^\\xff\\xff\\xff\\xff.*cstrikeCounter-Strike");
			list.add(elem);
		}
		if(debugStr. CVS==true){
			elem=createElem("cvs",1,"^BEGIN (AUTH|VERIFICATION|GSSAPI) REQUEST\\x0a");
			list.add(elem);
		}
		if(debugStr. DAYOFDEFEAT_SOURCE==true){
			elem=createElem("dayofdefeat",1,"^\\xff\\xff\\xff\\xff.*dodDay of Defeat");
			list.add(elem);
		}
		if(debugStr. DAZHIHUI==true){
			elem=createElem("dazhihui",1,"^(longaccoun|qsver2auth|\\x35[57]\\x30|\\+\\x10\\*)");
			list.add(elem);
		}
		if(debugStr. DHCP==true){
			//elem=createElem("dhcp",1,"^[\\x01\\x02][\\x01- ]\\x06.*c82sc");
			elem=createElem("dhcp",1,"^[\\x01\\x02][\\x01- ]\\x06.*c\\x82sc");
			list.add(elem);
		}
		if(debugStr. DIRECTCONNECT==true){
			elem=createElem("directconnect",1,"^(\\$mynick |\\$lock |\\$key )");
			list.add(elem);
		}
		if(debugStr. DNS==true){
			elem=createElem("dns",1,"^.?.?.?.?[\\x01\\x02].*.?.?.?.?.?.?[\\x01-?][a-z0-9][\\x01-?a-z]*[\\x02-\\x06][a-z][a-z][fglmoprstuvz]?[aeop]?(um)?.*[\\x01-\\x10\\x1c].*[\\x01\\x03\\x04\\xFF]");
			list.add(elem);
		}
		if(debugStr. DOOM3==true){
			elem=createElem("doom3",1,"^\\xff\\xffchallenge");
			list.add(elem);
		}
		if(debugStr. EDONKEY==true){
			//^[\xc5\xd4\xe3-\xe5].?.?.?.?([\x01\x02\x05\x14\x15\x16\x18\x19\x1a\x1b\x1c\x20\x21\x32\x33\x34\x35\x36\x38\x40\x41\x42\x43\x46\x47\x48\x49\x4a\x4b\x4c\x4d\x4e\x4f\x50\x51\x52\x53\x54\x55\x56\x57\x58[\x60\x81\x82\x90\x91\x93\x96\x97\x98\x99\x9a\x9b\x9c\x9e\xa0\xa1\xa2\xa3\xa4]|\x59................?[ -~]|\x96....$)
			elem=createElem("edonkey",1,"^[\\xc5\\xd4\\xe3-\\xe5].?.?.?.?([\\x01\\x02\\x05\\x14\\x15\\x16\\x18\\x19\\x1a\\x1b\\x1c\\x20\\x21\\x32\\x33\\x34\\x35\\x36\\x38\\x40\\x41\\x42\\x43\\x46\\x47\\x48\\x49\\x4a\\x4b\\x4c\\x4d\\x4e\\x4f\\x50\\x51\\x52\\x53\\x54\\x55\\x56\\x57\\x58[\\x60\\x81\\x82\\x90\\x91\\x93\\x96\\x97\\x98\\x99\\x9a\\x9b\\x9c\\x9e\\xa0\\xa1\\xa2\\xa3\\xa4]|\\x59................?[ -~]|\\x96....$)");
			list.add(elem);
		}
		if(debugStr. FASTTRACK==true){
			//^get (/.download/[ -~]*|/.supernode[ -~]|/.status[ -~]|/.network[ -~]*|/.files|/.hash=[0-9a-f]*/[ -~]*) http/1.1|user-agent: kazaa|x-kazaa(-username|-network|-ip|-supernodeip|-xferid|-xferuid|tag)|^give [0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]?[0-9]?[0-9]?
			elem=createElem("fasttrack",1,"^get (/.download/[ -~]*|/.supernode[ -~]|/.status[ -~]|/.network[ -~]*|/.files|/.hash=[0-9a-f]*/[ -~]*) http/1.1|user-agent: kazaa|x-kazaa(-username|-network|-ip|-supernodeip|-xferid|-xferuid|tag)|^give [0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]?[0-9]?[0-9]?");
			list.add(elem);
		}
		if(debugStr. FINGER==true){
			//^[a-z][a-z0-9\-_]+\x0d\x0a|login: [\x09-\x0d -~]* name: [\x09-\x0d -~]* Directory: 
			//***************************************************************************//
			//elem=createElem("finger",1,"^[a-z][a-z0-9\\-_]+|login: [\\x09-\\x0d -~]* name: [\\x09-\\x0d -~]* Directory:");
			elem=createElem("finger",1,"^login: [\\x09-\\x0d -~]* name: [\\x09-\\x0d -~]* Directory:");
			list.add(elem);
		}
		if(debugStr. FREENET==true){
			elem=createElem("freenet",1,"^\\x01[\\x08\\x09][\\x03\\x04]");
			list.add(elem);
		}
		if(debugStr. FTP==true){
			elem=createElem("ftp",1,"^220[\\x09-\\x0d -~]*ftp");
			list.add(elem);
		}
		if(debugStr. GKRELLM==true){
			elem=createElem("gkrellm",1,"^gkrellm [23].[0-9].[0-9]\\x0a$");
			list.add(elem);
		}
		if(debugStr. GNUCLEUSLAN==true){
			elem=createElem("gnucleuslan",1,"gnuclear connect/[\\x09-\\x0d -~]*user-agent: gnucleus [\\x09-\\x0d -~]*lan:");
			list.add(elem);
		}
		if(debugStr. GNUTELLA==true){
			//^(gnd[\x01\x02]?.?.?\x01|gnutella connect/[012]\.[0-9]\x0d\x0a|get /uri-res/n2r\?urn:sha1:|get /.*user-agent: (gtk-gnutella|bearshare|mactella|gnucleus|gnotella|limewire|imesh)|get /.*content-type: application/x-gnutella-packets|giv [0-9]*:[0-9a-f]*/|queue [0-9a-f]* [1-9][0-9]?[0-9]?\.[1-9][0-9]?[0-9]?\.[1-9][0-9]?[0-9]?\.[1-9][0-9]?[0-9]?:[1-9][0-9]?[0-9]?[0-9]?|gnutella.*content-type: application/x-gnutella|...................?lime)
			elem=createElem("gnutella",1,"^(gnd[\\x01\\x02]?.?.?\\x01|gnutella connect/[012]\\.[0-9]\\x0d\\x0a|get /uri-res/n2r\\?urn:sha1:|get /.*user-agent: (gtk-gnutella|bearshare|mactella|gnucleus|gnotella|limewire|imesh)|get /.*content-type: application/x-gnutella-packets|giv [0-9]*:[0-9a-f]*/|queue [0-9a-f]* [1-9][0-9]?[0-9]?\\.[1-9][0-9]?[0-9]?\\.[1-9][0-9]?[0-9]?\\.[1-9][0-9]?[0-9]?:[1-9][0-9]?[0-9]?[0-9]?|gnutella.*content-type: application/x-gnutella|...................?lime)");
			list.add(elem);
		}
		if(debugStr. GOBOOGY==true){
			//<peerplat>|^get /getfilebyhash\.cgi\?|^get /queue_register\.cgi\?|^get /getupdowninfo\.cgi\?
			elem=createElem("goboogy",1,"<peerplat>|^get /getfilebyhash\\.cgi\\?|^get /queue_register\\.cgi\\?|^get /getupdowninfo\\.cgi\\?");
			list.add(elem);
		}
		if(debugStr. GOPHER==true){
			//^[\x09-\x0d]*[1-9,+tgi][\x09-\x0d -~]*\x09[\x09-\x0d -~]*\x09[a-z0-9.]*\.[a-z][a-z].?.?\x09[1-9]
			elem=createElem("gopher",1,"^[\\x09-\\x0d]*[1-9,+tgi][\\x09-\\x0d -~]*\\x09[\\x09-\\x0d -~]*\\x09[a-z0-9.]*\\.[a-z][a-z].?.?\\x09[1-9]");
			list.add(elem);
		}
		if(debugStr. GUILDWARS==true){
			elem=createElem("guildwars",1,"^[\\x04\\x05]\\x0c.i\\x01");
			list.add(elem);
		}
		if(debugStr. H323==true){
			elem=createElem("h323",1,"^\\x03..?\\x08...?.?.?.?.?.?.?.?.?.?.?.?.?.?.?\\x05");
			list.add(elem);
		}
		if(debugStr. HALFLIFE2_DEATHMATCH==true){
			elem=createElem("halflife2_deathmatch",1,"^\\xff\\xff\\xff\\xff.*hl2mpDeathmatch");
			list.add(elem);
		}
		if(debugStr. HDDTEMP==true){
			elem=createElem("hddtemp",1,"^\\|/dev/[a-z][a-z][a-z]\\|[0-9a-z]*\\|[0-9][0-9]\\|[cfk]\\|");
			list.add(elem);
		}
		if(debugStr. HOTLINE==true){
			elem=createElem("hotline",1,"^....................TRTPHOTL\\x01\\x02");
			list.add(elem);
		}
		if(debugStr. HTTP_RTSP==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("http_risp",1,"^(get[\\x09-\\x0d -~]* Accept: application/x-rtsp-tunnelled|http/(0\\.9|1\\.0|1\\.1) [1-5][0-9][0-9] [\\x09-\\x0d -~]*a=control:rtsp://)");
			list.add(elem);
		}
		if(debugStr. HTTP==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("http",1,"http/(0\\.9|1\\.0|1\\.1) [1-5][0-9][0-9] [\\x09-\\x0d -~]*(connection:|content-type:|content-length:|date:)|post [\\x09-\\x0d -~]* http/[01]\\.[019]");
							//http/(0\.9|1\.0|1\.1) [1-5][0-9][0-9] [\x09-\x0d -~]*(connection:|content-type:|content-length:|date:)|post [\x09-\x0d -~]* http/[01]\.[019]
			list.add(elem);
		}
		if(debugStr. IDENT==true){
			elem=createElem("ident",1,"^[1-9][0-9]?[0-9]?[0-9]?[0-9]?[\\x09-\\x0d]*,[\\x09-\\x0d]*[1-9][0-9]?[0-9]?[0-9]?[0-9]?(\\x0d\\x0a|[\\x0d\\x0a])?$");
			list.add(elem);
		}
		if(debugStr. IMAP==true){
			
			elem=createElem("imap",1,"^(\\* ok|a[0-9]+ noop)");
			list.add(elem);
		}
		if(debugStr. IMESH==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("imesh",1,"^(post[\\x09-\\x0d -~]*<PasswordHash>................................</PasswordHash><ClientVer>|\\x34\\x80?\\x0d?\\xfc\\xff\\x04|get[\\x09-\\x0d -~]*Host: imsh\\.download-prod\\.musicnet\\.com|\\x02[\\x01\\x02]\\x83.*\\x02[\\x01\\x02]\\x83)");
			list.add(elem);
		}
		if(debugStr. IPP==true){
			elem=createElem("ipp",1,"ipp://");
			list.add(elem);
		}
		if(debugStr. IRC==true){
			//^(nick[\x09-\x0d -~]*user[\x09-\x0d -~]*:|user[\x09-\x0d -~]*:[\x02-\x0d -~]*nick[\x09-\x0d -~]*\x0d\x0a)
			elem=createElem("irc",1,"^(nick[\\x09-\\x0d -~]*user[\\x09-\\x0d -~]*:|user[\\x09-\\x0d -~]*:[\\x02-\\x0d -~]*nick[\\x09-\\x0d -~]*\\x0d\\x0a)");
			list.add(elem);
		}
		if(debugStr. JABBER==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("jabber",1,"<stream:stream[\\x09-\\x0d ][ -~]*[\\x09-\\x0d ]xmlns=['\"]jabber");
			list.add(elem);
		}
		if(debugStr. KUGOO==true){
			elem=createElem("kugoo",1,"^(\\x64.....\\x70....\\x50\\x37|\\x65.+)");
			list.add(elem);
		}
		if(debugStr. LIVE365==true){
			elem=createElem("live365",1,"membername.*session.*player");
			list.add(elem);
		}
		if(debugStr. LIVEFORSPEED==true){
			elem=createElem("liveforspeed",1,"^..\\x05\\x58\\x0a\\x1d\\x03");
			list.add(elem);
		}
		if(debugStr. LPD==true){
			//String str4="("+"31]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde])"+")";
			elem=createElem("lpd",1,"^(\\x01[!-~]+|\\x02[!-~]+\\x0a.[\\x01\\x02\\x03][\\x01-\\x0a -~]*|[\\x03\\x04][!-~]+[\\x09-\\x0d]+[a-z][\\x09-\\x0d -~]*|\\x05[!-~]+[\\x09-\\x0d]+([a-z][!-~]*[\\x09-\\x0d]+[1-9][0-9]?[0-9]?|root[\\x09-\\x0d]+[!-~]+).*)\\x0a$");
			list.add(elem);
		}
		if(debugStr. MOHAA==true){
			elem=createElem("mohaa",1,"^\\xff\\xff\\xff\\xffgetstatus\\x0a");
			list.add(elem);
		}
		if(debugStr. MSN_FILETRANSFER==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("msn_filetransfer",1,"^(ver [ -~]*msnftp\\x0d\\x0aver msnftp\\x0d\\x0ausr|method msnmsgr:)");
			list.add(elem);
		}
		if(debugStr. MSNMESSENGER==true){
			elem=createElem("msnmessenger",1,"ver [0-9]+ msnp[1-9][0-9]? [\\x09-\\x0d -~]*cvr0\\x0d\\x0a$|usr 1 [!-~]+ [0-9. ]+\\x0d\\x0a$|ans 1 [!-~]+ [0-9. ]+\\x0d\\x0a$");
			list.add(elem);
		}
		if(debugStr. MUTE==true){
			elem=createElem("mute",1,"^(Public|AES)Key: [0-9a-f]*\\x0aEnd(Public|AES)Key\\x0a$");
			list.add(elem);
		}
		if(debugStr. NAPSTER==true){
			elem=createElem("napster",1,"^(.[\\x02\\x06][!-~]+ [!-~]+ [0-9][0-9]?[0-9]?[0-9]?[0-9]? \"[\\x09-\\x0d -~]+\" ([0-9]|10)|1(send|get)[!-~]+ \"[\\x09-\\x0d -~]+\")");
			list.add(elem);
		}
		if(debugStr. NBNS==true){
			elem=createElem("nbns",1,"\\x01\\x10\\x01|\\)\\x10\\x01\\x01|0\\x10\\x01");
			list.add(elem);
		}
		if(debugStr. NCP==true){
			elem=createElem("ncp",1,"^(dmdt.*\\x01.*(\"\"|\\x11\\x11|uu)|tncp.*33)");
			list.add(elem);
		}
		if(debugStr. NETBIOS==true){
			elem=createElem("netbios",1,"\\x81.?.?.[A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P][A-P]");
			list.add(elem);
		}
		if(debugStr. NNTP==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("nntp",1,"^(20[01][\\x09-\\x0d -~]*AUTHINFO USER|20[01][\\x09-\\x0d -~]*news)");
			list.add(elem);
		}
		if(debugStr. NTP==true){
			String str7="(c[6-9a-f]|d[0-9a-f]|e[0-9a-f]|f[0-9a-f])";
			elem=createElem("ntp",1,"^([\\x13\\x1b\\x23\\xd3\\xdb\\xe3]|[\\x14\\x1c$].......?.?.?.?.?.?.?.?.?[\\xc6-\\xff])");
			list.add(elem);
		}
		if(debugStr. OPENFT==true){
			elem=createElem("openft",1,"x-openftalias: [-)(0-9a-z ~.]");
			list.add(elem);
		}
		if(debugStr. PCANYWHERE==true){
			elem=createElem("pcanywhere",1,"^(nq|st)$");
			list.add(elem);
		}
		if(debugStr. POCO==true){
			elem=createElem("poco",1,"^\\x80\\x94\\x0a\\x01....\\x1f\\x9e");
			list.add(elem);
		}
		if(debugStr. POP3==true){
			elem=createElem("pop3",1,"^(\\+ok |-err )");
			list.add(elem);
		}
		if(debugStr. PPLIVE==true){
			elem=createElem("pplive",1,"\\x01...\\xd3.+\\x0c.$");
			list.add(elem);
		}
		if(debugStr. QQ==true){
			elem=createElem("qq",1,"^.?.?\\x02.+\\x03$");
			//elem=createElem("qq",1,"^.?.?02.+03$");
			list.add(elem);
		}
		if(debugStr. QUAKE_HALFLIFE==true){
			elem=createElem("quake_halflife",1,"^\\xff\\xff\\xff\\xffget(info|challenge)");
			list.add(elem);
		}
		if(debugStr. QUAKE1==true){
			elem=createElem("quake1",1,"^\\x80\\x0c\\x01quake\\x03");
			list.add(elem);
		}
		if(debugStr. RADMIN==true){
			elem=createElem("radmin",1,"^\\x01\\x01(\\x08\\x08|\\x1b\\x1b)$");
			list.add(elem);
		}
		if(debugStr. RDP==true){
			elem=createElem("rdp",1,"rdpdr.*cliprdr.*rdpsnd");
			list.add(elem);
		}
		if(debugStr. REPLAYTV_IVS==true){
			elem=createElem("replaytv_ivs",1,"^(get /ivs-IVSGetFileChunk|http/(0\\.9|1\\.0|1\\.1) [1-5][0-9][0-9] [\\x09-\\x0d -~]*\\x23\\x23\\x23\\x23\\x23REPLAY_CHUNK_START\\x23\\x23\\x23\\x23\\x23)");
			list.add(elem);
		}
		if(debugStr. RLOGIN==true){
			elem=createElem("rlogin",1,"^[a-z][a-z0-9][a-z0-9]+/[1-9][0-9]?[0-9]?[0-9]?00");
			list.add(elem);
		}
		if(debugStr. RTP==true){
			elem=createElem("rtp",1,"^\\x80[\\x01-\"`-\\x7f\\x80-\\xa2\\xe0-\\xff]?..........*\\x80");
			list.add(elem);
		}
		if(debugStr. RTSP==true){
			elem=createElem("rtsp",1,"rtsp/1.0 200 ok");
			list.add(elem);
		}
		if(debugStr. RUNESOFMAGIC==true){
			elem=createElem("runesofmagic",1,"^\\x10\\x03...........\\x0a\\x02.....\\x0e");
			list.add(elem);
		}
		if(debugStr. SHOUTCAST==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("shoutcast",1,"^get /.*icy-metadata:1|icy [1-5][0-9][0-9] [\\x09-\\x0d -~]*(content-type:audio|icy-)");
			list.add(elem);
		}
		if(debugStr. SIP==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("sip",1,"^(invite|register|cancel|message|subscribe|notify) sip[\\x09-\\x0d -~]*sip/[0-2]\\.[0-9]");
			list.add(elem);
		}
		///
		if(debugStr. SKYPETOSKYPE==true){
			elem=createElem("skypetoskype",1,"^..\\x02.............");
			list.add(elem);
		}
		if(debugStr. SMB==true){
			elem=createElem("smb",1,"\\xffsmb[\\x72\\x25]");
			list.add(elem);
		}
		if(debugStr. SMTP==true){
			elem=createElem("smtp",1,"^220[\\x09-\\x0d -~]* (e?smtp|simple mail)");
			list.add(elem);
		}
		if(debugStr. SNMP==true){
			elem=createElem("snmp",1,"^\\x02\\x01\\x04.+([\\xa0-\\xa3]\\x02[\\x01-\\x04].?.?.?.?\\x02\\x01.?\\x02\\x01.?\\x30|\\xa4\\x06.+\\x40\\x04.?.?.?.?\\x02\\x01.?\\x02\\x01.?\\x43)");
			list.add(elem);
		}
		if(debugStr. SOCKS==true){
			elem=createElem("socks",1,"\\x05[\\x01-\\x08]*\\x05[\\x01-\\x08]?.*\\x05[\\x01-\\x03][\\x01\\x03].*\\x05[\\x01-\\x08]?[\\x01\\x03]");
			list.add(elem);
		}
		if(debugStr. SORIBADA==true){
			elem=createElem("soribada",1,"^GETMP3\\x0d\\x0aFilename|^\\x01.?.?.?(\\x51\\x3a\\+|\\x51\\x32\\x3a)|^\\x10[\\x14-\\x16]\\x10[\\x15-\\x17].?.?.?.?$");
			list.add(elem);
		}
		if(debugStr. SOULSEEK==true){
			elem=createElem("soulseek",1,"^(\\x05..?|.\\x01.[ -~]+\\x01F..?.?.?.?.?.?.?)$");
			list.add(elem);
		}
		if(debugStr. SSDP==true){
			elem=createElem("ssdp",1,"^notify[\\x09-\\x0d ]\\*[\\x09-\\x0d ]http/1\\.1[\\x09-\\x0d -~]*ssdp:(alive|byebye)|^m-search[\\x09-\\x0d ]\\*[\\x09-\\x0d ]http/1\\.1[\\x09-\\x0d -~]*ssdp:discover");
			list.add(elem);
		}
		if(debugStr. SSH==true){
			elem=createElem("ssh",1,"^ssh-[12]\\.[0-9]");
			list.add(elem);
		}		

		if(debugStr. SSL==true){
			elem=createElem("ssl",1,"^(.?.?\\x16\\x03.*\\x16\\x03|.?.?\\x01\\x03\\x01?.*\\x0b)");
			list.add(elem);
		}
		if(debugStr. STUN==true){
			elem=createElem("stun",1,"^[\\x01\\x02]................?$");
			list.add(elem);
		}
		if(debugStr. SUBSPACE==true){
			elem=createElem("subspace",1,"^\\x01....\\x11\\x10........\\x01$");
			list.add(elem);
		}
		if(debugStr. SUBVERSION==true){
			elem=createElem("subversion",1,"^\\( success \\( 1 2 \\(");
			list.add(elem);
		}
		if(debugStr. TEAMFORTRESS2==true){
			elem=createElem("teamfortress2",1,"^\\xff\\xff\\xff\\xff.....*tfTeam Fortress");
			list.add(elem);
		}
		if(debugStr. TEAMSPEAK==true){
			elem=createElem("teamspeak",1,"^\\xf4\\xbe\\x03.*teamspeak");
			list.add(elem);
		}
		if(debugStr. TELNET==true){
			elem=createElem("telnet",1,"^\\xff[\\xfb-\\xfe].\\xff[\\xfb-\\xfe].\\xff[\\xfb-\\xfe]");
			list.add(elem);
		}
		if(debugStr. TESLA==true){
			elem=createElem("tesla",1,"\\x03\\x9a\\x89\\x22\\x31\\x31\\x31\\.\\x30\\x30\\x20\\x42\\x65\\x74\\x61\\x20|\\xe2\\x3c\\x69\\x1e\\x1c\\xe9");
			list.add(elem);
		}
		if(debugStr. TFTP==true){
			//String str3="("+"3[01]|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("tftp",1,"^(\\x01|\\x02)[ -~]*(netascii|octet|mail)");
			list.add(elem);
		}
		if(debugStr. THECIRCLE==true){
			elem=createElem("thecircle",1,"^t\\x03ni.?[\\x01-\\x06]?t[\\x01-\\x05]s[\\x0a\\x0b](glob|who are you$|query data)");
			list.add(elem);
		}
		if(debugStr. TONGHUASHUN==true){
			elem=createElem("tonghuashun",1,"^(GET /docookie\\.php\\?uname=|\\xfd\\xfd\\xfd\\xfd\\x30\\x30\\x30\\x30\\x30)");
			list.add(elem);
		}
		if(debugStr. TOR==true){
			elem=createElem("tor",1,"TOR1.*<identity>");
			list.add(elem);
		}
		if(debugStr. TSP==true){
			String str8="0[1-9a-f]|1[0-3]|1[6-9a-f]|2[0-4]";
			elem=createElem("tsp",1,"^[\\x01-\\x13\\x16-$]\\x01.?.?.?.?.?.?.?.?.?.?[ -~]+");
			list.add(elem);
		}
		if(debugStr. UNKNOWN==true){
			elem=createElem("unknown",1,".");
			list.add(elem);
		}
		if(debugStr. UNSET==true){
			elem=createElem("unset",1,".");
			list.add(elem);
		}
		if(debugStr. UUCP==true){
			elem=createElem("uucp",1,"^\\x10here=");
			list.add(elem);
		}
		if(debugStr. VALIDCERTSSL==true){
			elem=createElem("validcertssl",1,"^(.?.?\\x16\\x03.*\\x16\\x03|.?.?\\x01\\x03\\x01?.*\\x0b).*(thawte|equifax secure|rsa data security, inc|verisign, inc|gte cybertrust root|entrust\\.net limited)");
			list.add(elem);
		}
		if(debugStr. VENTRILO==true){
			elem=createElem("ventrilo",1,"^..?v\\$\\xcf");
			list.add(elem);
		}
		if(debugStr. VNC==true){
			elem=createElem("vnc",1,"^rfb 00[1-9]\\.00[0-9]\\x0a$");
			list.add(elem);
		}
		if(debugStr. WHOIS==true){
			//String str3="("+"31|"+pattern5+"|"+pattern6+"|"+pattern7+"|"+pattern8+"|"+pattern9+"|"+pattern10+"|"+pattern11+"(7[89]|7[abcde)"+")";
			elem=createElem("whois",1,"^[ !-~]+\\x0d\\x0a$");
			list.add(elem);
		}
		if(debugStr. WORLDOFWARCRAFT==true){
			elem=createElem("worldofwarcraft",1,"^\\x06\\xec\\x01");
			list.add(elem);
		}
		if(debugStr. X11==true){
			elem=createElem("x11",1,"^[lb].?\\x0b");
			list.add(elem);
		}
		if(debugStr. XBOXLIVE==true){
			elem=createElem("xboxlive",1,"^\\x58\\x80........\\xf3|^\\x06\\x58\\x4e");
			list.add(elem);
		}
		if(debugStr. XUNLEI==true){
			elem=createElem("xunlei",1,"^([()]|get)(...?.?.?(reg|get|query)|.+User-Agent: (Mozilla/4\\.0 \\(compatible; (MSIE 6\\.0; Windows NT 5\\.1;? ?\\)|MSIE 5\\.00; Windows 98\\))))|Keep-Alive\\x0d\\x0a\\x0d\\x0a[26]");
			list.add(elem);
		}
		if(debugStr. YAHOO==true){
			elem=createElem("yahoo",1,"^(ymsg|ypns|yhoo).?.?.?.?.?.?.?[lwt].*\\xc0\\x80");
			list.add(elem);
		}
		if(debugStr. ZMAAP==true){
			elem=createElem("zmaap",1,"^\\x1b\\xd7\\x3b\\x48[\\x01\\x02]\\x01?\\x01");
			list.add(elem);
		}
	//*************************
		
		
	}
}
