package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class discountupdateActivity extends AppCompatActivity {

    private static final String TAG = "discountupdateActivity";

    String productName, originalPrice, discountPrice, expirationDate, deliveryDate, Data_User_StoreId;
    EditText e_productName, e_originalPrice, e_discountPrice, e_expirationDate, e_deliveryDate;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discountupdate);

        SharedPreferences preferences_storeid = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
        Data_User_StoreId = preferences_storeid.getString("user_storeid", "");

        e_productName = (EditText) findViewById(R.id.editTextTextPersonName);
        e_originalPrice = (EditText) findViewById(R.id.editTextTextPersonName2);
        e_discountPrice = (EditText) findViewById(R.id.editTextTextPersonName3);
        e_expirationDate = (EditText) findViewById(R.id.editTextTextPersonName4);
        e_deliveryDate = (EditText) findViewById(R.id.editTextTextPersonName5);

        updateButton = (Button) findViewById(R.id.btn_pd_update2);
        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                productName = e_productName.getText().toString();
                originalPrice = e_originalPrice.getText().toString();
                discountPrice = e_discountPrice.getText().toString();
                expirationDate = e_expirationDate.getText().toString();
                deliveryDate = e_deliveryDate.getText().toString();
                // 입력란이 공백일 때
                if (productName.matches("") || originalPrice.matches("") || discountPrice.matches("") || expirationDate.matches("") || deliveryDate.matches("")){
                    Toast.makeText(getApplicationContext(), "입력란을 다시 한 번 확인해주세요.", Toast.LENGTH_SHORT).show();
                } // 디비로 전송, 인텐트 닫기 -> 이전 인텐트로 이동
                else{
                    HashMap<String, Object> postingData = new HashMap<>();
                    postingData.put("itemName", productName);
                    postingData.put("itemPrice", originalPrice);
                    postingData.put("itemDiscountPrice", discountPrice);
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
                    Intent intent = new Intent(discountupdateActivity.this, productDiscountActivity.class);
                    startActivity(intent);
                    finish();//인텐트 종료
                }
            }
        });
    }
}