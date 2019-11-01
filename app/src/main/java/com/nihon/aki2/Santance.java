package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONObject;

public class Santance extends AppCompatActivity {
Button bt1,bt2,bt3,bt0,ok;
ImageView btnpre,btnnext;
TextView ch,jp;
String str1="",str2="",str3="";
String ans="",myans="_____",myans2="_____",myans3="_____",myans4="_____";
String anstotal="",mych="";
int num=4,len=0;
String [] contain=new String[4];
    String account="",passwd="",names="";
    private Menu menu;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.santance);
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
        num=Integer.valueOf(bundle.getString("NUM"));
        account=Account.getAccount();
        passwd=Account.getPasswd();
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt3=(Button)findViewById(R.id.bt3);
        bt0=(Button)findViewById(R.id.bt0);
        ok=(Button)findViewById(R.id.ok);
        btnpre=(ImageView)findViewById(R.id.btnpre);
        btnnext=(ImageView)findViewById(R.id.btnnext);
        jp=(TextView)findViewById(R.id.jp);
        ch=(TextView)findViewById(R.id.chinese);
        bt1.setOnClickListener(bt_1);
        bt2.setOnClickListener(bt_2);
        bt3.setOnClickListener(bt_3);
        bt0.setOnClickListener(bt_0);
        ok.setOnClickListener(okbtn);
        btnpre.setOnTouchListener(btpre);

        btnnext.setOnTouchListener(btnext);
        test(num);
        loadInterstitialAd();

    }
    private void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3776286057149986/3596969621");
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
    private Button.OnClickListener bt_1=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(anstotal.contains(contain[1])){
                bt1.setText(contain[1]);
                anstotal=  anstotal.replace(contain[1]+" ","");
              line();
            }
            else{
                anstotal+=contain[1]+" ";
                line();
            }

        }
    };
    private Button.OnClickListener bt_2=new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(anstotal.contains(contain[2])){
                bt2.setText(contain[2]);
                anstotal=  anstotal.replace(contain[2]+" ","");
                line();
            }
            else{
                anstotal+=contain[2]+" ";
                line();
            }

        }
    };
    private Button.OnClickListener bt_3=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(anstotal.contains(contain[3])){
                bt3.setText(contain[3]);
                anstotal=  anstotal.replace(contain[3]+" ","");
                line();
            }
            else{
                anstotal+=contain[3]+" ";
                line();
            }

        }
    };
    private Button.OnClickListener bt_0=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(anstotal.contains(contain[0])){
                bt0.setText(contain[0]);
                anstotal=  anstotal.replace(contain[0]+" ","");
                line();
            }
            else{
                anstotal+=contain[0]+" ";
                line();
            }

        }
    };
    private Button.OnClickListener okbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
           String tmp= str1+anstotal+str2;

            tmp=  tmp.replace(" ","");
            tmp=tmp.replace("（","");
            tmp=tmp.replace("）","");
            ans=ans.replace("）","");
            ans=ans.replace("（","");
            ans=  ans.replace(" ","");
            if(tmp.contains(ans)){mytoast("正解!");}

            else{mytoast(ans);
                clean();
                test(num);
               }

        }
    };
    private ImageView.OnTouchListener btnext=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    btnnext.setImageResource(R.drawable.aki_nexth);

                    break;
                case MotionEvent.ACTION_UP:
                    btnnext.setImageResource(R.drawable.aki_next);
                    num++;
                    String result = dbsantance.executeQuery(num+"",mych);
                    if(result.contains("null")){ num--;mytoast("本題為最後一題");}
                    else{
                        clean();
                        test(num);
                    }
                    break;
            }
            return true;
        }


    };
    private ImageView.OnTouchListener btpre=new ImageView.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    btnpre.setImageResource(R.drawable.aki_preh);

                    break;
                case MotionEvent.ACTION_UP:
                    btnpre.setImageResource(R.drawable.aki_pre);
                     num--;
                       String result = dbsantance.executeQuery(num+"",mych);
                       if(result.contains("null")){ num++;mytoast("本題為第一題");}
                      else{
                          clean();
                         test(num);
                        }
                     break;
        }
            return true;
    }


};
    private void clean(){
        str1="";
        str2="";
        myans="_____";
        myans2="_____";
        myans3="_____";
        myans4="_____";
        anstotal="";
        jp.setText("");
    }
    private void line(){
        String []s={"____","____","____","____"};
       String s1[]=anstotal.split(" ");

        for(int y=0;y<s1.length;y++){
           s[y]=s1[y];
        }
       // mytoast(s1.length+""+s[0]+"/"+s[1]+"/"+s[2]+"/"+s[3]);
        /*
        for(int y=0;y<s.length;y++){
            if(y>=s1.length)
            {s[y]="_____";}

        }*/
        //mytoast(myans+myans2);
        SpannableString myStr1 = new SpannableString(str1);
        SpannableString myStr2 = new SpannableString(str2);
        SpannableString A = new SpannableString(s[0]);
        A.setSpan(new UnderlineSpan(), 0, s[0].length(), 0);
        SpannableString B = new SpannableString(s[1]);
        B.setSpan(new UnderlineSpan(), 0, s[1].length(), 0);
        SpannableString C = new SpannableString(s[2]);
        C.setSpan(new UnderlineSpan(), 0, s[2].length(), 0);
        SpannableString D = new SpannableString(s[3]);
        D.setSpan(new UnderlineSpan(), 0, s[3].length(), 0);
        jp.setText(TextUtils.concat(myStr1, "  ", A,"  ",B,"  ",C,"  ",D, "  ", myStr2));
       /* if(len==2){
            jp.setText(TextUtils.concat(myStr1, "  ", A,"  ",B,"  ",C,"  ",D, "  ", myStr2));
        }
       if(len==3){
           jp.setText(TextUtils.concat( A,"  ",myStr1, "  ",B,"  ",C,"  ",D, "  ", myStr2));
       }*/
    }
    private void test(int num) {
        String result = dbsantance.executeQuery(num+"",mych);

        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                ch.setText(jsonData.getString("ch"));
                ans=jsonData.getString("jp");
                String Q=jsonData.getString("Q");
                String tmp=jsonData.getString("contain");
                String QA[]=Q.split("_");
                str1=QA[0];
                str2=QA[1];
                len=QA.length;
                jp.setText(str1+" "+myans+"  "+myans2+"  "+myans3+"  "+myans4+"  "+str2);
                /*
                if(QA.length==2){
                    jp.setText(str1+" "+myans+"  "+myans2+"  "+myans3+"  "+myans4+"  "+str2);
                }
                if(QA.length==3 && str1.equals("")){
                    str1=str2;
                    str2=QA[2];
                    jp.setText(myans+" "+str1+"  "+myans2+"  "+myans3+"  "+myans4+"  "+str2);
                }
               else if(QA.length==3 && !str1.equals("")){

                    jp.setText(str1+" "+myans+"  "+myans2+"  "+str2+"  "+myans3+"  "+myans4+"  "+str3);

                }
                */
               // mytoast(QA[0]+" ");
                String t[]=tmp.split(",");
                int [] r={0,0,0,0};
                int [] r1={0,0,0,0};
                for(int h=0;h<r1.length;h++){
                    int random=(int)(Math.random()*4);
                    if(r1[random]==0){
                        r1[random]=1;
                        r[h]=random;
                    }
                    else{
                        h--;
                    }
                }

                for(int j=0;j<contain.length;j++){  contain[j]=" ";}
                for(int j=0;j<contain.length;j++)
                {
                    contain[j]=t[r[j]];
                }

            }
            bt1.setText(contain[1]);
            bt2.setText(contain[2]);
            bt3.setText(contain[3]);
            bt0.setText(contain[0]);
        }

        catch(Exception e){}

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
            intent.setClass(Santance.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Santance.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Santance.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Santance.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Santance.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Santance.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Santance.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Santance.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Santance.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Santance.this)
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
