package com.example.abdlkdr.wowzasample.wowzaserver.Controller;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.RequestLiveChat;
import com.example.abdlkdr.wowzasample.wowzaserver.Service.RequestLiveChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
public class RequestLiveChatController {

    @Autowired
    RequestLiveChatService requestLiveChatService;


    @GetMapping(value = "createRequestLiveChat")
    @ResponseBody
    public RequestLiveChat createRequestLiveChat (@RequestParam(value = "user") String user){
        return requestLiveChatService.createRequestLiveChat(user);
    }

    @GetMapping(value = "changeRequestLiveChatStatus")
    @ResponseBody
    public void changeRequestLiveChatStatus(@RequestParam(value = "user") String user,@RequestParam(value = "toUser")String toUser){
        requestLiveChatService.changeRequestLiveChatStatus(user, toUser);
    }

    @GetMapping(value = "checkRequestIsExist")
    @ResponseBody
    public String checkRequestIsExist (@RequestParam(value = "toUser")String toUser){
       return requestLiveChatService.checkRequestIsExist(toUser);
    }
    @GetMapping(value = "setAcceptedStatus")
    @ResponseBody
    public void setAcceptedStatus (@RequestParam(value = "toUser")String toUser,@RequestParam(value = "isAccepted")Boolean isAccepted){
        requestLiveChatService.setAcceptedStatus(toUser,isAccepted);
    }
    @GetMapping(value = "changeAcceptedStatus")
    @ResponseBody
    public void changeAcceptedStatus (@RequestParam(value = "toUser")String toUser,@RequestParam(value = "acceptedStatus")String acceptedStatus){
        requestLiveChatService.changeAcceptedStatus(toUser,acceptedStatus);
    }
//    @GetMapping(value = "getRequest")
//    @ResponseBody
//    public RequestLiveChat getRequest (@RequestParam(value = "toUser")String toUser){
//        return requestLiveChatService.getRequest(toUser);
//    }
    @GetMapping(value = "getAcceptedStatus")
    @ResponseBody
    public String getAcceptedStatus (@RequestParam(value = "user")String user){
        return requestLiveChatService.getAcceptedStatus(user);
    }
//    @GetMapping(value = "getRequestForUser")
//    @ResponseBody
//    public RequestLiveChat getRequestForUser(@RequestParam(value = "user")String user){
//        return requestLiveChatService.getRequestForUser(user);
//    }
    @GetMapping(value = "deleteRequestLiveChat")
    @ResponseBody
    public void  deleteRequestLiveChat (@RequestParam(value = "username")String username){
        requestLiveChatService.deleteRequestLiveChat(username);
    }
    @GetMapping(value = "getRequestData")
    @ResponseBody
    public RequestLiveChat getRequestData (@RequestParam(value = "username")String username){
        return requestLiveChatService.getRequestData(username);
    }

}
