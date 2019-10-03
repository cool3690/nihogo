package com.example.aki2;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Mymenu extends AppCompatActivity {
    ImageView btn1,btn2,btn3,btn4;
    String key="";
   String account="",passwd="";
    public static final String KEY = "com.example.aki2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymenu);
        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
      getSupportActionBar().setDisplayShowTitleEnabled(false);//不顯示 title
      */
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

        btn1=(ImageView)findViewById(R.id.btn1);
        btn1.setOnTouchListener(b1);
        SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
        String h = sharedPreferences.getString("input" , "0");
        //mytoast(h);
        schedulejob(h);

    }
    public void schedulejob(String tmp){

        ComponentName componentName=new ComponentName(this,JobSchedulerService.class);
        PersistableBundle bundle = new PersistableBundle();

            bundle.putString("INPUT",tmp);

        JobInfo jobInfo= new JobInfo.Builder(123,componentName)
                .setPersisted(true) // 重開機後是否執行
                .setMinimumLatency(3000) // 延遲多久執行
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) //網路條件
                .setExtras(bundle)
                .build();
        JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int result=scheduler.schedule(jobInfo);

    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private ImageView.OnTouchListener b1=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn1.setImageResource(R.drawable.aki_menuh);

                    break;
                case MotionEvent.ACTION_UP:
                    btn1.setImageResource(R.drawable.aki_menu);
                    Intent intent=new Intent();
                    intent.setClass(Mymenu.this, Menushow.class);
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
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Mymenu.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Mymenu.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this, Mymenu.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Mymenu.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
