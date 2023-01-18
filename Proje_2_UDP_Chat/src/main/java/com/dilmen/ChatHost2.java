package com.dilmen;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatHost2 {

	private JFrame frmChathost2;
	private JEditorPane editorPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatHost2 window = new ChatHost2();
					window.frmChathost2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatHost2() {
		initialize();

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("let the thread begin");
				runServer();

			}
		});
		thread1.start();

	}

	protected void runServer() {
		try (DatagramSocket datagramSocket = new DatagramSocket(5001);) {
			while (true) {
				byte[] buffer = new byte[144];
				DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
				datagramSocket.receive(datagramPacket);
				String receivedText = new String(buffer, 0, datagramPacket.getLength());
				LocalDateTime now = LocalDateTime.now();
				int hours = now.getHour();
				int minutes = now.getMinute();
				int seconds = now.getSecond();
				String timeRecieved = hours + ":" + minutes + ":" + seconds;
				editorPane.setText(editorPane.getText() + "\t\n" + timeRecieved + " " + receivedText);

				// echo part echo message to send
				String returnString = "echo : " + receivedText;
				byte[] buffer2 = returnString.getBytes();
				InetAddress inetAddress = datagramPacket.getAddress();
				int port = datagramPacket.getPort();
				datagramPacket = new DatagramPacket(buffer2, buffer2.length, inetAddress, port);
				datagramSocket.send(datagramPacket);
				System.out.println("packet port: " + port + "address : " + inetAddress);
			}

		} catch (SocketException e) {
			System.out.println("socket exception erver");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("io exception server");
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChathost2 = new JFrame();
		frmChathost2.getContentPane().setBackground(new Color(220, 248, 198));
		frmChathost2.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ChatHost2.class.getResource("/com/dilmen/resources/whatsapp.png")));
		frmChathost2.setTitle("Ahmet's Whatsapp");
		frmChathost2.setBounds(100, 100, 509, 428);
		frmChathost2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChathost2.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 95, 409, 275);
		frmChathost2.getContentPane().add(scrollPane);

		editorPane = new JEditorPane();
		editorPane.setBackground(new Color(236, 229, 221));
		scrollPane.setViewportView(editorPane);

		JLabel lblNewLabel = new JLabel("Message");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(44, 10, 66, 13);
		frmChathost2.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					submitMessage();
				}
			}
		});
		textField.setForeground(new Color(0, 0, 128));
		textField.setBounds(38, 33, 314, 19);
		frmChathost2.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBackground(new Color(18, 140, 126));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitMessage();
			}
		});
		btnNewButton.setBounds(362, 32, 85, 21);
		frmChathost2.getContentPane().add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Chat History");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1.setBounds(38, 72, 72, 13);
		frmChathost2.getContentPane().add(lblNewLabel_1);
	}
	
	private void submitMessage() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			DatagramSocket datagramSocket = new DatagramSocket();
			String echoString;
			echoString = textField.getText();
			byte[] buffer = echoString.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 5000);
			datagramSocket.send(packet);
			byte[] buffer2 = new byte[144];
			datagramSocket.receive(packet);
			String rText = new String(buffer2, 0, packet.getLength());
			editorPane.setText(editorPane.getText() + "\n" + echoString + rText);
			textField.setText("");

		} catch (UnknownHostException ex) {
			System.out.println("client host exception");
			ex.printStackTrace();
		} catch (SocketException ex) {
			System.out.println("Socket client");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("io excep client");
			ex.printStackTrace();
		}
	}
}
