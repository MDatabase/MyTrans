package com.example.mytrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.Activity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static final int DRAW_OVER_OTHER_APP_PERMISSION = 123;
    private Spinner tT,tbT;
    ArrayList<String> langList;
    ArrayAdapter<String> arrayAdapter;
    Button OnOffBtn;
    Button fBtn;
    TextView fTxt,rTT;
    ToggleButton ftoggle;
    MenuItem mainmenuitem;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        rTT=findViewById(R.id.reportTT);
        switch (item.getItemId()) {
            case R.id.ppgAPImenu:
                //Toast.makeText(this, "메뉴 이벤트가 처리되었다.",Toast.LENGTH_SHORT).show();
                rTT.setText("메뉴 이벤트가 처리되었다.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askForSystemOverlayPermission(); // 퍼미션을 묻는 함수

        mainmenuitem=findViewById(R.id.ppgAPImenu);
        ftoggle=findViewById(R.id.fTB);
        fBtn=findViewById(R.id.fbutton);
        rTT=findViewById(R.id.reportTT);
        //fTxt=findViewById(R.id.ftextView);
        int badge_count = getIntent().getIntExtra("badge_count", 0);

        //fTxt.setText(badge_count + " messages received previously");


        fBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent svc = new Intent(MainActivity.this,FloatingWidgetService.class);
                startService(svc);
                finish();
            }
        });
        ftoggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent fIT=new Intent(MainActivity.this, FloatingWidgetService.class);
                if (isChecked == true){
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(MainActivity.this)) {
                        startService(fIT);
                    } else {
                        errorToast();
                    }
                    //Toast.makeText(MainActivity.this, "플로팅버튼-ON", Toast.LENGTH_SHORT).show();
                   // rTT.setText("토글버튼 이벤트가 처리되었다.");
                } else {
                    //stopService(fIT);
                    //Toast.makeText(MainActivity.this, "플로팅버튼-OFF", Toast.LENGTH_SHORT).show();
                    //rTT.setText("토글버튼off 이벤트가 처리되었다.");
                }

            }
        });


        langList=new ArrayList<>();
        langList.add("한국어");
        langList.add("영어");
        langList.add("일본어");
        langList.add("중국어");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,langList);

        tT=findViewById(R.id.tTSpin);
        tT.setAdapter(arrayAdapter);
        tT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                //Toast.makeText(getApplicationContext(),langList.get(i)+"가 선택되었습니다.",Toast.LENGTH_SHORT).show();
                rTT.setText("스피너 이벤트가 처리되었다.");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        tbT=findViewById(R.id.tbTSpin);
        tbT.setAdapter(arrayAdapter);
        tbT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                //Toast.makeText(getApplicationContext(),langList.get(i)+"가 선택되었습니다.",Toast.LENGTH_SHORT).show();
                rTT.setText("스피너 이벤트가 처리되었다.");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });




    }
    private void askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available to open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION);
        }
    }
    protected void onPause() {
        super.onPause();


        // To prevent starting the service if the required permission is NOT granted.
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                //Permission is not available. Display error text.
                Toast.makeText(this, "Draw over other app permission not available. Can't start the application without the permission.", Toast.LENGTH_LONG).show();
                finish();
            }
        }else {
        //super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void errorToast() {
        Toast.makeText(this, "Draw over other app permission not available. Can't start the application without the permission.", Toast.LENGTH_LONG).show();
    }
}

