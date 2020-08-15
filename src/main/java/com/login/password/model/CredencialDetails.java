package com.login.password.model;

import java.io.Serializable;

public class CredencialDetails implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String domain;
    private String username;
    private String password;
    private Integer userId;
    private Integer catagoryId;
    

    public CredencialDetails() {
    }

    public CredencialDetails(String domain, String username, String password, Integer userId, Integer catagoryId) {
        this.domain = domain;
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.catagoryId = catagoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public Integer getCatagoryId() {
		return catagoryId;
	}

	public void setCatagoryId(Integer catagoryId) {
		this.catagoryId = catagoryId;
	} 
    
    
}
