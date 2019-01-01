package server;

import java.util.List;

public class Login {
	private boolean loggedIn;
	private String email;
	private String password;
	private String loginMessage;
	private EmployeeData empData;
	private ClientThread client;
	private Employee loggedInEmployee;
	
	public Login(EmployeeData empData, ClientThread client) {
		this.client = client;
		this.empData = empData;
	}
	
	public void login() {
		setLoginDetails(client);
		authUser(empData.getEmployeList());
	}

	private void setLoginDetails(ClientThread client) {
		client.sendMessage("Enter email: ");
		email = client.readMessage();
		
		client.sendMessage("Enter password:");
		password = client.readMessage();
	}
	
	private void authUser(List<Employee> empData) {
		for(Employee e : empData) {
			if(e.getPassword().equals(getPassword()) && e.getEmail().equalsIgnoreCase(getEmail())) {
				if(!(checkIsLoggedIn(e))){
					setLoggedInEmployee(e);
					setLoggedIn(true);
					setLoginMessage("Login successful.");
					return;
				}else {
					setLoginMessage("This user is already logged in.");
					return;
				}
			}
		}
		setLoginMessage("Login details incorrect.");
		setLoggedIn(false);
	}
	
	public void logOut(Employee e) {
		e.setLoggedIn(false);
	}
	
	private boolean checkIsLoggedIn(Employee e) {
		return e.isLoggedIn();
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

	public Employee getLoggedInEmployee() {
		return loggedInEmployee;
	}

	public void setLoggedInEmployee(Employee loggedInEmployee) {	
		this.loggedInEmployee = loggedInEmployee;
		this.loggedInEmployee.setLoggedIn(true);
	}
	
	
}
