package com.example.iconparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QRCodeGenerator extends AppCompatActivity {

    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);
        tv1=findViewById(R.id.arivalTime);
        tv2=findViewById(R.id.leavingTime);

        Intent in=getIntent();
        String timeArrival =in.getStringExtra("arrival");
        String timeLeave= in.getStringExtra("leave");

        tv1.setText(timeArrival+":00 Am");
        tv2.setText(timeLeave+":00 Am");

    }
}
