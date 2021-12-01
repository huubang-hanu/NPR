package com.hanu.chat.client;


import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.ScrollPaneConstants;

import com.hanu.chat.util.Packet;
import com.hanu.chat.util.Tag;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ChatTab extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client client;
	private JTextArea content;
	private JTextArea message;
	private JButton btnSend;
	/**
	 * Create the panel.
	 */
	public ChatTab(Client client, String receiver) {
		this.client = client;
		setLayout(null);
		setBounds(10, 11, 546, 337);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 546, 268);
		add(scrollPane);
		
		content = new JTextArea();
		content.setEditable(false);
		content.setLineWrap(true);
		content.setBounds(123, 204, 118, 22);
		scrollPane.setViewportView(content);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 279, 436, 30);
		add(scrollPane_1);
		
		message = new JTextArea();
		message.setLineWrap(true);
		message.setBounds(119, 260, 44, 22);
		scrollPane_1.setViewportView(message);
		
		btnSend = new JButton("Send");
		String sender = this.client.getUsername();
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = message.getText();
				if(msg.trim().length() == 0) {
					return;
				}
				
				Packet packet = new Packet(Tag.SEND_MESSAGE, msg, sender, receiver);
				message.setText("");
				content.append("[You]: "+ msg +"\n");
				client.sendMessage(packet);
			}
		});
		btnSend.setBounds(457, 279, 89, 30);
		add(btnSend);
		
	}
	
	public JTextArea getContent() {
		return this.content;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public JTextArea getMessage() {
		return this.message;
	}

	public JButton getBtnSend() {
		return btnSend;
	}
	
	
	
}
