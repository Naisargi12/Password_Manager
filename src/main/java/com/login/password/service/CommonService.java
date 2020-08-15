package com.login.password.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.login.password.model.CredencialDetails;
import com.login.password.model.User;
import com.login.password.model.UserCredentialDetail;

@Service
public class CommonService {
	
	private final String file = "userCredentialDetail.file";
	public UserCredentialDetail getUserCredentialDetail() {
		try {
			FileInputStream fi = new FileInputStream(new File(file));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			UserCredentialDetail userCredentialDetail = (UserCredentialDetail) oi.readObject();

			oi.close();
			fi.close();
			return userCredentialDetail;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}
	public List<User> getUsers(UserCredentialDetail userCredentialDetail){
		if(userCredentialDetail!=null) {
			return userCredentialDetail.getUsers();
		}
		return null;
	}
	public List<CredencialDetails> getCredencialDetails(UserCredentialDetail userCredentialDetail){
		if(userCredentialDetail!=null) {
			return userCredentialDetail.getCredencialDetails();
		}
		return null;
	}
	public void setUserCredentialDetail(UserCredentialDetail userCredentialDetail) {
		try {
			FileOutputStream f = new FileOutputStream(new File(file));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(userCredentialDetail);

			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error initializing stream");
		}
	}
	public void createFileIfNotExist(){
		try {
			FileInputStream fi = new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setUserCredentialDetail(new UserCredentialDetail());
		}
	}
}
