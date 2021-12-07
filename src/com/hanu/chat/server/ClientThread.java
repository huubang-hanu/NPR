package com.hanu.chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JList;

import com.hanu.chat.database.Repository;
import com.hanu.chat.util.Packet;
import com.hanu.chat.util.Tag;

/**
 * A gateway to categorize message and transfer it between clients
 *
 */
public class ClientThread extends Thread {
	private Socket socket;
	private Server chatServer;

	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	private Repository repo;
	private JList<String> listUserOnline;

	public ClientThread(Socket socket, Server server, JList<String> listUserOnline) {
		this.socket = socket;
		this.chatServer = server;
		this.repo = Repository.getRepository();
		this.listUserOnline = listUserOnline;
	}

	@Override
	public void run() {

		try {
			inStream = new ObjectInputStream(socket.getInputStream());
			outStream = new ObjectOutputStream(socket.getOutputStream());

			Packet packet = null;
			do {
				try {
					packet = (Packet) inStream.readObject();
					if (packet == null) {
						break;
					}

					switch (packet.getTag()) {
					case Tag.SIGNUP_REQUEST:
						String username = packet.getSender();
						String password = packet.getContent();
						if (repo.signUp(username, password)) {
							sendMessage(new Packet(Tag.SIGNUP_SUCCESS, "Sign up success. Let join with us", "server",
									packet.getSender()));
						} else {
							sendMessage(new Packet(Tag.INVALID_USER, "Username already exist. Try again!", "server",
									packet.getSender()));
						}
						break;
					case Tag.SIGNIN_REQUEST:
						if (!repo.signIn(packet.getSender(), packet.getContent())) {
							sendMessage(new Packet(Tag.INVALID_USER, "Wrong username or password", "server",
									packet.getSender()));
							break;
						}

						if (repo.isOnline(packet.getSender())) {
							sendMessage(new Packet(Tag.USER_ALREADY_SIGNIN, "This user already sign in", "server",
									packet.getSender()));
							break;
						}

						repo.addUser(packet.getSender(), this);

						// Set data for list online user in Server view
						this.listUserOnline.setListData(repo.getUsernames());
						sendMessage(new Packet(Tag.SIGNIN_SUCCESS, "Sign success",repo.getUsernames(), "server",
								packet.getSender()));
						
						// Broadcast message to others people in application
						chatServer.broadcast(
								new Packet(Tag.NEW_USER_CONNECT, packet.getSender(), "Server", "All online user"), 
								this);
						break;

					case Tag.SEND_MESSAGE: // User send message
						if (packet.getReceiver().equals("Global")) { // Send to global tab
							chatServer.broadcast(new Packet(Tag.GLOBAL_MESSAGE, packet.getContent(), packet.getSender(),
									packet.getReceiver()), this);
						} else { // Send message to a specific user
							chatServer.broadcastToClient(new Packet(Tag.SEND_MESSAGE, packet.getContent(),
									packet.getSender(), packet.getReceiver()));
						}
						break;
					case Tag.SEND_FILE: // User send message
						if(packet.getReceiver().equals("Global")) {
							chatServer.broadcast(packet, this);
						} else {
							chatServer.broadcastToClient(packet);
						}
						
						break;
					case Tag.EXIT: // User log out application
						chatServer.broadcast(new Packet(Tag.USER_INACTIVE, packet.getContent(), packet.getSender(),
								packet.getReceiver()), this);
						chatServer.removeClient(packet.getSender());
						
						this.listUserOnline.setListData(repo.getUsernames());
						break;
					default:
						break;
					}

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} while (!packet.getTag().equalsIgnoreCase(Tag.EXIT));
		} catch (IOException e) {
			System.out.println("ClientThread's error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Write message to this stream
	 * 
	 * @param message
	 */
	public void sendMessage(Packet packet) {
		try {
			outStream.writeObject(packet);
			outStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
