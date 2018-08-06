package com.controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController implements Runnable {
	private ServerSocket serverSocket = null;
	private Socket clientSocket;
	private boolean running = true;
	private int port = 6666;
	
	@SuppressWarnings("unused")
	private Thread movingThread = null;
	
	public ServerController(int port) {
		this.port = port;
	}
	
	public void run() {
		synchronized (this) {
			this.movingThread = Thread.currentThread();
		}
		
		openServerSocket();
		System.out.println("Server successfully started");
		while(running) {
			try {
				clientSocket = this.serverSocket.accept();
			}catch(IOException e) {
				if(!running) {
					System.out.println("Error: Server has stopped");
					return;
				}
				throw new RuntimeException("Client cannot connect", e);
			}
			new Thread(new ClientThreadController(clientSocket)).start();
		}
	}
	
	public synchronized void stop() {
		this.running = false;
		try {
			this.serverSocket.close();
		}catch(IOException e) {
			throw new RuntimeException("Error: Server cannot be closed",e);
		}
	}
	
	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.port);
		}catch(IOException e) {
			throw new RuntimeException("Unable to open port " + this.port, e);
		}
	}
}
