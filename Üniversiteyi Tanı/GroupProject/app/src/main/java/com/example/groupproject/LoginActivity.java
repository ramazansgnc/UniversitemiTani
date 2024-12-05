package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_Id, edt_Pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_Id=findViewById(R.id.edtId);
        edt_Pw=findViewById(R.id.edtPw);



    }
    public Boolean dogrulamaID() //Kullanıcı adının boş olup olmadığını kontrol eder
    {
        String sId = edt_Id.getText().toString();
        if(sId.isEmpty())
        {
            edt_Id.setError("Kullanıcı adı girin");
            return false;
        }
        else{
            edt_Id.setError(null);
            return true;
        }
    }

    public Boolean dogrulamaPw() //Şifrenin boş olup olmadığını kontrol eder
    {
        String sPw = edt_Pw.getText().toString();
        if(sPw.isEmpty())
        {
            edt_Pw.setError("Şifre girin");
            return false;
        }
        else{
            edt_Pw.setError(null);
            return true;
        }
    }

    public void checkID() { // Kullanıcı bilgilerini kontrol eder
        String  sId = edt_Id.getText().toString().trim();;
        String  sPw = edt_Pw.getText().toString().trim();;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("kullanicilar");
        Query checkUserDatabase = reference.orderByChild("id").equalTo(sId);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    edt_Pw.setError(null);
                    String sifreVT= snapshot.child(sId).child("sifre").getValue(String.class);
                    // sId'de girilen id'ye göre veritabanındaki şifreyi sifreVT'ye atar
                    if (sifreVT.equals(sPw)) { // sifreVT ve dışarıdalan girilen şifre eşitse giriş başarılı olur
                        edt_Id.setError(null);
                        String adVT, soyadVT;
                        adVT  = snapshot.child(sId).child("ad").getValue(String.class);
                        soyadVT  = snapshot.child(sId).child("soyad").getValue(String.class);
                        Intent hintent = new Intent(LoginActivity.this, HomePage.class);
                        hintent.putExtra("ad",adVT);
                        hintent.putExtra("soyad",soyadVT);
                        startActivity(hintent);
                    }
                    else {
                        edt_Pw.setError("Şifre Yanlış!");
                        edt_Pw.requestFocus();
                    }
                }
                else{
                    edt_Id.setError("Kullanıcı Adı Yanlış!");
                    edt_Id.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this,error.toException().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void clcGiris(View v) //Giriş butonuna tıklandığında
    {
        if(dogrulamaID() && dogrulamaPw()) //ID ve şifre boş değilse
        {
            checkID(); // kullanıcı kontrolün ardından giriş yapar
        }
    }

    public void clckRegister(View v)// Kayıt butonuna tıklandığında Kayıt ol sayfasına yönlendirir
    {
        Intent intent= new Intent(LoginActivity.this, Register.class);
        finish();
        startActivity(intent);
    }

}
