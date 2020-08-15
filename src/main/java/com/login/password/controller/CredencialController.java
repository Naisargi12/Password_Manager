package com.login.password.controller;

import com.login.password.model.CredencialDetails;
import com.login.password.model.User;
import com.login.password.model.UserCredentialDetail;
import com.login.password.service.CommonService;
import com.login.password.service.CredentialService;
import com.login.password.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class CredencialController {

    @Autowired
    CredentialService credentialService;
    
    @Autowired
    CommonService commonService;
    
    @Autowired
    UserService userService;
    
    @PostMapping(value = "/addCredencials")
    @ResponseBody
    public String addCredencials(@RequestParam Map<String, String> params, Principal principal){
      // System.out.println(":::::::::::::::::::::"+principal.getName());
      //  System.out.println(":::::::::RequestParam::::::::::::"+params.toString());
        String result = "";
        if(principal.getName()!=null && principal.getName()!=""){
            try{
                credentialService.addCredencial(params,principal.getName());
                result="SUCCESS";
            }catch(Exception e){
                e.printStackTrace();
                result="SUCCESS";
            }
        }
        return result;
    }

    @GetMapping(value = "/getDecodedCredencial")
    @ResponseBody
    public String getDecodedCredencial(@RequestParam Map<String, String> params, Principal principal){
       // System.out.println(":::::::::RequestParam::::::::::::"+params.toString());
        String result = "";
        if(params.get("credentialId")!=null){
            try{
                result = credentialService.getDecodedCredencial(params.get("credentialId"),principal.getName());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
    
    @PostMapping(value="/filterCredentials")
    @ResponseBody
    public ModelAndView filterCredentials(Model model,@RequestParam Map<String, String> params, Principal principal) {
    	UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
    	User user = userService.findUserByEmail(principal.getName());
    	List<CredencialDetails> credencialDetails = credentialService.getFilteredCredentials(params.get("catId"),user.getId(),userCredentialDetail);
    	model.addAttribute("credencialDetails", credencialDetails);
    	return  new ModelAndView( "admin/trData" );
    }
    
    @PostMapping("removeCredential")
    @ResponseBody
    public String removeCredential(@RequestParam(name="credentialId",required=true) Integer credentialId) {
    	UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
    	credentialService.deleteCredentials(credentialId,userCredentialDetail);
    	return "success";
    }
}
