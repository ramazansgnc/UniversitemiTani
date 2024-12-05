package com.example.groupproject;



import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapIlh extends AppCompatActivity implements OnMapReadyCallback{

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) { // Haritanın başlangıç konum
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady:map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();

        }

    }



    private static final String TAG="MapActivity";
    private static final String FINE_LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION= Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private static final float DEFAULT_ZOOM=14.5f;

    //widgets
    private EditText mSearchText;
    private ImageView mGps;
    //değişkenler
    private Boolean mLocationPermissionsGranted=false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    @SuppressLint("MissingInflatedId")

    private Timer sureTimer;
    private Handler sureHandler;
    private int sayac = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mSearchText=(EditText) findViewById(R.id.input_search);
        mGps=(ImageView) findViewById(R.id.ic_gps);


        getLocationPermission();
        sureSayac();

        mSearchText.setText("harran üniversitesi ilahiyat");

    }

    private void init(){// Arama ve lokasyona geri dönme
        Log.d(TAG, "init: initializing");

        geoLocate();

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//
                Log.d(TAG,"onClick: clicked gps icon");
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }

    public void geoLocate(){// aranılan yere yönlendirme
        Log.d(TAG,"geoLocate: geoLocate");
        String searchString= mSearchText.getText().toString();//aratılacak yer

        Geocoder geocoder = new Geocoder(MapIlh.this);
        List<Address> list=new ArrayList<>();
        try {
            list=geocoder.getFromLocationName(searchString,1);
        }catch (IOException e){
            Log.e(TAG,"geoLocate: IOExpection: " + e.getMessage());
        }
        if (list.size()>0){
            Address address = list.get(0);
            Log.d(TAG,"geoLocate: found a location:"+ address.toString());//konum üstü bilgiler

            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }
    private void getDeviceLocation(){// lokasyona geri döndür
        Log.d(TAG,"getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted){
                Task location= mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"onComplate:found location");
                            Location currentLocation=(Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,"My Location" );
                        }else{
                            Log.d(TAG,"onComplate: current location location is null");
                            Toast.makeText(MapIlh.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        }catch (SecurityException e){
            Log.e(TAG,"getDeviceLocation:SecurityException: "+e.getMessage() );

        }
    }

    private void moveCamera(LatLng latLng,float zoom,String title){// ZOOM yapma
        Log.d(TAG,"moveCamera:moving the camera to: lat:" + latLng.latitude +", lng" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options=new MarkerOptions().position(latLng).title(title);
        mMap.addMarker(options);
        hideSoftKeyboard();

    }

    private void initMap(){// Haritanın yüklenmesi
        Log.d(TAG,"initMap:initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapIlh.this);
    }

    private void getLocationPermission(){// kullanıcıdan konum izni isteme
        Log.d(TAG,"getLocationPermission: get location lermission");
        String[] permission={Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted= true;
                initMap();
            }
            else{
                ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
            }

        }else{
            ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {//konum izin kontrolü
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG,"onRequestPermissionsResult: called.");

        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length>0) {
                    for (int i=0;i<grantResults.length;i++){
                        if (grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG,"onRequestPermissionsResult: permission failed.");

                            return;
                        }
                    }
                    Log.d(TAG,"onRequestPermissionsResult: permission granted.");

                    mLocationPermissionsGranted = true;
                    //haritamızı başlat
                    initMap();
                }
            }
        }
    }
    public void clcgo(View v){//search ikonuna basınca arama yapar
        geoLocate();
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void sureSayac(){//İstenilen yere gitmek için 1 saniye içinde otomatik arama yapan method

        sureHandler = new Handler();
        sureTimer = new Timer();


        TimerTask sureTimerTask = new TimerTask() {
            @Override
            public void run() {

                sureHandler.post(new Runnable() {
                    @Override
                    public void run() { //Her saniye girilen metot

                        sayac++;

                        // Herhangi bir hata oluşmaması için runOnUiThread içinde başlatıyoruz
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Süreyi her defasında sure bileşenimizde gösteriyoruz
                                if (sayac==2){

                                    geoLocate();

                                }

                            }
                        });

                    }
                });

            }
        };

        // İlk parametre TimerTaskımızı içeriyor, ikincisi başlarken oluşacak gecikme süresi
        // Üçüncü parametre kaç saniyede bir yenileneceğini soruyor biz 1000 milisaniye yani 1 saniye yaptık
        sureTimer.schedule(sureTimerTask,0,1000); //Saniyede bir kez girilecek

    }


}

