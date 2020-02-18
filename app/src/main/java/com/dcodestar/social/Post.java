package com.dcodestar.social;


import android.provider.Settings;

public class Post{
    String userName;
    String blog;
    String uri;
    String uid;
    String timestamp;

    public Post(String userName, String uri, String blog,String uid) {
        this.userName = userName;
        this.blog = blog;
        this.uid=uid;
        this.uri = uri;
        this.timestamp=String.valueOf(System.currentTimeMillis());
    }
    public Post(){}

    public String getUserName() {
        return userName;
    }

    public String getBlog() {
        return blog;
    }

    public String getUri() {
        return uri;
    }

   public String getUid(){
        return uid;
   }

    public String getTimestamp() {
        return timestamp;
    }
}