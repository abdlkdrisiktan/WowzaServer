package com.example.abdlkdr.wowzasample.wowzaserver.Repository;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.LiveChat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LiveChatRepository extends MongoRepository<LiveChat,String > {

    LiveChat findByUser(String user);
}
