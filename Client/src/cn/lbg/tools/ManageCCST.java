/**
 * 这是一个管理客户端和服务器保持通讯的线程类
 */
package cn.lbg.tools;

import java.util.HashMap;

import cn.lbg.model.ClientConnServerThread;

public class ManageCCST {
	
	private static HashMap hm=new HashMap<String, ClientConnServerThread>();
	
	//把创建好的ClientConnServerThread放入到hm
	public static void addClientConServerThread(String username,ClientConnServerThread ccst) {
		hm.put(username, ccst);
	}
	
	//可以通过用户名取得该线程 
	public static ClientConnServerThread getClientConServerThread(String username) {
		return (ClientConnServerThread)hm.get(username);
	}
}
