package com.run;
import com.controller.ServerController;

public class Server {
	
	private static int DEFAULT_PORT = 6666;
	private static int port;
	
	public static void main(String[] args) {
		port = Integer.parseInt(args[0]);
		if(Integer.valueOf(port)==null)
			port = DEFAULT_PORT;
		ServerController server = new ServerController(port);
		server.run();
	}
}