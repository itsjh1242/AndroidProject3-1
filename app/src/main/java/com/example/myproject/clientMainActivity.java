package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class clientMainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        //동서대학교 위도경도 : 35.1443, 129.0106
        LatLng Marker1 = new LatLng(35.1443,129.0106);
        MarkerOptions dsu = new MarkerOptions();
        dsu.position(Marker1);
        dsu.title("동서대학교");
        dsu.snippet("프로젝트 진행 대학");
        mMap.addMarker(dsu);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Marker1, 18));

        // 보금자리 위도 및 경도 : 35.147772, 129.008637
        LatLng Marker2 = new LatLng(35.147772, 129.008637);
        MarkerOptions myhome = new MarkerOptions();
        myhome.position(Marker2);
        myhome.title("보금자리");
        myhome.snippet("우리집");
        mMap.addMarker(myhome);
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
//    public void onMapReady(final GoogleMap googleMap){//시작하는 위치 표시함수
//        mMap = googleMap;
//        //동서대학교 위도경도 : 35.1443, 129.0106
//        LatLng DongseoUniv = new LatLng(35.1443,129.0106);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(DongseoUniv);
//        markerOptions.title("동서대학교");
//        markerOptions.snippet("프로젝트 진행 대학");
//        mMap.addMarker(markerOptions);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DongseoUniv, 18));
////        googleMap.moveCamera(CameraUpdateFactory.newLatLng(DongseoUniv)); // 처음 시작하는 부분을 latLng변수에 넣고 map에서 실행
////        googleMap.moveCamera(CameraUpdateFactory.zoomTo(16)); //시작할 때 멀리서 보여서 가깝게 보이게 줌 n단계로 시작
////        MarkerOptions markerOptions = new MarkerOptions().position(DongseoUniv).title("동서대학교"); // 맵 처음 실행시 위치 표시(마크) 변수
////        googleMap.addMarker(markerOptions); //마크 실행
//
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){ //gps사용을 허용했는지 확인하는 문구
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
//                        .setTitle("위치정보")
//                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
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