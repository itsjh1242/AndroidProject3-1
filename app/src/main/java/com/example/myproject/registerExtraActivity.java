package com.example.myproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerExtraActivity extends AppCompatActivity {

    String storeName, number, address; // 매장 이름, 사업자 등록 번호, 주소

    Button btn_okay;

    EditText edt_storeName, edt_Num, edt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_extra);

        btn_okay = (Button) findViewById(R.id.btn_extra_ok);

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
                    Toast.makeText(getApplicationContext(), "사업자(으)로 가입하셨습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerExtraActivity.this, MainActivity.class);
                    startActivity(intent);
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
}