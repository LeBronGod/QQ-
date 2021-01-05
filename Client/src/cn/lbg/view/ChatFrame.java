package cn.lbg.view;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.lbg.util.UIUtil;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.ImageIcon;

public class ChatFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea chatcontentTextArea = new JTextArea();
	private static final String CONNSTR = "192.168.43.117";
	private static final int CONNPORT = 6666;
	private Socket s = null;
	
	private DataOutputStream dos = null;
	
	private boolean isconn = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFrame frame = new ChatFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void init() {
		this.setTitle("群聊");
		UIUtil.setFrameCenter(this);
		UIUtil.setFrameImage(this);
		try {
			s = new Socket(CONNSTR, CONNPORT);
			isconn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("hhh");
		new Thread(new Recive()).start();
	}
	
	public void sendTOserver(String str) {
		try {
			if(str.trim().length()==0) {
				JOptionPane.showMessageDialog(this, "内容不能为空");
				return;
			}
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//多线程的接受服务器反发的消息的类
	class Recive implements Runnable {

		@Override
		public void run() {
			try {
				while(isconn) {
					System.out.println("hhh");
					DataInputStream dis = new DataInputStream(s.getInputStream());
					String str = dis.readUTF();
					System.out.println("收到消息"+str);
					chatcontentTextArea.append(str);
				}
			} catch (SocketException e) {
				System.out.println("服务器意外中断");
				chatcontentTextArea.append("服务器意外中断");
				//e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Create the frame.
	 */
	public ChatFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 704, 324);
		contentPane.add(scrollPane);
		
		//JTextArea chatcontentTextArea_1 = new JTextArea();
		scrollPane.setViewportView(chatcontentTextArea);
		chatcontentTextArea.setEditable(false);
		
		JTextArea sendcontentTextArea = new JTextArea();
		sendcontentTextArea.setBounds(14, 383, 585, 57);
		contentPane.add(sendcontentTextArea);
		
		JButton cfclose = new JButton("\u5173\u95ED");
		cfclose.setBounds(619, 383, 99, 26);
		cfclose.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\发送按钮.jpg"));
		cfclose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(cfclose);
		
		JButton cfsend = new JButton("\u53D1\u9001");
		cfsend.setBounds(619, 413, 99, 26);
		cfsend.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\关闭.jpg"));
		cfsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String st = sendcontentTextArea.getText();
				
				//发送至服务器端
				sendTOserver(st);
				sendcontentTextArea.setText("");
				//chatcontentTextArea_1.append(st+"\n");
				sendcontentTextArea.requestFocus();
			}
		});
		contentPane.add(cfsend);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(449, 351, 150, 24);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				sendcontentTextArea.setText(comboBox.getSelectedItem().toString());;
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u5FEB\u6377\u5E38\u7528\u8BED", "\u4F60\u597D\u554A", "\u6211\u4E0D\u77E5\u9053", "\u6536\u5230", "\u665A\u70B9\u8BF4\u73B0\u5728\u6709\u4E8B", "\u4F60\u8BF4\u5462", "\u5230\u54EA\u4E86"}));
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(14, 346, 345, 34);
		lblNewLabel_1.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\功能栏.jpg"));
		contentPane.add(lblNewLabel_1);
		
		init();
		
	}
}
