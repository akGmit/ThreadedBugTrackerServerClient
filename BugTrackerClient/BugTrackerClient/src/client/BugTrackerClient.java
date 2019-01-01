//Client application
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class BugTrackerClient {
	private static final int SERVER_PORT = 10000;
	private Scanner input;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket requestSocket;
	private String message;
	
	public void run() { 
		input = new Scanner(System.in);	
		input.useDelimiter("\n");
		try { 
			System.out.println("Enter IP address of a server: ");
			String serverIP = input.next();
			requestSocket = new Socket(serverIP, SERVER_PORT);
			//requestSocket = new Socket("localhost", SERVER_PORT); 
			
			//Output/input streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			System.out.println("Connected to BugTracker application server.");
			//Main do while loop for sending end recieving messsages from server
			do {
				message = (String) in.readObject();
				System.out.println(message);
				System.out.print("cmd> ");
				message = input.next();
				sendMessage(message);				
			} while (!(message.equalsIgnoreCase("exit")));

		} catch (Exception e) { 
			e.printStackTrace();
		}finally {
			try {
				out.close();
				in.close();
				requestSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//Send message to server method
	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new BugTrackerClient().run();
		System.out.println("BugTracker client closed.");
	}
}




