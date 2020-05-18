package com.nihon.aki2;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.Jsan;
import com.nihon.aki2.mydb.dbbasic50;
import com.nihon.aki2.mydb.dblisten;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Listening extends AppCompatActivity {
    ImageView play,btnpre,btnnext,pic;
    SeekBar sbar;
    int count=0;
    boolean tf=true;
    int totalTime=0;
    int L=0,T=0,length=0;
    String les="";
    String track="";
    TextView remainingTimeLabel;
    public MediaPlayer mediaplayer;
    String []filename=new String[45];
    String []lessons=new String[13];
    int []p1=new int[]{3,4,3,5,4,3,4,4,3,4,4,3};

    String url="https://akkyschool.com/images/listening/",ans="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listening);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        mediaplayer = new MediaPlayer();
        play = (ImageView) findViewById(R.id.play);
        pic=(ImageView)findViewById(R.id.pic);
        sbar = (SeekBar) findViewById(R.id.sbar);
        btnpre=(ImageView)findViewById(R.id.btnpre);
        btnnext=(ImageView)findViewById(R.id.btnnext);
        remainingTimeLabel=(TextView)findViewById(R.id.remainingTimeLabel);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
       ans= bundle.getString("ANS");
        if(ans.equals("A")){
            int j=0;
            for(int i=1;i<=12;i++){

                String t="L"+i;
                lessons[j]=t;
                j++;


            } length=45;
            pic.setImageResource(R.drawable.aki_p1);
            url="https://akkyschool.com/images/listening/primary1/";
        }
        else if(ans.equals("B")) {
            int j = 0;
            for (int i = 13; i <= 25; i++) {

                lessons[j] = "L" + i;
                j++;

            } length=42;
            url="https://akkyschool.com/images/listening/primary2/";
            p1=new int[]{2,4,3,4,3,3,3,3,3,4,3,3};
            pic.setImageResource(R.drawable.aki_p2);
        }
        else if(ans.equals("C")) {
            int j = 0;
            for (int i = 26; i <= 38; i++) {

                lessons[j] = "L" + i;
                j++;

            } length=39;
            p1=new int[]{2,3,3,3,3,3,3,3,3,3,3,3};
            url="https://akkyschool.com/images/listening/advance1/";
            pic.setImageResource(R.drawable.aki_a1);
        }
          else if(ans.equals("D")){
                int j=0;
                    for(int i=39;i<=50;i++){

                        lessons[j]="L"+i;
                        j++;
                    }
            length=36;
            p1=new int[]{2,3,3,3,3,3,3,3,3,3,3,3};
            url="https://akkyschool.com/images/listening/advance2/";
            pic.setImageResource(R.drawable.aki_a2);
        }
        play.setOnClickListener(playbyn);
        sbar.setOnSeekBarChangeListener(sbarbtn);
        for(int i=0;i<45;i++){
           int j=i+1;
           String tmp="T"+j+".mp3";
            filename[i]=tmp;
        }
      startDownload();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaplayer != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mediaplayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();
        btnnext.setOnClickListener(nextbtn);
        btnpre.setOnClickListener(prebtn);
    }
    private ImageView.OnClickListener nextbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            String tmp=url;
            count++;
            if(count>=length){mytoast("最後一個了");count--;}
            else {
                seraechsql(count);
                tmp+="L"+les+"/T"+track+".mp3";



                try {//+filename[0]
                    totalTime=0;
                    mediaplayer.reset();
                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaplayer.setDataSource(tmp);

                    mediaplayer.prepare();

                    totalTime=mediaplayer.getDuration();
                    sbar.setMax(totalTime);
                    mediaplayer.start();
                }
                catch (Exception e){}
            }


                  /*    */





        }
    };

    private ImageView.OnClickListener prebtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {

            String tmp=url;
            count--;
            if(count<0){mytoast("這是第一個");count=0;}
          else{
                seraechsql(count);
                tmp+="L"+les+"/T"+track+".mp3";



                try {//+filename[0]
                    totalTime=0;
                    mediaplayer.reset();
                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaplayer.setDataSource(tmp);

                    mediaplayer.prepare();

                    totalTime=mediaplayer.getDuration();
                    sbar.setMax(totalTime);
                    mediaplayer.start();
                }
                catch (Exception e){}
            }

        }
    };
    private  SeekBar.OnSeekBarChangeListener sbarbtn=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser){
                        mediaplayer.seekTo(progress);
                        sbar.setProgress(progress);
                    }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private ImageView.OnClickListener playbyn = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
           if(tf==true){
               tf=false;
               play.setImageResource(R.drawable.play);

           }
           else{
               tf=true;
               play.setImageResource(R.drawable.pause);
           }
            pauseplay();

        }
    };
    public void seraechsql(int count){
        //count


        String result = dblisten.executeQuery(ans+"");

        try{
            JSONArray jsonArray = new JSONArray(result);
            length=jsonArray.length();
            for(int i =count; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                les=jsonData.getString("lesson");
                track=jsonData.getString("track");
                break;

            }

        }

        catch(Exception e){}


    }
    private void playSong(int song) {


        String tmp=url;

        tmp+=lessons[L]+"/"+filename[T];

        try {//+filename[0]

            mediaplayer.reset();
            mediaplayer.setDataSource(tmp);
            mediaplayer.prepare();
            totalTime=mediaplayer.getDuration();
            sbar.setMax(totalTime);
            mediaplayer.start();
        } catch (IllegalStateException e) { }
        catch (IOException e) { }



    }
    private void startDownload() {
        String tmp=url;
        tmp+=lessons[L]+"/"+filename[T];
        new DownloadFileAsync().execute(tmp);
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {

            playSong(0);
            play.setImageResource(R.drawable.pause);
            tf=true;
            new Listening.DownloadFileAsync().cancel(true);
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            //   Log.d("ANDRO_ASYNC",progress[0]);
            // mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            // dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            sbar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
           // elapsedTimeLabel.setText(totalTime);

           String remainingTime = createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("" + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void pauseplay(){
        if(mediaplayer.isPlaying()){
            mediaplayer.pause();
        }
        else{
            mediaplayer.start();
        }
    }
    /* */
    @Override
    public void onPause(){
        super.onPause();
        mediaplayer.pause();
    }


}