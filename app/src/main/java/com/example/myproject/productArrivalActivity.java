package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class productArrivalActivity extends AppCompatActivity {

    Button btn_pa_update1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_arrival);

        btn_pa_update1 = (Button) findViewById(R.id.btn_pa_update1);
        btn_pa_update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(productArrivalActivity.this, arrivalupdateActivity.class);
                startActivity(intent);
            }
        });
    }
}