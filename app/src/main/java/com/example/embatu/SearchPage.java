package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {


    EditText aes_etxt_Search_SearchPage;

    ListView aes_ListV_SearchPage;
    ArrayAdapter arrayAdapter;

    Connection connection;
    Statement statement;
    ResultSet resultSet;

    String aes_SearchUserID;
    String aes_userID;
    String aes_SelectedItem_UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        getSupportActionBar().hide();

        aes_ListV_SearchPage = findViewById(R.id.aes_ListV_SearchPage);

        //ArrayList aes_Profiles = new ArrayList();
        //ArrayAdapter arrayAdapter=new ArrayAdapter()

        aes_ListV_SearchPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aes_SelectedItem_UserID = ((TextView) view).getText().toString();
                openProfile(aes_SelectedItem_UserID);
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        aes_etxt_Search_SearchPage = findViewById(R.id.aes_etxt_Search_SearchPage);
        aes_etxt_Search_SearchPage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (aes_etxt_Search_SearchPage.getText().toString().trim().length() >= 3) {
                    searchProfiles();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void searchProfiles() {
        aes_SearchUserID = aes_etxt_Search_SearchPage.getText().toString().trim();

        //My localhost Database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.102";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //aes_notification_EditProfilePage.setTextColor(Color.parseColor("#fb3640"));
            //aes_notification_EditProfilePage.setText("Bağlantı Hatası!");
            Log.e("ASK", throwables.getMessage());
        }
        //check Profiles
        try {
            resultSet = statement.executeQuery("SELECT user_ID FROM Users WHERE user_ID LIKE '%" + aes_SearchUserID + "%';");
            ArrayList aes_Profiles = new ArrayList();
            aes_Profiles.clear();
            while (resultSet.next()) {
                aes_userID = resultSet.getString("user_ID").trim();
                aes_Profiles.add(aes_userID);
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, aes_Profiles);
            aes_ListV_SearchPage.setAdapter(arrayAdapter);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void openProfile(String aes_SelectedItem_UserID) {
        Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("isMyProfile", false);
        intent.putExtra("targetUser_ID", aes_SelectedItem_UserID);
        startActivity(intent);
    }

    public void aes_img_Back_SearchPage(View view) {
        finish();
    }
}