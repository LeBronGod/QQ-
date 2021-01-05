package cn.lbg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import cn.lbg.pojo.Group;
import cn.lbg.pojo.User;

public class GetFriend {

	public Vector<User> getfriend(String user) throws Exception {

		Connection connection = new Connect().connect();
		// 查出所有好友
		String sql = "SELECT friendid FROM `friendship` WHERE checker='" + user + "' ";
		// 查用户表里的好友的信息
		String sql2 = "SELECT id,name,online FROM admin WHERE id=? ";
		// 获取执行查询的对象
		PreparedStatement statement = connection.prepareStatement(sql);
		PreparedStatement statement2 = connection.prepareStatement(sql2);

		ResultSet set = statement.executeQuery();
		Vector<User> friend = new Vector<User>();
		while (set.next()) {
			String id = set.getString("friendid");
			statement2.setString(1, id);
			ResultSet userinfo = statement2.executeQuery();
			if (userinfo.next()) {
				User users = new User();
				users.setID(userinfo.getString("id"));
				users.setUsername(userinfo.getString("name"));
				users.setOnline(userinfo.getString("online"));
				friend.addElement(users);
			}
		}
		return friend;
	}

	public Vector<User> getgroupmemberlist(String groupid) throws Exception {

		Connection connection = new Connect().connect();
		String sql = "SELECT memberid,membername,memberstatus FROM `groupmember` WHERE groupid='" + groupid + "'";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet set = statement.executeQuery();
		Vector<User> friend = new Vector<User>();
		while (set.next()) {
			User users = new User();
			users.setID(set.getString("memberid"));
			users.setUsername(set.getString("membername"));
			users.setStatus(set.getString("memberstatus"));
			friend.addElement(users);
		}
		return friend;
	}

	public Vector<Group> getgroup(String userid) throws Exception {

		Connection connection = new Connect().connect();
		// 去查出所有好友
		String sql = "SELECT `groupid` FROM `groupmember` WHERE memberid ='" + userid + "' ";
		// 获取执行查询的对象
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet set = statement.executeQuery();
		Vector<Group> friend = new Vector<Group>();
		while (set.next()) {
			String id = set.getString("groupid");
			String sql2 = "SELECT groupname,type FROM `group` WHERE groupid='" + id + "' ";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			ResultSet groupinfo = statement2.executeQuery();
			if (groupinfo.next()) {
				Group group = new Group();
				group.setGroupid(id);
				group.setGroupname(groupinfo.getString("groupname"));
				group.setType(groupinfo.getString("type"));
				friend.addElement(group);
			}
		}
		return friend;
	}
}
