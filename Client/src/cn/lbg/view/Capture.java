package cn.lbg.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;

import cn.lbg.pojo.Message;
import cn.lbg.pojo.Messagedao;
import cn.lbg.tools.ManageCCST;
import cn.lbg.tools.ManageChat;
import cn.lbg.util.UIUtil;
import cn.itcast.util.MyLookandFeel;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import java.awt.Font;

import static javax.swing.UIManager.setLookAndFeel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Capture {

	private JFrame frame;
	JProgressBar progressBar;
	AudioFormat audioFormat;
	TargetDataLine targetDataLine;
	long testtime;
	String owner;
	String friend;
	String groupid;
	JTextPane ContentArea;
	String type;
	File audioFile = null;

	/**
	 * Launch the application. public static void main(String[] args) {
	 * EventQueue.invokeLater(new Runnable() { public void run() { try { Capture
	 * window = new Capture(); window.frame.setVisible(true); } catch (Exception e)
	 * { e.printStackTrace(); } } }); }
	 */

	public void begin() {
		progressBar.setString("正在录音...");
		MyThread progress = new MyThread();
		progress.start();
		captureAudio();// 调用录音方法
		testtime = System.currentTimeMillis();
	}

	public void send() {
		try {
			closeCaptureAudio();
			frame.setVisible(false);
			System.out.println("录音了" + (System.currentTimeMillis() - testtime) / 1000 + "秒！");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String date = simpleDateFormat.format(new Date());
			String info = owner + "  " + date + "\n";
			ObjectOutputStream oos = new ObjectOutputStream(
					ManageCCST.getClientConServerThread(owner).getS().getOutputStream());
			Message m = new Message();
			m.setMesType(Messagedao.message_Voice);
			m.setSender(owner);
			m.setGetter(friend);
			m.setFilename(owner);
			m.setUrl(audioFile.getAbsolutePath());
			m.setCon(owner);
			m.setTime((System.currentTimeMillis() - testtime) / 1000);
			m.setSendTime(date);
			oos.writeObject(m);

			try {
				if (type.equals("1")) {
					Pchat chat = ManageChat.getPChat(m.getSender() + m.getGetter());
					chat.showVoice(m);
				} else {
					GChat chat=ManageChat.getGChat(m.getSender());
					chat.showVoice(m);
				}

			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			Socket socket = new Socket("127.0.0.1", 8888);
			OutputStream out = socket.getOutputStream();
			FileInputStream fis = new FileInputStream(audioFile);
			byte buf[] = new byte[1024];
			int len = 0;
			while ((len = fis.read(buf)) != -1) {
				out.write(buf);
			}
			socket.shutdownOutput();
			out.close();
			socket.close();
			fis.close();
			frame.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeCaptureAudio() {
		targetDataLine.stop();
		targetDataLine.close();
	}

	public void captureAudio() {
		try {
			audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			// 开启线程
			new CaptureThread().start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 8000F;
		int sampleSizeInBits = 16;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	class CaptureThread extends Thread {
		public void run() {
			AudioFileFormat.Type fileType = null;
			fileType = AudioFileFormat.Type.WAVE;
			String url = null;
			if(type.equals("1")) {
				url = "C:\\Users\\李秉庚\\Desktop\\file\\"+owner+"&"+friend;
			} else {
				url = "C:\\Users\\李秉庚\\Desktop\\file\\"+groupid;
			}
			File dir = new File(url);
			if(!dir.exists()){
                dir.mkdir();
            }
			audioFile = new File(dir,owner+".wav");
			int count = 1;
			while (audioFile.exists()){
				audioFile=new File(dir, owner+"("+(count++)+")"+".wav");
			}
			try {
				targetDataLine.open(audioFormat);
				targetDataLine.start();
				AudioSystem.write(new AudioInputStream(targetDataLine), fileType, audioFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Create the application.
	 */
	public Capture(String owner, String friend, JTextPane ContentArea, String type, String groupid) {
		try {
			setLookAndFeel(MyLookandFeel.SYS_WINDOWS);
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
		this.owner = owner;
		this.friend = friend;
		this.ContentArea = ContentArea;
		this.type = type;
		this.groupid = groupid;
		initialize();
		frame.setTitle("录音");
		UIUtil.setFrameCenter(frame);
		UIUtil.setFrameImage(frame);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 150);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		progressBar.setForeground(new Color(0, 255, 0));
		progressBar.setBounds(14, 13, 404, 34);
		progressBar.setStringPainted(true);
		progressBar.setString("最大时长60s");
		frame.getContentPane().add(progressBar);

		JButton btnNewButton = new JButton("\u5F00\u59CB");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				begin();
			}
		});
		btnNewButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnNewButton.setBounds(81, 60, 113, 27);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u53D1\u9001");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		btnNewButton_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 15));
		btnNewButton_1.setBounds(242, 60, 113, 27);
		frame.getContentPane().add(btnNewButton_1);
	}

	class MyThread extends Thread {// 自定义线程，实现进度的不断变化
		@Override
		public void run() {
			for (int i = 0; i <= 100; i++) {
				progressBar.setValue(i);// 让进度不断变化
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
