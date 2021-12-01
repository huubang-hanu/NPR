package com.hanu.chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JTabbedPane;
import com.hanu.chat.util.Packet;
import com.hanu.chat.util.Tag;

public class Client {
	private String hostName;
	private int port;
	private String username;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
//	private PrintWriter printWriter;
//	private BufferedReader bufferedReader;
	private JTabbedPane chatTabPane;;
	private Socket socket;

	public Client(String hostName, int port, JTabbedPane chatTabPane) {
		this.hostName = hostName;
		this.port = port;
		this.chatTabPane = chatTabPane;
	}

	public void execute() {
		try {
			socket = new Socket(hostName, port);
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
//			printWriter = new PrintWriter(socket.getOutputStream(), true);
//			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			printWriter.println(Tag.NEW_USER_CONNECT + ":" + username);
			outStream.writeObject(new Packet(Tag.NEW_USER_CONNECT, "New user connect!", username, Tag.SERVER));
			receive();

		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}
	}

	/**
	 * Read message if Server broadcast set content to global tab if username get
	 * tabChat -> base on user name
	 */
	private void receive() {
		Runnable runnable = () -> {
			while (true) {
				try {
					Packet packet = (Packet) inStream.readObject();
					
					if (packet == null) {
						break;
					}


					switch (packet.getTag()) {
						case Tag.NEW_USER_CONNECT:
							String newUsername = packet.getSender();
							ChatTab tab = new ChatTab(this, newUsername);
							chatTabPane.add(newUsername, tab);
							break;
						case Tag.SEND_MESSAGE:
							
							String msg = "[" + packet.getSender() + "]: " + packet.getContent();
							setContent(packet.getSender(), msg);
							break;
						case Tag.GLOBAL_MESSAGE:
							msg = "[" + packet.getSender() + "]: " + packet.getContent();
							setContent("Global", msg);
							break;
						case Tag.USER_INACTIVE:
							String tabName = packet.getSender();
							inActiveTab(tabName);
							break;
						default:
							break;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread th = new Thread(runnable);
		th.start();
	}
	
	/**
	 * Set content by tab's title
	 * 
	 * @param sender
	 * @param message
	 */
	private void setContent(String sender, String message) {
		int index = getTabPostition(sender);
		ChatTab c = (ChatTab) chatTabPane.getComponent(index);
		c.getContent().append(message + "\n");
	}

	private void inActiveTab(String tabTitle) {
		int index = getTabPostition(tabTitle);
		ChatTab c = (ChatTab) chatTabPane.getComponent(index);
		c.getContent().append(tabTitle + " EXITED \n ***************************** \n");
		c.getBtnSend().setEnabled(false);
	}

	/**
	 * Get position by tab's title
	 * 
	 * @param username
	 * @return
	 */
	private int getTabPostition(String tabTitle) {
		int tabCount = chatTabPane.getTabCount();
		for (int i = 0; i < tabCount; i++) {
			String currentTitle = chatTabPane.getTitleAt(i);
			if (currentTitle.equals(tabTitle))
				return i;
		}
		return -1;
	}

	/**
	 * Send message from client
	 * 
	 * @param msg
	 */
	public void sendMessage(Packet packet) {
		try {
			outStream.writeObject(packet);
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send exit message to request server close socket of this client
	 */
	public void exit() {
		try {
			outStream.writeObject(new Packet(Tag.EXIT, "Exit", this.username, Tag.SERVER));
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}
}
