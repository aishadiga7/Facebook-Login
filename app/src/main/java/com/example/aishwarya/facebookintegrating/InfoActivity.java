package com.example.aishwarya.facebookintegrating;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        TextView birthday = (TextView) findViewById(R.id.birthday);
        ImageView profilePic = (ImageView) findViewById(R.id.profile_pic);
        if (getIntent() != null) {
            UserInfo userInfo = getIntent().getParcelableExtra(LoginConstants.KEY_USER_INFO);
            name.setText(userInfo.getName());
            email.setText(userInfo.getEmailId());
            birthday.setText(userInfo.getBirthday());
            Picasso.with(InfoActivity.this).load(userInfo.getProfilePicUrl()).into(profilePic);
        }
    }
}
