/**
 * ����һ�������������
 */
package cn.lbg.tools;

import java.util.HashMap;
import java.util.Iterator;

import cn.lbg.view.GChat;
import cn.lbg.view.Pchat;

public class ManageChat {

	//˽��
	private static HashMap hmp=new HashMap<String, Pchat>();
	
	//����
	public static void addPChat(String loginIdAnFriendId,Pchat qqChat) {
		hmp.put(loginIdAnFriendId, qqChat);
	}
	//ȡ��
	public static Pchat getPChat(String loginIdAnFriendId ) {
		return (Pchat)hmp.get(loginIdAnFriendId);
	}
	
	
	//Ⱥ��
	private static HashMap hmg=new HashMap<String, GChat>();
	
	//����
	public static void addGChat(String groupid,GChat qqChat) {
		hmg.put(groupid, qqChat);
	}
	//ȡ��
	public static GChat getGChat(String groupid ) {
		return (GChat)hmg.get(groupid);
	}
}
