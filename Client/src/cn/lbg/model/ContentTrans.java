package cn.lbg.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument.LeafElement;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import cn.lbg.pojo.Contents;

public class ContentTrans {
	
	public static List<Contents> contentEncode(JTextPane textPane) {
		
		Document document = textPane.getDocument();
		List<Contents> list = new ArrayList<Contents>();
		for(int i=0;i<document.getLength();i++) {
			try {
				
				StyledDocument sd= textPane.getStyledDocument();
				Contents contents = new Contents();
				Element e = sd.getCharacterElement(i);
				
				if(e instanceof LeafElement) {
					if(e.getName().equals("content")) {
						contents.setContents(sd.getText(i,1));
					} else if (e.getName().equals("icon")) {
						//ÉèÖÃÍ¼Æ¬Â·¾¶
						System.out.println("1  "+e.getAttributes().getAttribute(StyleConstants.IconAttribute).toString());
						contents.setPath(e.getAttributes().getAttribute(StyleConstants.IconAttribute).toString());
					}
				}
				list.add(contents);
			}catch(Exception e) {
			
			}
		}
		return list;
	}
	
	public static void contentDecode(JTextPane textPane,List<Contents> list) {
		Document doc = contentAppend(textPane,"");
		SimpleAttributeSet set=new SimpleAttributeSet();
		for(Contents con:list) {
			if(con!=null) {
				if(con.getContents()!=null) {
					try {
						doc.insertString(doc.getLength(), con.getContents(), set);
					}catch(Exception e1) {
						e1.printStackTrace();
					}
				}else {
					textPane.setCaretPosition(doc.getLength());
					textPane.insertIcon(new ImageIcon(con.getPath()));
					System.out.println("2  "+con.getPath());
				}
			}
		}
		contentAppend(textPane,"\n");
		
	}
	
	public static Document contentAppend(JTextPane textPane,String content) {
		Document doc = textPane.getDocument();
		SimpleAttributeSet set=new SimpleAttributeSet();
		try {
			doc.insertString(doc.getLength(), content, set);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return doc;
	}
}
