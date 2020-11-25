package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.nihon.aki2.mydb.dbQA;

import org.json.JSONArray;
import org.json.JSONObject;

public class QA extends AppCompatActivity {
TextView Q1,txtResult;
ImageView nextpage,prepage;
RadioGroup radioGroup;
RadioButton a1,a2,a3,a4;
    private AdView mAdView;
int count=0;
boolean lock=true,anstmp=true;
String ans="",account="",passwd="",mych="",names="";
private Menu menu;
int num=3,yes=0,no=0;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa);
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

        mych=bundle.getString("MYCH");
        if(bundle.getString("NUM").equals("")){

        }
        else{
            num=Integer.valueOf(bundle.getString("NUM"));
        }


/*
        mych="3";
        num=22;*/
        Q1=(TextView)findViewById(R.id.Q1);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup) ;
        a1=(RadioButton)findViewById(R.id.a1);
        a2=(RadioButton)findViewById(R.id.a2);
        a3=(RadioButton)findViewById(R.id.a3);
        a4=(RadioButton)findViewById(R.id.a4);
        nextpage=(ImageView)findViewById(R.id.nextpage);
        prepage=(ImageView)findViewById(R.id.prepage);
        txtResult=(TextView)findViewById(R.id.txtResult);

        String result = dbQA.executeQuery(num+"",mych);
        String topic="";
        try{
            JSONArray jsonArray = new JSONArray(result);


            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
            JSONObject jsonData = jsonArray.getJSONObject(i);
              topic=jsonData.getString("Q1");
                Q1.setText(topic);
                a1.setText(jsonData.getString("A1"));
                a2.setText(jsonData.getString("A2"));
                a3.setText(jsonData.getString("A3"));
                a4.setText(jsonData.getString("A4"));
                ans=jsonData.getString("ans");
            }

        }

        catch(Exception e){}
        radioGroup.setOnCheckedChangeListener(answer);
        nextpage.setOnTouchListener(page);
       prepage.setOnTouchListener(pagepre);
       // prepage.setOnClickListener(pagepre2);
        String myid=getString(R.string.appid);
        MobileAds.initialize(this, myid);
        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
       // loadInterstitialAd();

    }
    private void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        String st = getString(R.string.idin);
        mInterstitialAd.setAdUnitId(st);
          mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
              if(mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }
    private RadioGroup.OnCheckedChangeListener answer=
            new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int  p = group.indexOfChild((RadioButton) findViewById(checkedId));	// 選項的索引值
                    int count = group.getChildCount(); // 清單總共有多少項
                    p++;
                 if(lock==true){ show (p);}
                    else{}

//mytoast(yes+"");
                }
            };

    void show (int p){
        if(ans.equals(p+"")){mytoast("正解!");anstmp=true; next();}
        else{mytoast("錯誤!");anstmp=false;txtResult.setText("正確答案是:  "+ans  );}

    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused) {
            next();
        }
    }
public void next(){
  num++;
  count++;
  if(count==5){ // loadInterstitialAd();
      }
    lock=false;
    radioGroup.clearCheck();
 if(anstmp==true){yes++;}
 else{no++;}
    String result = dbQA.executeQuery(num+"",mych);
    if(result.contains("null")){ num--;
    mytoast("本題為最後一題");
    //if(yes<0){yes=yes*(-1);}
       // mytoast(yes+"/"+no);

        Intent intent=new Intent();
        intent.setClass(QA.this,Keka.class);
        Bundle bundle=new Bundle();
        bundle.putString("NO", no+"");
        bundle.putString("YES", yes+"");
        intent.putExtras(bundle);
        startActivity(intent);

    }

    txtResult.setText(" ");
    String topic="";
    try{
        JSONArray jsonArray = new JSONArray(result);

        int k=0;
        // bt.setText("更多資訊");
        for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
        {
            JSONObject jsonData = jsonArray.getJSONObject(i);
            topic=jsonData.getString("Q1");
            Q1.setText(topic);
            a1.setText(jsonData.getString("A1"));
            a2.setText(jsonData.getString("A2"));
            a3.setText(jsonData.getString("A3"));
            a4.setText(jsonData.getString("A4"));
            ans=jsonData.getString("ans");
        }

    }

    catch(Exception e){}
    lock=true;
}
    private  ImageView.OnTouchListener page=new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    nextpage.setImageResource(R.drawable.aki_nexth);

                    break;
                case MotionEvent.ACTION_UP:
                    nextpage.setImageResource(R.drawable.aki_next);
                    next();
                    break;
            }
            return true;
        }
    };

    private  ImageView.OnTouchListener pagepre=new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    prepage.setImageResource(R.drawable.aki_preh);

                    break;
                case MotionEvent.ACTION_UP:
                    prepage.setImageResource(R.drawable.aki_pre);
                    pre();
                    break;
            }
           return true;
        }
    };
public void pre(){
    num--;
    lock=false;
    radioGroup.clearCheck();
    if(anstmp==true){yes++;}
    else{no++;}
    txtResult.setText(" ");
    String result = dbQA.executeQuery(num+"",mych);
    if(result.contains("null")){ num++;
    mytoast("本題為第一題");
    }

    String topic="";
    try{
        JSONArray jsonArray = new JSONArray(result);

        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonData = jsonArray.getJSONObject(i);
            topic=jsonData.getString("Q1");
            Q1.setText(topic);
            a1.setText(jsonData.getString("A1"));
            a2.setText(jsonData.getString("A2"));
            a3.setText(jsonData.getString("A3"));
            a4.setText(jsonData.getString("A4"));
            ans=jsonData.getString("ans");
        }

    }

    catch(Exception e){}
    lock=true;
}

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
            intent.setClass(QA.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(QA.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(QA.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(QA.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(QA.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(QA.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(QA.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(QA.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(QA.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(QA.this)
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
