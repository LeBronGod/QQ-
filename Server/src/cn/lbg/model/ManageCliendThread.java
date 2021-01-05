/**
 * ���Ƿ������˹�������ͻ��˵Ķ��߳�
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
	
	//��hm�����һ���ͻ���ͨѶ�̹߳�������
	public static void addClientThread(String user,ServerConnClientThread scct) {
		hm.put(user, scct);
	}
	
	public static ServerConnClientThread getClientThread(String user) {
		return (ServerConnClientThread)hm.get(user);
	}
	
	//���ص�ǰ���ߵ��˵����
	public static String getAllOnLineUserid() {
		//ʹ�õ��������
		Iterator it=hm.keySet().iterator();
		String res="";
		while(it.hasNext()) {
			res+=it.next().toString()+" ";//�Կո�ָ����
		}
		return res;
	}
	public static Vector<User> getallfriend(String u) throws Exception {
		// ��¼�ɹ�֮��ȥ��ȡ���ĺ����б�
		Vector <User> friend = new GetFriend().getfriend(u);
		return friend;
	}
	public static Vector<Group> getallgroup(String g) throws Exception {
		// ��¼�ɹ�֮��ȥ��ȡ���ĺ����б�
		Vector <Group> group = new GetFriend().getgroup(g);
		return group;
	}
	public static Vector<User> getallgroupmember(String g) throws Exception {
		// ��Ⱥ��ʱ��ȡȺ��Ա����
		Vector <User> friend = new GetFriend().getgroupmemberlist(g);
		return friend;
	}

	public static Vector<String> getCommonlanguage(String g) throws Exception {
		Vector <String> vector = new Find().getCommonlanguage(g);
		return vector;
	}
}
