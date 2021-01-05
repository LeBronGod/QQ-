package cn.lbg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import cn.lbg.pojo.Messagedao;

public class InfoChange {

	public void onlinestatuchange(String userid) throws Exception {
		Connection connection = new Connect().connect();
		String sql = "UPDATE `admin` SET `online` = '0' WHERE `id` = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, userid);
		statement.executeUpdate();
	}
	public boolean resetpwd(String userid,String pwd) throws Exception {
		boolean flag = false;
		Connection connection = new Connect().connect();
		String sql = "UPDATE `admin` SET `password` = `"+pwd+" WHERE `id` = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, userid);
		int updata = statement.executeUpdate();
		if(updata > 0) {
			flag = true;
		}
		return flag;
	}
	public void addCommonlanguage(String user,String con) throws Exception {
		Connection connection = new Connect().connect();
		String sql = "INSERT INTO commonlanguage(user,commonlanguage) VALUES(?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, user);
		statement.setString(2, con);
		statement.executeUpdate();
	}
}
