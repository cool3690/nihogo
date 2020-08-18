package com.nihon.aki2;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.PaintView;
import com.nihon.aki2.mydb.dbbasich;
import com.nihon.aki2.mydb.dbbasich50;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Basich50 extends AppCompatActivity {
    private PaintView paintView;
    ImageView clean,sound,img,btnpre,btnnext,pen;
    TextView show;
    GifImageView mygif;
    Spinner page;
    int num=0;
    public MediaPlayer mediaplayer;
    String[]mypage=new String[]{"ア","イ","ウ","エ","オ",
            "カ","キ","ク","ケ","コ",
            "サ","シ","ス","セ","ソ",
            "タ","チ","ツ","テ","ト",
            "ナ","ニ","ヌ","ネ","ノ",
            "ハ","ヒ","フ","ヘ","ホ",
            "マ","ミ","ム","メ","モ",
            "ヤ","ユ","ヨ",
            "ラ","リ","ル","レ","ロ",
            "ワ","ン"};
    int[] songfile=new int[] {R.raw.a, R.raw.i, R.raw.u, R.raw.e, R.raw.o,R.raw.ka, R.raw.ki, R.raw.ku, R.raw.ke, R.raw.ko,R.raw.sa, R.raw.shi, R.raw.su, R.raw.se, R.raw.so,
            R.raw.ta, R.raw.chi, R.raw.tsu, R.raw.te, R.raw.to,R.raw.na, R.raw.ni, R.raw.nu, R.raw.ne, R.raw.no,R.raw.ha, R.raw.hi, R.raw.hu, R.raw.he, R.raw.ho,R.raw.ma, R.raw.mi, R.raw.mu,
            R.raw.me, R.raw.mo ,R.raw.ya, R.raw.yu, R.raw.yo,R.raw.ra, R.raw.ri, R.raw.ru, R.raw.re, R.raw.ro,R.raw.wa,R.raw.n};
    int[] pic=new int[] {R.drawable.a_k, R.drawable.i_k, R.drawable.u_k, R.drawable.e_k, R.drawable.o_k,R.drawable.ka_k, R.drawable.ki_k, R.drawable.ku_k, R.drawable.ke_k, R.drawable.ko_k,R.drawable.sa_k, R.drawable.shi_k, R.drawable.su_k, R.drawable.se_k, R.drawable.so_k,
            R.drawable.ta_k, R.drawable.chi_k, R.drawable.tsu_k, R.drawable.te_k, R.drawable.to_k,R.drawable.na_k, R.drawable.ni_k, R.drawable.nu_k, R.drawable.ne_k, R.drawable.no_k,R.drawable.ha_k, R.drawable.hi_k, R.drawable.fu_k, R.drawable.he_k, R.drawable.ho_k,R.drawable.ma_k, R.drawable.mi_k, R.drawable.mu_k,
            R.drawable.me_k, R.drawable.mo_k,R.drawable.ya_k, R.drawable.yu_k, R.drawable.yo_k,R.drawable.ra_k, R.drawable.ri_k, R.drawable.ru_k, R.drawable.re_k, R.drawable.ro_k,R.drawable.wa_k,R.drawable.n_k};
    int[] pic2=new int[] {R.drawable.a_2, R.drawable.i_2, R.drawable.u_2, R.drawable.e_2, R.drawable.o_2,R.drawable.ka_2, R.drawable.ki_2, R.drawable.ku_2, R.drawable.ke_2, R.drawable.ko_2,R.drawable.sa_2, R.drawable.shi_2, R.drawable.su_2, R.drawable.se_2, R.drawable.so_2,
            R.drawable.ta_2, R.drawable.chi_2, R.drawable.tsu_2, R.drawable.te_2, R.drawable.to_2,R.drawable.na_2, R.drawable.ni_2, R.drawable.nu_2, R.drawable.ne_2, R.drawable.no_2,R.drawable.ha_2, R.drawable.hi_2, R.drawable.fu_2, R.drawable.he_2, R.drawable.ho_2,R.drawable.ma_2, R.drawable.mi_2, R.drawable.mu_2,
            R.drawable.me_2, R.drawable.mo_2,R.drawable.ya_2, R.drawable.yu_2, R.drawable.yo_2,R.drawable.ra_2, R.drawable.ri_2, R.drawable.ru_2, R.drawable.re_2, R.drawable.ro_2,R.drawable.wa_2,R.drawable.n_2};
    int[] picg=new int[] {R.drawable.a_g1, R.drawable.a_i1, R.drawable.u_g1, R.drawable.e_g1, R.drawable.o_g1,R.drawable.ka_g1, R.drawable.ki_g1, R.drawable.ku_g1, R.drawable.ke_g1, R.drawable.ko_g1,R.drawable.sa_g1, R.drawable.shi_g1, R.drawable.su_g1, R.drawable.se_g1, R.drawable.so_g1,
            R.drawable.ta_g1, R.drawable.chi_g1, R.drawable.tsu_g1, R.drawable.te_g1, R.drawable.to_g1,R.drawable.na_g1, R.drawable.ni_g1, R.drawable.nu_g1, R.drawable.ne_g1, R.drawable.no_g1,R.drawable.ha_g1, R.drawable.hi_g1, R.drawable.fu_g1, R.drawable.he_g1, R.drawable.ho_g1,R.drawable.ma_g1, R.drawable.mi_g1, R.drawable.mu_g1,
            R.drawable.me_g1, R.drawable.mo_g1,R.drawable.ya_g1, R.drawable.yu_g1, R.drawable.yo_g1,R.drawable.ra_g1, R.drawable.ri_g1, R.drawable.ru_g1, R.drawable.re_g1, R.drawable.ro_g1,R.drawable.wa_g1,R.drawable.n_g1,R.drawable.wo_g1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basich50);
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
        duration+=100;
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
      //  paintView.setVisibility(View.VISIBLE);
    }
    private ImageView.OnClickListener penbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
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
        String result = dbbasich50.executeQuery(s+"");

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
        mediaplayer= MediaPlayer.create(Basich50.this, song); //播放歌曲源
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
        switch(item.getItemId()) {
            case R.id.normal:
                paintView.normal();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
