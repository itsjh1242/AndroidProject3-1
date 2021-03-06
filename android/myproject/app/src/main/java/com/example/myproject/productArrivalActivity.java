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

public class productArrivalActivity extends AppCompatActivity {

    String TAG = "activity_product_arrival";

    Button btn_pa_update1, btn_pa_delete1;
    String Data_User_StoreId;


    ListView lv2;
    ArrayList<ModelItems> arrayList;
    AdapterArrival arrayAdapter;
    InterfaceItems interfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_arrival);

        SharedPreferences preferences = getSharedPreferences("user_storeid", Context.MODE_PRIVATE);
        Data_User_StoreId = preferences.getString("user_storeid","");

        lv2 = (ListView)findViewById(R.id.lv2);
        arrayList = new ArrayList<ModelItems>();
        arrayAdapter = new AdapterArrival(this, arrayList);
        interfaces = Client.getRetrofitInstance().create(InterfaceItems.class);
        lv2.setAdapter(arrayAdapter);

        btn_pa_update1 = (Button) findViewById(R.id.btn_pa_update1);
        btn_pa_update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(productArrivalActivity.this, arrivalupdateActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_pa_delete1 = (Button) findViewById(R.id.btn_pa_delete1);
        btn_pa_delete1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder ad = new AlertDialog.Builder(productArrivalActivity.this);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setMessage("????????? ????????? ????????? ??????????????????!");
                ad.setTitle("????????? ?????? ??????");
                final EditText adet = new EditText(productArrivalActivity.this);
                adet.setInputType(InputType.TYPE_CLASS_NUMBER);
                ad.setView(adet);

                ad.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int result = Integer.parseInt(adet.getText().toString());
                        // ?????? ??????
                        try{
                            InterfaceItems interfaceitems = Client.getRetrofitInstance().create(InterfaceItems.class);
                            interfaceitems.deleteFoodData(result).enqueue(new Callback(){
                                @Override
                                public void onResponse(Call call, Response response){
                                    if (response.isSuccessful()) {
                                        Log.e(TAG, "Delete" + "delete ??????");
                                    }
                                }
                                @Override
                                public void onFailure(Call call, Throwable t){

                                }
                            });
                            dialog.dismiss();
                            // ???????????? ????????????
                            finish();//????????? ??????
                            overridePendingTransition(0, 0);//????????? ?????? ?????????
                            Intent intent = getIntent(); //?????????
                            startActivity(intent); //???????????? ??????
                            overridePendingTransition(0, 0);//????????? ?????? ?????????

                        } catch (Exception e){ // ?????? ?????? ????????? ?????? ???????????? ???
                            Toast.makeText(getApplicationContext(), "?????? ?????? ??????????????????!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ad.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });
        getFoodList();
    }
    private void getFoodList(){
        arrayList.clear();
        Call<ArrayList<ModelItems>> call = interfaces.getFoodData2(Integer.parseInt(Data_User_StoreId)); // interface??? ?????? ?????????
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