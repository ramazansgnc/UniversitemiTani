package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class websites extends AppCompatActivity {
    Button UniWeb, ObsWeb, HaruzemWeb, SksWeb, MuhendislikWeb, EgitimWeb, GuzelSanatWeb, IlahiyatWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websites);

        UniWeb = findViewById(R.id.Uni);
        ObsWeb = findViewById(R.id.Obs);
        HaruzemWeb = findViewById(R.id.Haruzem);
        SksWeb = findViewById(R.id.Sks);
        MuhendislikWeb = findViewById(R.id.Muhendislik);
        EgitimWeb = findViewById(R.id.Egitim);
        GuzelSanatWeb = findViewById(R.id.GuzelSanat);
        IlahiyatWeb = findViewById(R.id.Ilahiyat);

        UniWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri linkimiz = Uri.parse("http://www.harran.edu.tr/"); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
                startActivity(intentimiz);
            }
        });

        ObsWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri linkimiz = Uri.parse("https://obs.harran.edu.tr/"); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
                startActivity(intentimiz);
            }
        });

        HaruzemWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri linkimiz = Uri.parse("http://haruzem.harran.edu.tr/"); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
                startActivity(intentimiz);
            }
        });

        SksWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri linkimiz = Uri.parse("https://skssvr.harran.edu.tr/User/Login"); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
                startActivity(intentimiz);
            }
        });

        MuhendislikWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri linkimiz = Uri.parse("http://muhendis.harran.edu.tr/"); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
                startActivity(intentimiz);
            }
        });

        EgitimWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri linkimiz = Uri.parse("http://egitim.harran.edu.tr/"); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
                startActivity(intentimiz);
            }
        });

        GuzelSanatWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri linkimiz = Uri.parse("http://gsf.harran.edu.tr/"); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
                startActivity(intentimiz);
            }
        });

        IlahiyatWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri linkimiz = Uri.parse("http://ilahiyat.harran.edu.tr/"); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
                startActivity(intentimiz);
            }
        });


    }

}