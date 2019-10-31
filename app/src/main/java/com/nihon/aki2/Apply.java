package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;

public class Apply extends AppCompatActivity {
    private TextView phone,pass,name,email;
    String account="",passwd="";
    boolean check=true;
    private AdView mAdView;
    //private Button  forget;
    ImageView ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply);
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
        phone=(TextView)findViewById(R.id.phone);
        pass=(TextView)findViewById(R.id.pass);
        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        ok=(ImageView)findViewById(R.id.ok);
       // forget=(Button)findViewById(R.id.forget);
        ok.setOnClickListener(okbtn);
       // forget.setOnClickListener(forgetbtn);
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
    private ImageView.OnClickListener okbtn= new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
        account=phone.getText().toString();
            int tmp=0;
            check=empty(check);
            try{
                String result = dbapply.executeQuery(account);
               JSONArray jsonArray = new JSONArray(result);
                    if(jsonArray.length()>0){tmp=1;mytoast("此帳號已註冊!");}
            }

            catch(Exception e){}

            check=empty(check);

            if(!check || tmp==1){//false
                // mytoast("test not ok!"+check);
            }
            else{//true
               String mpass= pass.getText().toString();
                String mname= name.getText().toString();
                String memail= email.getText().toString();
                mytoast("OK! "+tmp);
                dbinapply.executeQuery(account,mpass,mname,memail);
            }
        }
    };

    private Button.OnClickListener forgetbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Apply.this,Forgot.class);
            startActivity(intent);
        }
    };
    public  boolean empty(boolean a){
        a=true;
        if(TextUtils.isEmpty(phone.getText().toString())){
            mytoast("請輸入帳號(手機號碼)");
            a=false;
        }
        if(TextUtils.isEmpty(pass.getText().toString())){
            mytoast("請輸入密碼)");
            a=false;
        }
        if(TextUtils.isEmpty(name.getText().toString())){
            mytoast("請輸入姓名)");
            a=false;
        }
        if(TextUtils.isEmpty(email.getText().toString())){
            mytoast("請輸入信箱)");
            a=false;
        }

        return a;
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
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
            intent.setClass(Apply.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null ||account==""){
                Intent intent=new Intent();
                intent.setClass(Apply.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Apply.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Apply.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Apply.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Apply.this, Mymenu.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Apply.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Apply.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Apply.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Apply.this)
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
