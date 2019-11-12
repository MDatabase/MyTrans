package com.example.mytrans;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.Toast;

public class MusicService extends Service {

    MediaPlayer myplayer;

    public IBinder onBind(Intent myintent){
        return null;
    }
    public void onCreate(){
        myplayer = MediaPlayer.create(this,R.raw.song1);

        myplayer.setLooping(false);
    }
    public void onDestroy(){
        Toast.makeText(this,"MusicService 가 중지되었음.",Toast.LENGTH_SHORT).show();
        myplayer.stop();
    }
    public int onStartCommand(Intent myintent, int flags,int startId){
        Toast.makeText(this,"MusicService가 시작됨.",Toast.LENGTH_LONG).show();
        myplayer.start();
        return super.onStartCommand(myintent,flags,startId);
    }


}
