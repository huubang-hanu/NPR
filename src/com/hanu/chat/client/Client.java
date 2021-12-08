package com.hanu.chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.hanu.chat.util.FileCustom;
import com.hanu.chat.util.Packet;
import com.hanu.chat.util.Tag;

public class Client {
	private String username;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	private JTabbedPane chatTabPane;;
	private Socket socket;
	private JFrame welcomeView;
	private static final String TRUST_STORE_PATH = "truststore";
	private static final String TRUST_STORE_PW = "bt123456";

	static {
		System.setProperty("javax.net.ssl.trustStore", TRUST_STORE_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PW);
	}

	public Client(String hostName, int port, JFrame welcomeView) {
		this.welcomeView = welcomeView;

		try {
			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			// create SSLSocket from factory
			socket = (SSLSocket) socketFactory.createSocket(hostName, port);
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
			receive();

		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}
	}

	public void login(String username, String password) {
		try {
			outStream.writeObject(new Packet(Tag.SIGNIN_REQUEST, password, username, Tag.SERVER));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void signUp(String username, String password) {
		try {
			outStream.writeObject(new Packet(Tag.SIGNUP_REQUEST, password, username, Tag.SERVER));
		} catch (IOException e) {
			e.printStackTrace();
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
						String newUsername = packet.getContent();
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
					case Tag.SEND_FILE:
						FileCustom file = new FileCustom(UUID.randomUUID().toString(), packet.getContent(),
								packet.getFileContent());
						setFileListUI(file, packet.getSender());

						break;
					case Tag.SIGNIN_SUCCESS:
						ClientView view = new ClientView(this, packet.getOnlineUsers(), packet.getReceiver());
						setChatTabPane(view.getChatTabPane());
						this.welcomeView.setVisible(false);
						view.setVisible(true);
						break;
					case Tag.USER_ALREADY_SIGNIN:
						System.out.println("This user already sign in");
						JOptionPane.showConfirmDialog(welcomeView, packet.getContent(), "Server message",
								JOptionPane.DEFAULT_OPTION);
						break;
					case Tag.SIGNUP_SUCCESS:
						JOptionPane.showConfirmDialog(welcomeView, packet.getContent(), "Server message",
								JOptionPane.DEFAULT_OPTION);
						break;
					case Tag.INVALID_USER:
						JOptionPane.showConfirmDialog(welcomeView, packet.getContent(), "Server message",
								JOptionPane.DEFAULT_OPTION);
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

	private void setFileListUI(FileCustom file, String sender) {
		int index = getTabPostition(sender);
		ChatTab chatTab = (ChatTab) chatTabPane.getComponent(index);
		chatTab.getContent().append("[" + sender + "]: " + file.getName() + "\n");

		chatTab.addNewFile(file);

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

	public JTabbedPane getChatTabPane() {
		return chatTabPane;
	}

	public void setChatTabPane(JTabbedPane chatTabPane) {
		this.chatTabPane = chatTabPane;
	}

}
