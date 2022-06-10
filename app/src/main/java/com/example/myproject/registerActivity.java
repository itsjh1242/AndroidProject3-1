package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    String name, id, pw, pwcheck, usertype, db_userName;
    Button checkButton, join;
    EditText userName, userID, userPW, userPWCheck;
    RadioButton client, business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        //PW Check 버튼 클릭 시
        checkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // 사용자가 EditText에 입력한 값을 String형태로 가져온다.
                name = userName.getText().toString();
                id = userID.getText().toString();
                pw = userPW.getText().toString();
                pwcheck = userPWCheck.getText().toString();
                // CHECK 버튼을 눌렀을 때 EditText가 빈 칸이거나 PassWord Check Form정보가 일치하지 않을 때
                if (name.matches("") || id.matches("") || pw.matches("") || pwcheck.matches("") || !pw.matches(pwcheck)){
                    Toast.makeText(getApplicationContext(), "입력한 정보를 다시 한 번 확인하세요.", Toast.LENGTH_SHORT).show();
                }
                else{ // 사용자가 올바르게 입력한 경우 회원가입이 되게 함
                    join.setEnabled(true);
                }
            }
        });
        join.setOnClickListener(new View.OnClickListener(){
            @Override
            // <DataBase> 사용자가 회원가입 시 입력한 정보를 DataBase로 옮겨야 함
            public void onClick(View view){
                if (client.isChecked() == true){
                    usertype = "0";
                    HashMap<String, Object> postingData = new HashMap<>();
                    postingData.put("userId", id);
                    postingData.put("userName", name);
                    postingData.put("userPw", pw);
                    postingData.put("userType", usertype);
                    postingData.put("userLatitude", 0.0);
                    postingData.put("userLongitude", 0.0);
                    postingData.put("storeId", "");
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
                        Toast.makeText(getApplicationContext(), name + "님 고객으로 가입하셨습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(registerActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e){ //
                        Toast.makeText(getApplicationContext(), "입력 값을 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "추가 정보 입력 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerActivity.this, registerExtraActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}