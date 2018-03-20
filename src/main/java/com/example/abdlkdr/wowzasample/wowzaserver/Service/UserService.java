package com.example.abdlkdr.wowzasample.wowzaserver.Service;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.User;
import com.example.abdlkdr.wowzasample.wowzaserver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //++++++++++++++++++++++++++++++++++++++++//
    public void addNewUser() {
        User user = new User();
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setStatus("online");
        user.setUsername("1");
        userRepository.save(user);
    }

    //----------------------------------------//
    public String checkUserAvailable(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user.getStatus().contentEquals("online")) {
            return "online";
        } else {
            return "offline";
        }
    }

    //++++++++++++++++++++++++++++++++++++++++//
    public String setUserStatus(String username,String status) {
        User user = userRepository.getUserByUsername(username);
        if (user!=null){
            user.setStatus(status);
            userRepository.save(user);
            return user.getStatus();
        }
        return null;
    }

    //+++++++++++++++++++++++++++++++++++++++//
    public String loginUser(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user != null && !user.getUsername().isEmpty() && user.getUsername().equals(username)) {
            return "ok";
        } else {
            return "fail";
        }
    }

    //---------------------------------------//
    public User getUserStatus(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user != null && !user.getUsername().isEmpty()) {
            return user;
        } else {
            user = new User();
            return user;
        }
    }

    //+++++++++++++++++++++++++++++++++++++++//
    public List<User> getAllUser(String username){
        List<User> userList = userRepository.findAll();
        List<User> tempList ;
        if (userList!=null){
            tempList=userList;
            for (int i=0; i<tempList.size(); i++){
                System.out.println("for içerisişi");
                if (tempList.get(i).getUsername().contentEquals(username)){
                    tempList.remove(i);
                }
            }
            return tempList;
        }else {
            System.out.println("else");
            userList= new ArrayList<>();
            return userList;
        }
    }

}
