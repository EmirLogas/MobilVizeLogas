package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class aes_RegisterPage extends AppCompatActivity {

    String userID, userPass, userEmail;
    EditText etxt_ID_RegisterPage, etxt_Pass_RegisterPage, etxt_Mail_RegisterPage;
    TextView notification_RegisterPage;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    CheckBox aes_cBox_Agree_RegisterPage;

    boolean User_ID_Taked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        getSupportActionBar().hide();


        etxt_ID_RegisterPage = findViewById(R.id.aes_etxt_ID_RegisterPage);
        etxt_Pass_RegisterPage = findViewById(R.id.aes_etxt_Pass_RegisterPage);
        etxt_Mail_RegisterPage = findViewById(R.id.aes_etxt_Mail_RegisterPage);
        notification_RegisterPage = findViewById(R.id.aes_notification_RegisterPage);
        aes_cBox_Agree_RegisterPage = findViewById(R.id.aes_cBox_Agree_RegisterPage);
    }

    public void btn_Register_RegisterPage(View view) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.106";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            notification_RegisterPage.setTextColor(Color.parseColor("#fb3640"));
            notification_RegisterPage.setText("Bağlantı Hatası!");
            Log.e("ASK", throwables.getMessage());
        }

        if (aes_cBox_Agree_RegisterPage.isChecked()==true){
            if (etxt_ID_RegisterPage.getText().toString().trim().length() <= 0 || etxt_Pass_RegisterPage.getText().toString().trim().length() <= 0 || etxt_Mail_RegisterPage.getText().toString().trim().length() <= 0) {
                notification_RegisterPage.setTextColor(Color.parseColor("#fb3640"));
                notification_RegisterPage.setText("Bilgiler Eksiksiz Girilmeli!");
            } else {
                userID = etxt_ID_RegisterPage.getText().toString();
                userPass = etxt_Pass_RegisterPage.getText().toString();
                userEmail = etxt_Mail_RegisterPage.getText().toString();
                if (isValidEmail(userEmail) == false) {
                    notification_RegisterPage.setTextColor(Color.parseColor("#fb3640"));
                    notification_RegisterPage.setText("Geçersiz Email adresi!");
                } else {
                    try {
                        resultSet = statement.executeQuery("SELECT COUNT(User_ID) AS int FROM Users WHERE User_ID='" + userID + "';");
                        while (resultSet.next()) {
                            if (resultSet.getInt("int") > 0) {
                                notification_RegisterPage.setTextColor(Color.parseColor("#fb3640"));
                                notification_RegisterPage.setText("Kullanıcı adı kullanımda!");
                                User_ID_Taked = true;
                            } else {
                                User_ID_Taked = false;
                            }
                        }
                        resultSet = null;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        notification_RegisterPage.setTextColor(Color.parseColor("#fb3640"));
                        notification_RegisterPage.setText("ExecuteQuery Hatası");
                        Log.e("ASK", throwables.getMessage());
                    }
                    if (User_ID_Taked == false) {
                        try {
                            statement = connection.createStatement();
                            statement.executeUpdate("INSERT INTO Users (user_ID, user_Pass, user_Email)" + " VALUES ('" + userID + "','" + userPass + "','" + userEmail + "');");
                            notification_RegisterPage.setTextColor(Color.parseColor("#16c79a"));
                            notification_RegisterPage.setText("Kayıt tamamlandı!");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            Log.e("ASK", throwables.getMessage());
                            notification_RegisterPage.setTextColor(Color.parseColor("#fb3640"));
                            notification_RegisterPage.setText("Kayıt tamamlanamadı!");
                        }
                    }
                }
            }
        }
        else {
            notification_RegisterPage.setTextColor(Color.parseColor("#fb3640"));
            notification_RegisterPage.setText("Sözleşme kabul edilmeli!");
        }

    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void btn_Back_RegisterPage(View view) {
        Intent intent = new Intent(this, aes_MainPage.class);
        startActivity(intent);
    }
}