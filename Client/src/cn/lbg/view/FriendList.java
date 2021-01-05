/**
 * 这是我的好友列表
 */
package cn.lbg.view;

import javax.swing.JFrame;

import cn.lbg.pojo.Message;
import cn.lbg.pojo.Messagedao;
import cn.lbg.pojo.User;
import cn.lbg.model.ClientConnServerThread;
import cn.lbg.pojo.Group;
import cn.lbg.tools.ManageCCST;
import cn.lbg.tools.ManageChat;
import cn.lbg.tools.ManageFC;
import cn.lbg.tools.ManageLabel;
import cn.lbg.util.UIUtil;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.border.MatteBorder;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class FriendList {

	private JFrame frame;
	private JTextField motto;
	private JButton Group = new JButton("\u7FA4\u804A");
	private JButton Friend = new JButton("\u597D\u53CB");
	private JButton Notify = new JButton("\u901A\u77E5");
	private JPanel Newspanel = new JPanel();
	private JPanel Friendpanel = new JPanel();
	private JPanel Notifypanel = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JScrollPane scrollPane_2 = new JScrollPane();
	String username;
	String userid;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FriendList window = new FriendList("1","1");
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	// 更新在线的好友情况
	public void upateFriend(Message m) {
		JLabel label = ManageLabel.getLabel(m.getCon());
		System.out.println(m.getGetter());
		if (label.isEnabled()) {
			label.setEnabled(false);
		} else {
			label.setEnabled(true);
		}
	}
	public void addNotify(Message m) {
		JLabel jb1s = new JLabel(m.getCon(), new ImageIcon("src\\cn\\lbg\\resource\\tz.jpg"), JLabel.LEFT);
		jb1s.setFont(new Font("微软雅黑 Light", Font.BOLD, 14));
		System.out.println("来提醒了");
		jb1s.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Notifypanel.remove(jb1s);
					Notifypanel.updateUI();
					try {
						// 发包表示该条消息已读可删
						ObjectOutputStream oos = new ObjectOutputStream(
								ManageCCST.getClientConServerThread(m.getGetter()).getS().getOutputStream());
						Message m = new Message();
						m.setMesType(Messagedao.message_removeNotify);
						m.setSender(username+userid);
						oos.writeObject(m);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		Notifypanel.add(jb1s);
		Notifypanel.updateUI();
	}
	public void breakdata(Message m) {
		JLabel label = ManageLabel.getLabel(m.getCon());
		System.out.println(m.getCon()+"  label:"+label);
		if(m.getGroupid() != null) {
			Friendpanel.remove(label);
			Friendpanel.updateUI();
		} else {
			Newspanel.remove(label);
			Newspanel.updateUI();
		}
	}

	public void initfriendlist(Message m) {
		Vector<User> friend = m.getFriend();
		Iterator<User> it = friend.iterator();

		while (it.hasNext()) {
			User user = it.next();
			JLabel jb1s = new JLabel(user.getUsername(), new ImageIcon("src\\cn\\lbg\\resource\\QQ.jpg"), JLabel.LEFT);
			ManageLabel.addLabel(user.getUsername() + user.getID(), jb1s);
			if (user.getOnline().equals("1")) {
				jb1s.setEnabled(true);
			} else {
				jb1s.setEnabled(false);
			}
			jb1s.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
			
			JPopupMenu popuMenu = new JPopupMenu();	
			JMenuItem ac = new JMenuItem("删除好友");
			ac.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//发包删好友
					try {
						ObjectOutputStream oos = new ObjectOutputStream(
								ManageCCST.getClientConServerThread(m.getGetter()).getS().getOutputStream());
						Message m = new Message();
						m.setMesType(Messagedao.message_breakfriend);
						m.setSender(username);
						m.setOwnerid(userid);
						m.setGetter(user.getUsername());
						m.setCon(user.getID());
						oos.writeObject(m);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			popuMenu.add(ac);

			jb1s.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// 响应用户双击的事件，并得到好友的编号.
					if (e.getClickCount() == 2) {
						Pchat pchat = new Pchat(username + userid, user.getUsername() + user.getID());
						// 把聊天界面加入到管理类
						ManageChat.addPChat(username + userid + user.getUsername() + user.getID(), pchat);
						try {
							// 发送一个要求返回常用语的请求包.
							ObjectOutputStream oos = new ObjectOutputStream(
									ManageCCST.getClientConServerThread(m.getGetter()).getS().getOutputStream());
							Message m = new Message();
							m.setMesType(Messagedao.message_get_Common_language);
							m.setSender(username+userid);
							oos.writeObject(m);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else if(e.getButton()==MouseEvent.BUTTON3) {
						popuMenu.show(jb1s,e.getX(),e.getY());
					}
				}
			});
			Newspanel.add(jb1s);
		}
		Newspanel.updateUI();
	}

	public void initgrouplist(Message m) {
		Vector<Group> friend = m.getFriend();
		Iterator<Group> it = friend.iterator();

		while (it.hasNext()) {
			Group group = it.next();
			System.out.println(group.getGroupname());
			JLabel jb1s = new JLabel(group.getGroupname(), new ImageIcon("src\\cn\\lbg\\resource\\QQ.jpg"), JLabel.LEFT);
			ManageLabel.addLabel(userid+group.getGroupid(), jb1s);
			jb1s.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
			JPopupMenu popuMenu = new JPopupMenu();	
			JMenuItem ac = new JMenuItem("退出群聊");
			ac.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//发包退群
					try {
						ObjectOutputStream oos = new ObjectOutputStream(
								ManageCCST.getClientConServerThread(m.getGetter()).getS().getOutputStream());
						Message m2 = new Message();
						m2.setMesType(Messagedao.message_outgroup);
						m2.setGroupid(group.getGroupid());
						m2.setGroupname(group.getGroupname());
						m2.setSender(userid);
						m2.setCon(username);
						oos.writeObject(m2);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			popuMenu.add(ac);
			jb1s.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// 响应用户双击的事件，并得到群聊的编号.
					if (e.getClickCount() == 2) {
						// 得到该群的编号 把聊天界面加入到管理类
						// String groupname = ((JLabel) e.getSource()).getText();
						GChat Gchat = new GChat(username + userid, group.getGroupid());
						ManageChat.addGChat(group.getGroupid(), Gchat);

						try {
							// 发送一个要求返回在线群友的请求包.
							ObjectOutputStream oos = new ObjectOutputStream(
									ManageCCST.getClientConServerThread(m.getGetter()).getS().getOutputStream());
							Message m = new Message();
							m.setMesType(Messagedao.message_get_memberlist);
							m.setSender(group.getGroupid());
							oos.writeObject(m);
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else if(e.getButton()==MouseEvent.BUTTON3) {
						popuMenu.show(jb1s,e.getX(),e.getY());
					}
				}
			});
			Friendpanel.add(jb1s);
		}
		Friendpanel.updateUI();
	}
	public void initNotify(Message m) {
		Vector<String> friend = m.getFriend();
		Iterator<String> it = friend.iterator();

		while (it.hasNext()) {
			String notify = it.next();
			JLabel jb1s = new JLabel(notify, new ImageIcon("src\\cn\\lbg\\resource\\tz.jpg"), JLabel.LEFT);
			jb1s.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
			jb1s.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						Notifypanel.remove(jb1s);
						Notifypanel.updateUI();
						try {
							// 发包表示该条消息已读可删
							ObjectOutputStream oos = new ObjectOutputStream(
									ManageCCST.getClientConServerThread(m.getGetter()).getS().getOutputStream());
							Message m = new Message();
							m.setMesType(Messagedao.message_removeNotify);
							m.setSender(username+userid);
							oos.writeObject(m);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			Notifypanel.add(jb1s);
		}
		Notifypanel.updateUI();
	}

	/**
	 * Create the application.
	 */
	public FriendList(String username, String userid) {
		this.username = username;
		this.userid = userid;
		initialize();
		frame.setTitle("好友列表");
		UIUtil.setFrameCenter(frame);
		UIUtil.setFrameImage(frame);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// 发包告知服务器该客户端下线更改客户在线状态
				try {
					ObjectOutputStream oos = new ObjectOutputStream(
							ManageCCST.getClientConServerThread(username + userid).getS().getOutputStream());
					Message m = new Message();
					m.setMesType(Messagedao.message_Clientleave);
					m.setSender(username + userid);
					m.setCon(userid);
					oos.writeObject(m);
					ClientConnServerThread ccst = ManageCCST.getClientConServerThread(username + userid);
					ccst.getS().close();
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 153, 255));
		panel_1.setBounds(0, 0, 432, 136);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u5934\u50CF");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

				}
			}
		});
		lblNewLabel.setIcon(new ImageIcon(FriendList.class.getResource("/cn/lbg/resource/QQ.jpg")));
		lblNewLabel.setBounds(35, 26, 62, 70);
		panel_1.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(username);
		lblNewLabel_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		lblNewLabel_1.setBounds(138, 33, 123, 18);
		panel_1.add(lblNewLabel_1);

		motto = new JTextField();
		motto.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		motto.setBackground(new Color(102, 153, 255));
		motto.setBorder(null);
		motto.setBounds(138, 72, 140, 24);
		panel_1.add(motto);
		motto.setColumns(10);
		motto.setOpaque(false);
		motto.requestFocus(false);

		JPanel panel = new JPanel();
		panel.setBounds(0, 136, 432, 39);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		Friend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Group.setForeground(Color.BLACK);
				Friend.setForeground(Color.BLUE);
				Notify.setForeground(Color.BLACK);
				Newspanel.setVisible(true);
				Friendpanel.setVisible(false);
				Notifypanel.setVisible(false);
				scrollPane.setVisible(true);
				scrollPane_1.setVisible(false);
				scrollPane_2.setVisible(false);
			}
		});
		Friend.setContentAreaFilled(false);
		Friend.setOpaque(false);
		Friend.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		Friend.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		Friend.setBounds(0, 0, 134, 39);
		panel.add(Friend);

		Group.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Group.setForeground(Color.BLUE);
				Friend.setForeground(Color.BLACK);
				Notify.setForeground(Color.BLACK);
				Newspanel.setVisible(false);
				Friendpanel.setVisible(true);
				Notifypanel.setVisible(false);
				scrollPane.setVisible(false);
				scrollPane_1.setVisible(true);
				scrollPane_2.setVisible(false);
			}
		});
		Group.setContentAreaFilled(false);
		Group.setOpaque(false);
		Group.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		Group.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		Group.setBounds(148, 0, 134, 39);
		panel.add(Group);
		
		Notify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Notify.setForeground(Color.BLUE);
				Friend.setForeground(Color.BLACK);
				Group.setForeground(Color.BLACK);
				Newspanel.setVisible(false);
				Friendpanel.setVisible(false);
				Notifypanel.setVisible(true);
				scrollPane.setVisible(false);
				scrollPane_1.setVisible(false);
				scrollPane_2.setVisible(true);
			}
		});
		Notify.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		Notify.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		Notify.setOpaque(false);
		Notify.setContentAreaFilled(false);
		Notify.setBounds(298, 0, 134, 39);
		panel.add(Notify);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 652, 432, 49);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\FLD.jpg"));
		lblNewLabel_2.setBounds(0, 0, 432, 49);
		panel_2.add(lblNewLabel_2);

		JButton Findfriend = new JButton("New button");
		Findfriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Find_Create search = new Find_Create(username, userid);
				ManageFC.addFC(username + userid, search);
			}
		});
		Findfriend.setBounds(55, 0, 48, 49);
		panel_2.add(Findfriend);

		scrollPane.setBounds(0, 174, 432, 479);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(Newspanel);
		Newspanel.setLayout(new GridLayout(500, 1, 4, 4));

		scrollPane_1.setBounds(0, 174, 432, 479);
		frame.getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(Friendpanel);
		Friendpanel.setLayout(new GridLayout(500, 1, 4, 4));
		
		scrollPane_2.setBounds(0, 174, 432, 479);
		frame.getContentPane().add(scrollPane_2);
		scrollPane_2.setViewportView(Notifypanel);
		Notifypanel.setLayout(new GridLayout(500, 1, 4, 4));

		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 436, 734);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	public void addfriendlabel(String name, String id) {
		JLabel jb1s = new JLabel(name, new ImageIcon("src\\cn\\lbg\\resource\\QQ.jpg"), JLabel.LEFT);
		ManageLabel.addLabel(name + id, jb1s);
		jb1s.setEnabled(false);
		jb1s.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		jb1s.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 响应用户双击的事件，并得到好友的编号.
				if (e.getClickCount() == 2) {
					Pchat pchat = new Pchat(username + userid, name + id);
					// 把聊天界面加入到管理类
					ManageChat.addPChat(username + userid + name + id, pchat);
				}
			}
		});
		Newspanel.add(jb1s);
	}

	public void addgrouplabel(String name, String id) {
		JLabel jb1s = new JLabel(name, new ImageIcon("src\\cn\\lbg\\resource\\QQ.jpg"), JLabel.LEFT);
		jb1s.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		jb1s.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					GChat Gchat = new GChat(username, id);
					ManageChat.addGChat(id, Gchat);
					try {
						ObjectOutputStream oos = new ObjectOutputStream(
								ManageCCST.getClientConServerThread(username + userid).getS().getOutputStream());
						Message m = new Message();
						m.setMesType(Messagedao.message_get_memberlist);
						m.setSender(id);
						oos.writeObject(m);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		Friendpanel.add(jb1s);
	}
}
