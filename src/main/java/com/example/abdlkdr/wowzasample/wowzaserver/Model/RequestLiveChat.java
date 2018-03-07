package com.example.abdlkdr.wowzasample.wowzaserver.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "RequestLiveChat")
public class RequestLiveChat {

    @Id
    private String id;
    //Programda kişiyi seçen kişi
    private User user;
    //Seçilen kişi ve konuşmak için aradığı kişi
    private User toUser;
    //Konuşma hala devam ediyor mu diye status tutuyoruz
    private String status;
    //Live chat'in url oluşturuyoruz
    private String liveChatUrl;
    //İsteği kabul edip etmediğini kontrol ediyoruz  //Yes or No
    private boolean isAccepted;

    public RequestLiveChat() {
    }

    public RequestLiveChat(String id, User user, User toUser, String status, String liveChatUrl, boolean isAccepted) {
        this.id = id;
        this.user = user;
        this.toUser = toUser;
        this.status = status;
        this.liveChatUrl = liveChatUrl;
        this.isAccepted = isAccepted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLiveChatUrl() {
        return liveChatUrl;
    }

    public void setLiveChatUrl(String liveChatUrl) {
        this.liveChatUrl = liveChatUrl;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
