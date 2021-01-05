package cn.lbg.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * ר��������Ч������
 * @author �����
 *
 */
public class UIUtil {
	private UIUtil() {}
	
	//�޸�ͼ��
	public static void setFrameImage(JFrame jf) {
		//��ȡ���������
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		//����·����ȡͼƬ
		Image i = tk.getImage("src\\cn\\lbg\\resource\\QQ.jpg");
		
		//����������ͼƬ
		jf.setIconImage(i);
	}
	
	//���ô������
	public static void setFrameCenter(JFrame jf) {
		/*
		 * ˼·:1��ȡ��Ļ�Ŀ��
		 * 		2��ȡ����Ŀ��
		 * 		3������Ļ�ĸ�-����ĸߣ�/2;������Ļ�Ŀ������Ŀ�/2
		 * 		4���������Ǵ���ľ�������
		 */
		//��ȡ���߶���
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		//��ȡ��Ļ�Ŀ��
		Dimension d = tk.getScreenSize();
		double screenWidth = d.getWidth();
		double screenHeigth = d.getHeight();
		
		//��ȡ����Ŀ��
		int FrameWidth = jf.getWidth();
		int FrameHeigth = jf.getHeight();
		
		//�õ��µĿ��
		int width = (int)(screenWidth-FrameWidth)/2;
		int heigth = (int)(screenHeigth-FrameHeigth)/2;
		
		//���ô�������
		jf.setLocation(width, heigth);
	}
}
