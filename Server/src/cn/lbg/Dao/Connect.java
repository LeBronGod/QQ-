package cn.lbg.Dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Connect {

	public Connection connect() throws Exception {
		// 连接数据库
		Properties info = new Properties();
		info.load(new FileInputStream("src\\sql.properties"));
		String user = info.getProperty("user");
		String password = info.getProperty("password");
		String Driver = info.getProperty("Driver");
		String url = info.getProperty("url");
		// 注册驱动
		Class.forName(Driver);
		// 获取连接
		Connection connection = DriverManager.getConnection(url, user, password);

		return connection;
	}

	public static void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null || !rs.isClosed()) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null || !st.isClosed()) st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null || !conn.isClosed()) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
