package cn.lbg.pojo;

public class Contents implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String contents;
	private String path;
	
	public String getContents() {
		return contents;
	}
	public void setContents(String content) {
		this.contents = content;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
