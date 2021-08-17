package com.nihon.aki2;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nihon.aki2.control.GlobalVariable;
import com.nihon.aki2.control.JobSchedulerService;

import java.io.File;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Mymenu extends AppCompatActivity {
   // ImageView btn1,btn2,btn3,btn4;
    String key="";
   String account="",passwd="",names="";  private Menu menu;
    public static final String KEY = "com.example.aki2";
    Button btn1,btn2;
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
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn1.setOnClickListener(b1);
        btn2.setOnClickListener(b2);
        SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
        String h = sharedPreferences.getString("input" , "0");


        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
        {
            mytoast("請開啟網路!");
        }
        else{
           // schedulejob(h);
        }
        clearApplicationData();

    }
    public void schedulejob(String tmp){

        ComponentName componentName=new ComponentName(this, JobSchedulerService.class);
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

    private Button.OnClickListener b1=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {


            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=connManager.getActiveNetworkInfo();
            if (info == null || !info.isConnected())
            {
                mytoast("請開啟網路!");
            }
            else{

                Intent intent=new Intent();

                //Menushow.class  Jlptword
                //  intent.setClass(Mymenu.this,Listenchild.class);
                intent.setClass(Mymenu.this, Menushow.class);
                startActivity(intent);
            }
        }
    };
    private Button.OnClickListener b2=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {


            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=connManager.getActiveNetworkInfo();
            if (info == null || !info.isConnected())
            {
                mytoast("請開啟網路!");
            }
            else{

                Intent intent=new Intent();
                intent.setClass(Mymenu.this, Book_k.class);
                startActivity(intent);
            }
        }
    };
    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){
            String[] children = appDir.list();
            for(String s : children){
                if(!s.equals("lib")){
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File/data/data/APP_PACKAGE/" + s +" DELETED *******************");
                }
            }
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
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
            intent.setClass(Mymenu.this, Myweb.class);
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
                    .setIcon(R.drawable.righticon)
                    .setMessage("新澄管理顧問公司"+"\n臺南市私立慶誠文理短期補習班"+"\n連絡：sonyzone2004@gmail.com")
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
