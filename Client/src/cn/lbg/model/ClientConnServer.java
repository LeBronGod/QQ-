package cn.lbg.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cn.lbg.pojo.Message;
import cn.lbg.pojo.User;
import cn.lbg.tools.ManageCCST;

public class ClientConnServer {

	public  Socket s;
	
	//��������
	public String sendLoginInfoToServer(Object o) {
		String id = null;
		try {
			System.out.println("�ѷ�����Ϣ����������֤");
			s=new Socket("127.0.0.1",9999);
			
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			//��������֤�û���¼�ĵط�
			if(ms.getMesType().equals("1")) {
				id = ms.getCon();
				//����һ���ͻ��˺ͷ������˱���ͨѶ���ӵ��߳�
				ClientConnServerThread ccst=new ClientConnServerThread(s);
				//������ͨѶ�߳�
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
			System.out.println("�ѷ�����Ϣ��������ע��");
			s=new Socket("127.0.0.1",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			//���������֤�û�ע���Ƿ�ɹ��ĵط�
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
