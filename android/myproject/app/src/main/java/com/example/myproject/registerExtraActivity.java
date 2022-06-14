package com.example.myproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerExtraActivity extends AppCompatActivity {


    String TAG = "activity_register_extra";
    String storeName, number, address, chk_address, usertype, Data_USER_ID, Data_User_Type, Data_User_StoreId; // 매장 이름, 사업자 등록 번호, 주소
    double latitude,longtitude;
    Button btn_okay, btn_extra_chk;

    EditText edt_storeName, edt_Num, edt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_extra);

//        SharedPreferences preferences_id = getSharedPreferences("user_id", Context.MODE_PRIVATE);
//        SharedPreferences preferences_type = getSharedPreferences("user_type", Context.MODE_PRIVATE);
//        SharedPreferences preferences_storeid = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
//        Data_USER_ID = preferences_id.getString("user_id","");
//        Data_User_Type = preferences_type.getString("user_type", "");
//        Data_User_StoreId = preferences_storeid.getString("user_storeid", "");

        Intent intent = getIntent();
        String name, id, pw;
        name = intent.getStringExtra("Name");
        id = intent.getStringExtra("ID");
        pw = intent.getStringExtra("PW");

        btn_okay = (Button) findViewById(R.id.btn_extra_ok);
        btn_extra_chk = (Button) findViewById(R.id.btn_extra_chk);

        edt_storeName = (EditText) findViewById(R.id.edt_extra_storeName);
        edt_Num = (EditText) findViewById(R.id.edt_extra_number);
        edt_address = (EditText) findViewById(R.id.edt_extra_address);
        edt_address.setFocusable(false);

        edt_address.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(registerExtraActivity.this, searchActivity.class);
                getSearchResult.launch(intent);
            }
        });
        // EditText에서 받아온 주소를 위도, 경도 값으로 변환해서 전달
        btn_extra_chk.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    chk_address = edt_address.getText().toString();
                    List<Address> addresses = geoCoder.getFromLocationName(chk_address, 1);
                    if (addresses.size() >  0) {
                        latitude = addresses.get(0).getLatitude();
                        longtitude = addresses.get(0).getLongitude();
                        Toast.makeText(getApplicationContext(), "위도: " + latitude + "경도: " + longtitude, Toast.LENGTH_SHORT).show();
                        btn_okay.setEnabled(true);
                    }
                } catch (IOException e) { // TODO Auto-generated catch block
                    e.printStackTrace(); }
            }
        });


        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeName = edt_storeName.getText().toString();
                number = edt_Num.getText().toString();
                address = edt_address.getText().toString();

                if (storeName.matches("") || number.matches("") || address.matches("")){
                    Toast.makeText(getApplicationContext(), "입력한 정보를 확인하세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    usertype = "1"; // 사업자로 회원가입
                    HashMap<String, Object> postingData2 = new HashMap<>();
                    postingData2.put("userId", id);
                    postingData2.put("userName", name);
                    postingData2.put("userPw", pw);
                    postingData2.put("userType", usertype);
                    postingData2.put("userLatitude", latitude);
                    postingData2.put("userLongitude", longtitude);
                    postingData2.put("storeId", number);
                    postingData2.put("storeName", storeName);
                    try{
                        InterfaceUsers interfaceusers = Client.getRetrofitInstance().create(InterfaceUsers.class);
                        interfaceusers.postUserData(postingData2).enqueue(new Callback<ArrayList<ModelUsers>>() {
                            @Override
                            public void onResponse(Call<ArrayList<ModelUsers>> call, Response<ArrayList<ModelUsers>> response) {
                                if (response.isSuccessful()){
                                    ArrayList<ModelUsers> modelusers = response.body();
                                    Log.e(TAG, "onResonse: code : " + response.code());
                                    Log.e(TAG, "onResonse: body : " + response.body());
                                }
                            }
                            @Override
                            public void onFailure(Call<ArrayList<ModelUsers>> call, Throwable t) {

                            }
                        });
                        SharedPreferences preferences_id = getSharedPreferences("user_id", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_id = preferences_id.edit();
                        SharedPreferences preferences_type = getSharedPreferences("user_type", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_type = preferences_type.edit();
                        SharedPreferences preferences_storeid = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor_storeid = preferences_storeid.edit();
                        editor_id.putString("user_id", id);
                        editor_id.commit();
                        editor_type.putString("user_type", usertype);
                        editor_type.commit();
                        preferences_storeid.edit().putString("user_storeid", number).apply();
//                        editor_storeid.putString("user_storeid", number);
//                        editor_storeid.commit();
                        finish();
                        Toast.makeText(getApplicationContext(), "사업자(으)로 가입하셨습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(registerExtraActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e){ //
                        Toast.makeText(getApplicationContext(), "입력 값을 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    if (result.getData() != null){
                        String data = result.getData().getStringExtra("data");
                        edt_address.setText(data);
                    }
                }
            }
    );

    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address, 5);
            if((addresses == null) || (addresses.size() == 0)) {
                return null;
            }
            Address addressLoc = addresses.get(0);
            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLocation;
    }
}