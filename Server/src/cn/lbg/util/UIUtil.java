package cn.lbg.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * 专门做界面效果的类
 * @author 李秉庚
 *
 */
public class UIUtil {
	private UIUtil() {}
	
	//修改图标
	public static void setFrameImage(JFrame jf) {
		//获取工具类对象
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		//根据路径获取图片
		Image i = tk.getImage("src\\cn\\lbg\\resource\\QQ.jpg");
		
		//给窗体设置图片
		jf.setIconImage(i);
	}
	
	//设置窗体居中
	public static void setFrameCenter(JFrame jf) {
		/*
		 * 思路:1获取屏幕的宽高
		 * 		2获取窗体的宽高
		 * 		3（用屏幕的高-窗体的高）/2;（用屏幕的宽减窗体的宽）/2
		 * 		4这两个就是窗体的居中坐标
		 */
		//获取工具对象
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		//获取屏幕的宽高
		Dimension d = tk.getScreenSize();
		double screenWidth = d.getWidth();
		double screenHeigth = d.getHeight();
		
		//获取窗体的宽高
		int FrameWidth = jf.getWidth();
		int FrameHeigth = jf.getHeight();
		
		//得到新的宽高
		int width = (int)(screenWidth-FrameWidth)/2;
		int heigth = (int)(screenHeigth-FrameHeigth)/2;
		
		//设置窗体坐标
		jf.setLocation(width, heigth);
	}
}
