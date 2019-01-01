//ClientThread class implementing Runnable interface
//This class instances represents individual client connections
//Constructor takes Socket object for establishing connection
//And BugTracker object shared among all client threads
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
	private boolean userLoggedIn = false;
	private BugTracker bugTracker;
	private Employee employee;
	
	//Informational messages
	private String commandList = "Command list:\n1. Add bug\n2. Assign bug\n3. View not assigned bugs\n4. View all bugs\n5. Update bug record\nEnter \"exit\" to quit.";
	private String continueMessage = "\nPress enter to continue.";
	private String exitMessage = "\nEnter \"exit\" to quit";
	
	//Constructor
	public ClientThread(Socket request, BugTracker bugTracker) { 
		this.clientSocket = request;
		this.bugTracker = bugTracker;
	}
	
	//Runnable interface method run for thread
	//Instantiating output/input streams
	//Do/while loop for dealing with message passing between server and client
	public void run() {
		try{
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Client accepted from " + clientSocket.getInetAddress());
					
			//Main do while loop for server/client communication until stopped
			do {		
				
				//Call to method for authorization check
				if(!userLoggedIn)
					auth();
				
				//Main menu 
				if(!(message.equalsIgnoreCase("exit"))) {
					sendMessage(commandList);
					readMessage();
					System.out.println(message);
				}
				
				//If/else statement dealing with client requested functions
				//Depending on clients response, operations are delegated to BugTracker class object for further communicator
				//----
				//Add bug 
				if(message.equalsIgnoreCase("1")) {
					sendMessage(bugTracker.setBugRecord(this) + continueMessage);
				//Assign bug
				}else if(message.equalsIgnoreCase("2")) {
					
					sendMessage(bugTracker.getBugsList().getAllUnassignedBugRecords() + "\nEnter bug ID to assign:");
					String bugID = (String) in.readObject();

					sendMessage(bugTracker.getEmpList().getAllEmployees() + "\nEnter employee id to assign " + bugID+ " bug");
					String empID = (String) in.readObject();

					sendMessage(bugTracker.assignBugToEmployee(bugID, empID) + continueMessage);
				
				//View not assigned bugs
				}else if(message.equalsIgnoreCase("3")) {
					
					sendMessage(bugTracker.getBugsList().getAllUnassignedBugRecords() + continueMessage);
				
				//View all bugs
				}else if(message.equalsIgnoreCase("4")) {
					
					sendMessage(bugTracker.getBugsList().getAllBugRecords() + continueMessage);
				
				//Update bug record
				}else if(message.equalsIgnoreCase("5")) {
					
					sendMessage(bugTracker.getBugsList().getAllBugRecords() + "\nEnter bug ID to update record:" );
					String bugID = (String)in.readObject();

					sendMessage(bugTracker.updateBugRecord(this, bugID) + continueMessage);
				}else {
					sendMessage("Unknown command." + continueMessage);
				}

				if(!message.equalsIgnoreCase("exit")) message = (String) in.readObject();

			}while(!(message.equalsIgnoreCase("exit")));
	
		} catch (Exception e) {
			System.out.println("Client connection error.");
		}finally {
			closeConnection();
		}
	}
	
	//Method for client authorization or registration
	//If instance variable userLogged in is false
	//Client will be prompted for Login or Registration options
	private void auth() {
		try {
			do {			
				sendMessage("Command list:\n1.Login\n2.Register" + exitMessage);
				//message = (String) in.readObject();
				readMessage();

				if(message.equalsIgnoreCase("1")) {
					login();
				}else if(message.equalsIgnoreCase("2")) {
					register();
				}
			} while (!userLoggedIn && !(message.equalsIgnoreCase("exit")));
		} catch (Exception e) {
		}
	}
	//LOGIN
	private void login() throws Exception{
		Login login = new Login(bugTracker.getEmpList(), this);
		login.login();
		if(login.isLoggedIn()) {
			userLoggedIn = true;
			employee = login.getLoggedInEmployee();
		}
		sendMessage(login.getLoginMessage() + continueMessage);
		message = (String) in.readObject();
	}
	//REGISTER
	private void register() throws Exception{
		boolean userDetailsValid = false;
		do {
			bugTracker.registerNewEmployee(this);
			userDetailsValid = bugTracker.isEmployeeDataValid();
			if(userDetailsValid) {
				employee = bugTracker.getRegisteredEmployee();
				userLoggedIn = true;
			}
			sendMessage(bugTracker.getRegistrationStatus() + continueMessage);
			message = (String) in.readObject();
		}while(!userDetailsValid && !(message.equalsIgnoreCase("exit")));
	}

	//Close streams and client socket
	public void closeConnection() {
		System.out.println("Client ended connection from " + clientSocket.getInetAddress());
		try {
			if(employee != null)
				employee.setLoggedIn(false);
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
		}
	}

	//Send/Read message methods
	public void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			closeConnection();
		}
	}

	public String readMessage() {
		try {
			message = (String) in.readObject();
		} catch (Exception e) {
			closeConnection();
		}
		return message;
	}
}

