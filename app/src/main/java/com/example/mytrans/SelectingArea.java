package com.example.mytrans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectingArea extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_activity);

        setTitle("원하는 영역 지정");
        Button temp_button=findViewById(R.id.tempBtn);


        temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temp_it=new Intent(SelectingArea.this,TransOutput.class);
                startActivity(temp_it);
            }
        });
    }
}
