package cn.lbg.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import cn.lbg.pojo.Message;
import cn.lbg.pojo.Messagedao;
import cn.lbg.tools.ManageCCST;
import cn.lbg.util.UIUtil;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class SetCommonlanguage {

	private JFrame frame;
	private JTextField textField;
	String user;
	String friend;

	/**
	 * Launch the application.
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { SetCommonlanguage window = new
	 * SetCommonlanguage(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the application.
	 */
	public SetCommonlanguage(String user,String friend) {
		this.user = user;
		this.friend = friend;
		initialize();
		UIUtil.setFrameCenter(frame);
		UIUtil.setFrameImage(frame);
		frame.setTitle("Ìí¼Ó");
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 190);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("\u586B\u5165\u4E00\u6761\u4F60\u60F3\u6DFB\u52A0\u7684\u5E38\u7528\u8BED");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ Light", Font.BOLD, 15));
		lblNewLabel.setBounds(115, 13, 201, 18);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(68, 44, 288, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\u6DFB\u52A0");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(
							ManageCCST.getClientConServerThread(user).getS().getOutputStream());
					Message m = new Message();
					m.setMesType(Messagedao.message_set_Common_language);
					m.setSender(user);
					m.setGetter(friend);
					m.setCon(textField.getText().trim());
					oos.writeObject(m);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Î¢ÈíÑÅºÚ Light", Font.BOLD, 15));
		btnNewButton.setBounds(158, 81, 113, 27);
		frame.getContentPane().add(btnNewButton);
	}
}
