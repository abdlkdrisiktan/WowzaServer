package com.example.abdlkdr.wowzasample.wowzaserver.Service;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.DataRequestLiveChat;
import com.example.abdlkdr.wowzasample.wowzaserver.Model.RequestLiveChat;
import com.example.abdlkdr.wowzasample.wowzaserver.Model.User;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.DataRequestLiveChatRepository;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.RequestLiveChatRepository;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@SuppressWarnings("Duplicates")
@Service
public class RequestLiveChatService {


    @Autowired
    RequestLiveChatRepository requestLiveChatRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DataRequestLiveChatRepository dataRequestLiveChatRepository;

    //------------------------------------------------------------//
    //Kullanıcı client tarafında konuşma başlaktmak istediğinde   //
    //İstek buraya düşüyor ve random kişilerler match ediyor      //
    //İsteğin olup olmadığına bakıyor daha sonrasından ona göre   //
    //Yeni bir istek oluşturup gerekli elemanları set ediyor      //
    //------------------------------------------------------------//
    public RequestLiveChat createRequestLiveChat(String user) {
        List<User> list = userRepository.findAll();
        List<User> tempList ;
        for (int i=0; i<list.size(); i++){
            if (list.get(i).getStatus().contentEquals("online")){
                if (list.get(i).getUsername().contentEquals(user)){
                    list.remove(i);
                }
            }
            if (list.get(i).getStatus().contentEquals("offline")){
                list.remove(i);
            }
        }
        tempList=list;
        for (int i =0; i<tempList.size();i++){
            System.out.println(tempList.get(i).getUsername()+"  "+tempList.get(i).getStatus());
        }
        String toUser = "";
        Random rand = new Random();
        int n = rand.nextInt(tempList.size());
        toUser=tempList.get(n).getUsername();
        DataRequestLiveChat dataRequestLiveChat = new DataRequestLiveChat();
        User firstUser = userRepository.getUserByUsername(user);
        User secondUser = userRepository.getUserByUsername(toUser);
        if ( firstUser.getStatus().contentEquals("online") && secondUser.getStatus().contentEquals("online") ) {
            RequestLiveChat requestLiveChat = requestLiveChatRepository.findByUserUsernameAndToUserUsername(user, toUser);
            if (requestLiveChat == null){
                requestLiveChat = new RequestLiveChat();
                String uuid = UUID.randomUUID().toString();
                System.out.println(uuid);
                requestLiveChat.setId(uuid);
                dataRequestLiveChat.setId(uuid);
                if (firstUser != null && !firstUser.getUsername().isEmpty() && firstUser.getStatus().contentEquals("online")) {
                    requestLiveChat.setUser(firstUser);
                    dataRequestLiveChat.setUser(firstUser);
                }
                if (secondUser != null && !secondUser.getUsername().isEmpty() && secondUser.getStatus().contentEquals("online")) {
                    requestLiveChat.setToUser(secondUser);
                    dataRequestLiveChat.setToUser(secondUser);
                }
                requestLiveChat.setStatus("yes");
                requestLiveChat.setAccepted(false);
                requestLiveChat.setDate(Calendar.getInstance().getTime());
                dataRequestLiveChat.setDate(Calendar.getInstance().getTime());
                dataRequestLiveChatRepository.save(dataRequestLiveChat);
                requestLiveChatRepository.save(requestLiveChat);
                return requestLiveChat;
            }else {
                if (!toUser.equals(user)  && firstUser.getStatus().contentEquals("online") && secondUser.getStatus().contentEquals("online")) {
                    if (requestLiveChat !=null && requestLiveChat.getStatus().contentEquals("no"))
                    {
                        requestLiveChat.setStatus("yes");
                        requestLiveChatRepository.save(requestLiveChat);
                        return requestLiveChat;
                    }
                }
                return requestLiveChat;
            }
        }
        return new RequestLiveChat();
    }

    public void changeRequestLiveChatStatus(String user, String toUser) {
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByUserUsernameAndToUserUsername(user, toUser);
        if (requestLiveChat != null && requestLiveChat.getStatus().contentEquals("yes")) {
            requestLiveChat.setAccepted(true);
            requestLiveChat.setStatus("no");
            User firstUser = userRepository.getUserByUsername(user);
            if (firstUser != null && firstUser.getStatus().contentEquals("offline")) {
                firstUser.setStatus("online");
                requestLiveChat.setUser(firstUser);
            }
            User secondUser = userRepository.getUserByUsername(toUser);
            if (secondUser != null && secondUser.getStatus().contentEquals("offline")) {
                secondUser.setStatus("online");
                requestLiveChat.setToUser(secondUser);
            }
            requestLiveChatRepository.save(requestLiveChat);
        } else {
//            isAccepted(user, toUser);
        }
    }

    //Alert dialog'da hayır basdığımızda isAccepted secenegini
    public void changeAcceptedStatus(String toUser,String acceptedStatus) {
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByToUserUsername(toUser);
        if (requestLiveChat != null) {
           if (acceptedStatus.contentEquals("true")){
               requestLiveChat.setStatus("no");
               requestLiveChat.setAccepted(true);
               requestLiveChatRepository.save(requestLiveChat);
           }else {
               requestLiveChat.setStatus("yes");
               requestLiveChat.setAccepted(false);
               requestLiveChatRepository.save(requestLiveChat);
           }
        }

    }

    public String checkRequestIsExist(String toUser) {
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByToUserUsername(toUser);
        System.out.println("checkRequestIsExist");
        if (requestLiveChat != null && requestLiveChat.getStatus().contentEquals("yes") && requestLiveChat.getUser().getStatus().contentEquals("online") && requestLiveChat.getToUser().getStatus().contentEquals("online")) {
            return "yes";
        } else {
            return "no";
        }
    }

//    public RequestLiveChat getRequest(String toUser){
//        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByToUserUsername(toUser);
//        if (requestLiveChat != null && requestLiveChat.getUser().getStatus().contentEquals("online") && requestLiveChat.getToUser().getStatus().contentEquals("online")){
//
//            if (requestLiveChat.isAccepted()){
//                return requestLiveChat;
//            }else {
//                return null;
//            }
//        }else {
//            requestLiveChat= new RequestLiveChat();
//            return requestLiveChat;
//        }
//    }

//    public RequestLiveChat getRequestForUser(String user){
//        RequestLiveChat request = requestLiveChatRepository.findByUserUsername(user);
//        if (request != null && request.getStatus().contentEquals("yes")){
//            if (request.isAccepted()){
//                return request;
//            }else {
//                return null;
//            }
//        }else {
//            request= new RequestLiveChat();
//            return request;
//        }
//    }

    //Request kontrol edildikten sonra konuşma kabul edip etmediğini değiştirecek
    public void setAcceptedStatus(String toUser, boolean isAccepted) {
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByToUser(toUser);
        if (isAccepted) {
            System.out.println("is accepted deneme");
            if (requestLiveChat != null && requestLiveChat.getStatus().contentEquals("yes")) {
                requestLiveChat.setAccepted(true);
                System.out.println("if statement deneme");
                requestLiveChatRepository.save(requestLiveChat);
            }
        } else {
            System.out.println("else deneme");
            if (requestLiveChat != null) {
                requestLiveChat.setAccepted(false);
                requestLiveChatRepository.save(requestLiveChat);
            }
        }
    }

    public String getAcceptedStatus (String user){
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByUserUsername(user);
        if (requestLiveChat!=null && requestLiveChat.isAccepted()){
            return "true";
        }else {
            return "false";
        }
    }

    public void deleteRequestLiveChat(String username){
        RequestLiveChat user = requestLiveChatRepository.findByUserUsername(username);
        RequestLiveChat toUser = requestLiveChatRepository.findByToUserUsername(username);
        if (user !=null ){
            requestLiveChatRepository.delete(user);
        }
       else {
            if(toUser!=null){
                requestLiveChatRepository.delete(toUser);
            }
        }
    }

    public RequestLiveChat getRequestData(String username){
        RequestLiveChat user = requestLiveChatRepository.findByUserUsername(username);
        RequestLiveChat toUser = requestLiveChatRepository.findByToUserUsername(username);
        if (user!=null && user.getUser().getStatus().contentEquals("online") && user.getToUser().getStatus().contentEquals("online")){
            System.out.println("user if ");
            return user;
        }
        else {
            System.out.println("toUser if");
            return toUser;
        }
    }

}
