package com.nihon.aki2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private TextView acc,pwd;
    private  ImageView  btn;
    private AdView mAdView;
   // private Button  forget;
    String mycart="",account="",names="",passwd="";
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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
        if(intent != null && intent.getExtras() != null ) {
            mycart = bundle.getString("CART");
        }
        //forget=(Button)findViewById(R.id.forget);
        acc=(TextView)findViewById(R.id.acc);
        pwd=(TextView)findViewById(R.id.pwd);
        btn=(ImageView)findViewById(R.id.btn);
        btn.setOnClickListener(btnlogin);
      //  forget.setOnClickListener(btnforget);
        SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        String name_str=remdname.getString("acc", "");
        String pass_str=remdname.getString("pwd", "");
        acc.setText(name_str);
        pwd.setText(pass_str);
        String myid=getString(R.string.appid);
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
    private Button.OnClickListener btnforget=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Login.this,Forgot.class);
            startActivity(intent);
        }
    };
    private ImageView.OnClickListener btnlogin=new ImageView.OnClickListener(){
        public void onClick(View v){
            String account=acc.getText().toString();
            String passwd=pwd.getText().toString();
            SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
            SharedPreferences.Editor edit=remdname.edit();
            edit.putString("acc", acc.getText().toString());
            edit.putString("pwd", pwd.getText().toString());
            edit.commit();
            GlobalVariable Account = (GlobalVariable)getApplicationContext();
            Account.setAccount(account);
            Account.setPasswd(passwd);
            String result =dblogin.executeQuery(account,passwd);

            try{
                JSONArray jsonArray = new JSONArray(result);

                for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
                {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                    String name=jsonData.getString("name");
                    String email=jsonData.getString("email");
                    Account.setNames(name);
                    if(mycart==""){
                        Intent intent= new Intent();
                        intent.setClass(Login.this, MainActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("ACCOUNT", account);
                        bundle.putString("NAME", name);
                        bundle.putString("EMAIL", email);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        Intent intent= new Intent();
                        intent.setClass(Login.this, Mcart.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("ACCOUNT", account);
                        bundle.putString("NAME", name);
                        bundle.putString("EMAIL", email);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    //mytoast(account+name+email);
                }
            }

            catch(Exception e){}



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

    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Login.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Login.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Login.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Login.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Login.this)
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
