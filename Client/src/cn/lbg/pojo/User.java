package cn.lbg.pojo;

public class User implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private String gengder;
	private String birthday;
	private String want;
	private String status;
	private String id;
	private String online;

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGengder() {
		return gengder;
	}

	public void setGengder(String gengder) {
		this.gengder = gengder;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getwant() {
		return want;
	}

	public void setwant(String want) {
		this.want = want;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

}
