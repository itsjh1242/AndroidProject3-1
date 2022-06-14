package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientStoreActivity extends AppCompatActivity {

    ListView lv3;
    ArrayList<ModelItems> arrayList, arrayList2;
    Adapter arrayAdapter;
    AdapterArrival arrayAdapter2;
    InterfaceItems interfaces;
    String storeTitle, storeId;

    TextView title;

    Button btn_dc, btn_fresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_store);

        title = (TextView)findViewById(R.id.textviewclientstoretitle);

        lv3 = (ListView)findViewById(R.id.lv3);
        arrayList = new ArrayList<ModelItems>();
        arrayList2 = new ArrayList<ModelItems>();
        arrayAdapter = new Adapter(this, arrayList);
        arrayAdapter2 = new AdapterArrival(this, arrayList2);
        interfaces = Client.getRetrofitInstance().create(InterfaceItems.class);

        btn_dc = (Button) findViewById(R.id.btn_dc);
        btn_dc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                lv3.setAdapter(arrayAdapter);
            }
        });
        btn_fresh = (Button) findViewById(R.id.btn_fresh);
        btn_fresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                lv3.setAdapter(arrayAdapter2);
            }
        });

        Intent intent = getIntent();
        storeTitle = intent.getStringExtra("storeTitle");
        title.setText(storeTitle);
        storeId = intent.getStringExtra("storeId");
//        markerLat = intent.getStringExtra("markerLat");
//        markerLon = intent.getStringExtra("markerLon");
        getFoodListDiscount();
        getFoodListFresh();
    }
    private void getFoodListDiscount(){ // 할인 음식 가져오기
        arrayList.clear();
        Call<ArrayList<ModelItems>> call = interfaces.getFoodData(Integer.parseInt(storeId)); // interface에 있는 함수
//        Toast.makeText(getApplicationContext(), "" + storeId, Toast.LENGTH_SHORT).show();
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
//                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
    private void getFoodListFresh(){ // 신선 음식 가져오기
        arrayList2.clear();
        Call<ArrayList<ModelItems>> call = interfaces.getFoodData2(Integer.parseInt(storeId)); // interface에 있는 함수
//        Toast.makeText(getApplicationContext(), "" + storeId, Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<ArrayList<ModelItems>>(){
            @Override
            public void onResponse(Call<ArrayList<ModelItems>> call, Response<ArrayList<ModelItems>> response){
                ArrayList<ModelItems> items = response.body();
                for (int i = 0; i < items.toArray().length; i++){
                    arrayList2.add(items.get(i));
                }
                arrayAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ModelItems>> call, Throwable t){
//                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}