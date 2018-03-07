package com.example.abdlkdr.wowzasample.wowzaserver.Service;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.RequestLiveChat;
import com.example.abdlkdr.wowzasample.wowzaserver.Model.User;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.RequestLiveChatRepository;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class RequestLiveChatService {


    @Autowired
    RequestLiveChatRepository requestLiveChatRepository;

    @Autowired
    UserRepository userRepository;


    //Client tarafından gelecek iki tane user ismi ile request modelini oluşturacağız
    //verileri set ediyoruz
    //is accepted false olarak ayarlıyoruz ki
    //karşı tarafa bilgi gidene kadar konuşmayı başlatmayalım
    //eğer true olursa checkrequestısexıst ıcersine girecek
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
        if (requestLiveChat == null && !toUser.equals(user)) {
            requestLiveChat = new RequestLiveChat();
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
            requestLiveChat.setId(uuid);
            User firstUser = userRepository.getUserByUsername(user);
            if (firstUser != null && !firstUser.getUsername().isEmpty() && firstUser.getStatus().contentEquals("online") ) {
                firstUser.setStatus("offline");
                requestLiveChat.setUser(firstUser);
            }
            User secondUser = userRepository.getUserByUsername(toUser);
            if (secondUser != null && !secondUser.getUsername().isEmpty() && secondUser.getStatus().contentEquals("online")) {
                secondUser.setStatus("offline");
                requestLiveChat.setToUser(secondUser);
            }
            //konuşma hala devam ediyor mu etmiyor mu diye
            requestLiveChat.setStatus("yes");
            //is accepted ise kabul etti mi etmedimi diye
            //ilk menüden seçtiğinde false default olarak set edecektir daha sonra
            //chechreques is exit bakacak
            requestLiveChatRepository.save(requestLiveChat);
            return requestLiveChat;
        } else {
            if (!toUser.equals(user) && requestLiveChat.getStatus().contentEquals("no") ) {
                requestLiveChat.setStatus("yes");
                requestLiveChatRepository.save(requestLiveChat);
            }
            return requestLiveChat;
        }
    }

    public void changeRequestLiveChatStatus(String user, String toUser) {
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByUserUsernameAndToUserUsername(user, toUser);
        if (requestLiveChat != null && requestLiveChat.getStatus().contentEquals("yes")) {
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

    //Karşıdaki kullanıcıya request gelip gelmediğini kontrol etmek için
    public String checkRequestIsExist(String toUser) {
        //burada accepted booelan kontrol edersek devamlı ıstek gondermez
        //alert dialogda hayır dusuna basarsa o zaman bu metodun içerisinde girmez veya 4
        //alert dialog 'da hayır basınca o isteği silecek bir metot oluşturabiliriz böylece isteğin gelip gelmediğine de tekrardan bakabiliriz
        //
        RequestLiveChat requestLiveChat = requestLiveChatRepository.findByToUserUsername(toUser);
        if (requestLiveChat != null) {
            return "yes";
        } else {
            return "no";
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

}
