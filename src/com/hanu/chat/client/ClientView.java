package com.hanu.chat.client;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane chatTabPane;
	private Client client;

	/**
	 * Create the frame.
	 */
	public ClientView(Client client,String[] onlineUser, String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Add event handle before close chat app
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//Exit client
				
				client.exit();
				System.exit(0);
			}
		});
		setBounds(100, 100, 798, 576);
		setTitle("BT Social Network");
		contentPane = new JPanel();
		contentPane.setBackground(new Color(127, 255, 212));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		chatTabPane = new JTabbedPane(JTabbedPane.TOP);
		chatTabPane.setBounds(10, 38, 762, 479);
		contentPane.add(chatTabPane);

		this.client = client;
		JLabel lbUsername = new JLabel("Username: " + username);
		lbUsername.setFont(new Font("Imprint MT Shadow", Font.ITALIC, 13));
		lbUsername.setBounds(10, 11, 214, 23);
		contentPane.add(lbUsername);
		client.setUsername(username);
		initUI(onlineUser, username);
	}
	
	
	
	/**
	 *
	 *	Create <b>Global tab</b><br> 
	 *	Add <b>Tab</b> for all user who already online and update UI
	 * 
	 * 
	 * @param excludeUsername
	 */
	private void initUI(String[] usernames, String excludeUsername) {
		
		//Get all online users
//		String[] usernames = repo.getUsernames();

		//Set global tab
		ChatTab globalTab  = new ChatTab(client, "Global");
		chatTabPane.add("Global",globalTab);
		
		//Set other tab exclude current user
		if(usernames.length > 0) {
			for (int i = 0; i < usernames.length; i++) {
				if(!usernames[i].equals(excludeUsername)) {
					ChatTab tab  = new ChatTab(client, usernames[i]);
					chatTabPane.add(usernames[i],tab);
					tab.setClient(client);
				}
			}
		}
	}



	public JTabbedPane getChatTabPane() {
		return chatTabPane;
	}



	public void setChatTabPane(JTabbedPane chatTabPane) {
		this.chatTabPane = chatTabPane;
	}
	
	
}
