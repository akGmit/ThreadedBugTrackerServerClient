package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.ClientThread;

public class BugTrackerServer {

	private ServerSocket ss;
	private static final int SERVER_PORT = 10000;  
	private volatile boolean keepRunning = true;
	private BugTracker bugTracker;

	//Main method to start java application
	public static void main(String[] args) {
		new BugTrackerServer(); 
	}

	private BugTrackerServer(){
		try {
			bugTracker = new BugTracker();
			ss = new ServerSocket(SERVER_PORT); //Start the server socket listening on port 10000
			System.out.println("Server started and listening on port " + SERVER_PORT);
			
			while (keepRunning){ 
				try { 
					Socket s = ss.accept(); 
					new Thread(new ClientThread(s, bugTracker)).start(); 
				} catch (IOException e) { 
					System.out.println("Error handling incoming request..." + e.getMessage());
				}
			}
		} catch (IOException e) { 
			System.out.println("Yikes! Something bad happened..." + e.getMessage());
		}
	}
}

