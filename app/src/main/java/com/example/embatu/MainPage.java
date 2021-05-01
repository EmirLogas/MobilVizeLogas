package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        getSupportActionBar().hide();
    }

    public void btn_Register_MainPage(View view) {
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }

    public void btn_Login_MainPage(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}