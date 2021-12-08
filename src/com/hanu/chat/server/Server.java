package com.hanu.chat.server;

import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import com.hanu.chat.database.Repository;
import com.hanu.chat.util.Packet;
import com.hanu.chat.util.Tag;

public class Server {

	private int port;
	private JLabel lbSeverStatus;
	private JTextArea serverLog;
	private JList<String> listUserOnline;
	private Repository repo;
	private static final String KEY_STORE_PATH = "SSLStore";
	private static final String KEY_STORE_PW = "bt123456";
	
	static {
		System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PW);
	}
	
	public Server(int port, JLabel lbServerStatus, JTextArea serverLog, JList<String> listUserOnline) {
		this.port = port;
		this.lbSeverStatus = lbServerStatus;
		this.serverLog = serverLog;
		this.repo = Repository.getRepository();
		this.listUserOnline = listUserOnline;
	}

	/**
	 * Initialize SocketServer to listning connect from clients
	 */
	public void init() {
		

		try {
			// SSLServerSocketFactory for building SSLServerSockets
			SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			// create SSLServerSocket on specified port
			SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(this.port);

//			ServerSocket serverSocket = new ServerSocket(this.port);
			this.lbSeverStatus.setText("Server is listening on 127.0.0.1 port " + this.port);

			// Wait connect from client ...
			while (true) {
				Socket socket = serverSocket.accept();

				// Create new ClientThread to communicate with this user
				ClientThread newClient = new ClientThread(socket, this, listUserOnline);
				newClient.start();

			}
		} catch (IOException e) {
			System.out.println("Server's error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Inform information to an other user in system.
	 * 
	 * @param message
	 * @param excludeClient
	 */
	public void broadcast(Packet packet, ClientThread excludeClient) {

		// Capture time of message
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		String currentTime = dateFormat.format(new Date());

		// Log to server
		if (packet.getTag().equals(Tag.USER_INACTIVE)) {
			this.serverLog.append(packet.getSender() + " LOGGED OUT at " + currentTime + "\n");
		} else if (packet.getTag().equals(Tag.NEW_USER_CONNECT)) {
			this.serverLog.append(packet.getContent() + " SIGN IN at " + currentTime + "\n");
		}

		// Send message to other users in system, exclude sender
		this.repo.getClientThreads().forEach(client -> {
			if (client != excludeClient) {
				client.sendMessage(packet);
			}
		});
	}

	/**
	 * Broadcast message to a specific user
	 * 
	 * @param message
	 * @param receiver
	 */
	public void broadcastToClient(Packet packet) {

		// Find ClientThread using receiver's name
		ClientThread toClient = repo.getByUsername(packet.getReceiver());
		if (toClient != null) {
			// Send message to receiver
			toClient.sendMessage(packet);
		}
	}

	/**
	 * <pre>
	 *  if a client exited chat application
	 *  	remove it's username and ClientThread
	 * </pre>
	 * 
	 * @param username
	 * @param client
	 */
	public void removeClient(String username) {
		this.repo.removeUser(username);
	}

}
