package com.example.iconparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    EditText
            mallName;
    Button book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       book=findViewById(R.id.book);
       mallName=findViewById(R.id.search);





       book.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String name=mallName.getText().toString();
               if(name.equalsIgnoreCase("DLF MALL"))
               {
                   Intent in=new Intent(Home.this,mallActivity.class);
                   in.putExtra("mallName",name);
                   startActivity(in);
                   finish();
               }
               else if(name.equalsIgnoreCase("DLF MALL"))
               {
                   Intent in=new Intent(Home.this,mallActivity.class);
                   in.putExtra("mallName",name);
                   startActivity(in);
                   finish();
               }
           }
       });
    }
}
