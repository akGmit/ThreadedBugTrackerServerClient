//Main server class containing main method
//Instantiates BugTracker class object and shares it among all client threads
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

	//Main method to start server
	public static void main(String[] args) {
		new BugTrackerServer(); 
	}

	private BugTrackerServer(){
		try {
			bugTracker = new BugTracker();
			ss = new ServerSocket(SERVER_PORT); 
			System.out.println("Server started and listening on port " + SERVER_PORT);
			
			while (keepRunning){ 
				try { 
					Socket s = ss.accept(); 
					new Thread(new ClientThread(s, bugTracker)).start(); 			//Here we create new thread for each client and pass BugTracker object as parameter
				} catch (IOException e) { 
					System.out.println("Incoming connection error: " + e.getMessage());
				}
			}
		} catch (IOException e) { 
			System.out.println("Server error: " + e.getMessage());
		}
	}
}

