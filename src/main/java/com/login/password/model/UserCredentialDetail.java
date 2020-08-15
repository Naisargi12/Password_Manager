package com.login.password.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.login.password.model.Catagory;
import com.login.password.model.CredencialDetails;
import com.login.password.model.User;

public class UserCredentialDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<User> users;
	private List<CredencialDetails> credencialDetails;
	private List<Catagory> catagoryDetails;
	
	
	public UserCredentialDetail() {
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<CredencialDetails> getCredencialDetails() {
		return credencialDetails;
	}
	public void setCredencialDetails(List<CredencialDetails> credencialDetails) {
		this.credencialDetails = credencialDetails;
	}
	public Integer getUsersSize(){
		if(users==null) {
			return 0;
		} 
		return users.size();
	}
	public Integer getCredencialDetailsSize(){
		if(credencialDetails==null) {
			return 0;
		} 
		return credencialDetails.size();
	}
	public List<Catagory> getCatagoryDetails() {
		return catagoryDetails;
	}
	public void setCatagoryDetails(List<Catagory> catagoryDetails) {
		this.catagoryDetails = catagoryDetails;
	}
	public Integer getCatagoryDetailsSize(){
		if(catagoryDetails==null) {
			return 0;
		} 
		return catagoryDetails.size();
	}
	
}
