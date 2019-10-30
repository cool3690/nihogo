package com.nihon.aki2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONObject;

public class Shiken extends AppCompatActivity {
ImageView bt1,bt2,bt3;
String account="",passwd="";
    String mychbun="",mychqa="";
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shiken);
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

        bt1=(ImageView)findViewById(R.id.bt01);
        bt2=(ImageView)findViewById(R.id.bt02);
        bt3=(ImageView)findViewById(R.id.bt03);

        bt1.setOnTouchListener(bt_1);
        bt2.setOnTouchListener(bt_2);
        bt3.setOnTouchListener(bt_3);
        String result = dbmych.executeQuery();
        String result2 = dbmychqa.executeQuery();
        try{
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++)
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                mychbun=jsonData.getString("mych");
               // mytoast(mychbun);
            }

            JSONArray jsonArray1=new JSONArray(result2);
            for(int i=0;i<jsonArray1.length();i++)
            {
                JSONObject jsonData=jsonArray1.getJSONObject(i);
                mychqa=jsonData.getString("mych");
            }
        }

        catch(Exception e){}
      //  mytoast(mychqa);
        MobileAds.initialize(this, "ca-app-pub-3776286057149986~2243725047");
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


    private ImageView.OnTouchListener bt_1=new ImageView.OnTouchListener(){//單字
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//單字

                case MotionEvent.ACTION_DOWN:
                    bt1.setImageResource(R.drawable.aki_tango);

                    break;
                case MotionEvent.ACTION_UP:
                    bt1.setImageResource(R.drawable.aki_tangoh);
                    Intent intent=new Intent();
                    intent.setClass(Shiken.this,QAmenu.class);

                    Bundle bundle=new Bundle();
                   bundle.putString("QA",mychqa);
                    intent.putExtras(bundle);

                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener bt_2=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//句子重組

                case MotionEvent.ACTION_DOWN:
                    bt2.setImageResource(R.drawable.aki_santan);

                    break;
                case MotionEvent.ACTION_UP:
                    bt2.setImageResource(R.drawable.aki_santanh);
                    Intent intent=new Intent();
                    intent.setClass(Shiken.this,Sanmenu.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("BUN",mychbun);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener bt_3=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//課文、50音Lesson

                case MotionEvent.ACTION_DOWN:
                    bt3.setImageResource(R.drawable.aki_basic);

                    break;
                case MotionEvent.ACTION_UP:
                    bt3.setImageResource(R.drawable.aki_basich);
                    Intent intent=new Intent();
                    intent.setClass(Shiken.this,Basicmenu.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Shiken.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Shiken.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Mymenu.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
