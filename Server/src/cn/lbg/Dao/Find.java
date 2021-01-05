package cn.lbg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import cn.lbg.pojo.Group;
import cn.lbg.pojo.User;

public class Find {

	public User findfriend(String id) throws Exception {
		boolean b = false;

		Connection connection = new Connect().connect();

		String sql = "SELECT `id`,`name`,sex,`online` FROM admin WHERE id=?";
		// 获取执行查询的对象
		PreparedStatement statement = connection.prepareStatement(sql);
		// 设置占位符的具体值
		statement.setString(1, id);
		
		ResultSet set = statement.executeQuery();
		User user = new User();
		if (set.next()) {
			int count = set.getInt(1);
			if (count > 0) {
				user.setID(id);
				user.setUsername(set.getString("name"));
				user.setGengder(set.getString("sex"));
				user.setOnline(set.getString("online"));
				b = true;
			}
		}
		if(b) {
			return user;
		} else {
			return null;
		}
	}
	
	public Group findgroup(String id) throws Exception {
		boolean b = false;
		Connection connection = new Connect().connect();

		String sql = "SELECT `groupid`,`groupname` FROM `group` WHERE `groupid`=?";
		// 获取执行查询的对象
		PreparedStatement statement = connection.prepareStatement(sql);
		// 设置占位符的具体值
		statement.setString(1, id);
		
		ResultSet set = statement.executeQuery();
		Group group = new Group();
		if (set.next()) {
			int count = set.getInt(1);
			if (count > 0) {
				group.setGroupid(id);
				group.setGroupname(set.getString("groupname"));
				b = true;
			}
		}
		if(b) {
			return group;
		} else {
			return null;
		}
	}
	public Vector<String> getCommonlanguage(String user) throws Exception {
		Connection connection = new Connect().connect();
		
		String sql = "SELECT commonlanguage FROM `Commonlanguage` WHERE user='" + user + "' ";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet set = statement.executeQuery();
		Vector<String> vector = new Vector<String>();
		while (set.next()) {
			String Commonlanguage = set.getString("commonlanguage");
			vector.addElement(Commonlanguage);
		}
		
		return vector;
	}
}
