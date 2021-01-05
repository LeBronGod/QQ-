package cn.lbg.pojo;

import java.util.List;
import java.util.Vector;

public class Message  implements java.io.Serializable {
	/**
	 * 消息包的pojo
	 */
	private static final long serialVersionUID = 1L;
	private String mesType;
	private String sender;
	private String getter;
	private String con;
	private String sendTime;
	private String groupname;
	private String groupid;
	private String grouptype;
	private String ownername;
	private String ownerid;
	private String filename;
	private String url;
	private Vector friend;
	private long time;
	private List<Contents> content;
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<Contents> getContent() {
		return content;
	}

	public void setContent(List<Contents> content) {
		this.content = content;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGrouptype() {
		return grouptype;
	}

	public void setGrouptype(String grouptype) {
		this.grouptype = grouptype;
	}

	public Vector getFriend() {
		return friend;
	}

	public void setFriend(Vector friend) {
		this.friend = friend;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getMesType() {
		return mesType;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
}
