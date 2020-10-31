package org.rfc.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("sapUserBean")
@SessionScope
public class SAPUserBean {
	
	private String userName;
	private String password;
	private String client;
	
	public SAPUserBean() {
		super();
	}
	
	public SAPUserBean(String userName,String password,String client) {
		this.userName=userName;
		this.password=password;
		this.client=client;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public String getUserString() {
		if(userName==null) {
			return "No SAP user!";
		}
		else {
			return userName+"_"+client;
		}
	}
	
}
