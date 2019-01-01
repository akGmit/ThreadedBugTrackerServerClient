package server;

import java.util.List;

public class Login {
	private boolean loggedIn;
	private String email;
	private String password;
	private String loginMessage;
	
	public Login(EmployeeData empList, ClientThread client) {
		setLoginDetails(client);
		authUser(empList.getEmployeList());
	}

	private void setLoginDetails(ClientThread client) {
		client.sendMessage("Enter email: ");
		email = client.readMessage();
		
		client.sendMessage("Enter password:");
		password = client.readMessage();
	}
	
	private void authUser(List<Employee> empList) {
		for(Employee e : empList) {
			if(e.getPassword().equals(getPassword()) && e.getEmail().equalsIgnoreCase(getEmail())) {
				setLoggedIn(true);
				setLoginMessage("Login successful.");
				return;
			}
		}
		setLoginMessage("Login details incorrect.");
		setLoggedIn(false);
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}
	
	
}
