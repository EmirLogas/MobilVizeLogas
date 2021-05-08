package com.example.embatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditProfilePage extends AppCompatActivity {

    String aes_userID, aes_userBio, aes_userGender;
    TextView aes_txt_UserID_EditProfilePage, aes_notification_EditProfilePage;
    EditText aes_etxt_Bio_EditProfilePage;
    ImageView aes_img_ProfileImage_EditProfilePage;
    Spinner aes_Spinner_Gender_EditProfilPage;

    Connection connection;
    Statement statement;
    ResultSet resultSet;

    //did he have a biography before? variable for information
    boolean aes_hadBioBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_page);

        getSupportActionBar().hide();

        aes_txt_UserID_EditProfilePage = findViewById(R.id.aes_txt_UserID_EditProfilePage);
        aes_etxt_Bio_EditProfilePage = findViewById(R.id.aes_etxt_Bio_EditProfilePage);
        aes_notification_EditProfilePage = findViewById(R.id.aes_notification_EditProfilePage);
        aes_img_ProfileImage_EditProfilePage = findViewById(R.id.aes_img_ProfileImage_EditProfilePage);
        aes_Spinner_Gender_EditProfilPage = findViewById(R.id.aes_Spinner_Gender_EditProfilPage);

        //take user_ID before activity
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            String j = (String) b.get("user_ID");
            aes_userID = j;
        }

        aes_txt_UserID_EditProfilePage.setText(aes_userID);


        //My localhost Database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.106";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            aes_notification_EditProfilePage.setTextColor(Color.parseColor("#fb3640"));
            aes_notification_EditProfilePage.setText("Bağlantı Hatası!");
            Log.e("ASK", throwables.getMessage());
        }
        //check if he already had bio and take bio
        try {
            resultSet = statement.executeQuery("SELECT user_Bio,user_Gender,user_Pic FROM UsersProfiles WHERE user_ID = '" + aes_userID + "';");
            while (resultSet.next()) {
                aes_userBio = resultSet.getString("user_Bio").trim();
                aes_userGender = resultSet.getString("user_Gender").trim();
                aes_etxt_Bio_EditProfilePage.setText(aes_userBio);
                if (aes_userGender == null || aes_userGender.equals("Seçilmemiş")) {
                    aes_Spinner_Gender_EditProfilPage.setSelection(0);
                } else if (aes_userGender.equals("Erkek")) {
                    aes_Spinner_Gender_EditProfilPage.setSelection(1);
                } else if (aes_userGender.equals("Kadın")) {
                    aes_Spinner_Gender_EditProfilPage.setSelection(2);
                }
                if (resultSet.getBytes("user_Pic") != null) {
                    String image = resultSet.getString("user_Pic");
                    byte[] aes_pic = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(aes_pic, 0, aes_pic.length);
                    aes_img_ProfileImage_EditProfilePage.setImageBitmap(bmp);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //close edit page
    public void img_Back_EditProfilePage(View view) {
        finish();
    }

    public void btn_Save_EditProfilePage(View view) {
        //Localhost Database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ip = "192.168.1.106";
        String ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";instance=EMIRLOCAL;user=sa;password=964964ae;databasename=Embatu";
        try {
            connection = DriverManager.getConnection(ConnectionURL);
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            aes_notification_EditProfilePage.setTextColor(Color.parseColor("#fb3640"));
            aes_notification_EditProfilePage.setText("Bağlantı Hatası!");
            Log.e("ASK", throwables.getMessage());
        }


        aes_userGender = aes_Spinner_Gender_EditProfilPage.getSelectedItem().toString().trim();

        BitmapDrawable bitmapDrawable = (BitmapDrawable) aes_img_ProfileImage_EditProfilePage.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String image = Base64.encodeToString(bytes, Base64.DEFAULT);


        if (aes_etxt_Bio_EditProfilePage.getText().toString().trim().length() > 50) {
            aes_notification_EditProfilePage.setText("Biyografi 50 karakteri geçemez");
        } else {
            aes_userBio = aes_etxt_Bio_EditProfilePage.getText().toString().trim();
            try {
                resultSet = statement.executeQuery("SELECT COUNT(*) AS int FROM UsersProfiles WHERE User_ID='" + aes_userID + "';");
                while (resultSet.next()) {
                    if (resultSet.getInt("int") > 0) {
                        try {
                            statement.executeUpdate("UPDATE UsersProfiles SET user_Bio = '" + aes_userBio + "' , user_Pic = '" + image + "' , user_Gender = '" + aes_userGender + "' WHERE user_ID = '" + aes_userID + "';");
                            aes_hadBioBefore = true;
                            aes_notification_EditProfilePage.setTextColor(Color.parseColor("#16c79a"));
                            aes_notification_EditProfilePage.setText("Bilgiler Güncellendi");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        aes_hadBioBefore = false;
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (aes_hadBioBefore == false) {
                try {
                    statement = connection.createStatement();
                    statement.executeUpdate("INSERT INTO UsersProfiles (user_ID,user_Bio,user_Pic,user_Gender) VALUES ('" + aes_userID + "','" + aes_userBio + "', " + image + " ,'" + aes_userGender + "');");
                    aes_notification_EditProfilePage.setTextColor(Color.parseColor("#16c79a"));
                    aes_notification_EditProfilePage.setText("Bilgiler Güncellendi");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Log.e("ASK", throwables.getMessage());
                    aes_notification_EditProfilePage.setTextColor(Color.parseColor("#fb3640"));
                    aes_notification_EditProfilePage.setText("Bilgiler Güncellenemedi!");
                }
            }
        }


    }

    public void aes_btn_AddPhoto_EditProfilePage(View view) {
        dispatchTakePictureIntent();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            aes_img_ProfileImage_EditProfilePage.setImageBitmap(imageBitmap);
        }
    }
}