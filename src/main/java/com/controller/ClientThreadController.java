package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import com.model.ClientThreadModel;

public class ClientThreadController extends ClientThreadModel implements Runnable {

	private static String HANDSHAKE = "FirstRequest:/";

	private boolean running = true;

	public ClientThreadController(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
			inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outputStream = new PrintWriter(clientSocket.getOutputStream(), true);

			String line;
			String firstRequest;

			// Wait for client handshake
			do {
			} while (!(firstRequest = inputStream.readLine()).contains(HANDSHAKE));
			// Extract client name from handshake
			username = firstRequest.substring(firstRequest.indexOf(HANDSHAKE) + HANDSHAKE.length(),
					firstRequest.length());
			System.out.println("Client " + username + " connected");
			while (running) {
				if ((line = inputStream.readLine()) != null) {
					switch (line) {
					case "/disconnect":
						System.out.println(username + " disconnected");
						disconnect();
						running = false;
						break;
					default:
						System.out.println(username + "> " + line);
						sendMessage(line);
						break;
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("Connexion with " + username + " was abruptly closed");
			disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			inputStream.close();
			outputStream.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
