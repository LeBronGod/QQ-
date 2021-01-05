package cn.lbg.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import cn.lbg.model.ClientUser;
import cn.lbg.model.MailUtil;
import cn.lbg.model.Random;
import cn.lbg.pojo.Message;
import cn.lbg.pojo.User;
import cn.lbg.util.UIUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FindBack extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField verify;
	private JTextField userid;
	private JTextField username;
	private JTextField email;
	private String ans;
	private String emails;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FindBack window = new FindBack();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	//点击找回按钮后的事件
	public void findback() {
		String pwd = verify.getText().trim();
		if (!pwd.equals(ans)) {
			JOptionPane.showMessageDialog(this, "邮箱验证码错误");
			verify.setText("");
			verify.requestFocus();
			return;
		}
		ClientUser qqClientUser = new ClientUser();
		User u = new User();
		u.setUsername(username.getText().trim());
		u.setID(userid.getText().trim());
		u.setEmail(emails);
		u.setwant("找回密码");
		if (qqClientUser.registUser(u)) {
			// 给出提示
			JOptionPane.showMessageDialog(this, "验证成功，请重设该账号密保");
			ResetPwd rp = new ResetPwd(userid.getText().trim());
			this.dispose();
			rp.setVisible(true);
		}
		
	}

	public void sendkeyword() {
		try {
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
			emails = email.getText().trim();
			// 邮箱规则：邮箱的基本格式为“名称@域名”，需要使用“^”匹配邮箱的开始部分，用“$”匹配邮箱结束部分以保证邮箱前后不能有其他字符
			if (emails.equals("")) {
				JOptionPane.showMessageDialog(this, "邮箱不得为空");
				return;
			}
			String emailRegex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
			if (!emails.matches(emailRegex)) {
				JOptionPane.showMessageDialog(this, "邮箱格式错误！");
				email.setText("");
				email.requestFocus();
				return;
			}

			ans = Random.achieveCode();
			MailUtil.send_mail(emails, ans);
			JOptionPane.showMessageDialog(this, "邮件发送成功!请在收到后填写");
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(this, "邮件发送失败!请确认邮箱有效性");
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the application.
	 */
	public FindBack() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 540, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("找回密码");

		JLabel lblNewLabel = new JLabel("\u8D26\u53F7\uFF1A");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblNewLabel.setBounds(118, 54, 72, 18);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u6635\u79F0\uFF1A");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblNewLabel_1.setBounds(118, 99, 72, 18);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\u5BC6\u4FDD\u90AE\u7BB1\uFF1A");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblNewLabel_2.setBounds(118, 145, 91, 18);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\u9A8C\u8BC1\u7801\uFF1A");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblNewLabel_3.setBounds(118, 197, 72, 18);
		frame.getContentPane().add(lblNewLabel_3);

		JButton btnNewButton = new JButton("\u627E\u56DE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findback();
			}
		});
		btnNewButton.setBackground(new Color(173, 216, 230));
		btnNewButton.setFont(new Font("等线", Font.BOLD, 18));
		btnNewButton.setBounds(118, 233, 280, 35);
		frame.getContentPane().add(btnNewButton);

		verify = new JTextField();
		verify.setFont(new Font("微软雅黑", Font.BOLD, 15));
		verify.setBounds(195, 194, 86, 26);
		frame.getContentPane().add(verify);
		verify.setColumns(10);

		JButton btnNewButton_1 = new JButton("\u83B7\u53D6\u9A8C\u8BC1\u7801");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendkeyword();
			}
		});
		btnNewButton_1.setFont(new Font("等线 Light", Font.BOLD, 15));
		btnNewButton_1.setBounds(284, 194, 114, 26);
		frame.getContentPane().add(btnNewButton_1);

		userid = new JTextField();
		userid.setBounds(195, 52, 203, 24);
		frame.getContentPane().add(userid);
		userid.setColumns(10);

		username = new JTextField();
		username.setBounds(195, 97, 203, 24);
		frame.getContentPane().add(username);
		username.setColumns(10);

		email = new JTextField();
		email.setBounds(195, 143, 203, 24);
		frame.getContentPane().add(email);
		email.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel(
				"    \u6B63\u786E\u586B\u5199\u6D88\u606F\u540E\u5373\u53EF\u627E\u56DE\u5BC6\u4FDD");
		lblNewLabel_4.setFont(new Font("楷体", Font.BOLD, 15));
		lblNewLabel_4.setBounds(118, 13, 280, 18);
		frame.getContentPane().add(lblNewLabel_4);

		UIUtil.setFrameCenter(frame);
		UIUtil.setFrameImage(frame);
		ImageIcon image = new ImageIcon("src\\cn\\lbg\\resource\\pool.jpg");
		JLabel frame1 = new JLabel(image);
		frame1.setBounds(0, 0, 600, 350);
		frame.getContentPane().add(frame1);

	}
}
