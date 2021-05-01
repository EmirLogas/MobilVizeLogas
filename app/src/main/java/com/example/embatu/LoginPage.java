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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginPage extends AppCompatActivity {


    String userID, userPass;
    EditText etxt_ID_LoginPage, etxt_Pass_LoginPage;
    TextView notification_LoginPage;
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        getSupportActionBar().hide();

        etxt_ID_LoginPage = findViewById(R.id.aes_etxt_ID_LoginPage);
        etxt_Pass_LoginPage = findViewById(R.id.aes_etxt_Pass_LoginPage);
        notification_LoginPage = findViewById(R.id.aes_notification_LoginPage);
    }

    public void btn_Back_LoginPage(View view) {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    public void btn_Login_LoginPage(View view) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.102";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            notification_LoginPage.setTextColor(Color.parseColor("#fb3640"));
            notification_LoginPage.setText("Bağlantı Hatası!");
            Log.e("ASK", throwables.getMessage());
        }


        if (etxt_ID_LoginPage.getText().toString().trim().length() <= 0 || etxt_Pass_LoginPage.getText().toString().trim().length() <= 0) {
            notification_LoginPage.setText("Bilgiler boş bırakılamaz!");
            notification_LoginPage.setTextColor(Color.parseColor("#fb3640"));
        } else {
            userID = etxt_ID_LoginPage.getText().toString();
            userPass = etxt_Pass_LoginPage.getText().toString();

            try {
                resultSet = statement.executeQuery("SELECT COUNT(*) AS int FROM Users WHERE User_ID='" + userID + "' AND User_Pass='" + userPass + "' COLLATE SQL_Latin1_General_CP1_CS_AS;");
                while (resultSet.next()) {
                    if (resultSet.getInt("int") <= 0) {
                        notification_LoginPage.setTextColor(Color.parseColor("#fb3640"));
                        notification_LoginPage.setText("Kullanıcı adı veya şifre yanlış!");
                    } else {
                        Intent intent = new Intent(this, HomePage.class);
                        intent.putExtra("user_ID", userID);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                resultSet = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                notification_LoginPage.setTextColor(Color.parseColor("#fb3640"));
                notification_LoginPage.setText("ExecuteQuery Hatası");
                Log.e("ASK", throwables.getMessage());
            }
        }
    }
}