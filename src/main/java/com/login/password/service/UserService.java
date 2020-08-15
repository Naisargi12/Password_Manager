package com.login.password.service;

import com.login.password.service.CommonService;
import com.login.password.model.User;
import com.login.password.model.UserCredentialDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private CommonService commonService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
    	UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
		if(userCredentialDetail!=null) {
			List<User> userList = commonService.getUsers(userCredentialDetail);
			if(userList!=null) {
				return userList.stream().filter(userObj-> userObj.getEmail().equals(email)).findFirst().orElse(null);
			}
		}
    	return null;
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
		if(userCredentialDetail!=null) {
			List<User> userList = commonService.getUsers(userCredentialDetail);
			if(!CollectionUtils.isEmpty(userList)) {
				user.setId(userCredentialDetail.getUsersSize());
				userList.add(user);
			}else {
				List<User> users = new ArrayList<User>();
				user.setId(0);
				users.add(user);
				userCredentialDetail.setUsers(users);
			}
			commonService.setUserCredentialDetail(userCredentialDetail);
		}
    }

    public void addSecretKey(String secretKey, String userName) {
    	UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
		if(userCredentialDetail!=null) {
			List<User> userList = commonService.getUsers(userCredentialDetail);
			if(userList!=null) {
				User user =  userList.stream().filter(userObj-> userObj.getEmail().equals(userName)).findFirst().orElse(null);
				if(user!=null){
		            user.setSecretKey(secretKey);
		            commonService.setUserCredentialDetail(userCredentialDetail);
		        }
			}
		}
        
    }
    public void updateUserPassword(User user) {
		UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
		if(userCredentialDetail!=null) {
			List<User> userList = commonService.getUsers(userCredentialDetail);
			if(userList!=null) {
				User userObj =  userList.stream().filter(obj-> obj.getEmail().equals(obj.getEmail())).findFirst().orElse(null);
				userObj.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	            commonService.setUserCredentialDetail(userCredentialDetail);
	        }
		}
	}
   
}