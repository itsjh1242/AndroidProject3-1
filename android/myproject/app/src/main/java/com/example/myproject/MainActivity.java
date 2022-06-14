package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    String TAG = "activity_main";
//    boolean test;
    EditText ID, PW;
    Button btn_login, btn_join;
    String UserID, UserPW, db_id, db_pw, db_name, db_type, db_storeid, Data_USER_ID, Data_User_Type, Data_User_StoreId;
    InterfaceUsers interfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences_id = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        SharedPreferences preferences_type = getSharedPreferences("user_type", Context.MODE_PRIVATE);
        SharedPreferences preferences_storeid = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
        Data_USER_ID = preferences_id.getString("user_id","");
        Data_User_Type = preferences_type.getString("user_type", "");
        Data_User_StoreId = preferences_storeid.getString("user_storeid", "");

        if (!Data_USER_ID.matches("")){
            if (Data_User_Type.matches("0")){ // 고객
                Intent intent = new Intent(MainActivity.this, clientMainActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(MainActivity.this, businessActivity.class);
                startActivity(intent);
            }
        }

        ID = (EditText) findViewById(R.id.edt_id);
        PW = (EditText) findViewById(R.id.edt_pw);


        btn_login = (Button) findViewById(R.id.btn_login);
        btn_join = (Button) findViewById(R.id.btn_register);

        interfaces = Client.getRetrofitInstance().create(InterfaceUsers.class);

        btn_join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ // 버튼 클릭 시 회원가입 페이지로 이동
                Intent intent = new Intent(MainActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });
        // DB 연동 전 사용자 구분을 위한 코드
//        test = false;

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserID = ID.getText().toString();
                UserPW = PW.getText().toString();
                if (UserID.matches("") | UserPW.matches("")) {
                    Toast.makeText(getApplicationContext(), "아이디 및 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    InterfaceUsers interfaceusers = Client.getRetrofitInstance().create(InterfaceUsers.class);
                    Call<ArrayList<ModelUsers>> call = interfaceusers.getUserData(UserID);
                    call.enqueue(new Callback<ArrayList<ModelUsers>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ModelUsers>> call, Response<ArrayList<ModelUsers>> response) {
//                                Log.e(TAG, "onResonse: code : " + response.code());
//                                Log.e(TAG, "onResonse: body : " + response.body());
                            ArrayList<ModelUsers> modelusers = response.body();
                            try{
                                SharedPreferences preferences_id = getSharedPreferences("user_id", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_id = preferences_id.edit();
                                SharedPreferences preferences_type = getSharedPreferences("user_type", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_type = preferences_type.edit();
                                SharedPreferences preferences_storeid = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor_storeid = preferences_storeid.edit();
                                db_id = modelusers.get(0).getUserId();
                                db_pw = modelusers.get(0).getUserPw();
                                db_name = modelusers.get(0).getUserName();
                                db_type = modelusers.get(0).getUserType();
                                db_storeid = modelusers.get(0).getStoreId();
                                if ((UserPW.matches(db_pw)) && (db_type.matches("0"))){ // 고객이면,
                                    Toast.makeText(getApplicationContext(), "[고객] " + db_name + "님 로그인 되었습니다!", Toast.LENGTH_SHORT).show();
                                    editor_id.putString("user_id", db_id);
                                    editor_id.commit();
                                    editor_type.putString("user_type", db_type);
                                    editor_type.commit();
                                    Intent intent = new Intent(MainActivity.this, clientMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if ((UserPW.matches(db_pw)) && (db_type.matches("1"))){ // 사업자면,
                                    Toast.makeText(getApplicationContext(), "[사업자]" + db_name + "님 로그인 되었습니다!", Toast.LENGTH_SHORT).show();
                                    editor_id.putString("user_id", db_id);
                                    editor_id.commit();
                                    editor_type.putString("user_type", db_type);
                                    editor_type.commit();
                                    preferences_storeid.edit().putString("user_storeid", db_storeid).apply();
//                                    editor_storeid.putString("user_storeid", db_storeid);
//                                    editor_storeid.commit();
                                    Intent intent = new Intent(MainActivity.this, businessActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "로그인 실패!" + db_type, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){ // 만약 상품 번호를 잘못 입력했을 때
                                Toast.makeText(getApplicationContext(), "입력 값을 확인해주세요!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ArrayList<ModelUsers>> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }
}