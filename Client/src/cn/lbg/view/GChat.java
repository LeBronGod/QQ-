package cn.lbg.view;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.BadLocationException;

import cn.lbg.model.ContentTrans;
import cn.lbg.model.MyFont;
import cn.lbg.pojo.Contents;
import cn.lbg.pojo.Message;
import cn.lbg.pojo.Messagedao;
import cn.lbg.pojo.User;
import cn.lbg.tools.ManageCCST;
import cn.lbg.tools.ManageChat;
import cn.lbg.util.UIUtil;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import javax.swing.JScrollPane;

public class GChat {

	private JFrame frame;
	private JTextPane ContentArea;
	private JTextPane SendArea;
	private JPanel memberlist;
	private JLabel jb1s;
	String owner;
	String groupId;
	String path = null;
	String filename = null;
	JFileChooser fileChooser;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GChat window = new GChat("nihao","shabi");
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
	public GChat(String owner, String groupId) {
		this.owner = owner;
		this.groupId = groupId;
		initialize();
		frame.setTitle("Ⱥ��");
		UIUtil.setFrameCenter(frame);
		UIUtil.setFrameImage(frame);
		frame.setVisible(true);
	}

	// ������ʾ��Ϣ�ķ���
	public void showMessage(Message m) throws BadLocationException {
		String info = m.getSender() + "  " + m.getSendTime() + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		new MyFont().setText(ContentArea, m.getCon(), new Font("΢���ź� Light", Font.BOLD, 15), Color.black);
	}

	public void showFile(Message m) throws BadLocationException {
		String info = m.getSender() + " " + m.getSendTime() + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		showFileUI(m);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("ChatRecord\\" + groupId + ".txt", true));
			bw.write(info + "wenjian\n" + m.getUrl() + '\n' + m.getFilename() + '\n');
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showPicture(Message m) throws BadLocationException {
		String info = m.getSender() + " " + m.getSendTime() + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		ImageIcon img = new ImageIcon(m.getUrl());
		ContentArea.insertIcon(img);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("ChatRecord\\" + groupId + ".txt", true));
			bw.write(info + "tupian\n" + m.getUrl() + '\n');
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initmemberlist(Message m) {
		Vector<User> friend = m.getFriend();
		Iterator<User> it = friend.iterator();
		System.out.println("Ⱥ��Ա��" + friend.size());
		while (it.hasNext()) {
			User user = it.next();
			jb1s = new JLabel(user.getUsername(), new ImageIcon("src\\cn\\lbg\\resource\\xqe.jpg"), JLabel.LEFT);
			jb1s.setFont(new Font("΢���ź� Light", Font.BOLD, 12));
			jb1s.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// ��Ӧ�û�˫�����¼������õ����ѵı��.
					if (e.getClickCount() == 2) {
						Pchat pchat = new Pchat(owner, user.getUsername() + user.getID());
						// �����������뵽������
						ManageChat.addPChat(owner + user.getUsername() + user.getID(), pchat);
					}
				}
			});
			memberlist.add(jb1s);
		}
		memberlist.updateUI();
	}

	public void showchatrecord() throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader("ChatRecord\\" + groupId + ".txt"));
			String line = null;
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (i % 2 == 0) {
					new MyFont().setText(ContentArea, line + '\n', new Font("����", Font.PLAIN, 14), Color.BLUE);
				} else {
					if (line.equals("wenzi")) {
						line = br.readLine();
						new MyFont().setText(ContentArea, line + '\n', new Font("΢���ź� Light", Font.BOLD, 15),
								Color.black);
					} else if (line.equals("tupian")) {
						line = br.readLine();
						ImageIcon img = new ImageIcon(line);
						ContentArea.insertIcon(img);
					} else if (line.equals("wenjian")) {
						line = br.readLine();
						Message m = new Message();
						m.setUrl(line);
						line = br.readLine();
						m.setFilename(line);
						showFileUI(m);
					}
				}
				i++;
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Send() throws BadLocationException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = simpleDateFormat.format(new Date());
		List<Contents> content = ContentTrans.contentEncode(SendArea);
		String info = this.owner + "  " + date + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		ContentTrans.contentDecode(ContentArea, content);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("ChatRecord\\" + groupId + ".txt", true));
			bw.write(info + "wenzi\n" + SendArea.getText() + '\n');
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Message m = new Message();
		m.setMesType(Messagedao.message_comm_mes_group);
		m.setSender(this.owner);
		m.setGetter(this.groupId);
		m.setCon(SendArea.getText());
		m.setSendTime(date);
		m.setContent(content);
		// ���͸�������.
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					ManageCCST.getClientConServerThread(owner).getS().getOutputStream());
			oos.writeObject(m);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SendArea.setText("");
		}
	}

	public void choosefile() {

		int result = 0;
		File file = null;
		fileChooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView(); // ������Ҫ��һ��
		System.out.println(fsv.getHomeDirectory()); // �õ�����·��
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		fileChooser.setDialogTitle("��ѡ��Ҫ�ϴ����ļ�...");
		fileChooser.setApproveButtonText("��");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		result = fileChooser.showOpenDialog(frame);
		if (JFileChooser.APPROVE_OPTION == result) {
			path = fileChooser.getSelectedFile().getPath();
			filename = fileChooser.getSelectedFile().getName();
			System.out.println("path: " + path);
		} else {
			return;
		}
	}

	public void sendpicture() {
		choosefile();
		File file = fileChooser.getSelectedFile();
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("΢���ź�", Font.BOLD, 15)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("΢���ź�", Font.BOLD, 15)));
		if (!(file.getName().endsWith(".jpg") || file.getName().endsWith(".gif"))) {
			JOptionPane.showMessageDialog(null, "�ļ���ʽ����,�ļ���չ��������jpg��gif��");
			return;
		}
		if (file.length() >= 1024 * 1024 * 2) {
			JOptionPane.showMessageDialog(null, "�ļ����󣬲�Ӧ����2M���������ϴ���");
			return;
		}

		// �ϴ�
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					ManageCCST.getClientConServerThread(owner).getS().getOutputStream());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String date = simpleDateFormat.format(new Date());
			Message m = new Message();
			m.setMesType(Messagedao.message_Picture);
			m.setSender(this.owner);
			m.setGetter(this.groupId);
			m.setGroupid(this.groupId);
			m.setFilename(filename);
			m.setSendTime(date);
			oos.writeObject(m);

			Socket s = new Socket("127.0.0.1", 10007);

			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
			// �ϴ��ļ���׺
			OutputStream out = s.getOutputStream();
			byte buf[] = new byte[1024];
			int len = 0;
			while ((len = bin.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			s.shutdownOutput();// ���߷��������ļ��ϴ����
			// ��ȡ�������Ļ�����Ϣ
			InputStream in = s.getInputStream();
			byte buf2[] = new byte[1024];
			int len2 = in.read(buf2);
			System.out.println(new String(buf2, 0, len2));
			// ����
			out.close();
			bin.close();
			s.close();
			try {
				String info = this.owner + "  " + date + "\n";
				new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ImageIcon img = new ImageIcon(path);
			ContentArea.insertIcon(img);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendfile() {
		choosefile();
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String date = simpleDateFormat.format(new Date());
			ObjectOutputStream oos = new ObjectOutputStream(
					ManageCCST.getClientConServerThread(owner).getS().getOutputStream());
			Message m = new Message();
			m.setMesType(Messagedao.message_File);
			m.setSender(this.owner);
			m.setGetter(this.groupId);
			m.setGroupid(this.groupId);
			m.setFilename(filename);
			m.setUrl(path);
			m.setSendTime(date);
			oos.writeObject(m);

			try {
				String info = this.owner + "  " + date + "\n";
				new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
				showFileUI(m);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

			Socket s = new Socket("", 10006);
			OutputStream os = s.getOutputStream();
			BufferedReader bf = new BufferedReader(new FileReader(path));
			PrintWriter pw = new PrintWriter(os, true);

			String str = null;
			while ((str = bf.readLine()) != null) {
				pw.println(str);
			}
			pw.println("over#$@#@$");
			s.shutdownOutput();
			bf.close();

			InputStream in = s.getInputStream();
			DataInputStream din = new DataInputStream(in);
			System.out.println("serverӦ��:" + din.readUTF());
			s.close();
			din.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showVoice(Message m) throws BadLocationException {
		String info = m.getCon() + " " + m.getSendTime() + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		showVoiceUI(m);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("ChatRecord\\" + groupId + ".txt", true));
			bw.write(info + "yuyin\n" + m.getUrl() + '\n' + m.getTime() + '\n');
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showVoiceUI(Message m) {
		File file = new File(m.getUrl());
		JLabel lb = new JLabel("(" + m.getTime() + "s)", new ImageIcon("src\\cn\\lbg\\resource\\yy.png"), JLabel.LEFT);
		lb.setFont(new Font("΢���ź�", Font.BOLD, 13));
		lb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					try {
						AudioInputStream as = AudioSystem.getAudioInputStream(file);
						AudioFormat format = as.getFormat();
						// ת���ļ�����
						if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
							format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16,
									format.getChannels(), format.getChannels() * 2, format.getSampleRate(), false);
							as = AudioSystem.getAudioInputStream(format, as);
						}
						SourceDataLine sdl = null;
						DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
						sdl = (SourceDataLine) AudioSystem.getLine(info);
						sdl.open(format);
						sdl.start();
						int nBytesRead = 0;
						byte[] abData = new byte[1024];
						while (nBytesRead != -1) {
							nBytesRead = as.read(abData, 0, abData.length);
							if (nBytesRead >= 0)
								sdl.write(abData, 0, nBytesRead);
						}
						// �ر�SourceDataLine
						sdl.drain();
						sdl.close();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		try {
			ContentArea.insertComponent(lb);
			String line = "";
			new MyFont().setText(ContentArea, line + '\n', new Font("΢���ź� Light", Font.BOLD, 15), Color.black);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	public void sendvoice() {
		new Capture(this.owner, null, this.ContentArea, "2", this.groupId);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});
		frame.getContentPane().setBackground(new Color(100, 149, 237));
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		ContentArea = new JTextPane();
		ContentArea.setFont(new Font("΢���ź� Light", Font.BOLD, 15));
		ContentArea.setBounds(14, 13, 560, 298);
		frame.getContentPane().add(ContentArea);
		ContentArea.setEditable(false);

		SendArea = new JTextPane();
		SendArea.setFont(new Font("΢���ź� Light", Font.BOLD, 15));
		SendArea.setBounds(14, 350, 704, 59);
		frame.getContentPane().add(SendArea);

		JButton btnNewButton = new JButton("\u53D1\u9001");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Send();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("΢���ź� Light", Font.BOLD, 14));
		btnNewButton.setBounds(639, 413, 79, 27);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u5173\u95ED");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_1.setFont(new Font("΢���ź� Light", Font.BOLD, 14));
		btnNewButton_1.setBounds(533, 413, 79, 27);
		frame.getContentPane().add(btnNewButton_1);

		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("΢���ź� Light", Font.BOLD, 14));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendArea.setText(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "\u5FEB\u6377\u5E38\u7528\u8BED",
				"\u4F60\u597D\u554A", "\u6211\u4E0D\u77E5\u9053", "\u6536\u5230",
				"\u665A\u70B9\u8BF4\u73B0\u5728\u6709\u4E8B", "\u4F60\u8BF4\u5462", "\u5230\u54EA\u4E86" }));
		comboBox.setBounds(358, 413, 133, 27);
		frame.getContentPane().add(comboBox);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		lblNewLabel.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\ztl.jpg"));
		lblNewLabel.setBounds(14, 312, 602, 37);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\ztl1.jpg"));
		lblNewLabel_1.setBounds(607, 312, 111, 37);
		frame.getContentPane().add(lblNewLabel_1);

		JButton SendFile = new JButton("New button");
		SendFile.setOpaque(false);
		SendFile.setContentAreaFilled(false);
		SendFile.setBounds(140, 312, 39, 37);
		frame.getContentPane().add(SendFile);
		SendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosefile();
			}
		});

		JButton Sendpicture = new JButton("New button");
		Sendpicture.setContentAreaFilled(false);
		Sendpicture.setOpaque(false);
		Sendpicture.setBounds(231, 312, 39, 37);
		frame.getContentPane().add(Sendpicture);
		Sendpicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Emoticon(SendArea);
			}
		});

		JButton Sendface = new JButton("New button");
		Sendface.setOpaque(false);
		Sendface.setContentAreaFilled(false);
		Sendface.setBounds(14, 312, 39, 37);
		frame.getContentPane().add(Sendface);
		Sendface.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choosefile();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(577, 15, 141, 295);
		frame.getContentPane().add(scrollPane);
		memberlist = new JPanel();
		scrollPane.setViewportView(memberlist);
		memberlist.setBackground(new Color(255, 255, 255));
		memberlist.setLayout(new GridLayout(50, 1, 4, 4));

		JButton Sendvoice = new JButton("New button");
		Sendvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendvoice();
			}
		});
		Sendvoice.setContentAreaFilled(false);
		Sendvoice.setBounds(275, 317, 39, 27);
		frame.getContentPane().add(Sendvoice);

		try {
			showchatrecord();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void showFileUI(Message m) {
		File file = new File(m.getUrl());
		JLabel lb = new JLabel(m.getFilename() + " (" + getSize(file.length()) + ")",
				new ImageIcon("src\\cn\\lbg\\resource\\wj.jpg"), JLabel.LEFT);
		lb.setFont(new Font("΢���ź�", Font.BOLD, 14));
		lb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					final Runtime runtime = Runtime.getRuntime();
					final String cmd = "rundll32 url.dll FileProtocolHandler file://" + m.getUrl();
					try {
						runtime.exec(cmd);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		ContentArea.insertComponent(lb);
	}

	public static String getSize(long size) {
		// ����ֽ�������1024����ֱ����BΪ��λ�������ȳ���1024����3λ��̫��������
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		// ���ԭ�ֽ�������1024֮������1024�������ֱ����KB��Ϊ��λ
		// ��Ϊ��û�е���Ҫʹ����һ����λ��ʱ��
		// ����ȥ�Դ�����
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			// ��Ϊ�����MBΪ��λ�Ļ���Ҫ�������1λС����
			// ��ˣ��Ѵ�������100֮����ȡ��
			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
		} else {
			// �������Ҫ��GBΪ��λ�ģ��ȳ���1024����ͬ���Ĵ���
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
		}
	}
}
