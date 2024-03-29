package com.joshua.androidparam.utils;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class P2P0MACUtil {
	public static String getCMDP2pmac(){
		return CmdUtil.adb("cat /sys/class/net/p2p0/address");
	}
	public static String getCMDwlanmac(){
		
		return CmdUtil.adb("cat /sys/class/net/wlan0/address");
	}
	public static String getP2P0Mac() {
		Enumeration<NetworkInterface> interfaces;
		String result = "";
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface iF = interfaces.nextElement();
				byte[] addr;
				addr = iF.getHardwareAddress();
				if (addr == null || addr.length == 0) {
					continue;
				}
				List<InterfaceAddress> address = iF.getInterfaceAddresses();
				String mac = ConvertUtil.byte2mac(addr);//buf.toString();
				/*Logger.i( "interfaceName="+iF.getName()+", mac="+mac);
				if(iF.getName().equals("p2p0")){
					return mac;
				}*/
				result+=iF.getName()+": mac="+mac+"   \n";
//				return mac;
			} 
			return result;
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public static byte[] hexStringToByte(String hex) {   
		String[] strArr = hex.split(":");
	    int len = (hex.length() / 2);   
	    byte[] result = new byte[len];   
	    char[] achar = hex.toCharArray();   
	    for (int i = 0; i < len; i++) {   
	     int pos = i * 2;   
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }   
	    return result;   
	}
	private static byte toByte(char c) {   
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);   
	    return b;   
	}  
	
	
}
