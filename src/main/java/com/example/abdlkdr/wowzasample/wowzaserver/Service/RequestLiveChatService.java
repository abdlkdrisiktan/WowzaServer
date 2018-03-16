package com.example.abdlkdr.wowzasample.wowzaserver.Service;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.RequestLiveChat;
import com.example.abdlkdr.wowzasample.wowzaserver.Model.User;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.RequestLiveChatRepository;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("Duplicates")
@Service
public class RequestLiveChatService {


    @Autowired
    RequestLiveChatRepository requestLiveChatRepository;

    @Autowired
    UserRepository userRepository;

    //------------------------------------------------------------//
    //Kullanıcı client tarafında konuşma başlaktmak istediğinde   //
    //İstek buraya düşüyor ve random kişilerler match ediyor      //
    //İsteğin olup olmadığına bakıyor daha sonrasından ona göre   //
    //Yeni bir istek oluşturup gerekli elemanları set ediyor      //
    //------------------------------------------------------------//
    public RequestLiveChat createRequestLiveChat(String user) {
        List<User> list = userRepository.findAll();
        String toUser = "";
        Random rand = new Random();
        int n = rand.nextInt(list.size());
        toUser = list.get(n).getUsername();
        if (toUser.equals(user)) {
            createRequestLiveChat(user);
        }
        System.out.println(toUser);
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByUserUsernameAndToUserUsername(user, toUser);
        User firstUser = userRepository.getUserByUsername(user);
        User secondUser = userRepository.getUserByUsername(toUser);
        if (requestLiveChat == null && !toUser.equals(user) && firstUser.getStatus().contentEquals("online") && secondUser.getStatus().contentEquals("online")) {
            requestLiveChat = new RequestLiveChat();
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
            requestLiveChat.setId(uuid);
            if (firstUser != null && !firstUser.getUsername().isEmpty() && firstUser.getStatus().contentEquals("online")) {
//                firstUser.setStatus("offline");
                requestLiveChat.setUser(firstUser);
            }
            if (secondUser != null && !secondUser.getUsername().isEmpty() && secondUser.getStatus().contentEquals("online")) {
//                secondUser.setStatus("offline");
                requestLiveChat.setToUser(secondUser);
            }
            //konuşma hala devam ediyor mu etmiyor mu diye
            requestLiveChat.setStatus("yes");
            requestLiveChat.setAccepted(false);
            //is accepted ise kabul etti mi etmedimi diye
            //ilk menüden seçtiğinde false default olarak set edecektir daha sonra
            //chechreques is exit bakacak
            requestLiveChatRepository.save(requestLiveChat);
            return requestLiveChat;
        } else {
            System.out.println("username is     :   "+  user+   "   toUsername is   :   "+toUser);
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
            System.out.println("change request live chat status line 69");
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

    public RequestLiveChat getRequest(String toUser){
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByToUserUsername(toUser);
        System.out.println("getResquest");
        if (requestLiveChat != null && requestLiveChat.getStatus().contentEquals("yes") && requestLiveChat.getUser().getStatus().contentEquals("online") && requestLiveChat.getToUser().getStatus().contentEquals("online")){

            if (requestLiveChat.isAccepted()){
                return requestLiveChat;
            }else {
                return null;
            }
        }else {
            requestLiveChat= new RequestLiveChat();
            return requestLiveChat;
        }
    }

    public RequestLiveChat getRequestForUser(String user){
        RequestLiveChat request = requestLiveChatRepository.findByUserUsername(user);
        if (request != null && request.getStatus().contentEquals("yes")){
            if (request.isAccepted()){
                return request;
            }else {
                return null;
            }
        }else {
            request= new RequestLiveChat();
            return request;
        }
    }

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

}
