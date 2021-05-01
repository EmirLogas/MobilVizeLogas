package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    TextView txt_Embatu_HomePage, txt_HomePage_HomePage, txt_Profile_HomePage, txt_Settings_HomePage, txt_Exit_HomePage;
    ImageView img_EmbatuImage_HomePage, img_Menu_HomePage, img_Exit_HomePage;
    String userID;

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

        img_Menu_HomePage = findViewById(R.id.aes_img_Menu_HomePage);
        img_Exit_HomePage = findViewById(R.id.aes_img_Exit_HomePage);


    }

    public void btn_Menu_HomePage(View view) {
        txt_Embatu_HomePage.setVisibility(View.VISIBLE);
        img_EmbatuImage_HomePage.setVisibility(View.VISIBLE);
        txt_HomePage_HomePage.setVisibility(View.VISIBLE);
        txt_Profile_HomePage.setVisibility(View.VISIBLE);
        txt_Settings_HomePage.setVisibility(View.VISIBLE);
        txt_Exit_HomePage.setVisibility(View.VISIBLE);

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

        img_Menu_HomePage.setVisibility(View.VISIBLE);
        img_Exit_HomePage.setVisibility(View.INVISIBLE);
    }

    public void txt_Profile_HomePage(View view) {
        Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("user_ID", userID);
        startActivity(intent);
    }

    public void txt_Exit_HomePage(View view) {
        Intent intent = new Intent(this, MainPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}