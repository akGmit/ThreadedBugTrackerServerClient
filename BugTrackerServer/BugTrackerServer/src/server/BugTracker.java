package server;

import java.util.Date;
import java.util.Random;

public class BugTracker {
	private BugsData bugsData;
	private EmployeeData empData;
	
	public BugTracker() {
		this.bugsData = new BugsData();
		this.empData = new EmployeeData();
	}
	//Create bug record
	public String setBugRecord(ClientThread client) {
		Bug bug = new Bug();

		try {
			client.sendMessage("Enter application name:");
			bug.setApplicationName(client.readMessage());

			bug.setDate(new Date());

			client.sendMessage("Platform:");
			bug.setPlatform(client.readMessage());

			client.sendMessage("Bug record description:");
			bug.setDescription(client.readMessage());

			client.sendMessage("Status:");
			bug.setStatus(client.readMessage());

			bug.setId("B" + new Random().nextInt(100000));

			client.sendMessage("Assign to employee ID:");
			String assigned = client.readMessage();
			if(assigned.equalsIgnoreCase("")) {
				bug.setAssignedToID("N/A");
			}else {
				bug.setAssignedToID(assigned);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		addBug(bug);
		return "Bug added to databse.";
	}
	//Add bug to bugs database
	private void addBug(Bug bug) {
		if(isBugIdUniqe(bug)) {
			bugsData.addBug(bug);
		}
	}
	//Recursive check and set for unique Bug ID
	private boolean isBugIdUniqe(Bug bug) {
		for(Bug b : bugsData.getBugsData()) {
			if(b.getId().equalsIgnoreCase(bug.getId())) {
				bug.setId("B" + new Random().nextInt(100000));
				isBugIdUniqe(bug);
			}
		}
		return true;
	}
	
	public BugsData getBugsList() {
		return bugsData;
	}
	public void setBugsList(BugsData bugsData) {
		this.bugsData = bugsData;
	}
	public EmployeeData getEmpList() {
		return empData;
	}
	public void setEmpList(EmployeeData empData) {
		this.empData = empData;
	}
	
	
}
