//Class representing bug records data
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BugsData {
	private List<Bug> bugsData;
	private File file = new File("bugRecords.dat");
	
	public BugsData() {
		bugsData = new ArrayList<>();
		loadBugRecords();
	}
	
	//Add bug to bug list and write to file
	public void addBug(Bug bug) {
		bugsData.add(bug);
		writeToFile(bug);
	}

	//Load bug records from file
	//Read bugRecords.dat file and populate list from file
	//Using BufferedReader object with a FileReader object as parameter to read from file
	//Each line in record represents Bug class information
	private void loadBugRecords() {
		BufferedReader br;
		Bug bug;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				bug = new Bug();
				bug.setApplicationName(line);
				bug.setDate(new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy").parse(br.readLine()));
				bug.setPlatform(br.readLine());
				bug.setDescription(br.readLine());
				bug.setStatus(br.readLine());
				bug.setId(br.readLine());
				bug.setAssignedToID(br.readLine());
				bugsData.add(bug);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	//Append to file 
	private void writeToFile(Bug bug){
		try {
			PrintWriter out = new PrintWriter(new FileWriter(file, true), true);
			out.print(bug.toFile());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Rewrite file with new records after assignment
	//Delete current file
	//Create new file and populate from bugsData list
	public void updateFile() {
		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Bug b : bugsData) {
			writeToFile(b);
		}
	}
	
	//Return all bug records
	public String getAllBugRecords() {
		String divider = "----------------------------------------------";
		String bugList = "All bugs in record:\n" + divider;
		for(Bug b : bugsData) {
			bugList += b.toString() + "\n" + divider;
		}
		return bugList;
	}
	
	//Get all unassigned bugs 
	public String getAllUnassignedBugRecords() {
		String divider = "----------------------------------------------";
		String bugList = "All unassigned bugs in record:\n" + divider;
		for(Bug b : bugsData) {
			if(b.getAssignedToID().equalsIgnoreCase("N/A"))
				bugList += b.toString() + "\n" + divider;
		}
		return bugList;
	}
	//Getters/Setters
	public List<Bug> getBugsData() {
		return bugsData;
	}
	public void setBugsData(List<Bug> bugsData) {
		this.bugsData = bugsData;
	}
}
