package com.hanu.chat.client;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import com.hanu.chat.customUI.CustomButton;
import java.awt.SystemColor;

public class Login extends JPanel {
	private JTextField textField;
	private JPasswordField passwordField;
	
	/**
	 * Create the panel.
	 */
	public Login() {
		setLayout(null);
		setBounds(0, 0,765, 453);
		
		passwordField = new JPasswordField();
		passwordField.setOpaque(false);
		passwordField.setForeground(Color.BLACK);
		passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		passwordField.setBorder(null);
		passwordField.setBounds(68, 272, 296, 33);
		add(passwordField);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\icons8-user-30.png"));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.ITALIC, 21));
		lblNewLabel_1.setBounds(64, 159, 129, 30);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\icons8-chat-100.png"));
		lblNewLabel_3.setBounds(407, 64, 111, 85);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\logo.png"));
		lblNewLabel_4.setBounds(608, 314, 64, 64);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("<html> Helps you connect and share with the people in your life </html>");
		lblNewLabel_5.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_5.setFont(new Font("MV Boli", Font.PLAIN, 23));
		lblNewLabel_5.setBounds(407, 160, 290, 143);
		add(lblNewLabel_5);
		
		textField = new JTextField();
		textField.setOpaque(false);
		textField.setForeground(Color.BLACK);
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBorder(null);
		textField.setBounds(68, 187, 296, 33);
		add(textField);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(68, 301, 297, 2);
		add(separator_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(68, 214, 296, 2);
		add(separator);
		
		CustomButton btnSignIn = new CustomButton();
		btnSignIn.kSelectedColor = new Color(34, 139, 34);
		btnSignIn.setkSelectedColor(new Color(34, 139, 34));
		btnSignIn.kPressedColor = SystemColor.controlDkShadow;
		btnSignIn.setkPressedColor(SystemColor.controlDkShadow);
		btnSignIn.kHoverStartColor = new Color(0, 128, 128);
		btnSignIn.setkHoverStartColor(new Color(0, 128, 128));
		btnSignIn.kHoverForeGround = new Color(240, 248, 255);
		btnSignIn.setkHoverForeGround(new Color(240, 248, 255));
		btnSignIn.kHoverEndColor = new Color(169, 169, 169);
		btnSignIn.setkHoverEndColor(new Color(169, 169, 169));
		btnSignIn.kEndColor = Color.YELLOW;
		btnSignIn.setkEndColor(Color.YELLOW);
		btnSignIn.kBorderRadius = 20;
		btnSignIn.setkBorderRadius(20);
		btnSignIn.kBackGroundColor = new Color(218, 165, 32);
		btnSignIn.setkBackGroundColor(new Color(218, 165, 32));
		btnSignIn.setBounds(68, 330, 137, 39);
		btnSignIn.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnSignIn.setText("Sign in");
		add(btnSignIn);
		
		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\icons8-password-30.png"));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.ITALIC, 18));
		lblNewLabel_2.setBounds(68, 242, 111, 30);
		add(lblNewLabel_2);
		
		CustomButton btnSignup = new CustomButton();
		btnSignup.kHoverStartColor = new Color(112, 128, 144);
		btnSignup.setkHoverStartColor(new Color(112, 128, 144));
		btnSignup.kHoverForeGround = new Color(253, 245, 230);
		btnSignup.setkHoverForeGround(new Color(253, 245, 230));
		btnSignup.kEndColor = new Color(255, 255, 224);
		btnSignup.setkEndColor(new Color(255, 255, 224));
		btnSignup.kBorderRadius = 20;
		btnSignup.setkBorderRadius(20);
		btnSignup.kBackGroundColor = new Color(255, 165, 0);
		btnSignup.setkBackGroundColor(new Color(255, 165, 0));
		btnSignup.setBounds(228, 330, 137, 39);
		btnSignup.setText("Sign up");
		btnSignup.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(btnSignup);
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon("C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\1900851.png"));
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setBounds(0, 0,765, 453);
		add(background);

	}
}
