package com.farasatnovruzov.instagramclone.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    public String docId;
    public String email;
    public String postComment;
    public String downloadUrl;
    public String postDate;

    public Post(String docId,String email, String postComment, String downloadUrl,String postDate) {
        this.docId = docId;
        this.email = email;
        this.postComment = postComment;
        this.downloadUrl = downloadUrl;
        this.postDate = postDate;
    }
}
