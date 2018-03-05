package com.example.abdlkdr.wowzasample.wowzaserver.Repository;

import com.example.abdlkdr.wowzasample.wowzaserver.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User getUserByUsername(String username);
}
