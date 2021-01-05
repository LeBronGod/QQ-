/**
 * 这是管理好友列表的类
 */
package cn.lbg.tools;

import java.util.HashMap;

import cn.lbg.view.FriendList;

public class ManageFriendList {

private static HashMap hm=new HashMap<String, FriendList>();
	
	public static void addQqFriendList(String user,FriendList friendList) {
		
		hm.put(user, friendList);
	}
	
	public static FriendList getQqFriendList(String user) {
		return (FriendList)hm.get(user);
	}
}
