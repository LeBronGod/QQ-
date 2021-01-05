package cn.lbg.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cn.lbg.pojo.Message;
import cn.lbg.pojo.User;
import cn.lbg.tools.ManageCCST;

public class ClientConnServer {

	public  Socket s;
	
	//发送请求
	public String sendLoginInfoToServer(Object o) {
		String id = null;
		try {
			System.out.println("已发送消息至服务器验证");
			s=new Socket("127.0.0.1",9999);
			
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			//这里是验证用户登录的地方
			if(ms.getMesType().equals("1")) {
				id = ms.getCon();
				//创建一个客户端和服务器端保持通讯连接得线程
				ClientConnServerThread ccst=new ClientConnServerThread(s);
				//启动该通讯线程
				ccst.start();
				ManageCCST.addClientConServerThread(((User)o).getUsername()+id, ccst);
			    
			}else{
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	public boolean sendRegistInfoToServer(Object o) {
		boolean b=false;
		
		try {
			System.out.println("已发送消息至服务器注册");
			s=new Socket("127.0.0.1",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			//这里就是验证用户注册是否成功的地方
			if(ms.getMesType().equals("6")) {
				b = true;
			} else {
				s.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
