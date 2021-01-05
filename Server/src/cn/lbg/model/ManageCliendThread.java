/**
 * 这是服务器端管理各个客户端的多线程
 */
package cn.lbg.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import cn.lbg.Dao.Find;
import cn.lbg.Dao.GetFriend;
import cn.lbg.model.ServerConnClientThread;
import cn.lbg.pojo.Group;
import cn.lbg.pojo.User;

public class ManageCliendThread {

public static HashMap hm=new HashMap<String, ServerConnClientThread>();
	
	//向hm中添加一个客户端通讯线程管理起来
	public static void addClientThread(String user,ServerConnClientThread scct) {
		hm.put(user, scct);
	}
	
	public static ServerConnClientThread getClientThread(String user) {
		return (ServerConnClientThread)hm.get(user);
	}
	
	//返回当前在线的人的情况
	public static String getAllOnLineUserid() {
		//使用迭代器完成
		Iterator it=hm.keySet().iterator();
		String res="";
		while(it.hasNext()) {
			res+=it.next().toString()+" ";//以空格分割存起
		}
		return res;
	}
	public static Vector<User> getallfriend(String u) throws Exception {
		// 登录成功之后去获取他的好友列表
		Vector <User> friend = new GetFriend().getfriend(u);
		return friend;
	}
	public static Vector<Group> getallgroup(String g) throws Exception {
		// 登录成功之后去获取他的好友列表
		Vector <Group> group = new GetFriend().getgroup(g);
		return group;
	}
	public static Vector<User> getallgroupmember(String g) throws Exception {
		// 打开群聊时获取群成员名单
		Vector <User> friend = new GetFriend().getgroupmemberlist(g);
		return friend;
	}

	public static Vector<String> getCommonlanguage(String g) throws Exception {
		Vector <String> vector = new Find().getCommonlanguage(g);
		return vector;
	}
}
