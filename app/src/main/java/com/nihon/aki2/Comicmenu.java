package com.nihon.aki2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Comicmenu extends AppCompatActivity {
ImageView com1,com2,com3;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comicmenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        com1=(ImageView) findViewById(R.id.com1);
        com2=(ImageView) findViewById(R.id.com2);
        com3=(ImageView) findViewById(R.id.com3);
        com1.setOnTouchListener(com1btn);
        com2.setOnTouchListener(com1btn2);
        com3.setOnTouchListener(com1btn3);
        myad();
    }

    private ImageView.OnTouchListener com1btn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {//一拳超人

                case MotionEvent.ACTION_DOWN:
                    com1.setImageResource(R.drawable.aki_super);

                    break;
                case MotionEvent.ACTION_UP:
                    com1.setImageResource(R.drawable.aki_super);
                    Intent intent = new Intent();
                    intent.setClass(Comicmenu.this, Comic.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("WEB","A");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener com1btn2=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {//一拳超人

                case MotionEvent.ACTION_DOWN:
                    com2.setImageResource(R.drawable.aki_super);

                    break;
                case MotionEvent.ACTION_UP:
                    com2.setImageResource(R.drawable.aki_super);
                    Intent intent = new Intent();
                    intent.setClass(Comicmenu.this, Comic.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("WEB","B");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener com1btn3=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {//一拳超人

                case MotionEvent.ACTION_DOWN:
                    com3.setImageResource(R.drawable.aki_super);

                    break;
                case MotionEvent.ACTION_UP:
                    com3.setImageResource(R.drawable.aki_super);
                    Intent intent = new Intent();
                    intent.setClass(Comicmenu.this, Childstory.class);
                    //Work.class
                    //  intent.setClass(Menushow.this,Myweb.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    public void myad() {
        String myid = getString(R.string.idban);
        MobileAds.initialize(this, myid);
        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });

    }
}
