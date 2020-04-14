package com.nihon.aki2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import static android.widget.ListPopupWindow.MATCH_PARENT;

public class Comic extends AppCompatActivity    implements View.OnTouchListener {
    WebView myweb;
    ViewFlipper viewflipper;
    private GestureDetector mDetector;
    String account="",passwd="",names="",course_num="";
    private AdView mAdView;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comic);
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


        viewflipper=(ViewFlipper)findViewById(R.id.viewflipper);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

     // viewflipper.addView(getImageView(R.drawable.st01_1),0,params);
        viewflipper.addView(getImageView(R.drawable.st01_2),1,params);
        viewflipper.addView(getImageView(R.drawable.st01_3),2,params);
        viewflipper.addView(getImageView(R.drawable.st01_4),3,params);
        viewflipper.addView(getImageView(R.drawable.st01_5),4,params);


        viewflipper.startFlipping();
       // viewflipper.setFlipInterval(1000);
        viewflipper.setOnTouchListener( this);
        mDetector = new GestureDetector(new simpleGestureListener());
        /*
          myweb = (WebView) findViewById(R.id.webview);
        myweb.getSettings().setBuiltInZoomControls(true);
        myweb.getSettings().setJavaScriptEnabled(true);
       myweb.setWebViewClient(new WebViewClient());
       // https://www.ganganonline.com/
        //https://tonarinoyj.jp/episode/13933686331604558285
        myweb.loadUrl("https://drive.google.com/file/d/1c5nXMQYmfJ_nlgNrW-5pHVROkwpR9zu5/view?usp=sharing");
        myad();

*/
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
    public boolean onTouch(View v, MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }
    private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener{
        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
        @Override
        public boolean onDown(MotionEvent e) {
            // TODO Auto-generated method stub

            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            // Fling left     
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {

                viewflipper.showNext();
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling right     

                viewflipper.showPrevious();

            }
            return true;
        }
    }

    /*
    public void onBackPressed() {
        if (myweb.canGoBack()) {
            myweb.goBack();
            return;
        }

        super.onBackPressed();
    }

     */
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
