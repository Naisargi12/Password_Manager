package com.login.password.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.login.password.model.Catagory;
import com.login.password.model.UserCredentialDetail;

@Service
public class CatagoryService {
	
	public Catagory getCatagoryById(Integer id,UserCredentialDetail userCredentialDetail){
		if(userCredentialDetail.getCatagoryDetails()==null) {
			return null;
		}
		List<Catagory> catagogies = (List<Catagory>) userCredentialDetail.getCatagoryDetails().stream().filter(e->e.getId().equals(id)); 
		if(catagogies.isEmpty()) {
			return null;
		}
		return catagogies.get(0);
	}
	
	public List<Catagory> getAllCatagoryByUserId(Integer userId, UserCredentialDetail userCredentialDetail){
		if(userCredentialDetail.getCatagoryDetails()==null) {
			return null;
		}
		List<Catagory> catagogies = userCredentialDetail.getCatagoryDetails().stream().filter(e->e.getUserId().intValue()==userId.intValue()).collect(Collectors.toList());
		if(catagogies.isEmpty()) {
			return null;
		}
		return catagogies;
	}
	
	public Catagory addCatagory(UserCredentialDetail userCredentialDetail,Catagory catagory) {
		List userCatagories = new ArrayList<Catagory>();
		if(userCredentialDetail.getCatagoryDetails()==null) {
			userCredentialDetail.setCatagoryDetails(userCatagories);
		}
		List<Integer> hasList = userCredentialDetail.getCatagoryDetails().stream().map(e -> e.hashCode()).collect(Collectors.toList());
		catagory.setId(userCredentialDetail.getCatagoryDetails().size()+1);
		if(!hasList.contains(catagory.hashCode()))
			userCredentialDetail.getCatagoryDetails().add(catagory);
		return catagory;
	}
	
	public Catagory updateCatagory(UserCredentialDetail userCredentialDetail,Catagory catagory) {
		Catagory existingCatagory = userCredentialDetail.getCatagoryDetails().stream().filter(e-> e.getId().equals(catagory.getId())).findFirst().get();
		List<Integer> hasList = userCredentialDetail.getCatagoryDetails().stream().map(e -> e.hashCode()).collect(Collectors.toList());
		if(!hasList.contains(catagory.hashCode()))
			existingCatagory.setName(catagory.getName());
		return catagory;
	}
	
}
