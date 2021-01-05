package cn.lbg.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

public class DataCard {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataCard window = new DataCard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DataCard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 15));
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u6635\u79F0\uFF1A");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 15));
		lblNewLabel.setBounds(87, 55, 72, 18);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8D26\u53F7\uFF1A");
		lblNewLabel_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 15));
		lblNewLabel_1.setBounds(87, 109, 72, 18);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u6027\u522B\uFF1A");
		lblNewLabel_2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 15));
		lblNewLabel_2.setBounds(87, 164, 72, 18);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u4E2A\u6027\u7B7E\u540D\uFF1A");
		lblNewLabel_3.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 15));
		lblNewLabel_3.setBounds(87, 213, 88, 18);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u751F\u65E5\uFF1A");
		lblNewLabel_4.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 15));
		lblNewLabel_4.setBounds(87, 298, 72, 18);
		frame.getContentPane().add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("\u4FDD\u5B58\u4FEE\u6539");
		btnNewButton.setFont(new Font("µÈÏß", Font.BOLD, 15));
		btnNewButton.setBounds(87, 388, 113, 27);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u53D6\u6D88");
		btnNewButton_1.setFont(new Font("µÈÏß", Font.BOLD, 15));
		btnNewButton_1.setBounds(230, 388, 113, 27);
		frame.getContentPane().add(btnNewButton_1);
		
		textField = new JTextField();
		textField.setBounds(173, 53, 170, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(173, 107, 170, 24);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("µÈÏß", Font.BOLD, 15));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u7537 \u2642", "\u5973 \u2640"}));
		comboBox.setBounds(173, 162, 170, 24);
		frame.getContentPane().add(comboBox);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(171, 212, 172, 70);
		frame.getContentPane().add(textArea);
	}
}
