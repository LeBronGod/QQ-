package cn.lbg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckUser {
	
	
	public boolean checkuser(String username,String pwd) throws Exception {
		
		boolean flag = false;
		
		Connection connection = new Connect().connect();
		
		String sql = "SELECT `id` FROM admin WHERE name=? AND password=?";
		String sql2 = "UPDATE `admin` SET `online` = '1' WHERE `id` = ?";
		//获取执行查询的对象
		PreparedStatement statement = connection.prepareStatement(sql);
		PreparedStatement statement2 = connection.prepareStatement(sql2);
		//设置占位符的具体值
		statement.setString(1, username);
		statement.setString(2, pwd);
		
		ResultSet set = statement.executeQuery();
		if(set.next()) {
			int count = set.getInt(1);
			if(count>0) {
				statement2.setString(1, set.getString("id"));
				statement2.executeUpdate();
				flag = true;
			}
		}
		return flag;	
	}
	public String getUserid(String username,String pwd) throws Exception {
		String id = null;
		Connection connection = new Connect().connect();
		String sql = "SELECT `id` FROM admin WHERE name=? AND password=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, pwd);
		ResultSet set = statement.executeQuery();
		if(set.next()) {
			id = set.getString("id");
		}
		return id;
	}
	public boolean findback(String username,String userid,String email) throws Exception {
		boolean flag = false;
		Connection connection = new Connect().connect();
		
		String sql = "SELECT `eamil` FROM admin WHERE name=? AND id=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, userid);
		ResultSet set = statement.executeQuery();
		if(set.next()) {
			if(set.getString("email").equals(email)) {
				flag = true;
			}
		}
		return flag;
	}
}
