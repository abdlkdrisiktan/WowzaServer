package com.example.abdlkdr.wowzasample.wowzaserver.Service;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.User;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void addNewUser() {
        User user = new User();
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setStatus("offline");
        user.setUsername("melih");
        userRepository.save(user);
    }

    public String checkUserAvailable(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user.getStatus().contentEquals("online")){
            return "online";
        }else {
            return "offline";
        }
    }

    public String setUserStatus(String username){
        User user = userRepository.getUserByUsername(username);
        if (user.getStatus().contentEquals("online")){
            user.setStatus("offline");
            userRepository.save(user);
            return "offline";
        }else {
            user.setStatus("online");
            userRepository.save(user);
            return "online";
        }
    }

    public String loginUser(String username){
        User user= userRepository.getUserByUsername(username);
        if (user!=null && !user.getUsername().isEmpty()  && user.getUsername().equals(username)){
           return "ok";
        }
        else {
            return "fail";
        }
    }
    public User getUserStatus (String username){
        User user = userRepository.getUserByUsername(username);
        if (user!= null && !user.getUsername().isEmpty() ){
            return user;
        }else {
            user= new User();
            return user;
        }
    }


}
