/**
 * ����һ������ͻ��˺ͷ���������ͨѶ���߳���
 */
package cn.lbg.tools;

import java.util.HashMap;

import cn.lbg.model.ClientConnServerThread;

public class ManageCCST {
	
	private static HashMap hm=new HashMap<String, ClientConnServerThread>();
	
	//�Ѵ����õ�ClientConnServerThread���뵽hm
	public static void addClientConServerThread(String username,ClientConnServerThread ccst) {
		hm.put(username, ccst);
	}
	
	//����ͨ���û���ȡ�ø��߳� 
	public static ClientConnServerThread getClientConServerThread(String username) {
		return (ClientConnServerThread)hm.get(username);
	}
}
