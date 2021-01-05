/**
 * ����һ�����촰��
 */
package cn.lbg.view;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.BadLocationException;

import cn.lbg.pojo.Messagedao;
import cn.lbg.model.ContentTrans;
import cn.lbg.model.MyFont;
import cn.lbg.pojo.Contents;
import cn.lbg.pojo.Message;
import cn.lbg.tools.ManageCCST;
import cn.lbg.util.UIUtil;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pchat {

	private JFrame frame;
	private JTextPane ContentArea = new JTextPane();
	private JTextPane SendArea = new JTextPane();
	JComboBox<String> comboBox;
	String owner;
	String friend;
	String path = null;
	String filename = null;
	JFileChooser fileChooser;
	File file;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Pchat window = new Pchat("1","2");
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
	public Pchat(String owner, String friend) {
		this.owner = owner;
		this.friend = friend;
		initialize();
		frame.setTitle(owner + " ���ں� " + friend + " ����");
		UIUtil.setFrameCenter(frame);
		UIUtil.setFrameImage(frame);
		frame.setVisible(true);

	}

	// ������ʾ��Ϣ�ķ���
	public void showMessage(Message m) throws BadLocationException {
		String info = m.getSender() + " " + m.getSendTime() + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		ContentTrans.contentDecode(ContentArea, m.getContent());
		try {
			BufferedWriter bw = new BufferedWriter(
					new FileWriter("ChatRecord\\" + m.getSender() + "&" + owner + ".txt", true));
			bw.write(info + "wenzi\n" + m.getCon() + '\n');
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showFile(Message m) throws BadLocationException {
		String info = m.getCon() + " " + m.getSendTime() + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		showFileUI(m);
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
		try {
			ContentArea.insertComponent(lb);
			String line = "";
			new MyFont().setText(ContentArea, line + '\n', new Font("΢���ź� Light", Font.BOLD, 15), Color.black);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		try {
			String info = m.getCon() + " " + m.getSendTime() + "\n";
			BufferedWriter bw = new BufferedWriter(
					new FileWriter("ChatRecord\\" + m.getSender() + "&" + m.getGetter() + ".txt", true));
			bw.write(info + "wenjian\n" + m.getUrl() + '\n' + m.getFilename() + '\n');
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPicture(Message m) throws BadLocationException {
		String info = m.getCon() + " " + m.getSendTime() + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		ImageIcon img = new ImageIcon(m.getUrl());
		ContentArea.insertIcon(img);
		try {
			BufferedWriter bw = new BufferedWriter(
					new FileWriter("ChatRecord\\" + m.getSender() + "&" + m.getGetter() + ".txt", true));
			bw.write(info + "tupian\n" + m.getUrl() + '\n');
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showVoice(Message m) throws BadLocationException {
		String info = m.getCon() + " " + m.getSendTime() + "\n";
		new MyFont().setText(ContentArea, info, new Font("����", Font.PLAIN, 14), Color.BLUE);
		showVoiceUI(m);

		BufferedWriter bw;
		try {
			bw = new BufferedWriter(
					new FileWriter("ChatRecord\\" + m.getSender() + "&" + m.getGetter() + ".txt", true));
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

	public void showchatrecord() throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader("ChatRecord\\" + owner + "&" + friend + ".txt"));
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
					} else if (line.equals("yuyin")) {
						line = br.readLine();
						Message m = new Message();
						m.setUrl(line);
						line = br.readLine();
						m.setTime(Long.parseLong(line));
						showVoiceUI(m);
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
			BufferedWriter bw = new BufferedWriter(
					new FileWriter("ChatRecord\\" + friend + "&" + owner + ".txt", true));
			bw.write(info + "wenzi\n" + SendArea.getText() + '\n');
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Message m = new Message();
		m.setMesType(Messagedao.message_comm_mes);
		m.setSender(this.owner);
		m.setGetter(this.friend);
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
		file = null;
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
			file = fileChooser.getSelectedFile();
			System.out.println("path: " + path);
		} else {
			return;
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
			int index = file.getName().indexOf(".");
			String suffix = file.getName().substring(index);
			m.setMesType(Messagedao.message_File);
			m.setSender(this.owner);
			m.setGetter(this.friend);
			m.setFilename(file.getName().substring(0, index));
			m.setCon(this.owner);
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

			byte suffixBuf[] = suffix.getBytes();
			Socket socket = null;
			socket = new Socket("127.0.0.1", 8080);
			// send the suffix first
			OutputStream out = socket.getOutputStream();
			out.write(suffixBuf, 0, suffixBuf.length);
			out.write('\n');
			// send the file
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			byte buf[] = new byte[1024];
			int len = 0;
			while ((len = bis.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			socket.shutdownOutput();
			out.close();
			socket.close();
			bis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			m.setGetter(this.friend);
			m.setCon(this.owner);
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

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendvoice() {
		new Capture(this.owner, this.friend, this.ContentArea, "1", null);
	}

	public void showCommonlanguage(Message m) {
		Vector<String> v = m.getFriend();
		Iterator<String> it = v.iterator();
		while(it.hasNext()) {
			String line = it.next();
			comboBox.addItem(line);
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
		frame.getContentPane().setBackground(new Color(100, 149, 237));
		frame.setBounds(100, 100, 750, 507);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 704, 300);
		frame.getContentPane().add(scrollPane);
		ContentArea.setFont(new Font("΢���ź� Light", Font.BOLD, 15));
		ContentArea.setEditable(false);

		scrollPane.setViewportView(ContentArea);
		SendArea.setFont(new Font("΢���ź� Light", Font.BOLD, 15));

		SendArea.setBounds(14, 353, 704, 67);
		frame.getContentPane().add(SendArea);

		JButton btnNewButton = new JButton("\u5173\u95ED");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("΢���ź� Light", Font.BOLD, 15));
		btnNewButton.setBounds(544, 426, 80, 27);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u53D1\u9001");
		btnNewButton_1.setFont(new Font("΢���ź� Light", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Send();
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(638, 426, 80, 27);
		frame.getContentPane().add(btnNewButton_1);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\ztl.jpg"));
		lblNewLabel.setBounds(14, 314, 588, 38);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\ztl1.jpg"));
		lblNewLabel_1.setBounds(602, 314, 116, 38);
		frame.getContentPane().add(lblNewLabel_1);

		JButton Sendpicture = new JButton("New button");
		Sendpicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendpicture();
			}
		});
		Sendpicture.setOpaque(false);
		Sendpicture.setContentAreaFilled(false);
		Sendpicture.setBounds(231, 319, 36, 27);
		frame.getContentPane().add(Sendpicture);

		JButton SendFile = new JButton("New button");
		SendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendfile();
			}
		});
		SendFile.setContentAreaFilled(false);
		SendFile.setOpaque(false);
		SendFile.setBounds(140, 319, 36, 27);
		frame.getContentPane().add(SendFile);

		JButton Sendface = new JButton("New button");
		Sendface.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Emoticon(SendArea);
			}
		});
		Sendface.setOpaque(false);
		Sendface.setContentAreaFilled(false);
		Sendface.setBounds(14, 319, 39, 27);
		frame.getContentPane().add(Sendface);

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendArea.setText(comboBox.getSelectedItem().toString());
			}
		});
		comboBox.setFont(new Font("΢���ź� Light", Font.BOLD, 14));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"\u5FEB\u6377\u5E38\u7528\u8BED", "\u4F60\u597D\u554A", "\u6211\u4E0D\u77E5\u9053", "\u6536\u5230"}));
		comboBox.setBounds(378, 429, 127, 24);
		frame.getContentPane().add(comboBox);

		JButton Sendvoice = new JButton("New button");
		Sendvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendvoice();
			}
		});
		Sendvoice.setContentAreaFilled(false);
		Sendvoice.setOpaque(false);
		Sendvoice.setBounds(281, 314, 36, 38);
		frame.getContentPane().add(Sendvoice);	
		
		JButton set = new JButton("New button");
		set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SetCommonlanguage(owner,friend);
			}
		});
		set.setIcon(new ImageIcon("src\\cn\\lbg\\resource\\sz.jpg"));
		set.setContentAreaFilled(false);
		set.setBounds(342, 426, 36, 29);
		frame.getContentPane().add(set);

		try {
			showchatrecord();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
