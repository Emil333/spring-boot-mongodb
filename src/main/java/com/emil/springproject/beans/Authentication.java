package com.emil.springproject.beans;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "authentication")
public class Authentication {

    @Id
    private String token;

    private String userId;

    @Indexed(expireAfterSeconds = 86400)
    private LocalDateTime loginTime;

    public Authentication(String userId, LocalDateTime createdOn) {
        this.userId = userId;
        this.loginTime = createdOn;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
