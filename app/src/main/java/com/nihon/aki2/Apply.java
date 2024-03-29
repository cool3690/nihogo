package com.nihon.aki2;

import android.app.Dialog;
import android.content.Context;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nihon.aki2.control.GlobalVariable;
import com.nihon.aki2.mydb.dbapply;
import com.nihon.aki2.mydb.dbinapply;

import org.json.JSONArray;

public class Apply extends AppCompatActivity {
    private TextView phone,pass,name,email;
    String account="",passwd="",names="";
    private Menu menu;
    boolean check=true;
    MCrypt mcrypt = new MCrypt();
    //private AdView mAdView;
    Dialog dia;
    Context context;
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
        names=Account.getNames();
        phone=(TextView)findViewById(R.id.phone);
        pass=(TextView)findViewById(R.id.pass);
        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        ok=(ImageView)findViewById(R.id.ok);
        ok.setOnClickListener(okbtn);

        /*
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
        */
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
                mytoast("帳號申請成功! ");
                try {
                    MCrypt mcrypt = new MCrypt();

                    String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(mpass) );

                    dbinapply.executeQuery(account,encrypted,mname,memail);
                   // String decrypted = new String( mcrypt.decrypt( encrypted ) );

                } catch( Exception e ) {  }


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
            intent.setClass(Apply.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Apply.this, Menushow.class);
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
                 /*
            new AlertDialog.Builder(Apply.this)
                    .setTitle("版權所有")
                    .setIcon(R.drawable.righticon)
                    .setMessage("新澄管理顧問公司"+"\n臺南市私立慶誠文理短期補習班"+"\n連絡：sonyzone2004@gmail.com")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                        }
                    })
                    .show();
            */
            context = Apply.this;
            dia = new Dialog(context, R.style.rightcopystyle);
            dia.setContentView(R.layout.copyright);
            Button btok=(Button)dia.findViewById(R.id.btok);
            dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
            Window w = dia.getWindow();
            WindowManager.LayoutParams lp = w.getAttributes();
            lp.x = 0; // 新位置X坐標
            lp.width =950; // 寬度
            dia.show();
            dia.onWindowAttributesChanged(lp);
            btok.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia.dismiss();
                        }
                    }
            );
        }
        return super.onOptionsItemSelected(item);
    }
}
