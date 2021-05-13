package com.nihon.aki2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.nihon.aki2.control.PaintView;

import com.nihon.aki2.mydb.dbbasic50;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Basic50 extends AppCompatActivity {
    private PaintView paintView;
    ImageView clean,sound,img,btnpre,btnnext,pen;
    TextView show;
    Spinner page;
    GifImageView mygif;
    String account="";
    private AdView mAdView;
    Context context;
    Dialog dia;
    int num=0;
    String[] mypage= new String []{
            "あ","い","う","え","お",
            "か","き","く","け","こ",
            "さ","し","す","せ","そ",
            "た","ち","つ","て","と",
            "な","に","ぬ","ね","の",
            "は","ひ","ふ","へ","ほ",
            "ま","み","む","め","も",
            "や","ゆ","よ",
            "ら","り","る","れ","ろ",
            "わ","を","ん"};
    public MediaPlayer mediaplayer;
    int[] songfile=new int[] {R.raw.a, R.raw.i, R.raw.u, R.raw.e, R.raw.o,R.raw.ka, R.raw.ki, R.raw.ku, R.raw.ke, R.raw.ko,R.raw.sa, R.raw.shi, R.raw.su, R.raw.se, R.raw.so,
            R.raw.ta, R.raw.chi, R.raw.tsu, R.raw.te, R.raw.to,R.raw.na, R.raw.ni, R.raw.nu, R.raw.ne, R.raw.no,R.raw.ha, R.raw.hi, R.raw.hu, R.raw.he, R.raw.ho,R.raw.ma, R.raw.mi, R.raw.mu,
            R.raw.me, R.raw.mo ,R.raw.ya, R.raw.yu, R.raw.yo,R.raw.ra, R.raw.ri, R.raw.ru, R.raw.re, R.raw.ro,R.raw.wa,R.raw.wo,R.raw.n};

    int[] pic=new int[] {R.drawable.a, R.drawable.i, R.drawable.u, R.drawable.e, R.drawable.o,R.drawable.ka, R.drawable.ki, R.drawable.ku, R.drawable.ke, R.drawable.ko,R.drawable.sa, R.drawable.shi, R.drawable.su, R.drawable.se, R.drawable.so,
            R.drawable.ta, R.drawable.chi, R.drawable.tsu, R.drawable.te, R.drawable.to,R.drawable.na, R.drawable.ni, R.drawable.nu, R.drawable.ne, R.drawable.no,R.drawable.ha, R.drawable.hi, R.drawable.hu, R.drawable.he, R.drawable.ho,R.drawable.ma, R.drawable.mi, R.drawable.mu,
            R.drawable.me, R.drawable.mo ,R.drawable.ya, R.drawable.yu, R.drawable.yo,R.drawable.ra, R.drawable.ri, R.drawable.ru, R.drawable.re, R.drawable.ro,R.drawable.wa,R.drawable.wo,R.drawable.n};

    int[] pic2=new int[] {R.drawable.a_1, R.drawable.i_1, R.drawable.u_1, R.drawable.e_1, R.drawable.o_1,R.drawable.ka_1, R.drawable.ki_1, R.drawable.ku_1, R.drawable.ke_1, R.drawable.ko_1,R.drawable.sa_1, R.drawable.shi_1, R.drawable.su_1, R.drawable.se_1, R.drawable.so_1,
            R.drawable.ta_1, R.drawable.chi_1, R.drawable.tsu_1, R.drawable.te_1, R.drawable.to_1,R.drawable.na_1, R.drawable.ni_1, R.drawable.nu_1, R.drawable.ne_1, R.drawable.no_1,R.drawable.ha_1, R.drawable.hi_1, R.drawable.fu_1, R.drawable.he_1, R.drawable.ho_1,R.drawable.ma_1, R.drawable.mi_1, R.drawable.mu_1,
            R.drawable.me_1, R.drawable.mo_1,R.drawable.ya_1, R.drawable.yu_1, R.drawable.yo_1,R.drawable.ra_1, R.drawable.ri_1, R.drawable.ru_1, R.drawable.re_1, R.drawable.ro_1,R.drawable.wa_1,R.drawable.n_1,R.drawable.n_1};
    int[] picg=new int[] {R.drawable.a_g, R.drawable.i_g, R.drawable.u_g, R.drawable.e_g, R.drawable.o_g,R.drawable.ka_g, R.drawable.ki_g, R.drawable.ku_g, R.drawable.ke_g, R.drawable.ko_g,R.drawable.sa_g, R.drawable.shi_g, R.drawable.su_g, R.drawable.se_g, R.drawable.so_g,
            R.drawable.ta_g, R.drawable.chi_g, R.drawable.tsu_g, R.drawable.te_g, R.drawable.to_g,R.drawable.na_g, R.drawable.ni_g, R.drawable.nu_g, R.drawable.ne_g, R.drawable.no_g,R.drawable.ha_g, R.drawable.hi_g, R.drawable.fu_g, R.drawable.he_g, R.drawable.ho_g,R.drawable.ma_g, R.drawable.mi_g, R.drawable.mu_g,
            R.drawable.me_g, R.drawable.mo_g,R.drawable.ya_g, R.drawable.yu_g, R.drawable.yo_g,R.drawable.ra_g, R.drawable.ri_g, R.drawable.ru_g, R.drawable.re_g, R.drawable.ro_g,R.drawable.wa_g,R.drawable.wo_g,R.drawable.n_g};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic50);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
       // for(int i=0;i<45;i++){mypage[i]=i+1;}
        mygif=(GifImageView)findViewById(R.id.mygif);
        paintView = (PaintView) findViewById(R.id.paintView);
        clean=(ImageView)findViewById(R.id.clean);
        pen=(ImageView)findViewById(R.id.pen);
        sound=(ImageView)findViewById(R.id.sound);
        img=(ImageView)findViewById(R.id.img);
        show=(TextView) findViewById(R.id.show);
        btnpre=(ImageView)findViewById(R.id.btnpre);
        btnnext=(ImageView)findViewById(R.id.btnnext);
        page=(Spinner)findViewById(R.id.page);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        paintView.setBackground(getResources().getDrawable(pic[0]));
        mediaplayer=new MediaPlayer();
        clean.setOnClickListener(cbtn);
        sound.setOnClickListener(sbtn);
        pen.setOnClickListener(penbtn);
        pre();
        //show.setText("あい（愛）"+"\n[a-i]\n"+"愛：愛慕");
        btnpre.setOnTouchListener(btpre);
        btnnext.setOnTouchListener(btnext);
        ArrayAdapter<String> adapterPage=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,mypage);

        // 設定Spinner顯示的格式
        adapterPage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 設定Spinner的資料來源
        page.setAdapter(adapterPage);

        // 設定 spnPrefer 元件 ItemSelected 事件的 listener 為  spnPreferListener
        page.setOnItemSelectedListener(spnPreferListener);
        String myid=getString(R.string.appid);
        MobileAds.initialize(this, myid);
        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    private Spinner.OnItemSelectedListener spnPreferListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    //String sel=parent.getSelectedItem().toString();
                    num=position;

                    paintView.setVisibility(View.INVISIBLE);
                    count();
                    paintView.setBackground(getResources().getDrawable(pic[num]));
                    img.setImageResource(pic2[num]);


                    init();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

    private void init() {
        paintView.setVisibility(View.INVISIBLE);
        mygif.setVisibility(View.VISIBLE);
        mygif.setImageResource(picg[num]);
        final GifDrawable getDura=GifDrawable.createFromResource(getResources(),picg[num]);
        int duration=getDura.getDuration();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //getDura.stop();
                    mygif.setVisibility(View.GONE);
                    paintView.setVisibility(View.VISIBLE);

                    }
                });
            }
        }, duration);

    }

    private ImageView.OnClickListener penbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            paintView.setBackground(getResources().getDrawable(pic[num]));
            img.setImageResource(pic2[num]);
            init();
        }
    };
    private ImageView.OnClickListener cbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            paintView.init(metrics);
            paintView.clear();
        }
    };
    private ImageView.OnClickListener sbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            playSong(songfile[num]);

        }
    };
    public void count(){
        if(num<0){num=0;}
        if(num>=pic.length){num=pic.length-1;mytoast("最後一題");}
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        paintView.clear();
        pre();

    }
    public void pre(){
        int s=num+1;

        paintView.setVisibility(View.INVISIBLE);
        String result = dbbasic50.executeQuery(s+"");

        try{
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                String nihon=jsonData.getString("nihon");
                String pinyin=jsonData.getString("pinyin");
                String ch=jsonData.getString("ch");
                show.setText(nihon+"\n\n"+pinyin+"\n\n"+ch);
            }

        }

        catch(Exception e){}
        init();

    }
    private ImageView.OnTouchListener btnext=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    btnnext.setImageResource(R.drawable.aki_nexth);
                    num++;
                    count();
                    paintView.setBackground(getResources().getDrawable(pic[num]));
                    img.setImageResource(pic2[num]);
                    break;
                case MotionEvent.ACTION_UP:
                    btnnext.setImageResource(R.drawable.aki_next);

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
                    num--;
                    count();
                    paintView.setBackground(getResources().getDrawable(pic[num]));
                    img.setImageResource(pic2[num]);
                    break;
                case MotionEvent.ACTION_UP:

                    btnpre.setImageResource(R.drawable.aki_pre);
                    break;
            }
            return true;
        }


    };
    private void playSong(int song) {
        mediaplayer.reset();
        mediaplayer= MediaPlayer.create(Basic50.this, song); //播放歌曲源
        try {
            mediaplayer.prepare();
        } catch (IllegalStateException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //  e.printStackTrace();
        }
        mediaplayer.start(); //開始播放


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            intent.setClass(Basic50.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Basic50.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Basic50.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Basic50.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Basic50.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Basic50.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Basic50.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Basic50.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Basic50.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
             /*
            new AlertDialog.Builder(Basic50.this)
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
            context = Basic50.this;
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
