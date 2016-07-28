package com.example.aishwarya.facebookintegrating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;

public class MainActivity extends AppCompatActivity implements UserInfoListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginWithFacebbok = (Button) findViewById(R.id.login);
        mCallbackManager = CallbackManager.Factory.create();
        if (loginWithFacebbok != null) {
            loginWithFacebbok.setOnClickListener(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSocialCallBack(SocialResultStatus socialResultStatus, UserInfo userInfo) {
        if (socialResultStatus == SocialResultStatus.LOGIN_SUCCESS) {
            if (userInfo != null) {
                Log.d(TAG, "Name:" +userInfo.getName());
                Log.d(TAG, "Email:" +userInfo.getEmailId());
                Log.d(TAG, "Birthday:" +userInfo.getBirthday());
                Log.d(TAG, "ProfilePicUrl:" +userInfo.getProfilePicUrl());
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra(LoginConstants.KEY_USER_INFO, userInfo);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                fetchUserInfoFromFacebook();
                break;
        }
    }

    private void fetchUserInfoFromFacebook() {
        FacebookApp.getInstance().initFacebookSdk(getApplicationContext());
        FacebookApp.getInstance().loginWithReadPermission(MainActivity.this);
        FacebookApp.getInstance().setUserInfoListener(MainActivity.this);
        FacebookApp.getInstance().registerCallBack(mCallbackManager);
    }
}
