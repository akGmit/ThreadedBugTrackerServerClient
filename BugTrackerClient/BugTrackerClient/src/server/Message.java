package server;

import java.io.Serializable;

public class Message implements Serializable{
	private String msg;
	
	public Message() {
		
	}
	
	public void setMessage(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return msg;
	}
}
