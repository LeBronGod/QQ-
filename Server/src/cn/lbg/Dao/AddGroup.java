package cn.lbg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddGroup {
	public boolean addgroup(String groupname, String groupid, String owner, String groupType, String buildtime,String ownername,String ownerid) throws Exception {

		boolean flag = false;

		Connection connection = new Connect().connect();

		String sql = "INSERT INTO `Group`(groupid,owner,type,buildtime,groupname) VALUES(?,?,?,?,?)";
		String sql2 = "INSERT INTO Groupmember(groupid,memberid,membername,memberstatus) VALUES(?,?,?,?)";
		// 获取执行查询的对象
		PreparedStatement statement = connection.prepareStatement(sql);
		PreparedStatement statement2 = connection.prepareStatement(sql2);
		// 设置占位符的具体值
		System.out.println(groupType);
		statement.setString(1, groupid);
		statement.setString(2, owner);
		statement.setString(3, groupType);
		statement.setString(4, buildtime);
		statement.setString(5, groupname);
		statement2.setString(1, groupid);
		statement2.setString(2, ownerid);
		statement2.setString(3, ownername);
		statement2.setString(4, "群主");
		
		int update = statement.executeUpdate();
		int update2 = statement2.executeUpdate();
		System.out.println(update);
		System.out.println(update2);
		if (update == 1) {
			flag = true;
		}
		return flag;
	}
}
