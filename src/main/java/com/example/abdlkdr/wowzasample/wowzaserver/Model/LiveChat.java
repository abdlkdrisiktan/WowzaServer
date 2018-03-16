package com.example.abdlkdr.wowzasample.wowzaserver.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LiveChat")
public class LiveChat {

    @Id
    private String id;
    private String user;
    private String isClick;

    public LiveChat(String id, String user, String isClick) {
        this.id = id;
        this.user = user;
        this.isClick = isClick;
    }

    public LiveChat() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIsClick() {
        return isClick;
    }

    public void setIsClick(String isClick) {
        this.isClick = isClick;
    }
}
