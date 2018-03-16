package com.example.abdlkdr.wowzasample.wowzaserver.Controller;

import com.example.abdlkdr.wowzasample.wowzaserver.Service.LiveChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiveChatController {

    @Autowired
    LiveChatService liveChatService;

    @GetMapping(value = "setClickStatus")
    @ResponseBody
    public void setClickStatus (@RequestParam(value = "user")String user,@RequestParam(value = "isClick")String isClick){
        liveChatService.setClickStatus(user,isClick);
    }

    @GetMapping(value = "getClickStatus")
    @ResponseBody
    public String getClickStatus(@RequestParam(value = "toUser")String user){
        return liveChatService.getClickStatus(user);
    }

    @GetMapping(value = "createClickStatus")
    @ResponseBody
    public void createClickStatus(@RequestParam(value = "user")String user){
        liveChatService.createClickStatus(user);
    }
}
