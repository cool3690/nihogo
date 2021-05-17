package com.nihon.aki2;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.ads.AdView;
import com.nihon.aki2.control.GlobalVariable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Childstory extends AppCompatActivity    implements View.OnTouchListener{
    WebView myweb;
    String url="";
    int tmp=0,duration=0;
    ImageView myimg;
    ViewFlipper viewflipper;
    private GestureDetector mDetector;
    String []filename= new String[]{"st02_01.mp3","st02_02.mp3","st02_03.mp3","st02_04.mp3","st02_05.mp3"};
    int[] draw= new int[]{R.drawable.st02_01,R.drawable.st02_02,R.drawable.st02_03,R.drawable.st02_04,R.drawable.st02_05};
    MediaPlayer mediaplayer;
    MediaMetadataRetriever retriever;
    String account="",passwd="",names="",course_num="";
    private AdView mAdView;
    private Menu menu;
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childstory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();

        myimg=(ImageView)findViewById(R.id.myimg);
        viewflipper=(ViewFlipper)findViewById(R.id.viewflipper);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        viewflipper.setOnTouchListener(this);
        mDetector = new GestureDetector(new Childstory.simpleGestureListener());
        mediaplayer=new MediaPlayer();
        retriever = new MediaMetadataRetriever();

      new Childstory.MyTask().execute();
       // begin();
        /*
        url = "https://kei-sei.com/mp3/"+filename[tmp];

        try {
            mediaplayer.reset();
            mediaplayer.setDataSource(url);
            mediaplayer.prepare();
            mediaplayer.start();
            myimg.setImageResource(draw[tmp]);

        } catch (IOException e) { }

       viewflipper.addView(getImageView(R.drawable.st02_02),1,params);
        viewflipper.addView(getImageView(R.drawable.st02_03),2,params);
        viewflipper.addView(getImageView(R.drawable.st02_04),3,params);
        viewflipper.addView(getImageView(R.drawable.st02_05),4,params);
        viewflipper.startFlipping();
        viewflipper.setFlipInterval(15000);
 */
            mediaplayer.setOnPreparedListener(mbtn);


    }
    private MediaPlayer.OnPreparedListener mbtn=new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            duration = mp.getDuration();
        }
    };
    public void begin() {
        timer.schedule(t4, 1000, 15000) ;
    }


    TimerTask t4=new TimerTask() {
        @Override
        public void run() {
           int duration=20000;

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            url = "https://kei-sei.com/mp3/"+filename[tmp];

                            try {
                                mediaplayer.reset();
                                mediaplayer.setDataSource(url);
                                mediaplayer.prepare();
                                  mediaplayer.start();
                                 myimg.setImageResource(draw[tmp]);

                            } catch (IOException e) { }


                            tmp++;

                            if(tmp==5){tmp=0;}

                        }
                    });
                }
            }, duration);
        }
    };

    class MyTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            url = "https://kei-sei.com/mp3/"+filename[tmp];

            try {
                mediaplayer.stop();
                mediaplayer.reset();
                mediaplayer.setDataSource(url);
                mediaplayer.prepare();
                mediaplayer.start();
                myimg.setImageResource(draw[tmp]);


            } catch (IOException e) { }
            new Childstory.MyTask().cancel(true);
            return null;
        }
        protected void onProgressUpdate(Integer... progress) {
            //  setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {

        }
    }




    private ImageView getImageView(int id){
        ImageView imageView = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setImageResource(id);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return imageView;
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public boolean onTouch(View v, MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }
    private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener{
        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            // Fling left
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                if(tmp<4){
                    tmp++;
                    myimg.setImageResource(draw[tmp]);
                    url = "https://kei-sei.com/mp3/"+filename[tmp];

                    try {
                        mediaplayer.reset();
                        mediaplayer.setDataSource(url);
                        mediaplayer.prepare();
                        mediaplayer.start();
                        myimg.setImageResource(draw[tmp]);

                    } catch (IOException e) { }

                }
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling right
                if(tmp>0){
                    tmp--;
                    myimg.setImageResource(draw[tmp]);
                    url = "https://kei-sei.com/mp3/"+filename[tmp];

                    try {
                        mediaplayer.reset();
                        mediaplayer.setDataSource(url);
                        mediaplayer.prepare();
                        mediaplayer.start();
                        myimg.setImageResource(draw[tmp]);

                    } catch (IOException e) { }

                }


            }
            return true;
        }
    }
}
