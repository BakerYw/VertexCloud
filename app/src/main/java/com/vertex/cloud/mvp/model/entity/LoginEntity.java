package com.vertex.cloud.mvp.model.entity;

/**
 * @Author CHEESE
 * @Date 2021-10-22 22:15
 * @Version 1.0
 * @Describe
 **/
public class LoginEntity {

    /**
     * access_token : 2d0ea332-e089-4c52-b08b-ddafe6d2b397
     * expires_in : 43200
     */

    private String access_token;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
