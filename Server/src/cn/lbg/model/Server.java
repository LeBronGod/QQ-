package cn.lbg.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import cn.lbg.Dao.AddUser;
import cn.lbg.Dao.CheckUser;
import cn.lbg.Dao.GetFriend;
import cn.lbg.Dao.InfoChange;
import cn.lbg.model.ServerConnClientThread;
import cn.lbg.pojo.Message;
import cn.lbg.pojo.User;

public class Server {

	public Server() {
		try {
			// ��9999����
			System.out.println("����������9999�˿ڼ���");
			ServerSocket ss = new ServerSocket(9999);
			// ����,�ȴ�����
			while (true) {
				Socket s = ss.accept();
				// ���տͻ��˷�������Ϣ.
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				User u = (User) ois.readObject();
				System.out.println("���������յ��û�id:" + u.getUsername() + "  ����:" + u.getPassword());
				Message m = new Message();
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				if (u.getwant().equals("ע��")) {//����û�����Ը����ע��
					boolean flag = new AddUser().adduser(u.getUsername(),u.getPassword(),u.getEmail(),u.getGengder(),u.getBirthday(),u.getID());
					if(flag) {
						//����һ��ע��ɹ�����Ϣ
						m.setMesType("6");
						oos.writeObject(m);
					} else {
						m.setMesType("7");
						oos.writeObject(m);
						s.close();
					}
				} else if(u.getwant().equals("��¼")){//������Ҫ����¼
					boolean flag = new CheckUser().checkuser(u.getUsername(), u.getPassword());
					if (flag) {
						//�õ��û�id
						String id = new CheckUser().getUserid(u.getUsername(), u.getPassword());
						// ����һ���ɹ���½����Ϣ
						m.setCon(id);
						m.setMesType("1");
						oos.writeObject(m);

						// ����͵���һ���̣߳��ø��߳���ÿͻ��˱���ͨѶ.
						ServerConnClientThread scct = new ServerConnClientThread(s);
						ManageCliendThread.addClientThread(u.getUsername()+id, scct);
						// ������ÿͻ���ͨ�ŵ��߳�.
						scct.start();

						// ��֪ͨ���������û�.
						scct.notifyOther(u.getUsername()+id);
					} else {
						m.setMesType("2");
						oos.writeObject(m);
						s.close();
					}
				}else if(u.getwant().equals("�һ�����")) {
					boolean flag = new CheckUser().findback(u.getUsername(),u.getID(),u.getEmail());
					if(flag) {
						m.setMesType("6");
						oos.writeObject(m);
					} else {
						m.setMesType("7");
						oos.writeObject(m);
						s.close();
					}
				} else if(u.getwant().equals("��������")) {
					boolean flag = new InfoChange().resetpwd(u.getID(),u.getEmail());
					if(flag) {
						m.setMesType("6");
						oos.writeObject(m);
					} else {
						m.setMesType("7");
						oos.writeObject(m);
						s.close();
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
