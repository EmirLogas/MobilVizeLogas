package com.example.embatu;

public class PostsClassHomePage {
    String user_ID,post_Text;

    public PostsClassHomePage(String user_ID, String post_Text) {
        this.user_ID = user_ID;
        this.post_Text = post_Text;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getPost_Text() {
        return post_Text;
    }

    public void setPost_Text(String post_Text) {
        this.post_Text = post_Text;
    }
}
