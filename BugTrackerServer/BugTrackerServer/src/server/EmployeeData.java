//Class representing employee records data
//Deals with data file functions
//Reading from records data file
//Writing to data file

package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeData {
	private List<Employee> employeeList;
	private File file = new File("employeeList.dat");
	
	public EmployeeData() {
		employeeList = new ArrayList<>();
		loadEmployeeList();
	}
	
	private void loadEmployeeList() {
		BufferedReader br;
		Employee emp;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				emp = new Employee();
				emp.setName(line);
				emp.setEmpID(br.readLine());
				emp.setEmail(br.readLine());
				emp.setPassword(br.readLine());
				emp.setDepartment(br.readLine());
				emp.setAssignedBugId(br.readLine());
				employeeList.add(emp);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Add new employee 
	public void addNewEmployee(Employee emp) {
		employeeList.add(emp);
		writeToFile(emp);
	}
	
	//Write to employee records file
	private void writeToFile(Employee emp){
		try {
			PrintWriter out = new PrintWriter(new FileWriter(file, true), true);
			out.print(emp.toFile());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Employee> getEmployeList() {
		return employeeList;
	}

	public void setEmployeList(List<Employee> employeList) {
		this.employeeList = employeList;
	}
	
	public String getAllEmployees() {
		String divider = "----------------------------------------------";
		String empList = "Registered employees:\n" + divider;
		for(Employee e : employeeList) {
			empList += e.toString() + "\n" + divider;
		}
		return empList;
	}
}
