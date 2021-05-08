package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class aes_HomePage extends AppCompatActivity {

    TextView txt_Embatu_HomePage, txt_HomePage_HomePage, txt_Profile_HomePage, txt_Settings_HomePage, txt_Exit_HomePage, aes_txt_SearchPage_HomePage;
    ImageView img_EmbatuImage_HomePage, img_Menu_HomePage, img_Exit_HomePage;
    String userID;

    ListView aes_ListV_HomePage;
    //ArrayAdapter arrayAdapter;
    String aes_FollowingItems;
    String aes_PostItems;

    Connection connection;
    Statement statement;
    ResultSet resultSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            String j = (String) b.get("user_ID");
            userID = j;
        }

        txt_Embatu_HomePage = findViewById(R.id.aes_txt_Logas_HomePage);
        img_EmbatuImage_HomePage = findViewById(R.id.aes_img_LogasImage_HomePage);
        txt_HomePage_HomePage = findViewById(R.id.aes_txt_HomePage_HomePage);
        txt_Profile_HomePage = findViewById(R.id.aes_txt_Profile_HomePage);
        txt_Settings_HomePage = findViewById(R.id.aes_txt_Settings_HomePage);
        txt_Exit_HomePage = findViewById(R.id.aes_txt_Exit_HomePage);
        aes_txt_SearchPage_HomePage = findViewById(R.id.aes_txt_SearchPage_HomePage);
        aes_ListV_HomePage = findViewById(R.id.aes_ListV_HomePage);

        img_Menu_HomePage = findViewById(R.id.aes_img_Menu_HomePage);
        img_Exit_HomePage = findViewById(R.id.aes_img_Exit_HomePage);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.106";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("ASK", throwables.getMessage());
        }

        ArrayList followingUsersList = new ArrayList();

        try {
            resultSet = statement.executeQuery("SELECT followed_UserID FROM Follow WHERE following_UserID='" + userID + "';");
            followingUsersList.clear();
            while (resultSet.next()) {
                aes_FollowingItems = resultSet.getString("followed_UserID").trim();
                followingUsersList.add(aes_FollowingItems);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ArrayList followingUsersPost = new ArrayList();
        ArrayList newfollowingUsersPost = new ArrayList();
        aes_PostListAdapter adapter = null;


        for (int i = 0; i < followingUsersList.size(); i++) {
            try {
                resultSet = statement.executeQuery("SELECT post_Text FROM UsersPosts WHERE User_ID='" + followingUsersList.get(i).toString() + "';");
                ArrayList followingUsersPosts = new ArrayList();
                followingUsersPosts.clear();
                while (resultSet.next()) {
                    aes_PostItems = resultSet.getString("post_Text").trim();
                    aes_PostsClassHomePage aesPostsClassHomePage = new aes_PostsClassHomePage(followingUsersList.get(i).toString(), aes_PostItems);
                    newfollowingUsersPost.add(aesPostsClassHomePage);
                    adapter = new aes_PostListAdapter(this, R.layout.home_page_post_view, newfollowingUsersPost);
                    followingUsersPost.add(aes_PostItems);
                }
                //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, followingUsersPost);
                aes_ListV_HomePage.setAdapter(adapter);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void btn_Menu_HomePage(View view) {
        txt_Embatu_HomePage.setVisibility(View.VISIBLE);
        img_EmbatuImage_HomePage.setVisibility(View.VISIBLE);
        txt_HomePage_HomePage.setVisibility(View.VISIBLE);
        txt_Profile_HomePage.setVisibility(View.VISIBLE);
        txt_Settings_HomePage.setVisibility(View.VISIBLE);
        txt_Exit_HomePage.setVisibility(View.VISIBLE);
        aes_txt_SearchPage_HomePage.setVisibility(view.VISIBLE);

        aes_ListV_HomePage.setVisibility(ListView.INVISIBLE);

        img_Menu_HomePage.setVisibility(View.INVISIBLE);
        img_Exit_HomePage.setVisibility(View.VISIBLE);
    }

    public void img_Exit_HomePage(View view) {
        txt_Embatu_HomePage.setVisibility(View.INVISIBLE);
        img_EmbatuImage_HomePage.setVisibility(View.INVISIBLE);
        txt_HomePage_HomePage.setVisibility(View.INVISIBLE);
        txt_Profile_HomePage.setVisibility(View.INVISIBLE);
        txt_Settings_HomePage.setVisibility(View.INVISIBLE);
        txt_Exit_HomePage.setVisibility(View.INVISIBLE);
        aes_txt_SearchPage_HomePage.setVisibility(view.INVISIBLE);

        aes_ListV_HomePage.setVisibility(ListView.VISIBLE);

        img_Menu_HomePage.setVisibility(View.VISIBLE);
        img_Exit_HomePage.setVisibility(View.INVISIBLE);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.106";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("ASK", throwables.getMessage());
        }

        ArrayList followingUsersList = new ArrayList();

        try {
            resultSet = statement.executeQuery("SELECT followed_UserID FROM Follow WHERE following_UserID='" + userID + "';");
            followingUsersList.clear();
            while (resultSet.next()) {
                aes_FollowingItems = resultSet.getString("followed_UserID").trim();
                followingUsersList.add(aes_FollowingItems);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ArrayList followingUsersPost = new ArrayList();
        ArrayList newfollowingUsersPost = new ArrayList();
        aes_PostListAdapter adapter = null;


        for (int i = 0; i < followingUsersList.size(); i++) {
            try {
                resultSet = statement.executeQuery("SELECT post_Text FROM UsersPosts WHERE User_ID='" + followingUsersList.get(i).toString() + "';");
                ArrayList followingUsersPosts = new ArrayList();
                followingUsersPosts.clear();
                while (resultSet.next()) {
                    aes_PostItems = resultSet.getString("post_Text").trim();
                    aes_PostsClassHomePage aesPostsClassHomePage = new aes_PostsClassHomePage(followingUsersList.get(i).toString(), aes_PostItems);
                    newfollowingUsersPost.add(aesPostsClassHomePage);
                    adapter = new aes_PostListAdapter(this, R.layout.home_page_post_view, newfollowingUsersPost);
                    followingUsersPost.add(aes_PostItems);
                }
                //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, followingUsersPost);
                aes_ListV_HomePage.setAdapter(adapter);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void txt_Profile_HomePage(View view) {
        Intent intent = new Intent(this, aes_ProfilePage.class);
        intent.putExtra("isMyProfile", true);
        intent.putExtra("user_ID", userID);
        intent.putExtra("targetUser_ID", userID);
        startActivity(intent);
    }

    public void txt_Exit_HomePage(View view) {
        Intent intent = new Intent(this, aes_MainPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void aes_img_AddPost_HomePage(View view) {
        Intent intent = new Intent(this, aes_PostPage.class);
        intent.putExtra("user_ID", userID);
        startActivity(intent);
    }

    public void aes_txt_SearchPage_HomePage(View view) {
        Intent intent = new Intent(this, aes_SearchPage.class);
        intent.putExtra("user_ID", userID);
        startActivity(intent);
    }
}