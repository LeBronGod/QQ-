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
	* @param str 欲插入的文本
	* @param font 字体e799bee5baa6e4b893e5b19e31333335343931
	* @param backgroup 背景色
	* @param foreground 前景色
	* @throws BadLocationException
	*/
	public void setText(JTextPane ContentArea,String str,Font font,Color foreground) throws BadLocationException{
	    SimpleAttributeSet set=new SimpleAttributeSet();
	    if(foreground!=null)
	        StyleConstants.setForeground(set, foreground);//前景色
	    if(font!=null){
	        StyleConstants.setFontSize(set, font.getSize());//字体大小
	        StyleConstants.setFontFamily(set, font.getFontName());//字体名称
	        if(font.isBold()){//粗体
	            StyleConstants.setBold(set, true);
	        }
	        if(font.isItalic()){//斜体
	            StyleConstants.setItalic(set, true);
	        }
	    }
	    Document doc=ContentArea.getDocument();//或者使用JTextPanel的对象
	    doc.insertString(doc.getLength(), str, set);//插入字符，位置，字符串，属性集
	}
}
