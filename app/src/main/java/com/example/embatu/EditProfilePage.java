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

public class EditProfilePage extends AppCompatActivity {

    String userID, userBio;
    TextView txt_UserID_EditProfilePage, notification_EditProfilePage;
    EditText etxt_Bio_EditProfilePage;

    Connection connection;
    Statement statement;
    ResultSet resultSet;

    //did he have a biography before? variable for information
    boolean hadBioBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_page);

        getSupportActionBar().hide();

        txt_UserID_EditProfilePage = findViewById(R.id.aes_txt_UserID_EditProfilePage);
        etxt_Bio_EditProfilePage = findViewById(R.id.aes_etxt_Bio_EditProfilePage);
        notification_EditProfilePage = findViewById(R.id.aes_notification_EditProfilePage);

        //take user_ID before activity
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            String j = (String) b.get("user_ID");
            userID = j;
        }

        txt_UserID_EditProfilePage.setText(userID);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.102";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            notification_EditProfilePage.setTextColor(Color.parseColor("#fb3640"));
            notification_EditProfilePage.setText("Bağlantı Hatası!");
            Log.e("ASK", throwables.getMessage());
        }

        try {
            resultSet = statement.executeQuery("SELECT user_Bio FROM UsersProfiles WHERE user_ID = '" + userID + "';");
            while (resultSet.next()) {
                userBio = resultSet.getString("user_Bio").trim();
                etxt_Bio_EditProfilePage.setText(userBio);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void img_Back_EditProfilePage(View view) {
        finish();
    }

    public void btn_Save_EditProfilePage(View view) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.102";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            notification_EditProfilePage.setTextColor(Color.parseColor("#fb3640"));
            notification_EditProfilePage.setText("Bağlantı Hatası!");
            Log.e("ASK", throwables.getMessage());
        }

        if (etxt_Bio_EditProfilePage.getText().toString().trim().length() > 50) {
            notification_EditProfilePage.setText("Biografi 50 karakteri geçemez");
        } else {
            userBio = etxt_Bio_EditProfilePage.getText().toString().trim();
            try {
                resultSet = statement.executeQuery("SELECT COUNT(*) AS int FROM UsersProfiles WHERE User_ID='" + userID + "';");
                while (resultSet.next()) {
                    if (resultSet.getInt("int") > 0) {
                        try {
                            statement.executeUpdate("UPDATE UsersProfiles SET user_Bio = '" + userBio + "' WHERE user_ID = '" + userID + "';");
                            hadBioBefore = true;
                            notification_EditProfilePage.setTextColor(Color.parseColor("#16c79a"));
                            notification_EditProfilePage.setText("Bilgiler Güncellendi");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        hadBioBefore = false;
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (hadBioBefore == false) {
                try {
                    statement = connection.createStatement();
                    statement.executeUpdate("INSERT INTO UsersProfiles (user_ID,user_Bio) VALUES ('" + userID + "','" + userBio + "');");
                    notification_EditProfilePage.setTextColor(Color.parseColor("#16c79a"));
                    notification_EditProfilePage.setText("Bilgiler Güncellendi");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Log.e("ASK", throwables.getMessage());
                    notification_EditProfilePage.setTextColor(Color.parseColor("#fb3640"));
                    notification_EditProfilePage.setText("Bilgiler Güncellenemedi!");
                }
            }
        }
    }
}