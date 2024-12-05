package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//import com.example.groupproject.databinding.ActivityMainBinding;
import com.example.groupproject.databinding.ActivityPhotosBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Photos extends AppCompatActivity {
    public int sayac=2;

    ActivityPhotosBinding binding;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding= ActivityPhotosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resimYukleniyor();

    }
    public void resimYukleniyor()
    {
        progressDialog= new ProgressDialog(Photos.this);
        progressDialog.setMessage("Yükleniyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        storageReference= FirebaseStorage.getInstance().getReference("images/1.jpg");
        try {
            File localfile= File.createTempFile("tempfile",".jpg");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            binding.ivPhotos.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(Photos.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clcGeri(View v) { // Geri butonuna tıklandığında bir önceki fotoğrafa döner
        progressDialog= new ProgressDialog(Photos.this);
        progressDialog.setMessage("Yükleniyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if(sayac<1)
        {
            sayac=8;
        }
        String imageID= Integer.toString(sayac);
        storageReference= FirebaseStorage.getInstance().getReference("images/"+imageID+".jpg");
        try {
            File localfile= File.createTempFile("tempfile",".jpg");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            sayac--;
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            binding.ivPhotos.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Toast.makeText(Photos.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clcIleri(View v) { // İleri butonuna tıklandığında bir sonraki fotoğrafa gider
        progressDialog= new ProgressDialog(Photos.this);
        progressDialog.setMessage("Yükleniyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if(sayac>8)
        {
            sayac=1;
        }
        String imageID= Integer.toString(sayac);
        sayac++;
        storageReference= FirebaseStorage.getInstance().getReference("images/"+imageID+".jpg");
        try {
            File localfile= File.createTempFile("tempfile",".jpg");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            binding.ivPhotos.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Toast.makeText(Photos.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}