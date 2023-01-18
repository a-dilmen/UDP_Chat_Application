package com.dilmen;
import com.dilmen.server.Server;
import com.dilmen.view.ClientLogin;

public class Test {

	public static void main(String[] args) throws InterruptedException {

		Thread server = new Thread(new Runnable() {

			@Override
			public void run() {
				Server.main(args);

			}
		});

		Thread client1 = new Thread(new Runnable() {

			@Override
			public void run() {
				ClientLogin.main(args);
			}
		});

		Thread client2 = new Thread(new Runnable() {

			@Override
			public void run() {
				ClientLogin.main(args);
			}
		});

		Thread client3 = new Thread(new Runnable() {

			@Override
			public void run() {
				ClientLogin.main(args);
			}
		});

		server.start();
		Thread.sleep(1000);
		client1.start();
		Thread.sleep(1000);
		client2.start();
		Thread.sleep(1000);
		client3.start();
		Thread.sleep(1000);
		
		server.join();
		client1.join();
		client2.join();
		client3.join();
		
		
	}

}
