package com.example.aishwarya.facebookintegrating;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by aishwarya on 27/7/16.
 */
public class FacebookApp {
    private static final String TAG = FacebookApp.class.getSimpleName();
    private static final String FB_NAME = "name";
    private static final String FB_EMAILID = "email";
    private static final String FB_BIRTHDAY = "birthday";
    public static final String FACEBOOK_GRAPH_URL = "https://graph.facebook.com/";
    public static final String FB_ID = "id";
    public static final String FACEBOOK_PROFILE_PIC_END_URL = "/picture?type=large";
    private UserInfoListener mUserInfoListener;
    private static FacebookApp mFacebookApp;

    private FacebookApp() {

    }

    public static FacebookApp getInstance() {
        if (mFacebookApp == null) {
            mFacebookApp = new FacebookApp();
        }
        return mFacebookApp;
    }

    public void setUserInfoListener(UserInfoListener userInfoListener) {
        mUserInfoListener = userInfoListener;
        Log.d(TAG, "setUserInfoListener()");

    }

    public void loginWithReadPermission(Context context) {
        LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList("public_profile", "user_friends", "email"));
        Log.d(TAG, "loginWithReadPermission()");
    }

    public void initFacebookSdk(Context applicationContext) {
        FacebookSdk.sdkInitialize(applicationContext);
    }

    public void registerCallBack(CallbackManager callbackManager) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess --------" + loginResult.getAccessToken());
                Log.d(TAG, "Token--------" + loginResult.getAccessToken().getToken());
                Log.d(TAG, "Permision--------" + loginResult.getRecentlyGrantedPermissions());
                Log.d(TAG, "OnGraph------------------------");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d(TAG, "GraphResponse-------------" + response.toString());
                                UserInfo userInfo = null;
                                if (object != null) {
                                    userInfo =  getLoggedInUserData(object);
                                    mUserInfoListener.onSocialCallBack(SocialResultStatus.LOGIN_SUCCESS, userInfo);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,gender,birthday,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private UserInfo getLoggedInUserData(JSONObject object) {
        try {
            String name = object.getString(FB_NAME);
            String email = object.getString(FB_EMAILID);
            String birthday = object.getString(FB_BIRTHDAY);
            String fbAppId = object.getString(FB_ID);
            String profilePicUrl = FACEBOOK_GRAPH_URL+fbAppId+ FACEBOOK_PROFILE_PIC_END_URL;
            UserInfo userInfo = new UserInfo();
            userInfo.setName(name);
            userInfo.setEmailId(email);
            userInfo.setBirthday(birthday);
            userInfo.setProfilePicUrl(profilePicUrl);
            return userInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}
