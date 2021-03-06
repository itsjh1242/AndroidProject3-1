package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class clientMainActivity extends AppCompatActivity implements  OnMapReadyCallback {

    private GoogleMap mMap;
    private double userla, userlo;

    String TAG = "activity_client_main";
    String storeName, storeId_model, storeId, storeTitle;
    double lat, lon, distance;

    Location mLocation;
    LocationManager locationManager;

//    Button btn_id_1, btn_id_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        btn_id_1 = (Button) findViewById(R.id.btn_venti);
//        btn_id_2 = (Button) findViewById(R.id.btn_seomyeon);
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(clientMainActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//        super.onBackPressed();
//    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
//        (35.1519918, 129.01322919999998)
        LatLng coordinate = new LatLng(35.146701, 129.007656);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 18);
        mMap.animateCamera(location);

        // GPS ????????? ???????????? ??? ??????????????? 500m ????????? ?????? ??????????????? ????????????
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
            @Override
            public boolean onMyLocationButtonClick()
            {
//                mMap.setMyLocationEnabled(true);

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                mLocation = googleMap.getMyLocation();
                      if(mLocation != null) {
                    userla = mLocation.getLatitude();
                    userlo =mLocation.getLongitude();
                }
//                Toast.makeText(getApplicationContext(), Double.toString(userla) + "?????????" + Double.toString(userlo), Toast.LENGTH_SHORT).show();
                Location locationUser = new Location("Point User");
                locationUser.setLatitude(userla);
                locationUser.setLongitude(userlo);

                Location locationTarget = new Location("Point Target");

                InterfaceUsers interfaceusers = Client.getRetrofitInstance().create(InterfaceUsers.class);
                Call<ArrayList<ModelUsers>> call = interfaceusers.getUserData2();
                call.enqueue(new Callback<ArrayList<ModelUsers>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ModelUsers>> call, Response<ArrayList<ModelUsers>> response) {
                        Log.e(TAG, "onResonse: code : " + response.code());
                        Log.e(TAG, "onResonse: body : " + response.body());
                        ArrayList<ModelUsers> modelusers = response.body();
                        for (int i = 0; i < modelusers.toArray().length; i++) {
                            lat = modelusers.get(i).getUserLatitude();
                            lon = modelusers.get(i).getUserLongitude();
                            storeName = modelusers.get(i).getStoreName();
                            storeId_model = modelusers.get(i).getStoreId();
                            locationTarget.setLatitude(lat);
                            locationTarget.setLongitude(lon);
                            distance = locationUser.distanceTo(locationTarget);
                            if (distance < 500){
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon))
                                        .title(storeName).snippet(storeId_model)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_mark2));
                                mMap.addMarker(marker);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ModelUsers>> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }

                });
                return false;
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                storeTitle = marker.getTitle();
                storeId = marker.getSnippet();
//                startActivity(new Intent(getApplicationContext(), ClientStoreActivity.class));
                Intent intent = new Intent(clientMainActivity.this, ClientStoreActivity.class);
                intent.putExtra("storeTitle", storeTitle);
                intent.putExtra("storeId", storeId);
                startActivity(intent);
                return false;
            }
        });

        InterfaceUsers interfaceusers = Client.getRetrofitInstance().create(InterfaceUsers.class);
        Call<ArrayList<ModelUsers>> call = interfaceusers.getUserData2();
        call.enqueue(new Callback<ArrayList<ModelUsers>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelUsers>> call, Response<ArrayList<ModelUsers>> response) {
                Log.e(TAG, "onResonse: code : " + response.code());
                Log.e(TAG, "onResonse: body : " + response.body());
                ArrayList<ModelUsers> modelusers = response.body();
                for (int i = 0; i < modelusers.toArray().length; i++) {
                    lat = modelusers.get(i).getUserLatitude();
                    lon = modelusers.get(i).getUserLongitude();
                    storeName = modelusers.get(i).getStoreName();
                    storeId_model = modelusers.get(i).getStoreId();
                    LatLng latlng = new LatLng(lat, lon);
                    mMap.addMarker(new MarkerOptions().title(storeName).snippet(storeId_model).position(latlng));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelUsers>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }

        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) { //gps????????? ??????????????? ???????????? ??????{
            googleMap.setMyLocationEnabled(true);
            }
        else{
            checkLocationPermissionWithRationale();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("????????????")
                        .setMessage("??? ?????? ???????????? ???????????? ??????????????? ????????? ???????????????. ???????????? ????????? ???????????? ?????????.")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(clientMainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class clientMainActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_client_main);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//    @Override
//    public void onMapReady(final GoogleMap googleMap){//???????????? ?????? ????????????
//        mMap = googleMap;
//        //??????????????? ???????????? : 35.1443, 129.0106
//        LatLng DongseoUniv = new LatLng(35.1443,129.0106);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(DongseoUniv);
//        markerOptions.title("???????????????");
//        markerOptions.snippet("???????????? ?????? ??????");
//        mMap.addMarker(markerOptions);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DongseoUniv, 18));
////        googleMap.moveCamera(CameraUpdateFactory.newLatLng(DongseoUniv)); // ?????? ???????????? ????????? latLng????????? ?????? map?????? ??????
////        googleMap.moveCamera(CameraUpdateFactory.zoomTo(16)); //????????? ??? ????????? ????????? ????????? ????????? ??? n????????? ??????
////        MarkerOptions markerOptions = new MarkerOptions().position(DongseoUniv).title("???????????????"); // ??? ?????? ????????? ?????? ??????(??????) ??????
////        googleMap.addMarker(markerOptions); //?????? ??????
//
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){ //gps????????? ??????????????? ???????????? ??????
//            googleMap.setMyLocationEnabled(true);
//        }else{
//            checkLocationPermissionWithRationale();
//        }
//    }
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//
//    private void checkLocationPermissionWithRationale() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                new AlertDialog.Builder(this)
//                        .setTitle("????????????")
//                        .setMessage("??? ?????? ???????????? ???????????? ??????????????? ????????? ???????????????. ???????????? ????????? ???????????? ?????????.")
//                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                ActivityCompat.requestPermissions(clientMainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
//                            }
//                        }).create().show();
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        mMap.setMyLocationEnabled(true);
//                    }
//                } else {
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//        }
//    }
//}