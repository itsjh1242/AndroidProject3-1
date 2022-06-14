package com.example.myproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class productDiscountActivity extends AppCompatActivity {

    String TAG = "activity_product_discount";
    String Data_User_StoreId;
    Button btn_pd_update2, deleteButton;

    ListView lv1;
    ArrayList<ModelItems> arrayList;
    Adapter arrayAdapter;
    InterfaceItems interfaces;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_discount);

        SharedPreferences preferences = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
        Data_User_StoreId = preferences.getString("user_storeid","");

        lv1 = (ListView)findViewById(R.id.lv1);
        arrayList = new ArrayList<ModelItems>();
        arrayAdapter = new Adapter(this, arrayList);
        interfaces = Client.getRetrofitInstance().create(InterfaceItems.class);
        lv1.setAdapter(arrayAdapter);
        // 실행 함수

        btn_pd_update2 = (Button)findViewById(R.id.btn_pd_update1);
        btn_pd_update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(productDiscountActivity.this, discountupdateActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getFoodList();
        deleteButton = (Button) findViewById(R.id.btn_pd_delete);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder ad = new AlertDialog.Builder(productDiscountActivity.this);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setMessage("삭제할 상품의 품번을 입력해주세요!");
                ad.setTitle("등록된 상품 삭제");
                final EditText adet = new EditText(productDiscountActivity.this);
                adet.setInputType(InputType.TYPE_CLASS_NUMBER);
                ad.setView(adet);

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int result = Integer.parseInt(adet.getText().toString());
                        // 삭제 시작
                        try{
                            InterfaceItems interfaceitems = Client.getRetrofitInstance().create(InterfaceItems.class);
                            interfaceitems.deleteFoodData(result).enqueue(new Callback(){
                                @Override
                                public void onResponse(Call call, Response response){
                                    if (response.isSuccessful()) {
                                        Log.e(TAG, "Delete" + "delete 성공");
                                    }
                                }
                                @Override
                                public void onFailure(Call call, Throwable t){

                                }
                            });
                            dialog.dismiss();
                            // 새로고침 액티비티
                            finish();//인텐트 종료
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                            Intent intent = getIntent(); //인텐트
                            startActivity(intent); //액티비티 열기
                            overridePendingTransition(0, 0);//인텐트 효과 없애기

                        } catch (Exception e){ // 만약 상품 번호를 잘못 입력했을 때
                            Toast.makeText(getApplicationContext(), "입력 값을 확인해주세요!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });
    }

    private void getFoodList(){
        arrayList.clear();
        Call<ArrayList<ModelItems>> call = interfaces.getFoodData(Integer.parseInt(Data_User_StoreId)); // interface에 있는 함수
        Toast.makeText(getApplicationContext(), "" + Data_User_StoreId, Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<ArrayList<ModelItems>>(){
            @Override
            public void onResponse(Call<ArrayList<ModelItems>> call, Response<ArrayList<ModelItems>> response){
                ArrayList<ModelItems> items = response.body();
                for (int i = 0; i < items.toArray().length; i++){
                    arrayList.add(items.get(i));
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ModelItems>> call, Throwable t){
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}