package com.example.abdlkdr.wowzasample.wowzaserver.Controller;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.RequestLiveChat;
import com.example.abdlkdr.wowzasample.wowzaserver.Service.RequestLiveChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestLiveChatController {

    @Autowired
    RequestLiveChatService requestLiveChatService;


    @RequestMapping(method = RequestMethod.GET,value = "isAccepted")
    @ResponseBody
    public RequestLiveChat createRequestLiveChat (@RequestParam(value = "user") String user, @RequestParam(value = "toUser") String toUser){
        return requestLiveChatService.createRequestLiveChat(user, toUser);
    }

    @RequestMapping(method = RequestMethod.GET,value = "changeRequesLiveChatStatus")
    @ResponseBody
    public void changeRequestLiveChatStatus(@RequestParam(value = "user") String user,@RequestParam(value = "toUser")String toUser){
        requestLiveChatService.changeRequestLiveChatStatus(user, toUser);
    }

    @RequestMapping(method = RequestMethod.GET,value = "checkRequestIsExist")
    @ResponseBody
    public void checkRequestIsExist (@RequestParam(value = "toUser")String toUser,@RequestParam(value = "isAccepted")boolean isAccepted){
        requestLiveChatService.checkRequestIsExist(toUser,isAccepted);
    }
}
