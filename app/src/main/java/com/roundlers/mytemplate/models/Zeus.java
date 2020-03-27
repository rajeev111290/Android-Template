package com.roundlers.mytemplate.models;

import com.google.gson.JsonObject;

/**
 * Created by vipulthakur on 04/12/17.
 */

public class Zeus extends Exception {


    /**
     *  old error handle check
     */
    private int errorCode;
    /**
     *  for handling error code of new API endpoints.
     */
    private String newCode;


    /**
     *  Addition data if any with the error
     */
    private JsonObject payload;

    public Zeus(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Zeus(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public void setPayload(JsonObject payload) {
        this.payload = payload;
    }

    public JsonObject getPayload() {
        return payload;
    }

    public static class Builder{

        private int errorCode;
        private String newCode;
        private JsonObject payload;
        private String message;

        public Builder(String message){
            this.message=message;
            errorCode=-777;
        }

        public Builder setErrorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder setNewCode(String newCode) {
            this.newCode = newCode;
            return this;
        }

        public Builder setPayload(JsonObject payload) {
            this.payload = payload;
            return this;
        }

        public Zeus build(){

            Zeus errorObject=new Zeus(message);
            errorObject.setNewCode(newCode);
            errorObject.setErrorCode(errorCode);
            errorObject.setPayload(payload);
            return errorObject;
        }
    }



}
