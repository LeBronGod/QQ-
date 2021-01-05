/**
 * 这是一个管理聊天的类
 */
package cn.lbg.tools;

import java.util.HashMap;
import java.util.Iterator;

import cn.lbg.view.GChat;
import cn.lbg.view.Pchat;

public class ManageChat {

	//私聊
	private static HashMap hmp=new HashMap<String, Pchat>();
	
	//加入
	public static void addPChat(String loginIdAnFriendId,Pchat qqChat) {
		hmp.put(loginIdAnFriendId, qqChat);
	}
	//取出
	public static Pchat getPChat(String loginIdAnFriendId ) {
		return (Pchat)hmp.get(loginIdAnFriendId);
	}
	
	
	//群聊
	private static HashMap hmg=new HashMap<String, GChat>();
	
	//加入
	public static void addGChat(String groupid,GChat qqChat) {
		hmg.put(groupid, qqChat);
	}
	//取出
	public static GChat getGChat(String groupid ) {
		return (GChat)hmg.get(groupid);
	}
}
