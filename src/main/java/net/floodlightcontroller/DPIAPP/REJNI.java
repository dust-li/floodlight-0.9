package net.floodlightcontroller.DPIAPP;

public class REJNI{

	public native static boolean Match(String pattern,byte[] buffer,int length);
	
	static{
		System.loadLibrary("rejni");
	}
	
//	public static void main(String args[]){
//		
//		String pattern="\\x41.*\\xBB$";
//		byte[] matcher=new byte[4];
//		matcher[0]=(byte)0x41;
//		matcher[1]=(byte)0xFF;
//		matcher[2]=(byte)0xBB;
//		matcher[3]=(byte)0x00;
//		if(Match(pattern,matcher,2)==true)
//		{
//			System.out.println("YESYESYES!");
//		}
//		else
//		{
//			System.out.println("NONONONO!");
//		}
//	}
}
