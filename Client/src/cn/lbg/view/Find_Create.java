package cn.lbg.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.MatteBorder;

import cn.lbg.model.makeID;
import cn.lbg.pojo.Message;
import cn.lbg.pojo.Messagedao;
import cn.lbg.tools.ManageCCST;
import cn.lbg.tools.ManageChat;
import cn.lbg.tools.ManageFriendList;
import cn.lbg.util.UIUtil;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class Find_Create {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField2;
	private JTextField textField3;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JPanel Findfriend = new JPanel();
	private JPanel Findgroup = new JPanel();
	private JPanel Creategroup = new JPanel();
	private String groupType;
	private String username;
	private String userid;
	private String groupid;
	private String friend;
	JPanel showfriendpanel = new JPanel();
	JPanel showgrouppanel = new JPanel();
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Find_Create window = new Find_Create("1","1");
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Find_Create(String username,String userid) {
		this.username = username;
		this.userid = userid;
		initialize();
		frame.setTitle("查找");
		UIUtil.setFrameCenter(frame);
		UIUtil.setFrameImage(frame);
	}

	public void Notify() {
		JOptionPane.showMessageDialog(frame, "群聊创建成功,群号为" + groupid+"赶快邀请你的"+groupType+"加入吧!");
	}
	
	public void showfriend(Message m) {
		if(m.getCon().equals("None")) {
			JOptionPane.showMessageDialog(frame, "不好意思，用户不存在");
			return;
		} 
		JLabel jbl = new JLabel(m.getCon(), new ImageIcon("src\\cn\\Test\\resource\\QQ.jpg"), JLabel.LEFT);
		jbl.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		jbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int result = JOptionPane.showConfirmDialog(frame, "是否添加其为好友？");
					if(result == 0) {
						try {
							Message m2 = new Message();
							m2.setMesType(Messagedao.message_Makefriend);
							m2.setOwnerid(userid);
							m2.setSender(username);
							m2.setGetter(m.getCon());
							m2.setCon(friend);
							ObjectOutputStream oos = new ObjectOutputStream(ManageCCST.getClientConServerThread(username+userid).getS().getOutputStream());
							oos.writeObject(m2);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		showfriendpanel.add(jbl);
	}
	
	public void showgroup(Message m) {
		if(m.getCon().equals("None")) {
			JOptionPane.showMessageDialog(frame, "不好意思，群聊不存在");
			return;
		} 
		JLabel jbl = new JLabel(m.getCon()+"  群主："+m.getOwnername(), new ImageIcon("src\\cn\\Test\\resource\\QQ.jpg"), JLabel.LEFT);
		jbl.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		jbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int result = JOptionPane.showConfirmDialog(frame, "确定加入群聊？");
					if(result == 0) {
						try {
							Message m2 = new Message();
							m2.setMesType(Messagedao.message_Joingroup);
							m2.setGroupid(groupid);
							m2.setGetter(m.getCon());
							m2.setSender(userid);
							m2.setCon(username);
							ObjectOutputStream oos = new ObjectOutputStream(ManageCCST.getClientConServerThread(username+userid).getS().getOutputStream());
							oos.writeObject(m2);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		showgrouppanel.add(jbl);
	}
	
	public void creategroup() {
		String groupname = textField3.getText().trim();
		makeID idWorker = new makeID(1, 1);
		long id = idWorker.nextId();
		groupid = String.valueOf(id).substring(4, 13);
		Message m = new Message();
		m.setMesType(Messagedao.message_Creategroup);
		m.setGroupid(groupid);
		m.setGroupname(groupname);
		m.setGrouptype(groupType);
		m.setOwnerid(userid);
		m.setOwnername(username);
		m.setSender(username);
		m.setSendTime(new java.util.Date().toString());
		try {
			ObjectOutputStream oos = new ObjectOutputStream(ManageCCST.getClientConServerThread(username+userid).getS().getOutputStream());
			oos.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnNewButton = new JButton("\u627E\u4EBA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setForeground(Color.BLUE);
				btnNewButton_1.setForeground(Color.BLACK);
				btnNewButton_2.setForeground(Color.BLACK);
				Findfriend.setVisible(true);
				Findgroup.setVisible(false);
				Creategroup.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		btnNewButton.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setOpaque(false);
		btnNewButton.setBounds(82, 0, 113, 40);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("\u627E\u7FA4");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setForeground(Color.BLACK);
				btnNewButton_1.setForeground(Color.BLUE);
				btnNewButton_2.setForeground(Color.BLACK);
				Findfriend.setVisible(false);
				Findgroup.setVisible(true);
				Creategroup.setVisible(false);
			}
		});
		btnNewButton_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		btnNewButton_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setBounds(237, 0, 113, 40);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("\u5EFA\u7FA4");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setForeground(Color.BLACK);
				btnNewButton_1.setForeground(Color.BLACK);
				btnNewButton_2.setForeground(Color.BLUE);
				Findfriend.setVisible(false);
				Findgroup.setVisible(false);
				Creategroup.setVisible(true);
			}
		});
		btnNewButton_2.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		btnNewButton_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setOpaque(false);
		btnNewButton_2.setBounds(383, 0, 113, 40);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("|");
		lblNewLabel.setBounds(208, 2, 21, 35);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("|");
		lblNewLabel_1.setBounds(364, 2, 21, 35);
		frame.getContentPane().add(lblNewLabel_1);
		Findfriend.setBackground(new Color(135, 206, 235));
		
		Findfriend.setBounds(0, 40, 582, 313);
		frame.getContentPane().add(Findfriend);
		Findfriend.setLayout(null);
		
		textField = new JTextField();
		textField.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(0, 0, 0)));
		textField.setBounds(142, 65, 201, 37);
		Findfriend.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\ss.jpg"));
		btnNewButton_3.setBounds(337, 65, 105, 37);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				friend = textField.getText().trim();
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageCCST.getClientConServerThread(username+userid).getS().getOutputStream());
					Message m = new Message();
					m.setMesType(Messagedao.message_Findfriend);
					m.setGetter(friend);
					m.setSender(username+userid);
					oos.writeObject(m);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		Findfriend.add(btnNewButton_3);
		
		showfriendpanel.setBackground(new Color(135, 206, 235));
		showfriendpanel.setBounds(142, 123, 300, 77);
		Findfriend.add(showfriendpanel);
		
		
		Findgroup.setBackground(new Color(173, 216, 230));
		
		Findgroup.setBounds(0, 40, 582, 313);
		frame.getContentPane().add(Findgroup);
		Findgroup.setLayout(null);
		
		textField2 = new JTextField();
		textField2.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(0, 0, 0)));
		textField2.setBounds(142, 65, 201, 37);
		textField2.setColumns(10);
		Findgroup.add(textField2);
		
		showgrouppanel.setBackground(new Color(135, 206, 235));
		showgrouppanel.setBounds(142, 123, 300, 77);
		Findgroup.add(showgrouppanel);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\ss.jpg"));
		btnNewButton_4.setBounds(341, 65, 105, 37);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String group = textField.getText().trim();
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageCCST.getClientConServerThread(username).getS().getOutputStream());
					Message m = new Message();
					m.setMesType(Messagedao.message_Findgroup);
					m.setGetter(group);
					m.setSender(username);
					oos.writeObject(m);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		Findgroup.add(btnNewButton_4);
		
		Creategroup.setBackground(new Color(224, 255, 255));
		
		Creategroup.setBounds(0, 40, 582, 313);
		frame.getContentPane().add(Creategroup);
		Creategroup.setLayout(null);
		
		JLabel lblNewLabel2 = new JLabel("\u7FA4\u540D\u79F0\uFF1A");
		lblNewLabel2.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel2.setBounds(141, 58, 72, 18);
		Creategroup.add(lblNewLabel2);
		
		textField3 = new JTextField();
		textField3.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		textField3.setBounds(249, 55, 146, 24);
		Creategroup.add(textField3);
		textField3.setColumns(10);
		
		JLabel lblNewLabel1 = new JLabel("\u7FA4\u7C7B\u578B\uFF1A");
		lblNewLabel1.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		lblNewLabel1.setBounds(141, 117, 72, 18);
		Creategroup.add(lblNewLabel1);
		
		JButton btnNewButton = new JButton("\u521B\u5EFA\u7FA4\u804A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creategroup();
			}
		});
		btnNewButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnNewButton.setBounds(249, 176, 113, 27);
		Creategroup.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				groupType = comboBox.getSelectedItem().toString();
			}
		});
		comboBox.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u670B\u53CB", "\u540C\u5B66", "\u540C\u4E8B"}));
		comboBox.setBounds(249, 114, 86, 24);
		Creategroup.add(comboBox);
		Creategroup.setVisible(false);
		
		frame.setVisible(true);
	}

	public void reactmf(Message m) {
		if(m.getCon().equals("fail")) {
			JOptionPane.showMessageDialog(frame, "对不起，添加好友失败");
		} else {
			FriendList fl = ManageFriendList.getQqFriendList(m.getGetter());
			fl.addfriendlabel(m.getSender(),m.getOwnerid());
		}
		
	}

	public void reactjg(Message m) {
		if(m.getCon().equals("fail")) {
			JOptionPane.showMessageDialog(frame, "对不起，加入群聊失败");
		} else {
			FriendList fl = ManageFriendList.getQqFriendList(m.getGetter());
			fl.addgrouplabel(m.getSender(),m.getGroupid());
		}
		
	}
	
}
