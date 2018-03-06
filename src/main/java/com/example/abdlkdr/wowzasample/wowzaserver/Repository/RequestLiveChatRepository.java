package com.example.abdlkdr.wowzasample.wowzaserver.Repository;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.RequestLiveChat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestLiveChatRepository extends MongoRepository<RequestLiveChat,String> {

    RequestLiveChat findByUserAndToUser(String user,String toUser);

    RequestLiveChat findByToUser(String toUser);

}
