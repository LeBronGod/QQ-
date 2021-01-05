/**
 * 客户端连接服务器的多线程类
 */
package cn.lbg.model;

import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import cn.lbg.pojo.Messagedao;
import cn.lbg.pojo.Message;
import cn.lbg.tools.ManageChat;
import cn.lbg.tools.ManageFC;
import cn.lbg.tools.ManageFriendList;
import cn.lbg.view.Find_Create;
import cn.lbg.view.FriendList;
import cn.lbg.view.GChat;
import cn.lbg.view.Pchat;

public class ClientConnServerThread extends Thread {
	
	private Socket s;
	
	//构造函数
	public ClientConnServerThread(Socket s) {
		this.s=s;
	}
	
	public void run() {
		while(true) {
			//不停的读取从服务器端发来的消息
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				System.out.println("读取到从服务发来的消息"+ m.getSender() +" 给 "+m.getGetter()+" 内容："+m.getCon());
				
				if(m.getMesType().equals(Messagedao.message_comm_mes)) {
					//把从服务器获得消息，显示到朋友的聊天界面
					Pchat qqChat=ManageChat.getPChat(m.getGetter()+m.getSender());
					qqChat.showMessage(m);
				} else if(m.getMesType().equals(Messagedao.message_comm_mes_group)) {
					//显示到所有群成员的聊天界面
					GChat gChat=ManageChat.getGChat(m.getGetter());
					gChat.showMessage(m);
				} else if(m.getMesType().equals(Messagedao.message_ret_onLineFriend)) {
					System.out.println("客户端接收到好友上线"+m.getCon());
					String getter=m.getGetter();
					System.out.println("消息包的接受者为:"+getter);
					//修改相应的好友列表.
					FriendList qqFriendList=ManageFriendList.getQqFriendList(getter);
					//更新在线好友.
					if(qqFriendList!=null) {
						qqFriendList.upateFriend(m);
					}
				} else if(m.getMesType().equals(Messagedao.message_ret_Friendlist)) {
					String getter=m.getGetter();
					FriendList qqFriendList=ManageFriendList.getQqFriendList(getter);
					qqFriendList.initfriendlist(m);
				} else if(m.getMesType().equals(Messagedao.message_ret_memberlist)) {
					String getter=m.getGetter();
					GChat Gchat = ManageChat.getGChat(getter);
					Gchat.initmemberlist(m);
				} else if(m.getMesType().equals(Messagedao.message_ret_grouplist)) {
					String getter=m.getGetter();
					FriendList qqFriendList=ManageFriendList.getQqFriendList(getter);
					qqFriendList.initgrouplist(m);
				} else if(m.getMesType().equals(Messagedao.message_ret_Common_language)) {
					//返回到聊天界面显示常用语
					if(m.getGroupid() == null) {
						Pchat qqChat=ManageChat.getPChat(m.getSender()+m.getGetter());
						qqChat.showCommonlanguage(m);
					} else {
						GChat gChat=ManageChat.getGChat(m.getGroupid());
						//gChat.showCommonlanguage(m);
					}
				} else if(m.getMesType().equals(Messagedao.message_Creategroup_succeed)) {
					String getter=m.getGetter();
					Find_Create fc = ManageFC.getFC(getter);
					fc.Notify();
				} else if(m.getMesType().equals(Messagedao.message_Findfriend)) {
					Find_Create fc = ManageFC.getFC(m.getGetter());
					fc.showfriend(m);
				} else if(m.getMesType().equals(Messagedao.message_Findgroup)) {
					Find_Create fc = ManageFC.getFC(m.getGetter());
					fc.showgroup(m);
				} else if(m.getMesType().equals(Messagedao.message_Makefriend)) {
					String getter=m.getGetter();
					Find_Create fc = ManageFC.getFC(getter);
					fc.reactmf(m);
				} else if(m.getMesType().equals(Messagedao.message_breakfriend)) {
					String getter=m.getGetter();
					FriendList qqFriendList=ManageFriendList.getQqFriendList(getter);
					qqFriendList.breakdata(m);
				} else if(m.getMesType().equals(Messagedao.message_outgroup)) {
					String getter=m.getGetter();
					FriendList qqFriendList=ManageFriendList.getQqFriendList(getter);
					qqFriendList.breakdata(m);
				} else if(m.getMesType().equals(Messagedao.message_Notify)) {
					String getter=m.getGetter();
					FriendList qqFriendList=ManageFriendList.getQqFriendList(getter);
					qqFriendList.addNotify(m);
				} else if(m.getMesType().equals(Messagedao.message_Joingroup)) {
					String getter=m.getGetter();
					Find_Create fc = ManageFC.getFC(getter);
					fc.reactjg(m);
				} else if(m.getMesType().equals(Messagedao.message_File)) {
					String getter = m.getGetter();
					String sender = m.getSender();
					if(m.getGroupid() == null) {
						Pchat qqChat=ManageChat.getPChat(m.getGetter()+m.getSender());
						m.setSender(getter);
						m.setGetter(sender);
						qqChat.showFile(m);
					} else {
						GChat gChat=ManageChat.getGChat(m.getGetter());
						gChat.showFile(m);
					}
				} else if(m.getMesType().equals(Messagedao.message_Picture)) {
					String getter = m.getGetter();
					String sender = m.getSender();
					if(m.getGroupid() == null) {
						Pchat qqChat=ManageChat.getPChat(m.getGetter()+m.getSender());
						m.setSender(getter);
						m.setGetter(sender);
						qqChat.showPicture(m);
					} else {
						GChat gChat=ManageChat.getGChat(m.getGetter());
						gChat.showPicture(m);
					}
				} else if(m.getMesType().equals(Messagedao.message_Voice)) {
					String getter = m.getGetter();
					String sender = m.getSender();
					if(m.getGroupid() == null) {
						Pchat qqChat=ManageChat.getPChat(m.getGetter()+m.getSender());
						m.setSender(getter);
						m.setGetter(sender);
						qqChat.showVoice(m);
					} else {
						GChat gChat=ManageChat.getGChat(m.getGetter());
						gChat.showVoice(m);
					}
				}
			} catch (Exception e) {
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
