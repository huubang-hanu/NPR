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
					case Tag.NEW_USER_CONNECT: // New user connect to server
						if (!repo.isExist(packet.getSender())) {
							repo.addUser(packet.getSender(), this);

							// Set data for list online user in Server view
							this.listUserOnline.setListData(repo.getUsernames());

							// Broadcast message to others people in application
							chatServer.broadcast(packet, this);

						} else {
							System.out.println(packet.getSender() + " already exist!");
						}
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
			}while (!packet.getTag().equalsIgnoreCase(Tag.EXIT));

			socket.close();
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
//		writer.println(message);
		try {
			outStream.writeObject(packet);
			outStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
