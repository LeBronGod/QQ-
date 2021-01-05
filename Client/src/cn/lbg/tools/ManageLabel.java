package cn.lbg.tools;

import java.util.HashMap;

import javax.swing.JLabel;

public class ManageLabel {
	private static HashMap hmp=new HashMap<String, JLabel>();
	
	public static void addLabel(String friend,JLabel label) {
		hmp.put(friend, label);
	}
	//È¡³ö
	public static JLabel getLabel(String friend ) {
		return (JLabel)hmp.get(friend);
	}
}
