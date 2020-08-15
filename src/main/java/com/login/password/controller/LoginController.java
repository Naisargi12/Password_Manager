package com.login.password.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.login.password.model.CredencialDetails;
import com.login.password.model.User;
import com.login.password.model.UserCredentialDetail;
import com.login.password.service.CatagoryService;
import com.login.password.service.CommonService;
import com.login.password.service.CredentialService;
import com.login.password.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CredentialService credentialService;
    
    @Autowired
    private CatagoryService catagoryService;
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    SmartValidator validator;
    
    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

//    @RequestMapping(value={"/login"}, method = RequestMethod.POST)
//    public ModelAndView loginPost(){
//        System.out.println("dfdsvsdvdsv========================================");
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        validator.validate(user, bindingResult);
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
            modelAndView.addObject("error","There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        //System.out.println("after sucess===========");
        UserCredentialDetail userCredentialDetail = commonService.getUserCredentialDetail();
        modelAndView.addObject("user",user);
        List<CredencialDetails> credencialDetails =  credentialService.getAllUserCredential(user);
        modelAndView.addObject("catagories",catagoryService.getAllCatagoryByUserId(user.getId(), userCredentialDetail));
        modelAndView.addObject("credencialDetails",credencialDetails);
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @PostMapping(value = "/addSecretKey")
    public String addSecretKey(@RequestParam Map<String, String> params, Principal principal){
        userService.addSecretKey(params.get("secretKey"),principal.getName());
        return "redirect:/admin/home";
    }

    @PostMapping(value = "/sendOTP")
    @ResponseBody
    public String sendOTP(@RequestParam Map<String, String> params, HttpSession session){
        User user = userService.findUserByEmail(params.get("userName"));
        if(Objects.isNull(user)){
            return "fail";
        }
        Integer otp = new Random().nextInt(999999);
        sendEmail(user.getEmail(),otp);
        session.setAttribute("otpNumber",otp.toString());
        session.setAttribute("otpUserId",user.getEmail());
        return "success";
    }

    @PostMapping(value = "/verify")
    @ResponseBody
    public String verify(@RequestParam Map<String, String> params,HttpSession session){
        String userId = params.get("userName");
        String otpNumber = params.get("otpNumber");
        if(!Objects.isNull(userId) && !Objects.isNull(otpNumber) ){
            String message=null;
            if(!otpNumber.equals(session.getAttribute("otpNumber"))){
                message = "Invalid OTP.";
            }
            if(!userId.equals(session.getAttribute("otpUserId"))){
                message = "Invalid userId.";
            }
            if(Objects.isNull(message)){
                return "success";
            }else{
                return message;
            }
        }else{
            return "Please enter valid details";
        }
    }

    @PostMapping(value = "/saveNewPassword")
    @ResponseBody
    public String saveNewPassword(@RequestParam Map<String, String> params,HttpServletRequest request){
        String userId = params.get("userName");
        String newPassword = params.get("newPassword");
        User user = userService.findUserByEmail(userId);
        if(!Objects.isNull(userId) && !Objects.isNull(newPassword) && !Objects.isNull(user)){
            user.setPassword(newPassword);
            userService.updateUserPassword(user);
           return "success";
        }else{
            return "Not able to set Password.";
        }
    }
    
    void sendEmail(String to, Integer otp) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);

        msg.setSubject("Reset Password");
        msg.setText("your OTP is "+otp);

        javaMailSender.send(msg);

    }
}
