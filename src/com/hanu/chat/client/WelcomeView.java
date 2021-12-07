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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		setTitle("BT Social Network");
		setBounds(250, 30, 885, 670);
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
		ImageIcon iconFeature = createImageIcon("/logo.png",
                "logo icon");
		lblNewLabel_4.setIcon(iconFeature);
		lblNewLabel_4.setBounds(700, 399, 64, 64);
		contentPane.add(lblNewLabel_4);
			
		JLabel lblNewLabel_3 = new JLabel("");
		ImageIcon logo = createImageIcon("/icons8-chat-100.png",
                "logo icon");
		lblNewLabel_3.setIcon(logo);
		lblNewLabel_3.setBounds(499, 149, 111, 85);
		contentPane.add(lblNewLabel_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(160, 386, 297, 2);
		contentPane.add(separator_1);
		
		tfPassword = new JPasswordField();
		tfPassword.setForeground(Color.BLACK);
		tfPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tfPassword.setBounds(160, 357, 296, 31);
		contentPane.add(tfPassword);
		tfPassword.setOpaque(false);
		tfPassword.setBorder(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(160, 299, 296, 2);
		contentPane.add(separator);
		
		tfUsername = new JTextField();
		tfUsername.setForeground(Color.BLACK);
		tfUsername.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tfUsername.setBounds(160, 272, 296, 30);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		tfUsername.setOpaque(false);
		tfUsername.setBorder(null);
		
		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.ITALIC, 18));
		ImageIcon passwordIcon = createImageIcon("/icons8-password-30.png",
                "user icon");
		lblNewLabel_2.setIcon(passwordIcon);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(160, 327, 111, 30);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.ITALIC, 21));
		ImageIcon userIcon = createImageIcon("/icons8-user-30.png",
                "user icon");
		lblNewLabel_1.setIcon(userIcon);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(156, 244, 129, 30);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		ImageIcon background = createImageIcon("/1900851.png",
                "logo icon");
		
		lblNewLabel.setIcon(background);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 869, 631);
		contentPane.add(lblNewLabel);
		Client client = new Client("127.0.0.1", 8085, this);
		
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.login(tfUsername.getText(), String.valueOf(tfPassword.getPassword()));
			}
		});
		
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.signUp(tfUsername.getText(), String.valueOf(tfPassword.getPassword()));
			}
		});
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path,
	                                           String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
}
