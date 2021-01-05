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
			// 在9999监听
			System.out.println("服务器正在9999端口监听");
			ServerSocket ss = new ServerSocket(9999);
			// 阻塞,等待连接
			while (true) {
				Socket s = ss.accept();
				// 接收客户端发来的信息.
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				User u = (User) ois.readObject();
				System.out.println("服务器接收到用户id:" + u.getUsername() + "  密码:" + u.getPassword());
				Message m = new Message();
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				if (u.getwant().equals("注册")) {//如果用户的意愿是来注册
					boolean flag = new AddUser().adduser(u.getUsername(),u.getPassword(),u.getEmail(),u.getGengder(),u.getBirthday(),u.getID());
					if(flag) {
						//返回一个注册成功的消息
						m.setMesType("6");
						oos.writeObject(m);
					} else {
						m.setMesType("7");
						oos.writeObject(m);
						s.close();
					}
				} else if(u.getwant().equals("登录")){//否则是要来登录
					boolean flag = new CheckUser().checkuser(u.getUsername(), u.getPassword());
					if (flag) {
						//拿到用户id
						String id = new CheckUser().getUserid(u.getUsername(), u.getPassword());
						// 返回一个成功登陆的信息
						m.setCon(id);
						m.setMesType("1");
						oos.writeObject(m);

						// 这里就单开一个线程，让该线程与该客户端保持通讯.
						ServerConnClientThread scct = new ServerConnClientThread(s);
						ManageCliendThread.addClientThread(u.getUsername()+id, scct);
						// 启动与该客户端通信的线程.
						scct.start();

						// 并通知其它在线用户.
						scct.notifyOther(u.getUsername()+id);
					} else {
						m.setMesType("2");
						oos.writeObject(m);
						s.close();
					}
				}else if(u.getwant().equals("找回密码")) {
					boolean flag = new CheckUser().findback(u.getUsername(),u.getID(),u.getEmail());
					if(flag) {
						m.setMesType("6");
						oos.writeObject(m);
					} else {
						m.setMesType("7");
						oos.writeObject(m);
						s.close();
					}
				} else if(u.getwant().equals("重置密码")) {
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
