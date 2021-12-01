package com.hanu.chat.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;

public class ServerView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfIP;
	private JTextField tfPort;
	private JScrollPane container;
	private JScrollPane listContainer;
	private JList<String> listUserOnline;
	private JTextArea serverLog;
	private JLabel lbIp;
	private JLabel lbPort;
	private JButton btnStart;
	private JLabel lbServerStatus;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerView frame = new ServerView();
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
	public ServerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BT Social Network Server");
		setBounds(100, 100, 651, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		container = new JScrollPane();
		container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		container.setBounds(181, 117, 444, 333);
		contentPane.add(container);
		
		serverLog = new JTextArea();
		serverLog.setForeground(new Color(255, 255, 255));
		serverLog.setBackground(new Color(0, 128, 128));
		serverLog.setLineWrap(true);
		serverLog.setEditable(false);
		container.setViewportView(serverLog);
		
		lbIp = new JLabel("IP Address: ");
		lbIp.setBounds(27, 11, 71, 14);
		contentPane.add(lbIp);
		
		tfIP = new JTextField();
		tfIP.setText("127.0.0.1");
		tfIP.setEditable(false);
		tfIP.setBounds(108, 8, 71, 20);
		contentPane.add(tfIP);
		tfIP.setColumns(10);
		
		lbPort = new JLabel("Port: ");
		lbPort.setBounds(205, 11, 33, 14);
		contentPane.add(lbPort);
		
		tfPort = new JTextField();
		tfPort.setBounds(248, 8, 79, 20);
		contentPane.add(tfPort);
		tfPort.setColumns(10);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validatePort(tfPort.getText())) {
					int port = Integer.parseInt(tfPort.getText());
					startServer(port);
				}else {
					showError();
					
				}
			}
		});
		btnStart.setBounds(536, 11, 89, 23);
		contentPane.add(btnStart);
		
		lbServerStatus = new JLabel("");
		lbServerStatus.setBackground(new Color(255, 255, 255));
		lbServerStatus.setForeground(new Color(0, 0, 255));
		lbServerStatus.setFont(new Font("Sylfaen", Font.PLAIN, 17));
		lbServerStatus.setBounds(28, 39, 373, 34);
		contentPane.add(lbServerStatus);
		
		
		listContainer = new JScrollPane();
		listContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listContainer.setBounds(27, 117, 144, 333);
		contentPane.add(listContainer);
		listUserOnline = new JList<String>();
		listUserOnline.setBounds(27, 84, 127, 310);
		listContainer.setViewportView(listUserOnline);
		
		JLabel lblNewLabel = new JLabel("Online Users:");
		lblNewLabel.setFont(new Font("Leelawadee UI Semilight", Font.BOLD, 14));
		lblNewLabel.setBounds(27, 90, 109, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Server Log:");
		lblNewLabel_1.setFont(new Font("Leelawadee UI Semilight", Font.BOLD, 14));
		lblNewLabel_1.setBounds(181, 86, 79, 20);
		contentPane.add(lblNewLabel_1);
	}
	
	/**
	 * Start new thread for server listening request from user
	 * @param port
	 */
	private void startServer(int port) {
		Server server = new Server(port, lbServerStatus, serverLog,listUserOnline);
		Thread th  = new Thread() {
			@Override
			public void run() {
				server.init();
			};
		};
		
		th.start();
		
	}
	
	private boolean validatePort(String port) {
		try {  
		    Integer.parseInt(port);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	private void showError() {
		JOptionPane.showMessageDialog(this,"Your port number is not valid. Try again");  
	}
}
