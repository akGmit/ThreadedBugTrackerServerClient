//Register class for user registration funcionality
//Constructor takes EmployeeData object as parameter containing reference to current employee records data 
package server;

public class Register {
	private EmployeeData empData;
	private String regStatus;
	private boolean employeeDataValid;
	private Employee employee;

	public Register(EmployeeData empData) {
		this.empData = empData;
	}
	
	//Register employee method
	//Takes ClientThread as parameter
	//Creates new local Employee instance and gets it from setNewEmployeeData method
	//Passes this employee to isEmployeeDataValid to check if its unique
	//If unique calls synchronized addNewEemployeeMethod, which passes Employee instance to EmpployeeData object
	public void registerEmployee(ClientThread client) {
		
		Employee newEmployee = setNewEmployeeData(client);
		
		isEmployeeValid(newEmployee);
		
		if(isEmployeeDataValid()) {
			newEmployee.setLoggedIn(true);
			setEmployee(newEmployee);
			addNewEmployee(newEmployee);
		}
	}
	
	//Synchronized method to add new employee
	//Calls EmployeeData empData instance method to add employee to data file
	private synchronized void addNewEmployee(Employee emp) {
		empData.addNewEmployee(emp);
	}
	
	//Create new Employee instance
	//Prompt client for required information
	//Return created Employee instance
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

	//Check if required employee details are unique
	//Method synchronized so no two clients can create employee with invalid data
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
	
	//Getters/Setters
	public void setEmployee(Employee e) {
		this.employee = e;
	}
	
	public Employee getEmployee() {
		return this.employee;
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
