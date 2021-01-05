package cn.lbg.model;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MyFont {
	/** 
	* @param str ��������ı�
	* @param font ����e799bee5baa6e4b893e5b19e31333335343931
	* @param backgroup ����ɫ
	* @param foreground ǰ��ɫ
	* @throws BadLocationException
	*/
	public void setText(JTextPane ContentArea,String str,Font font,Color foreground) throws BadLocationException{
	    SimpleAttributeSet set=new SimpleAttributeSet();
	    if(foreground!=null)
	        StyleConstants.setForeground(set, foreground);//ǰ��ɫ
	    if(font!=null){
	        StyleConstants.setFontSize(set, font.getSize());//�����С
	        StyleConstants.setFontFamily(set, font.getFontName());//��������
	        if(font.isBold()){//����
	            StyleConstants.setBold(set, true);
	        }
	        if(font.isItalic()){//б��
	            StyleConstants.setItalic(set, true);
	        }
	    }
	    Document doc=ContentArea.getDocument();//����ʹ��JTextPanel�Ķ���
	    doc.insertString(doc.getLength(), str, set);//�����ַ���λ�ã��ַ��������Լ�
	}
}
