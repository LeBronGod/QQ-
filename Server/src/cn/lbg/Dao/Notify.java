package cn.lbg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Notify {
	
	public void removeNotify(String user) throws Exception {
		Connection connection = new Connect().connect();
		String sql = "DELETE FROM `notify` WHERE `user` = ? ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, user);
		statement.executeUpdate();
	}
	
	public void addNotify(String user,String content) throws Exception {
		Connection connection = new Connect().connect();
		String sql = "INSERT INTO `notify`(user,content) VALUES(?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, user);
		statement.setString(2, content);
		statement.executeUpdate();
	}
}
