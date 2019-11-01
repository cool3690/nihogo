package com.nihon.aki2;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Mymenu extends AppCompatActivity {
    ImageView btn1,btn2,btn3,btn4;
    String key="";
   String account="",passwd="",names="";  private Menu menu;
    public static final String KEY = "com.example.aki2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymenu);

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
        btn1=(ImageView)findViewById(R.id.btn1);
        btn1.setOnTouchListener(b1);
        SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
        String h = sharedPreferences.getString("input" , "0");

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

                    //h
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
            intent.setClass(Mymenu.this, Menushow.class);
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
        if (id == R.id.about) {
            new AlertDialog.Builder(Mymenu.this)
                    .setTitle("版權所有")
                    .setIcon(R.drawable.ic_launcher)
                    .setMessage("新澄管理顧問公司"+"\n台南私立亞紀塾日語短期補習班")
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
