package com.example.aishwarya.facebookintegrating;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aishwarya on 27/7/16.
 */
public class UserInfo implements Parcelable{
    private String mName;
    private String mEmailId;
    private String mBirthday;
    private String mProfilePicUrl;


    protected UserInfo(Parcel in) {
        mName = in.readString();
        mEmailId = in.readString();
        mBirthday = in.readString();
        mProfilePicUrl = in.readString();
    }

    public UserInfo() {

    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getProfilePicUrl() {
        return mProfilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        mProfilePicUrl = profilePicUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmailId() {
        return mEmailId;
    }

    public void setEmailId(String emailId) {
        mEmailId = emailId;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mEmailId);
        dest.writeString(mBirthday);
        dest.writeString(mProfilePicUrl);
    }
}
