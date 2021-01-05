package cn.lbg.view;
/**
 * 表情包界面
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import cn.lbg.util.UIUtil;

public class Emoticon extends JFrame{
	private static final long serialVersionUID = 168497430264112079L;
	public Emoticon(JTextPane textPane) {
		JPanel panel=(JPanel)getContentPane();
		panel.setLayout(null);
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				ImageIcon icon = new ImageIcon("src\\cn\\lbg\\resource\\"+(i*10+j+1)+".gif");
				JLabel lbIcon = new JLabel(icon);
				lbIcon.setSize(40,40);
				lbIcon.setLocation(0+j*40, 0+i*40);
				lbIcon.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						JLabel jlabel = (JLabel)e.getSource();
						Icon icon2 = jlabel.getIcon();
						//插入
						textPane.insertIcon(icon2);
						//关闭
						Emoticon.this.dispose();
					}
				});
				panel.add(lbIcon);
			}
		}
		setSize(420,450);
		setTitle("表情");
		setVisible(true);
		UIUtil.setFrameCenter(this);
		UIUtil.setFrameImage(this);

	}
}
