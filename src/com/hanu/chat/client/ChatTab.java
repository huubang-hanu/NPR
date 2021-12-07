package com.hanu.chat.client;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.ScrollPaneConstants;

import com.hanu.chat.util.Packet;
import com.hanu.chat.util.Tag;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChatTab extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client client;
	private JTextArea content;
	private JTextArea message;
	private JButton btnSend;
	byte[] fileContent = null;
	private boolean isAttachFile = false;
	/**
	 * Create the panel.
	 */
	public ChatTab(Client client, String receiver) {
		this.client = client;

		File[] fileToSend = new File[1];
	
		setLayout(null);
		setBounds(10, 11, 765, 453);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 0, 594, 401);
		add(scrollPane);

		content = new JTextArea();
		content.setEditable(false);
		content.setLineWrap(true);
		content.setBounds(123, 204, 118, 22);
		scrollPane.setViewportView(content);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 414, 597, 30);
		add(scrollPane_1);

		message = new JTextArea();
		message.setLineWrap(true);
		message.setBounds(119, 260, 44, 22);
		scrollPane_1.setViewportView(message);

		btnSend = new JButton("Send");
		String sender = this.client.getUsername();
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isAttachFile && fileContent != null) {
					Packet filePacket = new Packet(Tag.SEND_FILE, fileToSend[0].getName(), sender, receiver);
					filePacket.setFileContent(fileContent);
					message.setText("");
					content.append("[You]: " + fileToSend[0].getName() + "\n");
					isAttachFile = false;
					client.sendMessage(filePacket);
				}
				
				String msg = message.getText();
				if (msg.trim().length() == 0) {
					return;
				}

				Packet packet = new Packet(Tag.SEND_MESSAGE, msg, sender, receiver);
				message.setText("");
				content.append("[You]: " + msg + "\n");
				client.sendMessage(packet);
			}
		});
		btnSend.setBounds(666, 412, 89, 32);
		add(btnSend);

		JLabel btnAttach = new JLabel("");
		btnAttach.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Choose file to send");
				if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fileToSend[0] = jFileChooser.getSelectedFile();
					fileContent = readContentIntoByteArray(fileToSend[0]);
					isAttachFile = true;
					message.setText(fileToSend[0].getName());
				}
			}
		});
		btnAttach.setIcon(new ImageIcon(
				"C:\\Users\\nhbang\\eclipse-workspace\\ChatApplication\\image\\icons8-attachment-25.png"));
		btnAttach.setBounds(629, 412, 28, 32);
		add(btnAttach);
		
		JScrollPane listFileContainer = new JScrollPane();
		listFileContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listFileContainer.setBounds(611, 0, 144, 401);
		add(listFileContainer);

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

	private static byte[] readContentIntoByteArray(File file) {
		Path path = Paths.get(file.getAbsolutePath());
		try {
			byte[] data = Files.readAllBytes(path);
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
