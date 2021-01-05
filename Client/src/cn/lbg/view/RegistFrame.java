package cn.lbg.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import cn.lbg.model.ClientUser;
import cn.lbg.model.MailUtil;
import cn.lbg.model.Random;
import cn.lbg.model.makeID;
import cn.lbg.pojo.User;
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
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

import static javax.swing.UIManager.setLookAndFeel;

import java.awt.Color;
import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Font;

public class RegistFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField jftUsername;
	private JPasswordField jpfPassword;
	private JPasswordField resurepassword;
	private JTextField email;
	private JTextField keyword;
	private JTextField birthday;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private String ans;
	private String gengder;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RegistFrame window = new RegistFrame();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public void init() {
		this.setTitle("注册界面");
		UIUtil.setFrameCenter(this);
		UIUtil.setFrameImage(this);
		// 给窗体添加背景图
		ImageIcon image = new ImageIcon("src\\cn\\lbg\\resource\\pool.jpg");
		JLabel frame1 = new JLabel(image);
		frame1.setBounds(0, 0, 422, 522);// 图片位置和大小
		getContentPane().add(frame1);
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
	}

	// 取消
	public void Performed(ActionEvent e) {
		goLogin();
	}

	public void sendkeyword() {
		try {
			// 设置按钮显示效果
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
			// 设置文本显示效果
			UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
			String email = this.email.getText().trim();

			// 邮箱规则：邮箱的基本格式为“名称@域名”，需要使用“^”匹配邮箱的开始部分，用“$”匹配邮箱结束部分以保证邮箱前后不能有其他字符
			if (email.equals("")) {
				JOptionPane.showMessageDialog(this, "邮箱不得为空");
				return;
			}
			String emailRegex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
			if (!email.matches(emailRegex)) {
				JOptionPane.showMessageDialog(this, "邮箱格式错误！");
				this.email.setText("");
				this.email.requestFocus();
				return;
			}

			ans = Random.achieveCode();
			MailUtil.send_mail(email, ans);
			JOptionPane.showMessageDialog(this, "邮件发送成功!请在收到后填写");
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(this, "邮件发送失败!请确认邮箱有效性");
			e.printStackTrace();
		}
	}

	public void choseman() {
		gengder = "男";
	}

	public void chosewoman() {
		gengder = "女";
	}

	// 注册
	public void RegistPerformed(ActionEvent e) {
		/*
		 * 分析: A:获取用户名和密码 B:用正则表达式做数据校验 C:封装成用户对象 D:调用用户操作的功能进行注册 E:回到登录界面登录
		 */

		// 获取用户名和密码
		String username = this.jftUsername.getText().trim();
		String password = this.jpfPassword.getText().trim();
		String resurepassword = this.resurepassword.getText().trim();
		String email = this.email.getText().trim();
		String birthday = this.birthday.getText().trim();
		String Verify = this.keyword.getText().trim();

		// 设置按钮显示效果
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
		// 设置文本显示效果
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("微软雅黑", Font.BOLD, 15)));
		if (username.equals("")) {
			JOptionPane.showMessageDialog(this, "用户名不得为空");
			return;
		}
		if (password.equals("")) {
			JOptionPane.showMessageDialog(this, "密码不得为空");
			return;
		}
		if (!resurepassword.equals(password)) {
			JOptionPane.showMessageDialog(this, "两次密码不一致！");
			return;
		}

		// 用正则表达式做数据校验
		// 定义规则
		// 用户名规则 大小写都可以
		String usernameRegex = "[0-9a-zA-z]{7}";

		// 密码规则
		String passwordRegex = "\\w{6,12}";

		// 校验
//		if (!username.matches(usernameRegex)) {
//			JOptionPane.showMessageDialog(this, "用户名不满足条件(请想一个由7个以上英文字母组成的名字)");
//			this.jftUsername.setText("");
//			this.jftUsername.requestFocus();
//			return;
//		}

		if (!password.matches(passwordRegex)) {
			JOptionPane.showMessageDialog(this, "密码不满足条件(请再想一个6位以上的任意单词字符组成的密码)");
			this.jpfPassword.setText("");
			this.jpfPassword.requestFocus();
			return;
		}
		if (!Verify.equals(ans)) {
			JOptionPane.showMessageDialog(this, "邮箱验证码错误");
			this.keyword.setText("");
			this.keyword.requestFocus();
			return;
		}

//		// 封装成用户对象
//		User user = new User();
//		user.setUsername(username);
//		user.setPassword(password);
//
//		// 调用用户操作的功能进行注册(多态的用法)
//		Userdao ud = new UserDaoimpl();
//		ud.regist(user);

		// 邮箱验证成功后可为用户生成QQ号
		makeID idWorker = new makeID(1, 1);
		long id = idWorker.nextId();
		String ID = String.valueOf(id).substring(4, 13);
		// 要把这个对象传给服务器去注册
		ClientUser qqClientUser = new ClientUser();
		User u = new User();
		u.setPassword(password);
		u.setUsername(username);
		u.setEmail(email);
		u.setBirthday(birthday);
		u.setGengder(gengder);
		u.setID(ID);
		u.setwant("注册");
		if (qqClientUser.registUser(u)) {
			// 给出提示
			JOptionPane.showMessageDialog(this, "用户注册成功,您的QQ号为" + ID);
			goLogin();
		}

	}

	// 回到登录页面
	private void goLogin() {
		LoginFrame lf = new LoginFrame();
		this.dispose();
		lf.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public RegistFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 428, 557);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		lblNewLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel.setBounds(55, 55, 72, 18);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel_1.setBounds(55, 107, 72, 18);
		contentPane.add(lblNewLabel_1);

		jftUsername = new JTextField();
		jftUsername.setBounds(141, 52, 212, 24);
		contentPane.add(jftUsername);
		jftUsername.setColumns(10);

		jpfPassword = new JPasswordField();
		jpfPassword.setBounds(141, 104, 212, 24);
		contentPane.add(jpfPassword);

		JButton btnNewButton = new JButton("\u6CE8\u518C");
		btnNewButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistPerformed(e);
			}
		});
		btnNewButton.setBounds(91, 465, 72, 27);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u53D6\u6D88");
		btnNewButton_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Performed(e);
			}
		});
		btnNewButton_1.setBounds(269, 465, 72, 27);
		contentPane.add(btnNewButton_1);

		resurepassword = new JPasswordField();
		resurepassword.setBounds(141, 155, 212, 24);
		contentPane.add(resurepassword);

		JLabel lblNewLabel_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		lblNewLabel_2.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel_2.setBounds(55, 158, 84, 18);
		contentPane.add(lblNewLabel_2);

		email = new JTextField();
		email.setBounds(141, 272, 212, 24);
		contentPane.add(email);
		email.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("\u90AE\u7BB1\uFF1A");
		lblNewLabel_3.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel_3.setBounds(55, 273, 72, 18);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("\u751F\u65E5\uFF1A");
		lblNewLabel_4.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel_4.setBounds(55, 215, 72, 18);
		contentPane.add(lblNewLabel_4);

		JRadioButton man = new JRadioButton("\u7537");
		man.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		man.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choseman();
			}
		});
		buttonGroup.add(man);
		man.setBounds(141, 333, 50, 27);
		// man.setSelected(true);
		contentPane.add(man);

		JRadioButton woman = new JRadioButton("\u5973");
		woman.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		woman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chosewoman();
			}
		});
		buttonGroup.add(woman);
		woman.setBounds(219, 333, 50, 27);
		contentPane.add(woman);

		JLabel lblNewLabel_5 = new JLabel("\u6027\u522B\uFF1A");
		lblNewLabel_5.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel_5.setBounds(55, 337, 72, 18);
		contentPane.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("\u90AE\u7BB1\u9A8C\u8BC1\u7801\uFF1A");
		lblNewLabel_6.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel_6.setBounds(44, 396, 106, 18);
		contentPane.add(lblNewLabel_6);

		keyword = new JTextField();
		keyword.setBounds(141, 393, 95, 24);
		contentPane.add(keyword);
		keyword.setColumns(10);

		JButton btnNewButton_2 = new JButton("\u53D1\u9001\u9A8C\u8BC1\u7801");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendkeyword();
			}
		});
		btnNewButton_2.setBounds(240, 392, 113, 27);
		contentPane.add(btnNewButton_2);
		
		birthday = new JTextField();
		birthday.setBounds(141, 214, 212, 24);
		birthday.setColumns(10);
		CalendarPanel p = new CalendarPanel(birthday, "yyyy-MM-dd");
		p.initCalendarPanel();
		contentPane.add(p);
		contentPane.add(birthday);

		textField = new JTextField();
		textField.setBounds(141, 213, 212, 24);
		contentPane.add(textField);
		textField.setColumns(10);

		init();
	}
}
