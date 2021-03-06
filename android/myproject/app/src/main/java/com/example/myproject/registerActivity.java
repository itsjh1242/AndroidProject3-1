package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerActivity extends AppCompatActivity {

    String TAG = "activity_register";
    String name, id, pw, pwcheck, usertype, Data_USER_ID, Data_User_Type;
    Button checkButton, join;
    EditText userName, userID, userPW, userPWCheck;
    RadioButton client, business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences preferences_id = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        SharedPreferences preferences_type = getSharedPreferences("user_type", Context.MODE_PRIVATE);
//        SharedPreferences preferences_storeid = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
        Data_USER_ID = preferences_id.getString("user_id","");
        Data_User_Type = preferences_type.getString("user_type", "");
//        Data_User_StoreId = preferences_storeid.getString("user_storeid", "");

        //Button
        checkButton = (Button) findViewById(R.id.btn_r_chk);
        join = (Button) findViewById(R.id.btn_r_join);
        //EditText
        userName = (EditText) findViewById(R.id.edt_r_name);
        userID = (EditText) findViewById(R.id.edt_r_id);
        userPW = (EditText) findViewById(R.id.edt_r_pw);
        userPWCheck = (EditText) findViewById(R.id.edt_r_pwchk);

        //RadioButton
        client = (RadioButton) findViewById(R.id.rbtn_client);
        business = (RadioButton) findViewById(R.id.rbtn_business);
        //PW Check ?????? ?????? ???
        checkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // ???????????? EditText??? ????????? ?????? String????????? ????????????.
                name = userName.getText().toString();
                id = userID.getText().toString();
                pw = userPW.getText().toString();
                pwcheck = userPWCheck.getText().toString();
                // CHECK ????????? ????????? ??? EditText??? ??? ???????????? PassWord Check Form????????? ???????????? ?????? ???
                if (name.matches("") || id.matches("") || pw.matches("") || pwcheck.matches("") || !pw.matches(pwcheck)){
                    Toast.makeText(getApplicationContext(), "????????? ????????? ?????? ??? ??? ???????????????.", Toast.LENGTH_SHORT).show();
                }
                else{ // ???????????? ???????????? ????????? ?????? ??????????????? ?????? ???
                    join.setEnabled(true);
                }
            }
        });
        join.setOnClickListener(new View.OnClickListener(){
            @Override
            // <DataBase> ???????????? ???????????? ??? ????????? ????????? DataBase??? ????????? ???
            public void onClick(View view){
                if (client.isChecked() == true){
                    usertype = "0";
                    HashMap<String, Object> postingData = new HashMap<>();
                    postingData.put("userId", id);
                    postingData.put("userName", name);
                    postingData.put("userPw", pw);
                    postingData.put("userType", usertype);
//                    postingData.put("userLatitude", 0.0);
//                    postingData.put("userLongitude", 0.0);
                    try{
                        InterfaceUsers interfaceusers = Client.getRetrofitInstance().create(InterfaceUsers.class);
                        interfaceusers.postUserData(postingData).enqueue(new Callback<ArrayList<ModelUsers>>() {
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
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });
                        SharedPreferences preferences_id = getSharedPreferences("user_id", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor_id = preferences_id.edit();
                        SharedPreferences preferences_type = getSharedPreferences("user_type", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor_type = preferences_type.edit();
//                        SharedPreferences preferences_storeid = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor_storeid = preferences_storeid.edit();
//                        editor_id.putString("user_id", id);
//                        editor_id.commit();
//                        editor_type.putString("user_type", usertype);
//                        editor_type.commit();
                        preferences_id.edit().putString("user_id", id).apply();
                        preferences_type.edit().putString("user_type", usertype).apply();
                        Toast.makeText(getApplicationContext(), name + "??? ???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(registerActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e){ //
                        Toast.makeText(getApplicationContext(), "?????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "?????? ?????? ?????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerActivity.this, registerExtraActivity.class);
                    intent.putExtra("Name", name);
                    intent.putExtra("ID", id);
                    intent.putExtra("PW", pw);
                    intent.putExtra("userType", usertype);
                    startActivity(intent);
                }

            }
        });
    }
}