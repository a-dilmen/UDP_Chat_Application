package com.dilmen.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.codec.digest.DigestUtils;

import com.dilmen.controller.ClientController;
import com.dilmen.entity.Client;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;


// Frame that manages registry and login operations
// Each client is assigned with a DatagramSocket with login successful operation

public class ClientLogin extends JFrame {

	private static final long serialVersionUID = 453535832460724759L;

	private JPanel contentPane;
	private JTextField tFUsername;
	private JPasswordField tFPassword;
	private ClientController clientController;
	private JLabel lblLoginStatus;
	private DatagramSocket datagramSocket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientLogin frame = new ClientLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientLogin.class.getResource("/com/dilmen/resources/whatsapp.png")));
		setTitle("Login");
		clientController = new ClientController();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 227, 227);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tFUsername = new JTextField();
		tFUsername.setBounds(102, 20, 96, 19);
		contentPane.add(tFUsername);
		tFUsername.setColumns(10);

		tFPassword = new JPasswordField();
		tFPassword.setColumns(10);
		tFPassword.setBounds(102, 70, 96, 19);
		contentPane.add(tFPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setBounds(102, 128, 85, 21);
		contentPane.add(btnLogin);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		btnRegister.setBounds(102, 159, 85, 21);
		contentPane.add(btnRegister);

		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(25, 23, 79, 13);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(25, 73, 67, 13);
		contentPane.add(lblPassword);

		lblLoginStatus = new JLabel("");
		lblLoginStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoginStatus.setBounds(25, 105, 173, 13);
		contentPane.add(lblLoginStatus);
	}

	protected void register() {
		Client client = new Client(tFUsername.getText(),new String(tFPassword.getPassword()), false);
		clientController.create(client);
	}

	protected void login() {
		Client client = null;
		client = clientController.login(tFUsername.getText().toString(),
				DigestUtils.sha256Hex(new String(tFPassword.getPassword())));
		if (client != null) {
			
			int port = definePortForClient().getLocalPort();
			client.setOnline(true);
			client.setPort(port);
			System.err.println(port);
			clientController.update(client);
			ClientView view = new ClientView(client,datagramSocket);
			view.setVisible(true);
			dispose();

		} else {
			lblLoginStatus.setText("Login failed try again");
			tFUsername.setText("");
			tFPassword.setText("");
		}

	}
	
	protected DatagramSocket definePortForClient() {
		try {
			datagramSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return datagramSocket;
	}

}
