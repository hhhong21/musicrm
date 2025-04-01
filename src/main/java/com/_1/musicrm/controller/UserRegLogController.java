package com._1.musicrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRegLogController {

    @GetMapping("/userRegister")
    public String showUserRegistrationPage() {
        return "userRegister"; // return to userRegister.html
    }
    
    @GetMapping("/userLogin")
        public String showUserLoginPage() {
            return "userLogin"; // return to userLogin.html
        }
    
    @GetMapping("/user-info")
    public String showUserInformationManagementPage() {
        return "userInformationManagement"; // return to userInformationManagement.html
    }
    
}