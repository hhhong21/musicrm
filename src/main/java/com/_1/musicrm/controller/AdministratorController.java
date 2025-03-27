package com._1.musicrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com._1.musicrm.model.Administrator;
import com._1.musicrm.service.AdministratorService;

@Controller
public class AdministratorController {
    @Autowired
    private AdministratorService adminService;

    @GetMapping("/AdministratorRegister")
    public String getRegisterAdminForm(Model model){
        model.addAttribute("admin", new Administrator());
        return "AdministratorRegister"; 
    }

    @PostMapping("/AdministratorRegister")
    public String registerAdmin(@ModelAttribute Administrator admin){
        adminService.saveAdmin(admin);
        return "redirect:/AdministratorLogin"; 
    }

    @GetMapping("/AdministratorLogin")
    public String getLoginAdminForm(Model model){
        model.addAttribute("admin", new Administrator());
       return "AdministratorLogin"; 
    }

    @PostMapping("/AdministratorLogin")
    public String loginAdmin(@ModelAttribute Administrator admin, Model model){
        String result = adminService.loginAdmin(admin);
        switch(result){
            case "Administrator Not Found!" -> {
                model.addAttribute("error", "Administrator Not Found!");
                return "AdministratorLogin";
            }
            case "Password Mismatch!" -> {
                model.addAttribute("error", "Password Mismatch!");
                return "AdministratorLogin";
            } 
            case "Login Success!" -> {
                return "redirect:/AdministratorHome";
            }
            default -> {
                model.addAttribute("error", "Unknown Error!");
                return "AdministratorLogin";
            }
        }
    }

    @GetMapping("/AdministratorHome")
    public String showAdminHome(){
        return "AdministratorHome"; 
    }
}