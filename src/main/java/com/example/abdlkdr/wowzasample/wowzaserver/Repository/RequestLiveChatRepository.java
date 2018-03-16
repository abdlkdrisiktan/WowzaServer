package com.example.abdlkdr.wowzasample.wowzaserver.Repository;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.RequestLiveChat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestLiveChatRepository extends MongoRepository<RequestLiveChat,String> {

    RequestLiveChat findByUserUsernameAndToUserUsername(String user,String toUser);

    RequestLiveChat findByToUser(String toUser);

    RequestLiveChat findByToUserUsername(String toUsername);

    RequestLiveChat findByUserAndToUser (String user, String toUser);

    RequestLiveChat findByUserUsername(String user);

}
