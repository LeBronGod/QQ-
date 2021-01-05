package cn.lbg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Join {
	
	public boolean makefriend(String name1,String id1,String name2,String id2) throws Exception {
		boolean flag = true;
		
		Connection connection = new Connect().connect();
		String sql = "INSERT INTO friendship(checker,friendid) VALUES(?,?)";
		String sql2 = "INSERT INTO friendship(checker,friendid) VALUES(?,?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		PreparedStatement statement2 = connection.prepareStatement(sql2);
		statement.setString(1, name1+id1);
		statement.setString(2, id2);
		statement2.setString(1, name2+id2);
		statement2.setString(2, id1);
		
		int update = statement.executeUpdate();
		int update2 = statement2.executeUpdate();
		if(update == 1) {
			flag = true;
		}
		return flag;
	}
	public boolean breakfriend(String name1,String id1,String name2,String id2) throws Exception {
		boolean flag = true;
		
		Connection connection = new Connect().connect();
		String sql = "DELETE FROM friendship WHERE `checker` = ? AND `friendid` = ?";
		String sql2 = "DELETE FROM friendship WHERE `checker` = ? AND `friendid` = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		PreparedStatement statement2 = connection.prepareStatement(sql2);
		statement.setString(1, name1+id1);
		statement.setString(2, id2);
		statement2.setString(1, name2+id2);
		statement2.setString(2, id1);
		
		int update = statement.executeUpdate();
		int update2 = statement2.executeUpdate();
		if(update == 1) {
			flag = true;
		}
		return flag;
	}
	
	
	public boolean joingroup(String groupid,String id,String name) throws Exception {
		boolean flag = true;
		
		Connection connection = new Connect().connect();
		String sql = "INSERT INTO Groupmember(groupid,memberid,membername,memberstatus) VALUES(?,?,?,?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, groupid);
		statement.setString(2, id);
		statement.setString(3, name);
		statement.setString(4, "≥…‘±");
		
		int update = statement.executeUpdate();
		if(update == 1) {
			flag = true;
		}
		return flag;
	}
	public boolean outgroup(String groupid,String id,String name) throws Exception {
		boolean flag = true;
		
		Connection connection = new Connect().connect();
		String sql = "DELETE FROM Groupmember WHERE `groupid` = ? AND `memberid` = ? AND `membername` = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, groupid);
		statement.setString(2, id);
		statement.setString(3, name);
		
		int update = statement.executeUpdate();
		if(update == 1) {
			flag = true;
		}
		return flag;
	}
	
	
}
