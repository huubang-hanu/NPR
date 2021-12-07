package com.hanu.chat.client;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.ScrollPaneConstants;

import com.hanu.chat.util.FileCustom;
import com.hanu.chat.util.Packet;
import com.hanu.chat.util.Tag;
import com.hanu.chat.util.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	private JScrollPane listFileContainer;
	private JList listFile;
	private List<FileCustom> files;
	
	/**
	 * Create the panel.
	 */
	public ChatTab(Client client, String receiver) {
		this.client = client;
		this.files = new ArrayList<FileCustom>();
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
					FileCustom file = new FileCustom(UUID.randomUUID().toString(), fileToSend[0].getName(), fileContent);
					addNewFile(file);
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
		btnSend.setBounds(650, 412, 89, 32);
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
		
		
		btnAttach.setIcon( createImageIcon("/icons8-attachment-25.png",
                "attachment icon"));
		btnAttach.setBounds(615, 412, 28, 32);
		add(btnAttach);
		
		listFileContainer = new JScrollPane();
		listFileContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listFileContainer.setBounds(611, 0, 144, 401);
		listFile = new JList();
		
		listFile.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            downloadFile(index);
		        } 
		    }
		});
		listFile.setBounds(611, 0, 144, 401);
		listFileContainer.setViewportView(listFile);
		add(listFileContainer);

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
	
	
	
	public void downloadFile(int fileIndex) {
		FileCustom file = files.get(fileIndex);
		
		JFrame parentFrame = new JFrame();
		 
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Dowload file");   
		fileChooser.setSelectedFile(new File(file.getName()));
		
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		
		
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    try {
				FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
				fileOutputStream.write(file.getData());
				fileOutputStream.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public void addNewFile(FileCustom file) {
		files.add(file);
		listFile.setListData(files.toArray());
	}
}
