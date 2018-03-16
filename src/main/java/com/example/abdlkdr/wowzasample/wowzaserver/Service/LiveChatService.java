package com.example.abdlkdr.wowzasample.wowzaserver.Service;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.LiveChat;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.LiveChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LiveChatService {
    @Autowired
    LiveChatRepository liveChatRepository;


    public void createClickStatus(String user){
        LiveChat liveChat = liveChatRepository.findByUser(user);
        if (liveChat==null){
            liveChat= new LiveChat();
            String uuid = UUID.randomUUID().toString();
            liveChat.setId(uuid);
            liveChat.setUser(user);
            liveChat.setIsClick("false");
            liveChatRepository.save(liveChat);
        }
    }
    public void setClickStatus(String user,String isClick){
        LiveChat liveChat = liveChatRepository.findByUser(user);
        if (liveChat!=null){
            liveChat.setIsClick(isClick);
            liveChatRepository.save(liveChat);

        }else {
            createClickStatus(user);
        }
    }
    public String getClickStatus(String user){
        LiveChat liveChat = liveChatRepository.findByUser(user);
        if (liveChat!=null && liveChat.getIsClick().contentEquals("true")){
                return  "true";
        }else {
            return "false";
        }
    }
}
