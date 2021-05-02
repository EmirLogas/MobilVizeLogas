package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfilePage extends AppCompatActivity {

    TextView textView, txt_UserBio_ProfilePage;
    String userID, userBio, targetUser_ID;
    View aes_img_Edit_ProfilePage;
    boolean isMyProfile;
    Button aes_btn_Follow_ProfilePage;


    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
    }

    @Override
    protected void onStart() {
        super.onStart();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.102";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        getSupportActionBar().hide();

        aes_btn_Follow_ProfilePage = findViewById(R.id.aes_btn_Follow_ProfilePage);
        textView = findViewById(R.id.aes_txt_UserID_ProfilePage);
        txt_UserBio_ProfilePage = findViewById(R.id.aes_txt_UserBio_ProfilePage);
        aes_img_Edit_ProfilePage = findViewById(R.id.aes_img_Edit_ProfilePage);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            isMyProfile = (boolean) bundle.get("isMyProfile");
            targetUser_ID = (String) bundle.get("targetUser_ID");
            userID = (String) bundle.get("user_ID");

            if (isMyProfile == false) {
                aes_img_Edit_ProfilePage.setVisibility(ImageView.INVISIBLE);
                aes_btn_Follow_ProfilePage.setVisibility(Button.VISIBLE);
                isMyProfile = false;
                textView.setText(targetUser_ID);
            }
            if (isMyProfile == true) {
                aes_img_Edit_ProfilePage.setVisibility(ImageView.VISIBLE);
                aes_btn_Follow_ProfilePage.setVisibility(Button.INVISIBLE);
                isMyProfile = true;
                textView.setText(userID);
            }
        }

        try {
            resultSet = statement.executeQuery("SELECT user_Bio FROM UsersProfiles WHERE user_ID = '" + userID + "';");
            while (resultSet.next()) {
                userBio = resultSet.getString("user_Bio").trim();
                txt_UserBio_ProfilePage.setText(userBio);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void img_Back_profilePage(View view) {
        finish();
    }

    public void img_Edit_ProfilePage(View view) {
        Intent intent = new Intent(this, EditProfilePage.class);
        intent.putExtra("user_ID", userID);
        startActivity(intent);
    }
}