package com.roundlers.mytemplate.models;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.roundlers.mytemplate.base.BaseModel;
import com.roundlers.mytemplate.constants.Constants;

@Entity(primaryKeys = {"userId"})
public class User implements Parcelable, Comparable, BaseModel {
    private String userName;

    @NonNull
    @SerializedName("userid")
    private String userId;

    private String emailId;
    private int verficationCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getVerficationCode() {
        return verficationCode;
    }

    public void setVerficationCode(int verficationCode) {
        this.verficationCode = verficationCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int getModelType() {
        return Constants.ModelType.USER;
    }

    @Override
    public int compareTo(Object o) {
        if (o != null && o instanceof User) {
            User user = (User) o;
            return userName.compareTo(user.userName);
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User))
            return false;
        User user = (User) o;
        if (userId != null && user.getUserId() != null) {
            return user.getUserId().equalsIgnoreCase(userId);
        }
        return false;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int ii) {
        dest.writeString(userId);
    }

    private User(Parcel in) {
        userId = in.readString();
    }

    public User() {

    }
}
