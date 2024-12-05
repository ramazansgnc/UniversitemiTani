package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class HaritaActivity extends AppCompatActivity {

    private static final String TAG ="HaritaActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;



    public Button btMuh, btTip,btFenE,btIlh,btIIBF,btYmk,btBes, btRektr,btKtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapmenu);

        if (isServicesOK()){
            init();
        }

    }

    private void init(){
        btMuh =(Button) findViewById(R.id.btnMuh);
        btTip =(Button) findViewById(R.id.btnTip);
        btFenE =(Button) findViewById(R.id.btnFenE);
        btIlh =(Button) findViewById(R.id.btnIlh);
        btIIBF =(Button) findViewById(R.id.btnIIBF);
        btYmk =(Button) findViewById(R.id.btnYmk);
        btRektr =(Button) findViewById(R.id.btnRektr);
        btKtp =(Button) findViewById(R.id.btnKtp);
        btBes =(Button) findViewById(R.id.btnBes);



        btMuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapMuhendis.class);
                        startActivity(intent);

            }
        });
        btTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapTip.class);
                startActivity(intent);

            }
        });
        btFenE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapFenEdb.class);
                startActivity(intent);

            }
        });
        btIlh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapIlh.class);
                startActivity(intent);

            }
        });
        btIIBF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapIIBF.class);
                startActivity(intent);

            }
        });
        btYmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapYM.class);
                startActivity(intent);

            }
        });
        btRektr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapRektr.class);
                startActivity(intent);

            }
        });
        btKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapKtp.class);
                startActivity(intent);

            }
        });
        btBes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HaritaActivity.this, MapBes.class);
                startActivity(intent);

            }
        });

    }
    public boolean isServicesOK(){//Google play hizmetleri kontrolü
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(HaritaActivity.this);
        if (available== ConnectionResult.SUCCESS){
            //her şey yolunda ve kullanıcı harita isteklerinde bulunabilir
            Log.d(TAG,"isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //bir hata oluştu ama çözebiliriz
            Log.d(TAG,"isServicesOK: an error occured but we can fix it");
            Dialog dialog= GoogleApiAvailability.getInstance().getErrorDialog(HaritaActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this,"You cant make map requests",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}