package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    private TextView txtDene;
    private String birlestir;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        txtDene=findViewById(R.id.tvAdSoyad);

        Intent intent = getIntent();
        String ad = intent.getStringExtra("ad");
        String soyad = intent.getStringExtra("soyad");
        //Giriş yapan kullanıcının ad ve soyadını veri tabanından çeker

        birlestir= ad + " " + soyad;
        txtDene.setText(birlestir);
        //Gelen ad ve soyadı birleştirip panelin üst kısmındaki label'a yazar


    }

    public void clcGoImage(View v) //Galeri butonuna tıklandığında ilgili sayfaya yönlendirir
    {
        Intent intent= new Intent(HomePage.this, Photos.class);
        startActivity(intent);
    }
    public void clcGoWebSite(View v) //Web Sitesi butonuna tıklandığında ilgili sayfaya yönlendirir
    {
        Intent intent= new Intent(HomePage.this, websites.class);
        startActivity(intent);
    }

    public void clcGoFaculty(View v) //Fakülteler butonuna tıklandığında ilgili sayfaya yönlendirir
    {
        Intent intent= new Intent(HomePage.this, HaritaActivity.class);
        startActivity(intent);
    }

    public void clcGoCalculator(View v) //Hesap Makinesi butonuna tıklandığında ilgili sayfaya yönlendirir
    {
        Intent intent= new Intent(HomePage.this, Calculator.class);
        startActivity(intent);
    }

}