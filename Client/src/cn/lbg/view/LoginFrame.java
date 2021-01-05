package cn.lbg.view;

import static javax.swing.UIManager.setLookAndFeel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.lbg.pojo.Messagedao;
import cn.lbg.model.ClientUser;
import cn.lbg.pojo.Message;
import cn.lbg.pojo.User;
import cn.lbg.tools.ManageCCST;
import cn.lbg.tools.ManageFriendList;
import cn.lbg.util.UIUtil;
import cn.itcast.util.MyLookandFeel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.FontUIResource;

public class LoginFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField jtfUsername;
	private JPasswordField jpfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// 上皮肤
		try {
			setLookAndFeel(MyLookandFeel.SYS_NIMBUS);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void init() {
		this.setTitle("登录界面");
		UIUtil.setFrameCenter(this);
		UIUtil.setFrameImage(this);
		// 给窗体添加背景图
		ImageIcon image = new ImageIcon("src\\cn\\lbg\\resource\\pool.jpg");
		JLabel frame1 = new JLabel(image);
		frame1.setBounds(0, 0, 444, 265);// 图片位置和大小
		getContentPane().add(frame1);
	}

	// 跳转至注册界面
	public void Performed() {
		RegistFrame rf = new RegistFrame();
		this.dispose();
		rf.setVisible(true);

	}

	// 重置文本框
	public void Performed2() {
		this.jtfUsername.setText("");
		this.jpfPassword.setText("");
		jtfUsername.requestFocus();

	}

	//跳转至找回密码界面
	public void Performed3() {
		FindBack fb = new FindBack();
		this.dispose();
		fb.setVisible(true);
	}
	
	// 登录
	public void LoginPerformed() {
		/*
		 * 思路:1、获取用户名和密码 2、正则表达式验证用户名和密码 3、创建对象调用功能，返回布尔值 4、根据返回值给出提示
		 */

		// 获取用户名和密码
		String username = this.jtfUsername.getText().trim();
		String password = this.jpfPassword.getText().trim();

		// 用正则表达式做数据校验
		// 定义规则
		// 用户名规则 大小写都可以
		String usernameRegex = "[0-9a-zA-z]{7}";

		// 密码规则
		String passwordRegex = "\\w{6,12}";

		// 校验
		// 设置按钮显示效果
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
		// 设置文本显示效果
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
//		if (!username.matches(usernameRegex)) {
//			JOptionPane.showMessageDialog(this, "用户名不满足条件(一个由7个以上英文字母组成的名字)");
//			this.jtfUsername.setText("");
//			this.jtfUsername.requestFocus();
//			return;
//		}

		if (!password.matches(passwordRegex)) {
			JOptionPane.showMessageDialog(this, "密码不满足条件(一个6位以上的任意单词字符组成的密码)");
			this.jpfPassword.setText("");
			this.jpfPassword.requestFocus();
			return;
		}
		// 要把这个对象传给服务器去验证
		ClientUser qqClientUser = new ClientUser();
		User u = new User();
		u.setPassword(password);
		u.setUsername(username);
		u.setwant("登录");
		u.setOnline("1");

		String userid = qqClientUser.checkUser(u);
		System.out.println(userid);
		if (userid != null) {
			try {
				//将好友列表先创建并加入管理
				FriendList qqList=new FriendList(u.getUsername(),userid);
				ManageFriendList.addQqFriendList(u.getUsername()+userid, qqList);
				
				// 发送一个要求返回在线好友的请求包.
				ObjectOutputStream oos = new ObjectOutputStream(ManageCCST.getClientConServerThread(u.getUsername()+userid).getS().getOutputStream());
				// 做一个消息包
				Message m = new Message();
				m.setMesType(Messagedao.message_get_Friendlist);
				// 指明我要这个用户的好友情况.
				m.setSender(username+userid);
				oos.writeObject(m);
				
				ObjectOutputStream oos2 = new ObjectOutputStream(ManageCCST.getClientConServerThread(u.getUsername()+userid).getS().getOutputStream());
				Message m2 = new Message();
				m2.setMesType(Messagedao.message_get_grouplist);
				// 指明我要这个用户的群情况.
				m2.setSender(u.getUsername()+userid);
				m2.setCon(userid);
				oos2.writeObject(m2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//JOptionPane.showMessageDialog(this, "登录成功");
			// 关闭掉登录界面
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "用户名密码错误");
		}
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setBackground(new Color(102, 102, 255));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D\uFF1A\r\n");
		lblNewLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel.setBounds(81, 54, 70, 18);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel_1.setBounds(81, 104, 72, 18);
		contentPane.add(lblNewLabel_1);

		jtfUsername = new JTextField();
		jtfUsername.setOpaque(false);
		jtfUsername.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		jtfUsername.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		jtfUsername.setBounds(165, 51, 200, 24);
		contentPane.add(jtfUsername);
		jtfUsername.setColumns(10);

		jpfPassword = new JPasswordField();
		jpfPassword.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		jpfPassword.setOpaque(false);
		jpfPassword.setBounds(165, 101, 200, 24);
		contentPane.add(jpfPassword);

		JButton btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPerformed();
			}
		});
		btnNewButton.setBounds(81, 188, 70, 27);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u91CD\u7F6E");
		btnNewButton_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Performed2();
			}
		});
		btnNewButton_1.setBounds(190, 188, 70, 27);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("\u6CE8\u518C");
		btnNewButton_2.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnNewButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Performed();
			}
		});
		btnNewButton_2.setBounds(295, 188, 70, 27);
		contentPane.add(btnNewButton_2);

		JCheckBox chckbxNewCheckBox = new JCheckBox("\u8BB0\u4F4F\u5BC6\u7801");
		chckbxNewCheckBox.setFont(new Font("微软雅黑 Light", Font.BOLD, 13));
		chckbxNewCheckBox.setBounds(165, 134, 89, 27);
		contentPane.add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("\u81EA\u52A8\u767B\u5F55");
		chckbxNewCheckBox_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 13));
		chckbxNewCheckBox_1.setBounds(276, 134, 89, 27);
		contentPane.add(chckbxNewCheckBox_1);

		JButton btnNewButton_3 = new JButton("\u5FD8\u8BB0\u5BC6\u7801\uFF1F");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Performed3();
			}
		});
		btnNewButton_3.setFont(new Font("等线 Light", Font.BOLD, 15));
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_3.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_3.setForeground(Color.BLACK);
			}
		});
		btnNewButton_3.setContentAreaFilled(false);
		btnNewButton_3.setOpaque(false);
		btnNewButton_3.setBackground(new Color(100, 149, 237));
		btnNewButton_3.setBounds(317, 225, 113, 27);
		contentPane.add(btnNewButton_3);
		init();
	}
}
