package com.login.password.service;

import com.login.password.service.CommonService;
import com.login.password.service.UserService;
import com.login.password.PasswordEncrypter.AES;
import com.login.password.model.CredencialDetails;
import com.login.password.model.User;
import com.login.password.model.UserCredentialDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CredentialService {
	
	@Autowired
    private CommonService commonService;
	
    @Autowired
    UserService userService;


    public void addCredencial(Map<String, String> params,String username) throws Exception{
        User user = userService.findUserByEmail(username);
        
        UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
		if(userCredentialDetail!=null) {
			List<CredencialDetails> credentialDetailList = commonService.getCredencialDetails(userCredentialDetail);
			if(!CollectionUtils.isEmpty(credentialDetailList)) {
				if(!StringUtils.isEmpty(params.get("credentialId"))){
					CredencialDetails credentialDetails = credentialDetailList.stream().filter(cd -> cd.getId().toString().equals(params.get("credentialId"))).collect(Collectors.toList()).get(0);
					credentialDetails.setDomain(params.get("domainName"));
					credentialDetails.setUsername(params.get("userName"));
					credentialDetails.setCatagoryId(Integer.parseInt(params.get("catId")));
		            String  s= AES.encrypt(params.get("password"),user.getSecretKey());
		            credentialDetails.setPassword(s);
		        }else{
		        	CredencialDetails credentialDetails = new CredencialDetails(params.get("domainName"),params.get("userName"),AES.encrypt(params.get("password"),user.getSecretKey()),user.getId(),Integer.parseInt(params.get("catId")));
		        	credentialDetails.setId(userCredentialDetail.getCredencialDetailsSize());
		        	credentialDetailList.add(credentialDetails);
		        }
			}else {
				List<CredencialDetails> credencialDetails = new ArrayList<CredencialDetails>();
				CredencialDetails credencialDetails2 = new CredencialDetails(params.get("domainName"),params.get("userName"),AES.encrypt(params.get("password"),user.getSecretKey()),user.getId(),Integer.parseInt(params.get("catId")));
				credencialDetails2.setId(0);
				credencialDetails.add(credencialDetails2);
				userCredentialDetail.setCredencialDetails(credencialDetails);
			}
			commonService.setUserCredentialDetail(userCredentialDetail);
		}
		
		
        
        commonService.setUserCredentialDetail(userCredentialDetail);
    }

    @SuppressWarnings("unchecked")
	public List<CredencialDetails> getAllUserCredential(User user) {
    	UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
    	List<CredencialDetails> credencialDetailsList = commonService.getCredencialDetails(userCredentialDetail);
        if(!CollectionUtils.isEmpty(credencialDetailsList)){
        	return credencialDetailsList.stream().filter(cd -> cd.getUserId()==user.getId()).collect(Collectors.toList());
        }
    	return null;
    }

    public String getDecodedCredencial(String credentialId, String username) throws Exception {
        User user = userService.findUserByEmail(username);
        CredencialDetails credencialDetails = findCredencialDetailsById(credentialId);
        return credencialDetails!=null ? AES.decrypt(credencialDetails.getPassword(),user.getSecretKey()) : null;
    }
    
    public CredencialDetails findCredencialDetailsById(String credentialId) {
    	UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
		if(userCredentialDetail!=null) {
			List<CredencialDetails> credencialDetailsList = commonService.getCredencialDetails(userCredentialDetail);
			if(credencialDetailsList!=null) {
				return credencialDetailsList.stream().filter(cdObj-> cdObj.getId().toString().equals(credentialId)).findFirst().orElse(null);
			}
		}
    	return null;
    }

	public List<CredencialDetails> getFilteredCredentials(String catId, int id, UserCredentialDetail userCredentialDetail) {
		
		if(userCredentialDetail.getCredencialDetails()==null) {
			return new ArrayList<CredencialDetails>();
		}
		if(catId=="" ||  catId==null) {
			return (List<CredencialDetails>) userCredentialDetail.getCredencialDetails().stream().filter(e->(e.getUserId().intValue()==id)).collect(Collectors.toList());
		}
		return userCredentialDetail.getCredencialDetails().stream().filter(e->(e.getUserId().intValue()==id && e.getCatagoryId().intValue()==Integer.valueOf(catId).intValue())).collect(Collectors.toList());
	}

	public void deleteCredentials(Integer credentialId, UserCredentialDetail userCredentialDetail) {
		List<CredencialDetails> credentialDetails = userCredentialDetail.getCredencialDetails();
		credentialDetails = credentialDetails.stream().filter(e-> !e.getId().equals(credentialId)).collect(Collectors.toList());
		userCredentialDetail.setCredencialDetails(credentialDetails);
		commonService.setUserCredentialDetail(userCredentialDetail);
	}
}
