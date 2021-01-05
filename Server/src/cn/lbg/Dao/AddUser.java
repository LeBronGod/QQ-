package cn.lbg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddUser {

	public boolean adduser(String username, String pwd, String email, String gengder, String birthday,String id) throws Exception {

		boolean flag = false;

		Connection connection = new Connect().connect();
		String sql = "INSERT INTO admin(NAME,PASSWORD,SEX,email,birthdata,ONLINE,id) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, pwd);
		statement.setString(3, gengder);
		statement.setString(4, email);
		statement.setString(5, birthday);
		statement.setString(6, "0");
		statement.setString(7, id);
		
		int update = statement.executeUpdate();
		System.out.println(update);
		if (update == 1) {
			flag = true;
		}
		return flag;
	}
}
