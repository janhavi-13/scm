package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.scm.entities.Providers;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        // System.out.println("Home page handler");
        // model.addAttribute("name", "JJ");
        // model.addAttribute("wmsg", "Welcome to my spring boot project");
        // model.addAttribute("link", "https://www.google.com");
        return "home"; //page
    }

    //about
    @RequestMapping("/about")
    public String aboutPage(){
        return "about";
    }

    //services
    @RequestMapping("/services")
    public String servicesPage(){
        return "services";
    }

    //contact
    @RequestMapping("/contact")
    public String contactPage(){
        return "contact";
    }

    //login
    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    //register
    @RequestMapping("/register")
    public String registerPage(Model model){
        UserForm userForm = new UserForm();
        // userForm.setName("JJ");
        // userForm.setAbout("This is about : Write something about yourself");
        //default data bhi daal skte hai
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //process register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session){
        //fetch the form data
        //User form
        System.out.println(userForm);

        //validate form date
        if(rBindingResult.hasErrors()) return "register";

        //save to database
        //UserForm --> User
        // commented out bcz wasnt able to save the default values
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic(
        //     "@{'/images/defaultPP.jpeg'}"
        // )
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("@{'/images/defaultPP.jpeg'}");

        if (user.getProvider() == null) {
            user.setProvider(Providers.SELF);
        }
        User savedUser = userService.saveUser(user);
        System.out.println(savedUser);

        //msg= "successfull registration"
        Message message = Message.builder().content("Registration Successfull").type(MessageType.green).build();
        session.setAttribute("message",message);


        //redirect to login page
        return "redirect:/register";
    }
}
