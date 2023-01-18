package com.dilmen.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dilmen.entity.Client;

public class Server {
	private static final int PORT = 5000;
	private static DatagramSocket datagramSocket = null;
	static byte[] receive = new byte[6550];
	static byte[] rturn = new byte[6550];
	static String split = "&&!!&!";

	public static void main(String[] args) {
		try {
			datagramSocket = new DatagramSocket(PORT);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		System.out.println("Server started to listen at port 5000");
		while (true) {
			try {

				DatagramPacket datagramPacketRecieve = new DatagramPacket(receive, receive.length);

				serverOperations(datagramPacketRecieve);

			} catch (SocketTimeoutException e) {
				System.out.println("SocketTimeoutException ");
			} catch (PortUnreachableException e) {
				System.out.println("PortUnreachableException ");
			} catch (SocketException e) {
				System.out.println("socket creation error");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IOException");
			}
		}
	}

	private static void serverOperations(DatagramPacket datagramPacketRecieve) throws IOException {

		// receiving incoming packages
		datagramSocket.receive(datagramPacketRecieve);
		// storing package details into buffer byte array named receive
		receive = datagramPacketRecieve.getData();
		String rec = new String(receive, 0, receive.length);

		// manipulating data to forward message
		String[] arr = rec.split(split);
		int receiverPort = Integer.parseInt(arr[0]);
		Format f = new SimpleDateFormat("HH:mm:ss");
		String time = f.format(new Date());
		String message = "\t\t" + arr[1] + " : " + time + ": " + arr[2];

		// storing return message to buffer byte array and creating forward package
		rturn = message.getBytes();
		DatagramPacket datagramPacketSend = new DatagramPacket(rturn, 0, rturn.length, InetAddress.getLocalHost(),
				receiverPort);
		// message send
		datagramSocket.send(datagramPacketSend);

		// clear buffers
		receive = new byte[655];
		rturn = new byte[655];
	}

}
