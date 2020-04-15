package com.nihon.aki2;

import android.os.Bundle;

import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class Childstory extends AppCompatActivity    implements View.OnTouchListener{
    WebView myweb;
    ViewFlipper viewflipper;
    private GestureDetector mDetector;
    String account="",passwd="",names="",course_num="";
    private AdView mAdView;
    private Menu menu;
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
        mDetector = new GestureDetector(new Childstory.simpleGestureListener());



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
}
