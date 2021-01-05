package cn.lbg.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.lbg.model.ClientUser;
import cn.lbg.pojo.User;
import cn.lbg.util.UIUtil;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResetPwd extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField password;
	private JTextField resure;
	private String userid;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetPwd window = new ResetPwd();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
	/**
	 * Create the application.
	 */
	
	//点击确定，重置密码
	public void resetpwd() {
		String pwd = password.getText().trim();
		String repwd = resure.getText().trim();
		if(!pwd.equals(repwd)) {
			JOptionPane.showMessageDialog(this, "两次密码输入不一致!");
			resure.setText("");
			return;
		}
		ClientUser qqClientUser = new ClientUser();
		User u = new User();
		u.setID(userid);
		u.setPassword(pwd);
		u.setwant("重置密码");
		
	}
	
	public ResetPwd(String userid) {
		this.userid = userid;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("等线 Light", Font.BOLD, 18));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblNewLabel.setBounds(79, 53, 90, 18);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u786E\u8BA4\u65B0\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lblNewLabel_1.setBounds(79, 114, 90, 18);
		frame.getContentPane().add(lblNewLabel_1);
		
		password = new JTextField();
		password.setBounds(183, 50, 160, 24);
		frame.getContentPane().add(password);
		password.setColumns(10);
		
		resure = new JTextField();
		resure.setBounds(183, 111, 160, 24);
		frame.getContentPane().add(resure);
		resure.setColumns(10);
		
		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetpwd();
			}
		});
		btnNewButton.setBackground(new Color(255, 255, 240));
		btnNewButton.setFont(new Font("等线 Light", Font.BOLD, 18));
		btnNewButton.setBounds(79, 169, 264, 40);
		frame.getContentPane().add(btnNewButton);
		
		UIUtil.setFrameCenter(frame);
		UIUtil.setFrameImage(frame);
		ImageIcon image = new ImageIcon("src\\cn\\lbg\\resource\\pool.jpg");
		JLabel frame1 = new JLabel(image);
		frame1.setBounds(0, 0, 600, 350);
		frame.getContentPane().add(frame1);
	}

}
