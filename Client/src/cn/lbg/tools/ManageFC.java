package cn.lbg.tools;

import java.util.HashMap;

import cn.lbg.view.Find_Create;

public class ManageFC {
	private static HashMap hmp = new HashMap<String , Find_Create>();
	
	public static void addFC(String user,Find_Create fc) {
		hmp.put(user,fc);
	}
	
	public static Find_Create getFC(String user) {
		return (Find_Create)hmp.get(user);
	}
}
