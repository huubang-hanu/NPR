package com.hanu.chat.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hanu.chat.customUI.CustomButton;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;


public class WelcomeView extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsername;
	private JPasswordField tfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeView frame = new WelcomeView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomeView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 670);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		CustomButton btnSignup = new CustomButton();
		btnSignup.kHoverStartColor = new Color(112, 128, 144);
		btnSignup.kHoverForeGround = new Color(253, 245, 230);
		btnSignup.kBackGroundColor = new Color(255, 165, 0);
		btnSignup.kEndColor = new Color(255, 255, 224);
		btnSignup.kBorderRadius = 20;
		btnSignup.setBounds(320, 415, 137, 39);
		btnSignup.setText("Sign up");
		btnSignup.setFont(new Font("Segoe UI", Font.BOLD, 18));
		contentPane.add(btnSignup);
		
		CustomButton btnSignIn = new CustomButton();
		btnSignIn.kSelectedColor = new Color(34, 139, 34);
		btnSignIn.kBackGroundColor = new Color(218, 165, 32);
		btnSignIn.kHoverEndColor = new Color(169, 169, 169);
		btnSignIn.kHoverStartColor = new Color(0, 128, 128);
		btnSignIn.kHoverForeGround = new Color(240, 248, 255);
		btnSignIn.kPressedColor = new Color(105, 105, 105);
		btnSignIn.kEndColor = new Color(255, 255, 0);
		btnSignIn.kBorderRadius = 20;
		btnSignIn.setBounds(160, 415, 137, 39);
		contentPane.add(btnSignIn);
		btnSignIn.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnSignIn.setText("Sign in");
		
		JLabel lblNewLabel_5 = new JLabel("<html> Helps you connect and share with the people in your life </html>");
		lblNewLabel_5.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_5.setFont(new Font("MV Boli", Font.PLAIN, 23));
		lblNewLabel_5.setBounds(499, 245, 290, 143);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\logo.png"));
		lblNewLabel_4.setBounds(700, 399, 64, 64);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\icons8-chat-100.png"));
		lblNewLabel_3.setBounds(499, 149, 111, 85);
		contentPane.add(lblNewLabel_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(160, 386, 297, 2);
		contentPane.add(separator_1);
		
		tfPassword = new JPasswordField();
		tfPassword.setForeground(Color.BLACK);
		tfPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tfPassword.setBounds(160, 357, 296, 33);
		contentPane.add(tfPassword);
		tfPassword.setOpaque(false);
		tfPassword.setBorder(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(160, 299, 296, 2);
		contentPane.add(separator);
		
		tfUsername = new JTextField();
		tfUsername.setForeground(Color.BLACK);
		tfUsername.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tfUsername.setBounds(160, 272, 296, 33);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		tfUsername.setOpaque(false);
		tfUsername.setBorder(null);
		
		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.ITALIC, 18));
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\icons8-password-30.png"));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(160, 327, 111, 30);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.ITALIC, 21));
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\icons8-user-30.png"));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(156, 244, 129, 30);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\1900851.png"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 869, 631);
		contentPane.add(lblNewLabel);
	}
}
