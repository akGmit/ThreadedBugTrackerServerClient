package server;

public class Employee {
	private String name;
	private String empID;
	private String email;
	private String password;
	private String department;
	private String assignedBugId = "N/A";
	
	//Setters
	//Getters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmpID() {
		return empID;
	}
	public void setEmpID(String string) {
		this.empID = string;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getAssignedBugId() {
		return assignedBugId;
	}
	public void setAssignedBugId(String assignedBugId) {
		this.assignedBugId = assignedBugId;
	}
	
	public String toFile() {
		String emp;
		emp = String.format("%s\n%s\n%s\n%s\n%s\n%s\n", 
				getName(), getEmpID(), getEmail(), getPassword(), getDepartment(), getAssignedBugId());
		return emp;
	}
	
	public String toString() {
		String emp;
		emp = String.format("\n%17s %s \n%-17s %s \n%-17s %s\n%-17s %s\n%-17s %s", "Employee name:",
				getName(), "Employee ID:", getEmpID(),"Employee email:", getEmail(), 
					"Department:", getDepartment(), "Assigned Bug:", getAssignedBugId());
		return emp;
	}
}
