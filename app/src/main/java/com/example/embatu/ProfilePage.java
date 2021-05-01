package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfilePage extends AppCompatActivity {

    TextView textView, txt_UserBio_ProfilePage;
    String userID, userBio;

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

        textView = findViewById(R.id.aes_txt_UserID_ProfilePage);
        txt_UserBio_ProfilePage = findViewById(R.id.aes_txt_UserBio_ProfilePage);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            String j = (String) b.get("user_ID");
            userID = j;
        }
        textView.setText(userID);

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