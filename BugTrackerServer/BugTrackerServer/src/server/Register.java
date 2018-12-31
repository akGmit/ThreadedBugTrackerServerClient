package server;

public class Register {
	private EmployeeData empData;
	private String regStatus;
	private boolean employeeDataValid;

	public Register(EmployeeData empData) {
		this.empData = empData;
	}

	public void registerEmployee(ClientThread client) {
		
		Employee newEmployee = setNewEmployeeData(client);
		
		isEmployeeValid(newEmployee);
		
		if(isEmployeeDataValid())
			addNewEmployee(newEmployee);
	}
	
	private synchronized void addNewEmployee(Employee emp) {
		empData.addNewEmployee(emp);
	}

	private Employee setNewEmployeeData(ClientThread client) {
		Employee newEmployee = new Employee();

		try {
			client.sendMessage("Enter full name of employee:");
			newEmployee.setName(client.readMessage());

			client.sendMessage("Enter employee ID:");
			newEmployee.setEmpID(client.readMessage());

			client.sendMessage("Enter email:");
			newEmployee.setEmail(client.readMessage());

			client.sendMessage("Enter password:");
			newEmployee.setPassword(client.readMessage());

			client.sendMessage("Enter department:");
			newEmployee.setDepartment(client.readMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newEmployee;
	}

	//Check if required details are unique
	private synchronized void isEmployeeValid(Employee emp) {
		for(Employee e : this.empData.getEmployeList()) {
			if(e.getEmail().equalsIgnoreCase(emp.getEmail())) {
				this.regStatus = "Error. Email already exists.";
				setEmployeeDataValid(false);
				return;
			}else if(e.getEmpID().equalsIgnoreCase(emp.getEmpID())) {
				setRegStatus("Error. Employee ID already exists.");
				setEmployeeDataValid(false);
				return;
			}
		}
		setRegStatus("Registration succesfull.");
		setEmployeeDataValid(true);
	}
	
	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public boolean isEmployeeDataValid() {
		return employeeDataValid;
	}

	public void setEmployeeDataValid(boolean employeeDataValid) {
		this.employeeDataValid = employeeDataValid;
	}

}
