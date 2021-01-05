package cn.lbg.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import cn.lbg.pojo.Messagedao;
import cn.lbg.pojo.User;
import cn.lbg.Dao.AddGroup;
import cn.lbg.Dao.Find;
import cn.lbg.Dao.InfoChange;
import cn.lbg.Dao.Join;
import cn.lbg.Dao.Notify;
import cn.lbg.pojo.Group;
import cn.lbg.pojo.Message;

public class ServerConnClientThread extends Thread {

	Socket s;

	public ServerConnClientThread(Socket s) {
		// 把服务器和该客户端的连接赋给s
		this.s = s;
	}

	// 让该线程去通知其它用户
	public void notifyOther(String user) {
		// 得到所有在线的人的线程,告诉他们这个b在线状态发生了改变
		HashMap hm = ManageCliendThread.hm;
		Iterator it = hm.keySet().iterator();

		while (it.hasNext()) {
			try {
				Message m = new Message();
				m.setCon(user);
				m.setMesType(Messagedao.message_ret_onLineFriend);
				// 取出在线人的id
				String onLineUser = it.next().toString();
				if (onLineUser == user) {
					continue;
				}
				ObjectOutputStream oos = new ObjectOutputStream(
						ManageCliendThread.getClientThread(onLineUser).s.getOutputStream());
				m.setGetter(onLineUser);
				oos.writeObject(m);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void run() {
		
		while(true) {
			//这里该线程就可以接收客户端的信息.
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message)ois.readObject();
				
				System.out.println(m.getSender()+" 给 "+m.getGetter()+"发来"+m.getCon());
				
				//对从客户端取得的消息进行类型判断，然后做相应的处理
				if(m.getMesType().equals(Messagedao.message_comm_mes)) {
					//取得接收人的通信线程完成转发.
					ServerConnClientThread sc = ManageCliendThread.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
				} else if (m.getMesType().equals(Messagedao.message_comm_mes_group)){
					//取得群内所有人的群聊通信线程完成转发.
					Vector<User> res=ManageCliendThread.getallgroupmember(m.getGetter());
					Iterator<User> it = res.iterator();
					while(it.hasNext()) {
						User user = it.next();
						ServerConnClientThread sc = ManageCliendThread.getClientThread(user.getUsername()+user.getID());
						ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
						oos.writeObject(m);
					}
				} else if(m.getMesType().equals(Messagedao.message_get_onLineFriend)) {
					System.out.println(m.getSender()+" 要他的在线好友情况");
					//把在服务器的好友给该客户端返回.
					String res=ManageCliendThread.getAllOnLineUserid();
					Message m2=new Message();
					m2.setMesType(Messagedao.message_ret_onLineFriend);
					m2.setCon(res);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_get_Friendlist)) {
					System.out.println(m.getSender()+"刚上线要初始化好友列表");
					//取得所有好友给该客户端返回.
					Vector<User> res=ManageCliendThread.getallfriend(m.getSender());
					Message m2=new Message();
					m2.setMesType(Messagedao.message_ret_Friendlist);
					m2.setFriend(res);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_get_grouplist)) {
					System.out.println(m.getSender()+"刚上线要初始化群聊列表");
					//取得所有群给该客户端返回.
					Vector<Group> res=ManageCliendThread.getallgroup(m.getCon());
					System.out.println(res.size());
					Message m2=new Message();
					m2.setMesType(Messagedao.message_ret_grouplist);
					m2.setFriend(res);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_get_memberlist)) {
					System.out.println(m.getSender()+"刚点开了一个群聊窗口初始化成员列表");
					//取得所有群成员给该客户端返回.
					Vector<User> res=ManageCliendThread.getallgroupmember(m.getSender());
					Message m2=new Message();
					m2.setMesType(Messagedao.message_ret_memberlist);
					m2.setFriend(res);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_Creategroup)) {
					System.out.println(m.getSender()+"刚刚创建了一个群聊");
					boolean flag = new AddGroup().addgroup(m.getGroupname(), m.getGroupid(), m.getSender(), m.getGrouptype(), m.getSendTime(),m.getOwnername(),m.getOwnerid());
					Message m2=new Message();
					if(flag) {
						m2.setMesType("16");
					} else {
						m2.setMesType("17");
					}
					m2.setGetter(m.getOwnername()+m.getOwnerid());
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_Findfriend)) {
					System.out.println(m.getSender()+"寻找好友");
					User user = new Find().findfriend(m.getGetter());
					Message m2=new Message();
					m2.setMesType(Messagedao.message_Findfriend);
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					if(user.equals(null)) {
						m2.setCon("None");
						m2.setGetter(m.getSender());
					} else {
						m2.setGetter(m.getSender());
						m2.setSender(m.getGetter());
						m2.setCon(user.getUsername());
					}
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_Findgroup)) {
					System.out.println(m.getSender()+"添加群聊");
					Group group = new Find().findgroup(m.getGetter());
					Message m2=new Message();
					m2.setMesType(Messagedao.message_Findfriend);
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					if(group.equals(null)) {
						m2.setCon("None");
						m2.setGetter(m.getSender());
					} else {
						m2.setGetter(m.getSender());
						m2.setSender(m.getGetter());
						m2.setCon(group.getGroupname());
					}
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_Makefriend)) {
					System.out.println(m.getSender()+"请求添加"+m.getGetter()+"为好友");
					boolean flag = new Join().makefriend(m.getSender(), m.getOwnerid(),m.getGetter(), m.getCon());
					Message m2=new Message();
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					m2.setMesType(Messagedao.message_Makefriend);
					m2.setGetter(m.getSender()+m.getOwnerid());
					m2.setOwnerid(m.getCon());
					if(flag) {
						m2.setCon("success");
					} else {
						m2.setCon("fail");
					}
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_Joingroup)) {
					System.out.println(m.getSender()+"请求加入"+m.getCon());
					boolean flag = new Join().joingroup(m.getGroupid(), m.getSender(), m.getCon());
					Message m2=new Message();
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					m2.setMesType(Messagedao.message_Joingroup);
					m2.setGetter(m.getCon()+m.getSender());
					if(flag) {
						m2.setCon("success");
					} else {
						m2.setCon("fail");
					}
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_get_Common_language)) {
					Vector<String> res=ManageCliendThread.getCommonlanguage(m.getSender());
					m.setMesType(Messagedao.message_ret_Common_language);
					m.setFriend(res);
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m);
				} else if(m.getMesType().equals(Messagedao.message_set_Common_language)) {
					new InfoChange().addCommonlanguage(m.getSender(), m.getCon());
					Vector<String> res=ManageCliendThread.getCommonlanguage(m.getSender());
					m.setMesType(Messagedao.message_ret_Common_language);
					m.setFriend(res);
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m);
				} else if(m.getMesType().equals(Messagedao.message_breakfriend)) {
					new Join().breakfriend(m.getSender(), m.getOwnerid(),m.getGetter(), m.getCon());//名字，id，名字，id
					Message m2=new Message();
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					m2.setMesType(Messagedao.message_breakfriend);
					m2.setGetter(m.getSender()+m.getOwnerid());
					m2.setOwnerid(m.getCon());
					m2.setCon(m.getGetter()+m.getCon());
					oos.writeObject(m2);
				} else if(m.getMesType().equals(Messagedao.message_outgroup)) {
					new Join().outgroup(m.getGroupid(), m.getSender(), m.getCon());//群号，退出者id，退出者名字
					Message m2=new Message();
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					m2.setMesType(Messagedao.message_outgroup);
					m2.setGetter(m.getCon()+m.getSender());
					m2.setCon(m.getSender()+m.getGroupid());
					oos.writeObject(m2);
					Vector<User> res=ManageCliendThread.getallgroupmember(m.getGroupid());
					System.out.println("返回来的群成员数："+res.size());
					Iterator<User> it = res.iterator();
					while(it.hasNext()) {
						User user = it.next();
						if(!user.getStatus().equals("成员")) {
							System.out.println(user.getStatus()+" : "+user.getUsername());
							ServerConnClientThread sc = ManageCliendThread.getClientThread(user.getUsername()+user.getID());
							ObjectOutputStream ooss = new ObjectOutputStream(sc.s.getOutputStream());
							m.setCon(m.getCon()+" 退出群 "+m.getGroupname());
							m.setMesType(Messagedao.message_Notify);
							m.setGetter(user.getUsername()+user.getID());
							ooss.writeObject(m);
							new Notify().addNotify(user.getUsername()+user.getID(),m.getCon());
						}
					}
				} else if(m.getMesType().equals(Messagedao.message_removeNotify)) {
					new Notify().removeNotify(m.getSender());
				} else if(m.getMesType().equals(Messagedao.message_Clientleave)) {
					System.out.println(m.getSender()+"下线了");
					new InfoChange().onlinestatuchange(m.getCon());
					ServerConnClientThread scct = ManageCliendThread.getClientThread(m.getSender());
					scct.notifyOther(m.getSender());
					scct.getS().close();
				} else if(m.getMesType().equals(Messagedao.message_File)) {
					String filename = m.getFilename();
					ServerSocket server = new ServerSocket(8080);
		            Socket ss = server.accept();
		            System.out.println(ss.getInetAddress().getHostAddress()+"...发送消息来");
					String url = null;
					if(m.getGroupid() == null) {
						url = "C:\\Users\\李秉庚\\Desktop\\file\\"+m.getSender()+"&"+m.getGetter();
					} else {
						url = "C:\\Users\\李秉庚\\Desktop\\file\\"+m.getGroupid();
					}
					File dir = new File(url);
					if(!dir.exists()){
	                    dir.mkdir();
	                }
					InputStream in=ss.getInputStream();
					//this variable "count" is used creating many files with different subscripts
					int count=1;
					File file=null;
					int i=0;
					String suffix;
					byte buf[]=new byte[1024];
					while ((buf[i++]=(byte)in.read())!='\n'){
					}
					suffix=new String(buf,0,i-1);
					file=new File(dir, filename+suffix);
					while (file.exists()){
						file=new File(dir, filename+"("+(count++)+")"+suffix);
					}
					int len=0;
					FileOutputStream fos=new FileOutputStream(file);
					while ((len=in.read(buf))!=-1){
						fos.write(buf, 0, len);
					}
					in.close();
					fos.close();
					ss.close();
					server.close();
		            if (m.getGroupid() == null) {
						ServerConnClientThread sc = ManageCliendThread.getClientThread(m.getGetter());
						ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
						m.setUrl(file.getAbsolutePath());
						oos.writeObject(m);
					} else {
						Vector<User> res=ManageCliendThread.getallgroupmember(m.getGetter());
						Iterator<User> it = res.iterator();
						while(it.hasNext()) {
							User user = it.next();
							ServerConnClientThread sc = ManageCliendThread.getClientThread(user.getUsername()+user.getID());
							ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
							m.setUrl(file.getAbsolutePath());
							oos.writeObject(m);
						}
					}
				} else if(m.getMesType().equals(Messagedao.message_Picture)) {
					ServerSocket server = new ServerSocket(10007);
		            Socket ss = server.accept();
		            String ip = ss.getInetAddress().getHostAddress();
		            System.out.println(ip+"...发送图片来");
		            try {
		                BufferedInputStream bin = new BufferedInputStream(ss.getInputStream());
		                String url = null;
						if(m.getGroupid() == null) {
							url = "C:\\Users\\李秉庚\\Desktop\\picture\\"+m.getSender()+"&"+m.getGetter();
						} else {
							url = "C:\\Users\\李秉庚\\Desktop\\picture\\"+m.getGroupid();
						}
		                File dir = new File(url);
		                if(!dir.exists()){
		                    dir.mkdir();
		                }
		                int count=1;
		                //我觉得这里的后缀名，需要通过发送方也发过来的
		                File file = new File(dir, ip+".jpg");
		                while(file.exists()){
		                    file = new File(dir,ip+"("+(count++) +")"+".jpg"); //带号的文件名
		                }
		                FileOutputStream fout = new FileOutputStream(file);
		                //从socket流中读取数据，存储到本地文件。相当于对拷
		                byte buf[] = new byte[1024];
		                int len=0;
		                while( (len=bin.read(buf))!=-1){
		                    fout.write(buf, 0, len);
		                }
		                //图片接收完毕向客户端发送回馈信息
		                OutputStream out = ss.getOutputStream();
		                out.write( "上传成功".getBytes() );
		                fout.close();
		                ss.close();
		                server.close();
		              
		                if (m.getGroupid() == null) {
							ServerConnClientThread sc = ManageCliendThread.getClientThread(m.getGetter());
							ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
							m.setUrl(file.getAbsolutePath());
							oos.writeObject(m);
						} else {
							Vector<User> res=ManageCliendThread.getallgroupmember(m.getGetter());
							Iterator<User> it = res.iterator();
							while(it.hasNext()) {
								User user = it.next();
								ServerConnClientThread sc = ManageCliendThread.getClientThread(user.getUsername()+user.getID());
								ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
								m.setUrl(file.getAbsolutePath());
								oos.writeObject(m);
							}
						}
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
				} else if(m.getMesType().equals(Messagedao.message_Voice)) {
					String filename = m.getFilename();
					ServerSocket server = new ServerSocket(8888);
		            Socket ss = server.accept();
		            System.out.println(ss.getInetAddress().getHostAddress()+"...发送消息来");
					String url = null;
					if(m.getGroupid() == null) {
						url = "C:\\Users\\李秉庚\\Desktop\\voice\\"+m.getGetter()+"&"+m.getSender();
					} else {
						url = "C:\\Users\\李秉庚\\Desktop\\voice\\"+m.getGroupid();
					}
					File dir = new File(url);
					if(!dir.exists()){
		                dir.mkdir();
		            }
					InputStream in=ss.getInputStream();
					int count=1;
					File file=null;
					file=new File(dir, filename+".wav");
					while (file.exists()){
						file=new File(dir, filename+"("+(count++)+").wav");
					}
					int len=0;
					byte buf[]=new byte[1024];
					FileOutputStream fos = new FileOutputStream(file);
					while ((len=in.read(buf))!=-1) {
						fos.write(buf, 0, len);
					}
					in.close();
					fos.close();
					ss.close();
					server.close();
		            if (m.getGroupid() == null) {
						ServerConnClientThread sc = ManageCliendThread.getClientThread(m.getGetter());
						ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
						m.setUrl(file.getAbsolutePath());
						oos.writeObject(m);
					} else {
						Vector<User> res=ManageCliendThread.getallgroupmember(m.getGetter());
						Iterator<User> it = res.iterator();
						while(it.hasNext()) {
							User user = it.next();
							ServerConnClientThread sc = ManageCliendThread.getClientThread(user.getUsername()+user.getID());
							ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
							m.setUrl(file.getAbsolutePath());
							oos.writeObject(m);
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
}
