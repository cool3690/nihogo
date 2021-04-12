package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.nihon.aki2.mydb.dbmych;
import com.nihon.aki2.mydb.dbmychqa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Grading extends AppCompatActivity {
    ImageView bt1,bt2;
    String account="",passwd="",names="";
    String level="";
    private Menu menu;
    String mychbun="",mychqa="";
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grading);
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
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        level=bundle.getString("LEVEL");
        bt1=(ImageView)findViewById(R.id.bt01);
        bt2=(ImageView)findViewById(R.id.bt02);

        bt1.setOnTouchListener(bt_1);
        bt2.setOnTouchListener(bt_2);
        String myid=getString(R.string.appid);
        MobileAds.initialize(this, myid);
        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
       // mydb();

    }
    public  void mydb(){
        String result = dbmych.executeQuery();

        try{
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++)
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                int a=jsonData.getInt("mych");
                mychbun=a+"";
                // mytoast(mychbun);
            }


        }

        catch(Exception e){}
    }
    public  void mydb2(){

        String result2 = dbmychqa.executeQuery();
        try{

            JSONArray jsonArray1=new JSONArray(result2);
            for(int i=0;i<jsonArray1.length();i++)
            {
                JSONObject jsonData=jsonArray1.getJSONObject(i);
                int a=jsonData.getInt("mych");
                mychqa=a+"";
            }
        }

        catch(Exception e){}
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
                    mydb2();
                    Intent intent=new Intent();
                    intent.setClass(Grading.this,QAmenu.class);

                    Bundle bundle=new Bundle();
                    bundle.putString("QA",mychqa);
                    bundle.putString("LEVEL",level);
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
                    mydb();
                    Intent intent=new Intent();
                    intent.setClass(Grading.this,Sanmenu.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("BUN",mychbun);
                    bundle.putString("LEVEL",level);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(names==null ||names=="" ){
            menu.findItem(R.id.login).setTitle("登入");
        }
        else{
            menu.findItem(R.id.login).setTitle("歡迎"+names);
        }
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Grading.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Grading.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Grading.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Grading.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Grading.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Grading.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Grading.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Grading.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Grading.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Grading.this)
                    .setTitle("版權所有")
                    .setIcon(R.drawable.ic_launcher)
                    .setMessage("新澄管理顧問公司"+"\n台南私立亞紀塾日語短期補習班"+"\nふじやま國際學院")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
