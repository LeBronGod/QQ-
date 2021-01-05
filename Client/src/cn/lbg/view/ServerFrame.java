package cn.lbg.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.lbg.util.UIUtil;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;


import javax.swing.JScrollPane;

public class ServerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea serverdetails = new JTextArea();
	private static final int PORT = 6666;

	private static ServerSocket ss = null;
	private ArrayList<Clientconnect> cclist = new ArrayList<Clientconnect>();

	private boolean isstart = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrame frame = new ServerFrame();
					frame.setVisible(true);
					frame.StartServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void init() {
		this.setTitle("������");
		UIUtil.setFrameCenter(this);
		UIUtil.setFrameImage(this);
		if(isstart) {
			serverdetails.append("�����������ã��ȴ��ͻ��˽���\n");
		} else {
			serverdetails.append("���������������ʹ�����ȵ������\n");
		}
		
	}

	// ������������������һ������
	class Clientconnect implements Runnable {
		Socket s = null;

		public Clientconnect(Socket s) {
			this.s = s;
			(new Thread(this)).start();
		}

		// ͬʱ���ܿͻ�����Ϣ(���̵߳�����)
		@Override
		public void run() {
			try {
				DataInputStream dis = new DataInputStream(s.getInputStream());
				while (isstart) {
					String st = dis.readUTF();
					String str = s.getInetAddress().getHostAddress() +"/"+ s.getPort() + " ˵: "+ st+'\n';
					//System.out.println(str);
					serverdetails.append(str);
					//����cclist������ÿ���ͻ���
					Iterator<Clientconnect> it = cclist.iterator();
					while(it.hasNext()) {
						Clientconnect c = it.next();
						System.out.println(str);
						c.send(str);
					}
					
				}
			} catch (SocketException e) {
				System.out.println(s.getInetAddress().getHostAddress() +"/"+ s.getPort()+"�ͻ�������");
				serverdetails.append(s.getInetAddress().getHostAddress() +"/"+ s.getPort()+"�ͻ�������");
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void send(String str) {
			try {
				DataOutputStream doss = new DataOutputStream(this.s.getOutputStream());
				doss.writeUTF(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// �������˵Ľ���(���̵߳�����)
//	public void reciveFROMcilent() {
//		try {
//			dis = new DataInputStream(s.getInputStream());
//			String st = dis.readUTF();
//			serverdetails.append(st + '\n');
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	// ����������
	public void StartServer(){
		try {
			try {
				ss = new ServerSocket(PORT);
				isstart = true;
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			while (isstart) {
				Socket s = ss.accept();
				cclist.add(new Clientconnect(s));
				System.out.println("�����û� : " + s.getInetAddress().getHostAddress() + " �˿ں� : " + s.getPort() + '\n');
				serverdetails.append("�����û� : " + s.getInetAddress().getHostAddress() + " �˿ں� : " + s.getPort() + '\n');
			}
		} catch (SocketException e) {
			System.out.println("�����������ж�");
			serverdetails.append("�����������ж�");
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// �رշ�����
	public void stopserver() {
		
		try {
			
			if (ss != null) {
				ss.close();
				isstart = false;
			}
			System.exit(0);
			System.out.println("�������ѶϿ�\n");
			serverdetails.append("�������ѶϿ�\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���������ť���¼�
	public void startPerformed() {
		try {
			if(ss == null) {
				ss = new ServerSocket(PORT);
			}
			isstart = true;
			serverdetails.append("������������!"+'\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public ServerFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton startserver = new JButton("\u5F00\u542F\u670D\u52A1\u5668");
		startserver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startPerformed();
			}
		});
		startserver.setBounds(168, 37, 113, 27);
		contentPane.add(startserver);
		
		JButton closeserver = new JButton("\u5173\u95ED\u670D\u52A1\u5668");
		closeserver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopserver();
			}
		});
		closeserver.setBounds(310, 37, 113, 27);
		contentPane.add(closeserver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 85, 704, 355);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(serverdetails);

		serverdetails.setEditable(false);

		JButton exitserver = new JButton("\u9000\u51FA\u670D\u52A1\u5668");
		exitserver.setBounds(460, 37, 113, 27);
		contentPane.add(exitserver);

		init();
		
	}
}
