package com.dilmen.controller;

import java.util.ArrayList;
import java.util.List;

import com.dilmen.dao.ClientDao;
import com.dilmen.entity.Client;

public class ClientController {
	
	ClientDao clientDao = new ClientDao();
	
	public void create(Client client) {
		clientDao.create(client);
	}
	
	public Client login(String username,String password) {
		return clientDao.login(username, password);
	}

	public void update(Client client) {
		clientDao.update(client);
		
	}

	public List<String> findOnline(Client client) {
		List<Client> clients = clientDao.findOnline();
		List<String> clientNames = new ArrayList();
		// filtering all online clients except the owner of the query
		clients = clients.stream().filter(cli-> !cli.getUsername().equals(client.getUsername())).toList();
		for(Client client0: clients) {
			clientNames.add(client0.getUsername());
		}
		return clientNames;
	}

	public Client find(String username) {
		return clientDao.find(username);
	}
	
	
}
