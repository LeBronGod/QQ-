package cn.lbg.model;

import cn.lbg.pojo.User;

public class ClientUser {

	public String checkUser(User u) {
		return new ClientConnServer().sendLoginInfoToServer(u);
	}
	public boolean registUser(User u) {
		return new ClientConnServer().sendRegistInfoToServer(u);
	}
	
}
