package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText edt_Id, edt_Pw, edt_Ad, edt_Soyad, edt_TelNo;

    private String sId, sPw, sAd, sSoyad, sTelNo;


    private DatabaseReference mReference;
    private HashMap<String,Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edt_Id=findViewById(R.id.edtId);
        edt_Pw=findViewById(R.id.edtPw);
        edt_Ad=findViewById(R.id.edtAd);
        edt_Soyad=findViewById(R.id.edtSoyad);
        edt_TelNo=findViewById(R.id.edtTelNo);


        mReference= FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onBackPressed() //Geri tuşuna basıldığında Giriş ekranına döner
    {
        Intent backIntent = new Intent(Register.this, LoginActivity.class);
        finish();
        startActivity(backIntent);
    }
    private void Clear()
    {
        edt_Id.setText("");
        edt_Pw.setText("");
        edt_Ad.setText("");
        edt_Soyad.setText("");
        edt_TelNo.setText("");
    }

    public void kayitOl(View view) //kayıt ol butonundaki onClick
    {
        sId=edt_Id.getText().toString();
        sPw=edt_Pw.getText().toString();
        sAd=edt_Ad.getText().toString();
        sSoyad=edt_Soyad.getText().toString();
        sTelNo=edt_TelNo.getText().toString();

        //Değerler boş girilmediği sürece
        if(!TextUtils.isEmpty(sId) && !TextUtils.isEmpty(sPw) && !TextUtils.isEmpty(sAd)
                && !TextUtils.isEmpty(sSoyad) && !TextUtils.isEmpty(sTelNo))
        {
            //mData ile Veritabanına bilgileri gireriz
            //("veritabanındaki key kısmı" , key'in dışarıdan girilen değeri)
            mData=new HashMap<>();
            mData.put("id",sId);
            mData.put("sifre",sPw);
            mData.put("ad",sAd);
            mData.put("soyad",sSoyad);
            mData.put("telNo",sTelNo);


            mReference.child("kullanicilar").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.hasChild(sId)) //Veritabanındaki bütün id'leri sID'de girilen ile kıyaslar ve değer true ise
                    {
                        Toast.makeText(Register.this,"Böyle bir kullanıcı adı zaten var!",Toast.LENGTH_SHORT).show();
                    }
                    else // mData'ya girilen değerleri sId'deki değere göre veri tabanına ekle
                    {
                        mReference.child("kullanicilar").child(sId).setValue(mData)
                                .addOnCompleteListener(Register.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(Register.this,"Kayıt işlemi başarılı!",Toast.LENGTH_SHORT).show();
                                            Clear();
                                            edt_Ad.requestFocus();
                                        }
                                        else
                                        {
                                            Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Register.this,error.toException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this,"İstenilen Değerler Boş Geçilemez",Toast.LENGTH_SHORT).show();
        }

    }


}