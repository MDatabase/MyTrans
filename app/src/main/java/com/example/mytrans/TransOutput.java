package com.example.mytrans;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransOutput extends AppCompatActivity {
    TessBaseAPI tess;
    String dataPath="";
    Button ocrbtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_activity);

        setTitle("번역 결과 출력 액티비티");

        // 데이터 경로
        dataPath=getFilesDir()+"/tessract/";

        // 한글, 영어, 일본어 확인
        checkFile(new File(dataPath+"tessdata/"),"kor");
        checkFile(new File(dataPath+"tessdata/"),"eng");
        checkFile(new File(dataPath+"tessdata/"),"jpn");

        // 문자 인식을 수행할 tess 객체 생성
        String lang = "kor+eng+jpn";
        tess=new TessBaseAPI();
        tess.init(dataPath,lang);

        //문자 인식 수행
        ocrbtn=findViewById(R.id.ocrBtn);
        ocrbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                processImage(BitmapFactory.decodeResource(getResources(), R.drawable.test/*인식할 파일*/));
            }
        });

    }
    public void processImage(Bitmap bitmap){
        Toast.makeText(getApplicationContext(), "이미지가 복잡할 경우 해석 시 많은 시간이 소요될 수도 있습니다.", Toast.LENGTH_LONG).show();
        String OCRresult = null;
        tess.setImage(bitmap);
        OCRresult = tess.getUTF8Text();
        TextView OCRTextView = (TextView) findViewById(R.id.ocrTV/*결과를 출력할 텍스트뷰 아이디*/);

        OCRTextView.setText(OCRresult);
    }
    private void copyFiles(String lang) {
        try {
            //location we want the file to be at
            String filepath = dataPath + "/tessdata/" + lang + ".traineddata";

            //get access to AssetManager
            AssetManager assetManager = getAssets();

            //open byte streams for reading/writing
            InputStream inStream = assetManager.open("tessdata/" + lang + ".traineddata");
            OutputStream outStream = new FileOutputStream(filepath);

            //copy the file to the location specified by filepath
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, read);
            }
            outStream.flush();
            outStream.close();
            inStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void checkFile(File dir, String lang) {
        //directory does not exist, but we can successfully create it
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles(lang);
        }
        //The directory exists, but there is no data file in it
        if(dir.exists()) {
            String datafilePath = dataPath+ "/tessdata/" + lang + ".traineddata";
            File datafile = new File(datafilePath);
            if (!datafile.exists()) {
                copyFiles(lang);
            }
        }
    }
}
