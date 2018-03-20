package com.example.abdlkdr.wowzasample.wowzaserver.Controller;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.User;
import com.example.abdlkdr.wowzasample.wowzaserver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST,value = "addNewUser")
    @ResponseBody
    public void addNewUser(){
        userService.addNewUser();
    }

    @RequestMapping(method =  RequestMethod.GET,value = "checkUserAvailable")
    @ResponseBody
    public String checkUserAvailable(@RequestParam(value = "username") String username){
        return userService.checkUserAvailable(username);
    }

    @RequestMapping(method = RequestMethod.GET,value = "setUserStatus")
    @ResponseBody
    public String setUserStatus(@RequestParam(value = "username")String username,@RequestParam(value = "status")String status){
        return userService.setUserStatus(username,status);
    }

    @RequestMapping(method = RequestMethod.GET,value = "loginUser")
    @ResponseBody
    public String loginUser(@RequestParam(value = "username")String username){
        return userService.loginUser(username);
    }
    @RequestMapping(method = RequestMethod.GET,value = "getUserStatus")
    @ResponseBody
    public User getUserStatus (@RequestParam(value = "username")String username){
        return userService.getUserStatus(username);
    }
    @RequestMapping(method = RequestMethod.GET,value = "getAllUser")
    @ResponseBody
    public List<User> getAllUser (@RequestParam(value = "username")String username){
        return userService.getAllUser(username);
    }

}
