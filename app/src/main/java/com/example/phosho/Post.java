package com.example.phosho;

import android.widget.EditText;

public class Post {

    private String postid;
    private String myUrl;
    private EditText description;
    private String userid;

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getMyUrl() {
        return myUrl;
    }

    public void setMyUrl(String myUrl) {
        this.myUrl = myUrl;
    }

    public EditText getDescription() {
        return description;
    }

    public void setDescription(EditText description) {
        this.description = description;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
