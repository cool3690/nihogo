package com.nihon.aki2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.nihon.aki2.control.GlobalVariable;
import com.nihon.aki2.mydb.dbbasich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Basich extends AppCompatActivity {
    TextView Q1,txtResult;
    ImageView nextpage,prepage;
    RadioGroup radioGroup;
    Button btsong;
    private InterstitialAd mInterstitialAd;
    RadioButton a1,a2,a3,a4;
    boolean lock=true;
    String  account="",passwd="",names="";
    int count=0,ans=0;
    private Menu menu;
    private AdView mAdView;
    public MediaPlayer mediaplayer;
    Context context;
    int wrong=0;
    Dialog dia;
    int[] songfile=new int[] {R.raw.a, R.raw.i, R.raw.u, R.raw.e, R.raw.o,R.raw.ka, R.raw.ki, R.raw.ku, R.raw.ke, R.raw.ko,R.raw.sa, R.raw.shi, R.raw.su, R.raw.se, R.raw.so,
            R.raw.ta, R.raw.chi, R.raw.tsu, R.raw.te, R.raw.to,R.raw.na, R.raw.ni, R.raw.nu, R.raw.ne, R.raw.no,R.raw.ha, R.raw.hi, R.raw.hu, R.raw.he, R.raw.ho,R.raw.ma, R.raw.mi, R.raw.mu,
            R.raw.me, R.raw.mo ,R.raw.ya, R.raw.yu, R.raw.yo,R.raw.ra, R.raw.ri, R.raw.ru, R.raw.re, R.raw.ro,R.raw.wa,R.raw.n,R.raw.wo};
    String[]mytest=new String[]{"ア","イ","ウ","エ","オ",
            "カ","キ","ク","ケ","コ",
            "サ","シ","ス","セ","ソ",
            "タ","チ","ツ","テ","ト",
            "ナ","ニ","ヌ","ネ","ノ",
            "ハ","ヒ","フ","ヘ","ホ",
            "マ","ミ","ム","メ","モ",
            "ヤ","ユ","ヨ",
            "ラ","リ","ル","レ","ロ",
            "ワ","ン"};
    String[] mychoose= new String [] {"a", "i", "u", "e", "o","ka", "ki", "ku", "ke", "ko","sa", "shi", "su", "se", "so",
            "ta", "chi", "tsu", "te", "to","na", "ni", "nu", "ne", "no","ha", "hi", "hu", "he", "ho","ma", "mi", "mu",
            "me", "mo ","ya", "yu", "yo","ra", "ri", "ru", "re", "ro","wa","n","wo"};
   // int[] songfile=new int[] {R.raw.a, R.raw.i, R.raw.u, R.raw.e, R.raw.o,R.raw.ka, R.raw.ki, R.raw.ku, R.raw.ke, R.raw.ko};
    int num=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basich);
       
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
        btsong=(Button)findViewById(R.id.btsong);
        Q1=(TextView)findViewById(R.id.Q1);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup) ;
        a1=(RadioButton)findViewById(R.id.a1);
        a2=(RadioButton)findViewById(R.id.a2);
        a3=(RadioButton)findViewById(R.id.a3);
        a4=(RadioButton)findViewById(R.id.a4);
        nextpage=(ImageView)findViewById(R.id.nextpage);
        prepage=(ImageView)findViewById(R.id.prepage);
        txtResult=(TextView)findViewById(R.id.txtResult);
        mediaplayer=new MediaPlayer();
        next();
        radioGroup.setOnCheckedChangeListener(answer);
        nextpage.setOnTouchListener(page);
        prepage.setOnTouchListener(pagepre);
        btsong.setOnClickListener(btnsong);
        // prepage.setOnClickListener(pagepre2);
        nextpage.setVisibility(View.GONE);
        prepage.setVisibility(View.GONE);
        String myid=getString(R.string.idban);
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
                    if(ans==p){ show (p);}
                    else{if(lock){
                        txtResult.setText("正確答案是:  "+ans  );
                        wrong++;
                        lock=false;
                    }

                    }


                }
            };
    private Button.OnClickListener btnsong=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {

                case R.id.btsong:  //播放
                    playSong(songfile[num ]);
                    break;
            }
        }
    };
    void show (int p){
        if(ans==p){mytoast("正解!"); txtResult.setText(" ");}
        else{mytoast("錯誤!");wrong++;}
        next();
    }
    public void next(){
        txtResult.setText(" "  );
        lock=false;
        radioGroup.clearCheck();
        Random random=new Random();
        num=random.nextInt(45);
        if(count==10){

            Intent intent=new Intent();
            intent.setClass(Basich.this,Keka.class);
            Bundle bundle=new Bundle();
            bundle.putString("NO", wrong+"");
            bundle.putString("YES", (10-wrong)+"");
            intent.putExtras(bundle);
            startActivity(intent);
            mytoast("本題為最後一題");}
        //if(count==10){loadInterstitialAd();}
        /*
        String result = dbbasich.executeQuery(num+"");
        if(result.contains("null")){ num--;
            mytoast("本題為最後一題");
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
                ans=jsonData.getInt("ans");
            }
            playSong(songfile[num-1]);
        }

        catch(Exception e){}
         */
        else{
            int a=0,b=0,c=0;
            for(;;){
                a=random.nextInt(45);
                b=random.nextInt(45);
                c=random.nextInt(45);
                if(a!=b && b!=c && a!=c &&num!=a &&num!=b &&num!=c){break;}
            }
            int []arr ={a,b,c,num};
            Arrays.sort(arr);
            Q1.setText(mychoose[num]);
            a1.setText(mytest[arr[0]]);
            a2.setText(mytest[arr[1]]);
            a3.setText(mytest[arr[2]]);
            a4.setText(mytest[arr[3]]);
            for(int i=0;i<4;i++)
            {
                if(arr[i]==num){
                    ans=i+1;
                }
            }
            playSong(songfile[num]);
            lock=true;
            count++;
        }

       // num++;
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
    private void playSong(int song) {
        mediaplayer.reset();
        mediaplayer= MediaPlayer.create(Basich.this, song); //播放歌曲源
        try {
            mediaplayer.prepare();
        } catch (IllegalStateException e) {
           // e.printStackTrace();
        } catch (IOException e) {
           // e.printStackTrace();
        }
        mediaplayer.start(); //開始播放
      /*
        mediaplayer.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer arg0) {
                nextSong(); //播放完後播下一首
            }
        });
        */

    }
    public void pre(){
        num--;
        lock=false;
        radioGroup.clearCheck();
        txtResult.setText(" ");
        String result = dbbasich.executeQuery(num+"");
        if(result.contains("null")){ num++;
            mytoast("本題為第一題");
        }

        String topic="";
        try{
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                topic=jsonData.getString("Q1");
                Q1.setText(topic);
                a1.setText(jsonData.getString("A1"));
                a2.setText(jsonData.getString("A2"));
                a3.setText(jsonData.getString("A3"));
                a4.setText(jsonData.getString("A4"));
                ans=jsonData.getInt("ans");
            }
            playSong(songfile[num]);
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
            intent.setClass(Basich.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Basich.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Basich.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Basich.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Basich.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Basich.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Basich.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Basich.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Basich.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
             /*
            new AlertDialog.Builder(Basich.this)
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
            context = Basich.this;
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
