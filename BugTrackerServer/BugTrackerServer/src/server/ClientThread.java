package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable{
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message;
	boolean userLoggedIn = false;
	private BugTracker bugTracker;
	//Informational messages
	private String commandList = "Command list:\n1. Add bug\n2. Assign bug\n3. View not assigned bugs\n4. View all bugs\n5. Update bug record";
	private String exitMessage = "\n\nEnter \"exit\" to quit, or \"C\" to continue";
	private String tryAgainMessage = "\n\nEnter \"exit\" or \"c\" to try again.";
	private String continueMessage = "\nPress enter to continue.";

	public ClientThread(Socket request, BugTracker bugTracker) { 
		this.clientSocket = request;
		this.bugTracker = bugTracker;
	}

	public void run() {

		try{
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Client accepted from " + clientSocket.getInetAddress());

			do {		
				//Login or register do while loop
				auth();
				
				
				if(!(message.equalsIgnoreCase("exit"))) {
					sendMessage(commandList);
					message = (String) in.readObject();
					System.out.println(message);
				}

				if(message.equalsIgnoreCase("1")) {
					sendMessage(bugTracker.setBugRecord(this) + continueMessage);
					message = "ok";
				}else if(message.equalsIgnoreCase("2")) {
					sendMessage(bugTracker.getBugsList().getAllUnassignedBugRecords() + "\nEnter bug ID to assign:");
					String bugID = (String) in.readObject();

					sendMessage(bugTracker.getEmpList().getAllEmployees() + "\nEnter employee id to assign " + bugID+ " bug");
					String empID = (String) in.readObject();

					sendMessage(bugTracker.assignBugToEmployee(bugID, empID) + continueMessage);
					

				}else if(message.equalsIgnoreCase("3")) {
					sendMessage(bugTracker.getBugsList().getAllUnassignedBugRecords() + exitMessage);
				}else if(message.equalsIgnoreCase("4")) {
					sendMessage(bugTracker.getBugsList().getAllBugRecords() + exitMessage);
				}else if(message.equalsIgnoreCase("5")) {
					sendMessage(bugTracker.getBugsList().getAllBugRecords() + "/nEnter bug ID to update record:" );
					String bugID = (String)in.readObject();

					sendMessage(bugTracker.updateBugRecord(this, bugID) + continueMessage);
				}else if(!(message.equalsIgnoreCase("ok"))){
					sendMessage("Unknown command." + exitMessage);
				}

				if(!message.equalsIgnoreCase("exit")) message = (String) in.readObject();

			}while(!(message.equalsIgnoreCase("exit")));

			//System.out.println("Client ended connection from " + clientSocket.getInetAddress());

		} catch (Exception e) {
			System.out.println("EXception here");
			System.out.println(e);
		}finally {
			closeConnection();
		}
	}

	private void auth() throws Exception{
		if(!userLoggedIn) {
			do {			
				sendMessage("Welcome to Bug Tracker application.\nEnter command number to continue:\n1.Login\n2.Register");
				message = (String) in.readObject();

				if(message.equalsIgnoreCase("1")) {

					Login login = new Login(bugTracker.getEmpList(), this);
					userLoggedIn = login.isLoggedIn();
					sendMessage(login.getLoginMessage() + continueMessage);
					message = (String) in.readObject();

				}else if(message.equalsIgnoreCase("2")) {
					boolean userDetailsValid = false;
					do {
						bugTracker.registerNewEmployee(this);
						userDetailsValid = bugTracker.isEmployeeDataValid();
						userLoggedIn = true;
						sendMessage(bugTracker.getRegistrationStatus() + continueMessage);
						message = (String) in.readObject();
					}while(!userDetailsValid && !(message.equalsIgnoreCase("exit")));
				}
			} while (!userLoggedIn && !(message.equalsIgnoreCase("exit")));
		}
	}

	//Close streams and client socket
	public void closeConnection() {
		System.out.println("Client ended connection from " + clientSocket.getInetAddress());
		try {
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Send/Read message methods
	public void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			System.out.println("Closed at sendmessage");
			closeConnection();
		}
	}

	public String readMessage() {
		try {
			message = (String) in.readObject();
		} catch (Exception e) {
			System.out.println("Closed at readMEssage");
			closeConnection();
		}
		return message;
	}
}

