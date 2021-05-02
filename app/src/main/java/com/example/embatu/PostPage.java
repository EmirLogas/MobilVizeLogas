package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostPage extends AppCompatActivity {


    Connection connection;
    Statement statement;

    TextView aes_notification_PostPage;
    EditText aes_etxt_LogText_PostPage;
    String aes_Log, aes_userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            String j = (String) b.get("user_ID");
            aes_userID = j;
        }

        aes_notification_PostPage = findViewById(R.id.aes_notification_PostPage);
        aes_etxt_LogText_PostPage = findViewById(R.id.aes_etxt_LogText_PostPage);
    }

    public void aes_img_Back_PostPage(View view) {
        finish();
    }

    public void aes_btn_Post_PostPage(View view) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.102";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            aes_notification_PostPage.setTextColor(Color.parseColor("#fb3640"));
            aes_notification_PostPage.setText("Bağlantı Hatası!");
            Log.e("ASK", throwables.getMessage());
        }

        if (aes_etxt_LogText_PostPage.getText().toString().trim().length() > 300 && aes_etxt_LogText_PostPage.getText().toString().trim().length() <= 0) {
            aes_notification_PostPage.setText("Log 1 karakterden küçük, 300 karakterden fazla olamaz!");
        } else {
            aes_Log = aes_etxt_LogText_PostPage.getText().toString().trim();
            try {
                statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO UsersPosts (user_ID,post_Text) VALUES ('" + aes_userID + "','" + aes_Log + "');");
                aes_notification_PostPage.setTextColor(Color.parseColor("#16c79a"));
                aes_notification_PostPage.setText("Loglama başarılı");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Log.e("ASK", throwables.getMessage());
                aes_notification_PostPage.setTextColor(Color.parseColor("#fb3640"));
                aes_notification_PostPage.setText("Loglama başarısız!");
            }
        }
    }
}