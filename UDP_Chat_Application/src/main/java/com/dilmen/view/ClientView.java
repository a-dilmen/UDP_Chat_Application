package com.dilmen.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;

//Network protocol imports
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

// entity manger imports
import com.dilmen.controller.ClientController;
import com.dilmen.entity.Client;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientView extends JFrame {

	private static final long serialVersionUID = 3328206897338508802L;
	private JPanel contentPane;
	private JTextField tFMessage;
	private JEditorPane editorPane;
	private JLabel lblOnlineCheck;
	private DatagramSocket datagramSocket;
	private String split = "&&!!&!";
	private ClientController clientController;
	private Client clientOwner;
	private Client clientReceiver;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private final int SERVER_PORT = 5000;
	private byte[] buffer = new byte[6550];

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public ClientView(Client client, DatagramSocket datagramSocket) {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ClientView.class.getResource("/com/dilmen/resources/whatsapp.png")));
		setTitle(client.getUsername());

		this.datagramSocket = datagramSocket;
		clientController = new ClientController();
		clientOwner = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 433);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 563, 290);
		contentPane.add(scrollPane);

		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);

		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.setOnline(false);
				clientController.update(client);
				dispose();
			}
		});
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(488, 372, 85, 21);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Send");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		btnNewButton_1.setBackground(new Color(127, 255, 0));
		btnNewButton_1.setBounds(488, 34, 85, 21);
		contentPane.add(btnNewButton_1);

		comboBox = new JComboBox();
		comboBox.setBounds(10, 34, 85, 21);
		contentPane.add(comboBox);

		tFMessage = new JTextField();
		tFMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});
		tFMessage.setBounds(121, 35, 357, 19);
		contentPane.add(tFMessage);
		tFMessage.setColumns(10);

		JButton btnFindOnline = new JButton("Find Online");
		btnFindOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> clientNames = clientController.findOnline(client);
				String[] array = clientNames.toArray(new String[clientNames.size()]);
				updateOnline(array);

			}
		});
		btnFindOnline.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnFindOnline.setBounds(10, 3, 85, 21);
		contentPane.add(btnFindOnline);

		lblOnlineCheck = new JLabel("");
		lblOnlineCheck.setBounds(121, 12, 239, 13);
		contentPane.add(lblOnlineCheck);

		Thread listen = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					recieveMessage();
				}

			}
		});
		listen.start();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void updateOnline(String[] array) {
		contentPane.remove(comboBox);
		// Populating comboBox with online clients each time triggered
		comboBox = new JComboBox(array);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = comboBox.getSelectedItem().toString();
				clientReceiver = clientController.find(username);
				lblOnlineCheck.setText("");
			}
		});
		comboBox.setBounds(10, 34, 85, 21);
		contentPane.add(comboBox);
	}

	void sendMessage() {
		try {
			// Server message format consists of port assigned to client that should receive the message and the   
			String temp = clientReceiver.getPort() + split + clientOwner.getUsername() + split + tFMessage.getText().toString();
			buffer = temp.getBytes();
			
			DatagramPacket message = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), SERVER_PORT);
			editorPane.setText(editorPane.getText() + "\n" + tFMessage.getText());
			datagramSocket.send(message);
			
			tFMessage.setText("");
			buffer = new byte[6550]; // Without this line packages lose data screen shows less character

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			lblOnlineCheck.setText("Please select someone online to send your message");
		}
	}

	void recieveMessage() {
		DatagramPacket receive = new DatagramPacket(buffer, buffer.length);
		try {
			datagramSocket.receive(receive);
			String message = new String(receive.getData());
			editorPane.setText(editorPane.getText() + "\n" + message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
