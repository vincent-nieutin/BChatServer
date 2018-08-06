package com.model;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class ClientThreadModel {
	
	protected BufferedReader inputStream = null;
	protected PrintWriter outputStream = null;
	protected String username = "";
	protected Socket clientSocket = null;
	
	protected static List<ClientThreadModel> clientThreads = new ArrayList<ClientThreadModel>();
	
	public ClientThreadModel() {
		System.out.println("Client added");
		clientThreads.add(this);
	}
	
	public List<ClientThreadModel> getClients() {
		return clientThreads;
	}
	
	protected synchronized void sendMessage(String message) {
		List<ClientThreadModel> clients = this.getClients();
		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).getOutputStream().println(username + "> " + message);
		}
	}
	
	protected PrintWriter getOutputStream() {
		return outputStream;
	} 
}
