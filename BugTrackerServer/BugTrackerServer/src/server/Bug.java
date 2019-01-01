package server;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bug {
	//Instance variables
	private String applicationName;
	private Date date;
	private String platform;
	private String description;
	private String status;
	private String id;
	private String assignedToID;
	//Lock
	private Lock lock = new ReentrantLock();
	
	
	//Setters
	//Getters
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAssignedToID() {
		return assignedToID;
	}
	public void setAssignedToID(String assignedToID) {
		this.assignedToID = assignedToID;
	}
	
	//Lock methods for disabling concurrent editing of a bug
	public void lockBug() {
		lock.lock();
	}
	
	public void unlockBug() {
		lock.unlock();
	}
	
	public boolean tryLock() {
		return lock.tryLock();
	}
	//Method for formatted object output to file
	public String toFile() {
		String bug;
		bug = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", 
				getApplicationName(), getDate(), getPlatform(), getDescription(), getStatus(), getId(), getAssignedToID());
		return bug;
	}
	//Formatted output of object to screen
	public String toString() {
		String bug;
		bug = String.format("\n%17s %s \n%-17s %s \n%-17s %s\n%-17s %s\n%-17s %s\n%-17s %s\n%-17s %s", "Application name:",
				getApplicationName(), "Date:", getDate(),"Platform:", getPlatform(), 
					"Description:", getDescription(), "Status:", getStatus(), "Bug ID:", getId(), "Assigned to:", getAssignedToID());
		return bug;
	}
}
