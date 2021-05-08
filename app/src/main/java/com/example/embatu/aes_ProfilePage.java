package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class aes_ProfilePage extends AppCompatActivity {

    TextView textView, txt_UserBio_ProfilePage, aes_txt_FollowersCount_ProfilePage, aes_txt_FollowingCount_ProfilePage;
    ImageView aes_img_ProfileImage_ProfilePage;
    String userID, userBio, targetUser_ID, followerCount, followingCount;
    View aes_img_Edit_ProfilePage;
    boolean isMyProfile;
    boolean followingProfile;
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
        String ip = "192.168.1.106";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        getSupportActionBar().hide();
        aes_img_ProfileImage_ProfilePage = findViewById(R.id.aes_img_ProfileImage_ProfilePage);
        aes_txt_FollowersCount_ProfilePage = findViewById(R.id.aes_txt_FollowersCount_ProfilePage);
        aes_txt_FollowingCount_ProfilePage = findViewById(R.id.aes_txt_FollowingCount_ProfilePage);
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
            userID = userID.toLowerCase();

            if (isMyProfile == false) {
                aes_img_Edit_ProfilePage.setVisibility(ImageView.INVISIBLE);
                aes_btn_Follow_ProfilePage.setVisibility(Button.VISIBLE);
                textView.setText(targetUser_ID);
            }
            if (isMyProfile == true) {
                aes_img_Edit_ProfilePage.setVisibility(ImageView.VISIBLE);
                aes_btn_Follow_ProfilePage.setVisibility(Button.INVISIBLE);
                textView.setText(userID);
            }
        }

        try {
            resultSet = statement.executeQuery("SELECT user_Pic FROM UsersProfiles WHERE user_ID = '" + targetUser_ID + "';");
            while (resultSet.next()) {
                if (resultSet.getBytes("user_Pic") != null) {
                    String image = resultSet.getString("user_Pic");
                    byte[] aes_pic = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(aes_pic, 0, aes_pic.length);
                    aes_img_ProfileImage_ProfilePage.setImageBitmap(bmp);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        checkFollowCounts();

        if (isMyProfile == true) {
            try {
                resultSet = statement.executeQuery("SELECT user_Bio FROM UsersProfiles WHERE user_ID = '" + userID + "';");
                while (resultSet.next()) {
                    userBio = resultSet.getString("user_Bio").trim();
                    txt_UserBio_ProfilePage.setText(userBio);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                resultSet = statement.executeQuery("SELECT user_Bio FROM UsersProfiles WHERE user_ID = '" + targetUser_ID + "';");
                while (resultSet.next()) {
                    userBio = resultSet.getString("user_Bio").trim();
                    txt_UserBio_ProfilePage.setText(userBio);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (isMyProfile == false) {
            try {
                resultSet = statement.executeQuery("SELECT following_UserID,followed_UserID FROM Follow WHERE following_UserID = '" + userID + "' AND followed_UserID='" + targetUser_ID + "';");
                if (resultSet.next()) {
                    aes_btn_Follow_ProfilePage.setText("Takip ediliyor");
                    followingProfile = true;
                } else {
                    aes_btn_Follow_ProfilePage.setText("Takip et");
                    followingProfile = false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void img_Back_profilePage(View view) {
        finish();
    }

    public void img_Edit_ProfilePage(View view) {
        Intent intent = new Intent(this, aes_EditProfilePage.class);
        intent.putExtra("user_ID", userID);
        startActivity(intent);
    }

    public void aes_btn_Follow_ProfilePage(View view) {
        if (followingProfile == false) {
            try {
                statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO Follow (following_UserID, followed_UserID) VALUES ('" + userID + "','" + targetUser_ID + "');");
                aes_btn_Follow_ProfilePage.setText("Takip ediliyor");
                followingProfile = true;
                //notification_RegisterPage.setTextColor(Color.parseColor("#16c79a"));
                //notification_RegisterPage.setText("Kayıt tamamlandı!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Log.e("ASK", throwables.getMessage());
                //notification_RegisterPage.setTextColor(Color.parseColor("#fb3640"));
                //notification_RegisterPage.setText("Kayıt tamamlanamadı!");
            }
        } else {
            try {
                statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM Follow WHERE following_UserID='" + userID + "' AND followed_UserID='" + targetUser_ID + "';");
                aes_btn_Follow_ProfilePage.setText("Takip et");
                followingProfile = false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Log.e("ASK", throwables.getMessage());
            }
        }
        checkFollowCounts();
    }

    void checkFollowCounts() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.106";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            resultSet = statement.executeQuery("SELECT COUNT(*) AS int FROM Follow WHERE followed_UserID='" + targetUser_ID + "'");
            while (resultSet.next()) {
                followerCount = String.valueOf(resultSet.getInt("int"));
                aes_txt_FollowersCount_ProfilePage.setText(followerCount);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("ASK", throwables.getMessage());
        }

        //Following
        try {
            resultSet = statement.executeQuery("SELECT COUNT(*) AS int FROM Follow WHERE following_UserID='" + targetUser_ID + "'");
            while (resultSet.next()) {
                followingCount = String.valueOf(resultSet.getInt("int"));
                aes_txt_FollowingCount_ProfilePage.setText(followingCount);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("ASK", throwables.getMessage());
        }
    }
}