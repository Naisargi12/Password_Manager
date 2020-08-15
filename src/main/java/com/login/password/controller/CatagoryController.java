package com.login.password.controller;

import com.login.password.model.Catagory;

import com.login.password.model.CredencialDetails;
import com.login.password.model.User;
import com.login.password.model.UserCredentialDetail;
import com.login.password.service.CatagoryService;
import com.login.password.service.CommonService;
import com.login.password.service.CredentialService;
import com.login.password.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class CatagoryController {
	
	@Autowired
    private CommonService commonService;
	
    @Autowired
    CatagoryService catagoryService;
    
    @Autowired
    UserService userService;

    @GetMapping(value = "/catagoryDetail")
    public ModelAndView getDecodedCredencial(Principal principal){
    	UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
    	User user = userService.findUserByEmail(principal.getName());
    	List<Catagory> catagories = catagoryService.getAllCatagoryByUserId(user.getId(), userCredentialDetail);
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("catagories", catagories);
    	modelAndView.addObject("userId", user.getId());
        modelAndView.setViewName("catagories");
        return modelAndView;
    }
    
    @PostMapping(value = "/saveCategory", consumes="application/json", produces="application/json")
    @ResponseBody
    public Catagory saveCategory(@RequestBody  Catagory catagory){
    	UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
    	if(catagory.getId()==null) {
    		catagoryService.addCatagory(userCredentialDetail, catagory);
    	}else {
    		catagoryService.updateCatagory(userCredentialDetail, catagory);
    	}
    	commonService.setUserCredentialDetail(userCredentialDetail);
        return catagory;
    }
    
}
