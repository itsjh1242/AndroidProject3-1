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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class arrivalupdateActivity extends AppCompatActivity {

    String TAG = "activity_product_arrival";

    String Data_User_StoreId, productName, originalPrice, expirationDate, deliveryDate;
    EditText edt_update_name, edt_update_price, edt_update_exp, edt_update_made;
    Button btn_update_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrivalupdate);

        SharedPreferences preferences_storeid = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
        Data_User_StoreId = preferences_storeid.getString("user_storeid", "");

        edt_update_name = (EditText) findViewById(R.id.edt_update_name);
        edt_update_price = (EditText) findViewById(R.id.edt_update_price);
        edt_update_exp = (EditText) findViewById(R.id.edt_update_exp);
        edt_update_made = (EditText) findViewById(R.id.edt_update_made);

        btn_update_update = (Button) findViewById(R.id.btn_pa_update2);
        btn_update_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                productName = edt_update_name.getText().toString();
                originalPrice = edt_update_price.getText().toString();
                expirationDate = edt_update_exp.getText().toString();
                deliveryDate = edt_update_made.getText().toString();
                // 입력란이 공백일 때
                if (productName.matches("") || originalPrice.matches("") || expirationDate.matches("") || deliveryDate.matches("")){
                    Toast.makeText(getApplicationContext(), "입력란을 다시 한 번 확인해주세요.", Toast.LENGTH_SHORT).show();
                } // 디비로 전송, 인텐트 닫기 -> 이전 인텐트로 이동
                else{
                    HashMap<String, Object> postingData = new HashMap<>();
                    postingData.put("itemName", productName);
                    postingData.put("itemPrice", originalPrice);
                    postingData.put("itemDiscountPrice", 0);
                    postingData.put("itemExpirationDate", expirationDate);
                    postingData.put("itemDeliveryDate", deliveryDate);
                    postingData.put("storeId", Data_User_StoreId); // 수정 필요!
                    InterfaceItems interfaceitems = Client.getRetrofitInstance().create(InterfaceItems.class);
                    interfaceitems.postFoodData(postingData).enqueue(new Callback<ArrayList<ModelItems>>(){
                        @Override
                        public void onResponse(Call<ArrayList<ModelItems>> call, Response<ArrayList<ModelItems>> response){
                            if(response.isSuccessful()){
                                ArrayList<ModelItems> modelitems = response.body();
                                Log.e(TAG, "POST" + "post 성공");
                                Log.e(TAG, "POST" + modelitems.get(0).getItemName());
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ModelItems>> call, Throwable t){

                        }
                    });
                    Toast.makeText(getApplicationContext(), productName + "(을)를 추가했습니다!", Toast.LENGTH_SHORT).show();
                    // 새로고침 액티비티
                    Intent intent = new Intent(arrivalupdateActivity.this, productArrivalActivity.class);
                    startActivity(intent);
                    finish();//인텐트 종료
                }
            }
        });
    }
}