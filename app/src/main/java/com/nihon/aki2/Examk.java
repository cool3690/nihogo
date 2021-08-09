package com.nihon.aki2;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.Jsan;
import com.nihon.aki2.control.Jsan_k;
import com.nihon.aki2.control.JsansAdapter;
import com.nihon.aki2.control.JsansAdapter_k;
import com.nihon.aki2.control.PaintView;
import com.nihon.aki2.control.RssNews;
import com.nihon.aki2.control.RssNewsXMLParsingHandler;
import com.nihon.aki2.control.SimpleXMLParser;
import com.nihon.aki2.mydb.dbQA;
import com.nihon.aki2.mydb.dbbasic50;
import com.nihon.aki2.mydb.dbbasich50;
import com.nihon.aki2.mydb.dbqanum;
import com.nihon.aki2.mydb.dbsannum;
import com.nihon.aki2.mydb.dbsantance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.os.StrictMode;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Phaser;

import javax.xml.parsers.ParserConfigurationException;

public class Examk extends AppCompatActivity {
    Spinner spnlev,spntype;
    ListView list;
    boolean lock=true;
    RelativeLayout R1,R2,R3,R4;
    RadioGroup radioGroup;
    TextView Q1,txtResult,pretxt,nexttxt;
    View btnpre,btnnext;
    RadioButton a1,a2,a3,a4;
    int num=0,listpos=0,len=0,count=0,ans_lev=0;
    int num_lev=0;
    TextView ch,jp;
    Button bt1,bt2,bt3,bt0,ok,btsong;
    String  myans="_____",myans2="_____",myans3="_____",myans4="_____";
    String str1="",str2="",str3="";
    String anstotal="",mych="";
    String [] contain=new String[4];
    String ans="";
    //R4
    private PaintView paintView;
    ImageView clean,sound,img,pen,sound2;
    TextView show;
    Spinner page;
    GifImageView mygif;
    int[] pic=new int[] {R.drawable.a, R.drawable.i, R.drawable.u, R.drawable.e, R.drawable.o,R.drawable.ka, R.drawable.ki, R.drawable.ku, R.drawable.ke, R.drawable.ko,R.drawable.sa, R.drawable.shi, R.drawable.su, R.drawable.se, R.drawable.so,
            R.drawable.ta, R.drawable.chi, R.drawable.tsu, R.drawable.te, R.drawable.to,R.drawable.na, R.drawable.ni, R.drawable.nu, R.drawable.ne, R.drawable.no,R.drawable.ha, R.drawable.hi, R.drawable.hu, R.drawable.he, R.drawable.ho,R.drawable.ma, R.drawable.mi, R.drawable.mu,
            R.drawable.me, R.drawable.mo ,R.drawable.ya, R.drawable.yu, R.drawable.yo,R.drawable.ra, R.drawable.ri, R.drawable.ru, R.drawable.re, R.drawable.ro,R.drawable.wa,R.drawable.wo,R.drawable.n};

    int[] pic2=new int[] {R.drawable.a_1, R.drawable.i_1, R.drawable.u_1, R.drawable.e_1, R.drawable.o_1,R.drawable.ka_1, R.drawable.ki_1, R.drawable.ku_1, R.drawable.ke_1, R.drawable.ko_1,R.drawable.sa_1, R.drawable.shi_1, R.drawable.su_1, R.drawable.se_1, R.drawable.so_1,
            R.drawable.ta_1, R.drawable.chi_1, R.drawable.tsu_1, R.drawable.te_1, R.drawable.to_1,R.drawable.na_1, R.drawable.ni_1, R.drawable.nu_1, R.drawable.ne_1, R.drawable.no_1,R.drawable.ha_1, R.drawable.hi_1, R.drawable.fu_1, R.drawable.he_1, R.drawable.ho_1,R.drawable.ma_1, R.drawable.mi_1, R.drawable.mu_1,
            R.drawable.me_1, R.drawable.mo_1,R.drawable.ya_1, R.drawable.yu_1, R.drawable.yo_1,R.drawable.ra_1, R.drawable.ri_1, R.drawable.ru_1, R.drawable.re_1, R.drawable.ro_1,R.drawable.wa_1,R.drawable.n_1,R.drawable.n_1};
    int[] picg=new int[] {R.drawable.a_g, R.drawable.i_g, R.drawable.u_g, R.drawable.e_g, R.drawable.o_g,R.drawable.ka_g, R.drawable.ki_g, R.drawable.ku_g, R.drawable.ke_g, R.drawable.ko_g,R.drawable.sa_g, R.drawable.shi_g, R.drawable.su_g, R.drawable.se_g, R.drawable.so_g,
            R.drawable.ta_g, R.drawable.chi_g, R.drawable.tsu_g, R.drawable.te_g, R.drawable.to_g,R.drawable.na_g, R.drawable.ni_g, R.drawable.nu_g, R.drawable.ne_g, R.drawable.no_g,R.drawable.ha_g, R.drawable.hi_g, R.drawable.fu_g, R.drawable.he_g, R.drawable.ho_g,R.drawable.ma_g, R.drawable.mi_g, R.drawable.mu_g,
            R.drawable.me_g, R.drawable.mo_g,R.drawable.ya_g, R.drawable.yu_g, R.drawable.yo_g,R.drawable.ra_g, R.drawable.ri_g, R.drawable.ru_g, R.drawable.re_g, R.drawable.ro_g,R.drawable.wa_g,R.drawable.wo_g,R.drawable.n_g};

    String course[]={"五十音","初級一","初級二","進階一","進階二"};
    public MediaPlayer mediaplayer;
    int[] songfile=new int[] {R.raw.a, R.raw.i, R.raw.u, R.raw.e, R.raw.o,R.raw.ka, R.raw.ki, R.raw.ku, R.raw.ke, R.raw.ko,R.raw.sa, R.raw.shi, R.raw.su, R.raw.se, R.raw.so,
            R.raw.ta, R.raw.chi, R.raw.tsu, R.raw.te, R.raw.to,R.raw.na, R.raw.ni, R.raw.nu, R.raw.ne, R.raw.no,R.raw.ha, R.raw.hi, R.raw.hu, R.raw.he, R.raw.ho,R.raw.ma, R.raw.mi, R.raw.mu,
            R.raw.me, R.raw.mo ,R.raw.ya, R.raw.yu, R.raw.yo,R.raw.ra, R.raw.ri, R.raw.ru, R.raw.re, R.raw.ro,R.raw.wa,R.raw.n,R.raw.wo};
    int[] songfile2=new int[] {R.raw.g_a, R.raw.g_i, R.raw.g_u, R.raw.g_e, R.raw.g_o,R.raw.g_ka, R.raw.g_ki, R.raw.g_ku, R.raw.g_ke, R.raw.g_ko,R.raw.g_sa, R.raw.g_shi, R.raw.g_su, R.raw.g_se, R.raw.g_so,
            R.raw.g_ta, R.raw.g_chi, R.raw.g_tsu, R.raw.g_te, R.raw.g_to,R.raw.g_na, R.raw.g_ni, R.raw.g_nu, R.raw.g_ne, R.raw.g_no,R.raw.g_ha, R.raw.g_hi, R.raw.g_fu, R.raw.g_he, R.raw.g_ho,R.raw.g_ma, R.raw.g_mi, R.raw.g_mu,
            R.raw.g_me, R.raw.g_mo ,R.raw.g_ya, R.raw.g_yu, R.raw.g_yo,R.raw.g_ra, R.raw.g_ri, R.raw.g_ru, R.raw.g_re, R.raw.g_ro,R.raw.g_wa,R.raw.g_wo,R.raw.g_n};
    int[] pic3=new int[] {R.drawable.a_k, R.drawable.i_k, R.drawable.u_k, R.drawable.e_k, R.drawable.o_k,R.drawable.ka_k, R.drawable.ki_k, R.drawable.ku_k, R.drawable.ke_k, R.drawable.ko_k,R.drawable.sa_k, R.drawable.shi_k, R.drawable.su_k, R.drawable.se_k, R.drawable.so_k,
            R.drawable.ta_k, R.drawable.chi_k, R.drawable.tsu_k, R.drawable.te_k, R.drawable.to_k,R.drawable.na_k, R.drawable.ni_k, R.drawable.nu_k, R.drawable.ne_k, R.drawable.no_k,R.drawable.ha_k, R.drawable.hi_k, R.drawable.fu_k, R.drawable.he_k, R.drawable.ho_k,R.drawable.ma_k, R.drawable.mi_k, R.drawable.mu_k,
            R.drawable.me_k, R.drawable.mo_k,R.drawable.ya_k, R.drawable.yu_k, R.drawable.yo_k,R.drawable.ra_k, R.drawable.ri_k, R.drawable.ru_k, R.drawable.re_k, R.drawable.ro_k,R.drawable.wa_k,R.drawable.n_k};
    int[] pic4=new int[] {R.drawable.a_2, R.drawable.i_2, R.drawable.u_2, R.drawable.e_2, R.drawable.o_2,R.drawable.ka_2, R.drawable.ki_2, R.drawable.ku_2, R.drawable.ke_2, R.drawable.ko_2,R.drawable.sa_2, R.drawable.shi_2, R.drawable.su_2, R.drawable.se_2, R.drawable.so_2,
            R.drawable.ta_2, R.drawable.chi_2, R.drawable.tsu_2, R.drawable.te_2, R.drawable.to_2,R.drawable.na_2, R.drawable.ni_2, R.drawable.nu_2, R.drawable.ne_2, R.drawable.no_2,R.drawable.ha_2, R.drawable.hi_2, R.drawable.fu_2, R.drawable.he_2, R.drawable.ho_2,R.drawable.ma_2, R.drawable.mi_2, R.drawable.mu_2,
            R.drawable.me_2, R.drawable.mo_2,R.drawable.ya_2, R.drawable.yu_2, R.drawable.yo_2,R.drawable.ra_2, R.drawable.ri_2, R.drawable.ru_2, R.drawable.re_2, R.drawable.ro_2,R.drawable.wa_2,R.drawable.n_2};
    int[] picg2=new int[] {R.drawable.a_g1, R.drawable.a_i1, R.drawable.u_g1, R.drawable.e_g1, R.drawable.o_g1,R.drawable.ka_g1, R.drawable.ki_g1, R.drawable.ku_g1, R.drawable.ke_g1, R.drawable.ko_g1,R.drawable.sa_g1, R.drawable.shi_g1, R.drawable.su_g1, R.drawable.se_g1, R.drawable.so_g1,
            R.drawable.ta_g1, R.drawable.chi_g1, R.drawable.tsu_g1, R.drawable.te_g1, R.drawable.to_g1,R.drawable.na_g1, R.drawable.ni_g1, R.drawable.nu_g1, R.drawable.ne_g1, R.drawable.no_g1,R.drawable.ha_g1, R.drawable.hi_g1, R.drawable.fu_g1, R.drawable.he_g1, R.drawable.ho_g1,R.drawable.ma_g1, R.drawable.mi_g1, R.drawable.mu_g1,
            R.drawable.me_g1, R.drawable.mo_g1,R.drawable.ya_g1, R.drawable.yu_g1, R.drawable.yo_g1,R.drawable.ra_g1, R.drawable.ri_g1, R.drawable.ru_g1, R.drawable.re_g1, R.drawable.ro_g1,R.drawable.wa_g1,R.drawable.n_g1,R.drawable.wo_g1};

    String[] mytest= new String []{
            "あ","い","う","え","お",
            "か","き","く","け","こ",
            "さ","し","す","せ","そ",
            "た","ち","つ","て","と",
            "な","に","ぬ","ね","の",
            "は","ひ","ふ","へ","ほ",
            "ま","み","む","め","も",
            "や","ゆ","よ",
            "ら","り","る","れ","ろ",
            "わ","ん","を"};
    String[]mytest2=new String[]{"ア","イ","ウ","エ","オ",
            "カ","キ","ク","ケ","コ",
            "サ","シ","ス","セ","ソ",
            "タ","チ","ツ","テ","ト",
            "ナ","ニ","ヌ","ネ","ノ",
            "ハ","ヒ","フ","ヘ","ホ",
            "マ","ミ","ム","メ","モ",
            "ヤ","ユ","ヨ",
            "ラ","リ","ル","レ","ロ","ン",
            "ワ"};
    String[] mychoose= new String [] {"a", "i", "u", "e", "o","ka", "ki", "ku", "ke", "ko","sa", "shi", "su", "se", "so",
            "ta", "chi", "tsu", "te", "to","na", "ni", "nu", "ne", "no","ha", "hi", "hu", "he", "ho","ma", "mi", "mu",
            "me", "mo ","ya", "yu", "yo","ra", "ri", "ru", "re", "ro","wa","n","wo"};
    //String type[]={"平假名測驗","片假名測驗","單選題","句子重組"};
    ArrayList <String> typelist=new ArrayList();
    ArrayList<Jsan_k> jsans=new ArrayList<Jsan_k>();
    int lev=0,spnlesson=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examk);
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
        mediaplayer=new MediaPlayer();
        pretxt=(TextView)findViewById(R.id.pretxt);
        nexttxt=(TextView)findViewById(R.id.nexttxt);
        btnpre=(View)findViewById(R.id.btnpre);
        btnnext=(View)findViewById(R.id.btnnext);
        pretxt.setOnClickListener(pretxtbtn);
        btnpre.setOnClickListener(pretxtbtn);
        nexttxt.setOnClickListener(pretxtbtn);
        btnnext.setOnClickListener(pretxtbtn);
        //////
        spnlev=(Spinner)findViewById(R.id.spnlev);
        spntype=(Spinner)findViewById(R.id.spntype);
        list=(ListView)findViewById(R.id.list);
        R1=(RelativeLayout)findViewById(R.id.R1);
        //R2
        R2=(RelativeLayout)findViewById(R.id.R2);
        Q1=(TextView)findViewById(R.id.Q1);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup) ;
        a1=(RadioButton)findViewById(R.id.a1);
        a2=(RadioButton)findViewById(R.id.a2);
        a3=(RadioButton)findViewById(R.id.a3);
        a4=(RadioButton)findViewById(R.id.a4);
        btsong=(Button)findViewById(R.id.btsong);
        txtResult=(TextView)findViewById(R.id.txtResult);
        radioGroup.setOnCheckedChangeListener(answer);
        R2.setVisibility(View.GONE);

        //R3
        R3=(RelativeLayout)findViewById(R.id.R3);
        ch=(TextView)findViewById(R.id.chinese);
        jp=(TextView)findViewById(R.id.jp);
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt3=(Button)findViewById(R.id.bt3);
        bt0=(Button)findViewById(R.id.bt0);
        ok=(Button)findViewById(R.id.ok);
        bt1.setOnClickListener(bt_1);
        bt2.setOnClickListener(bt_2);
        bt3.setOnClickListener(bt_3);
        bt0.setOnClickListener(bt_0);
        ok.setOnClickListener(okbtn);
        R3.setVisibility(View.GONE);
        ////R4
        R4=(RelativeLayout)findViewById(R.id.R4);
        R4.setVisibility(View.GONE);
        mygif=(GifImageView)findViewById(R.id.mygif);
        paintView = (PaintView) findViewById(R.id.paintView);
        clean=(ImageView)findViewById(R.id.clean);
        pen=(ImageView)findViewById(R.id.pen);
        sound=(ImageView)findViewById(R.id.sound);
        sound2=(ImageView)findViewById(R.id.sound2);
        img=(ImageView)findViewById(R.id.img);
        show=(TextView) findViewById(R.id.show);
        page=(Spinner)findViewById(R.id.page);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        paintView.setBackground(getResources().getDrawable(pic[0]));
        mediaplayer=new MediaPlayer();
        clean.setOnClickListener(cbtn);
        sound.setOnClickListener(sbtn);
        sound2.setOnClickListener(sbtn2);
        pen.setOnClickListener(penbtn);

        ArrayAdapter<String> adapterPage=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,mytest);

        // 設定Spinner顯示的格式
        adapterPage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 設定Spinner的資料來源
        page.setAdapter(adapterPage);

        // 設定 spnPrefer 元件 ItemSelected 事件的 listener 為  spnPreferListener
        page.setOnItemSelectedListener(spnPreferListener);



        ///////////////
        typelist.add("平假名練習");
        typelist.add("平假名測驗");
        typelist.add("片假名練習");
        typelist.add("片假名測驗");
        ArrayAdapter<String> spnlevpn=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,course);
        spnlev.setAdapter(spnlevpn);
        spnlev.setOnItemSelectedListener(levbtn);

        ArrayAdapter<String> spntypespn=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,typelist);
        spntype.setAdapter(spntypespn);
        spntype.setOnItemSelectedListener(typebtn);

        final JsansAdapter_k adapter = new JsansAdapter_k(this, R.layout.jsant_k, jsans);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
       // list.setSelector(R.drawable.green);
        list.setOnItemClickListener(lstPreferListener);
        btsong.setOnClickListener(btnsong);
/////////////////////////////////
        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
        nav_view.setSelectedItemId(R.id.btn5);
        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.btn5:
                        startActivity(new Intent(getApplicationContext(),Menushow.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn6:
                        startActivity(new Intent(getApplicationContext(),Tool_k.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn7:
                        startActivity(new Intent(getApplicationContext(),Change.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
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
                    if(spnlesson==0){
                        paintView.setBackground(getResources().getDrawable(pic[num]));
                        img.setImageResource(pic2[num]);
                    }
                  else{
                        paintView.setBackground(getResources().getDrawable(pic3[num]));
                        img.setImageResource(pic4[num]);
                    }



                    init();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

    private void init() {
        paintView.setVisibility(View.INVISIBLE);
        mygif.setVisibility(View.VISIBLE);
        if(spnlesson==0){
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
       else{
            mygif.setImageResource(picg2[num]);
            final GifDrawable getDura=GifDrawable.createFromResource(getResources(),picg2[num]);
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


    }
    private ImageView.OnClickListener penbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(spnlesson==0){
                paintView.setBackground(getResources().getDrawable(pic[num]));
                img.setImageResource(pic2[num]);
            }
            else{
                paintView.setBackground(getResources().getDrawable(pic3[num]));
                img.setImageResource(pic4[num]);
            }
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
    private ImageView.OnClickListener sbtn2=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            playSong(songfile2[num]);

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
        preR4();

    }
    public void preR4(){
        int s=num+1;
        String result ="";
        paintView.setVisibility(View.INVISIBLE);
        if(spnlesson==0){
            result = dbbasic50.executeQuery(s+"");
        }
        else{
              result = dbbasich50.executeQuery(s+"");
            }

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
///////////////


    private Button.OnClickListener btnsong=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {

                case R.id.btsong:  //播放
                    playSong(songfile[num_lev ]);
                    break;
            }
        }
    };

    private TextView.OnClickListener pretxtbtn=new TextView.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.pretxt:
                case R.id.btnpre:
                    if(lev==0){
                        if(spnlesson==0){
                            num--;
                            count();
                            paintView.setBackground(getResources().getDrawable(pic[num]));
                            img.setImageResource(pic2[num]);
                        }
                        else if(spnlesson==1){
                            radioGroup.clearCheck();
                            count--;
                            next_lev0();
                        }
                        else if(spnlesson==2){

                        }
                        else if(spnlesson==3){
                            radioGroup.clearCheck();
                            count--;
                            next_lev3();
                        }
                    }
                    else{
                        if(spnlesson==0){
                            pre();
                        }
                        else{
                            num--;
                            String result = dbsantance.executeQuery(num+"",listpos+"");
                            if(result.contains("null")){ num++;mytoast("本題為第一題");}
                            else{
                                clean();
                                test(num);
                            }
                        }
                    }
                    break;
                case R.id.nexttxt:
                case R.id.btnnext:
                    if(lev==0){
                        if(spnlesson==0){
                            num++;
                            count();
                            paintView.setBackground(getResources().getDrawable(pic[num]));
                            img.setImageResource(pic2[num]);
                        }
                        else  if(spnlesson==1){
                            radioGroup.clearCheck();
                            count++;
                            next_lev0();
                        }
                        else if(spnlesson==2){

                        }
                        else if(spnlesson==3){
                            radioGroup.clearCheck();
                            count++;
                            next_lev3();
                        }
                    }
                    else{
                        if(spnlesson==0){
                            next();
                        }
                        else{

                            num++;
                            String result = dbsantance.executeQuery(num+"",listpos+"");
                            if(result.contains("null")){ num--;mytoast("本題為最後一題");}
                            else{
                                clean();
                                test(num);
                            }
                        }
                    }

                    break;
                default:
                    break;
            }
        }
    };
    private RadioGroup.OnCheckedChangeListener answer=
            new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int  p = group.indexOfChild((RadioButton) findViewById(checkedId));	// 選項的索引值
                    int count = group.getChildCount(); // 清單總共有多少項
                    p++;
                    show(p);


//mytoast(yes+"");
                }
            };
    void show (int p){
        //next_lev0();
        if(lev==0){
        if(spnlesson==1){
            String tmp=ans_lev+"";
            if(tmp.equals(p+"")){
                mytoast("正解!");
                radioGroup.clearCheck();
                count++;
                next_lev0();

            }

            else{
                if(lock){
                    txtResult.setText("正確答案是:  "+ans_lev  );

                    lock=false;
                }

            }
        }
        else if(spnlesson==3){
            String tmp=ans_lev+"";
            if(tmp.equals(p+"")){
                mytoast("正解!");
                radioGroup.clearCheck();
                count++;
                next_lev3();

            }

            else{
                if(lock){
                    txtResult.setText("正確答案是:  "+ans_lev  );

                    lock=false;
                }

            }
        }

        }

        else{
            if(ans.equals(p+"")){mytoast("正解!"); ; next();}
            else{   txtResult.setText("正確答案是:  "+ans  );}
        }


    }
    public void next_lev0(){
        lock=true;
        txtResult.setText("" );
        /* radioGroup.clearCheck();*/
        Random random=new Random();
        num_lev=random.nextInt(45);
        if(count>=10){
            mytoast("本題為最後一題");
        }
        else if(count<0){
            mytoast("本題為第一題");
        }
        else{
            int a=0,b=0,c=0;
            for(;;){
                a=random.nextInt(45);
                b=random.nextInt(45);
                c=random.nextInt(45);
                if(a!=b && b!=c && a!=c &&num_lev!=a &&num_lev!=b &&num_lev!=c){break;}
            }
            int []arr ={a,b,c,num_lev};
            Arrays.sort(arr);
            Q1.setText(mychoose[num_lev]);
            a1.setText(mytest[arr[0]]);
            a2.setText(mytest[arr[1]]);
            a3.setText(mytest[arr[2]]);
            a4.setText(mytest[arr[3]]);
            for(int i=0;i<4;i++)
            {
                if(arr[i]==num_lev){
                    ans_lev=i+1;
                }
            }
            playSong(songfile[num_lev]);
            lock=true;

        }
    }
    public void next_lev3(){
        lock=true;
        txtResult.setText("" );
        /* radioGroup.clearCheck();*/
        Random random=new Random();
        num_lev=random.nextInt(45);
        if(count>=10){
            mytoast("本題為最後一題");
        }
        else if(count<0){
            mytoast("本題為第一題");
        }
        else{
            int a=0,b=0,c=0;
            for(;;){
                a=random.nextInt(45);
                b=random.nextInt(45);
                c=random.nextInt(45);
                if(a!=b && b!=c && a!=c &&num_lev!=a &&num_lev!=b &&num_lev!=c){break;}
            }
            int []arr ={a,b,c,num_lev};
            Arrays.sort(arr);
            Q1.setText(mychoose[num_lev]);
            a1.setText(mytest2[arr[0]]);
            a2.setText(mytest2[arr[1]]);
            a3.setText(mytest2[arr[2]]);
            a4.setText(mytest2[arr[3]]);
            for(int i=0;i<4;i++)
            {
                if(arr[i]==num_lev){
                    ans_lev=i+1;
                }
            }
            playSong(songfile[num_lev]);
            lock=true;

        }
    }
    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    if(lev==0){

                    }
                    else if(lev==1)
                    {btsong.setVisibility(View.GONE);
                        if(spnlesson==0){
                            R1.setVisibility(View.GONE);
                            R3.setVisibility(View.GONE);
                            R2.setVisibility(View.VISIBLE);
                            listpos=position+1;
                            countnum(listpos);

                            next();
                        }
                        else{
                            R1.setVisibility(View.GONE);
                            R2.setVisibility(View.GONE);
                            R3.setVisibility(View.VISIBLE);
                            listpos=position+1;
                            countnumR3(listpos);
                            //mytoast(num+"");
                            test(num);
                        }
                    }
                    else if(lev==2){
                        if(spnlesson==0){
                            R1.setVisibility(View.GONE);
                            R3.setVisibility(View.GONE);
                            R2.setVisibility(View.VISIBLE);
                            listpos=position+1+12;
                            countnum(listpos);

                            next();
                        }
                        else{
                            R1.setVisibility(View.GONE);
                            R2.setVisibility(View.GONE);
                            R3.setVisibility(View.VISIBLE);
                            listpos=position+1+12;
                            countnumR3(listpos);
                            test(num);
                        }
                    }
                    else if(lev==3){
                        if(spnlesson==0){
                            R1.setVisibility(View.GONE);
                            R3.setVisibility(View.GONE);
                            R2.setVisibility(View.VISIBLE);
                            listpos=position+1+25;
                            countnum(listpos);

                            next();
                        }
                        else{
                            R1.setVisibility(View.GONE);
                            R2.setVisibility(View.GONE);
                            R3.setVisibility(View.VISIBLE);
                            listpos=position+1+25;
                            countnumR3(listpos);
                            test(num);
                        }
                    }
                    else if(lev==4){
                        if(spnlesson==0){
                            R1.setVisibility(View.GONE);
                            R3.setVisibility(View.GONE);
                            R2.setVisibility(View.VISIBLE);
                            listpos=position+1+38;
                            countnum(listpos);

                            next();
                        }
                        else{
                            R1.setVisibility(View.GONE);
                            R2.setVisibility(View.GONE);
                            R3.setVisibility(View.VISIBLE);
                            listpos=position+1+38;
                            countnumR3(listpos);
                            test(num);
                        }
                    }
                }
            };
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
            if(tmp.contains(ans)){
                mytoast("正解!");
                num++;
                String result = dbsantance.executeQuery(num+"",listpos+"");
                if(result.contains("null")){ num--;mytoast("本題為最後一題");}
                else{
                    clean();
                    test(num);
                }
            }

            else{mytoast(ans);
                clean();
                test(num);
            }

        }
    };
    public void countnumR3(int nu){
        String result = dbsannum.executeQuery(listpos+"");
        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                num= Integer. parseInt(jsonData.getString("id")) ;
                // mytoast(num+"");
            }

        }

        catch(Exception e){}
    }
    public void countnum(int nu){
        String result = dbqanum.executeQuery(listpos+"");
        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                num= Integer. parseInt(jsonData.getString("num")) ;
                // mytoast(num+"");
            }

        }

        catch(Exception e){}
    }
    public void pre(){
        num--;

        radioGroup.clearCheck();
        //mytoast(num+"");
        String result = dbQA.executeQuery(num+"",listpos+"");
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
                ans=jsonData.getString("ans");
            }

        }

        catch(Exception e){}

    }
    public void next(){


        radioGroup.clearCheck();
           //mytoast(num+"");
        String result = dbQA.executeQuery(num+"",listpos+"");
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
                ans=jsonData.getString("ans");
            }

        }

        catch(Exception e){}
        num++;
    }
    private Spinner.OnItemSelectedListener levbtn= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.GONE);
            R3.setVisibility(View.GONE);
            lev=position;
            if(position==0){
                typelist.clear();
                typelist.add("平假名練習");
                typelist.add("平假名測驗");
                typelist.add("片假名練習");
                typelist.add("片假名測驗");
            }
            else{
                typelist.clear();
                typelist.add("單選題");
                typelist.add("句子重組");
            }
            change();

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };
    public void change(){
    ArrayAdapter<String> spntypespn=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,typelist);

  //  spntypespn.clear();
    //spntypespn.addAll(typelist);
    spntypespn.notifyDataSetChanged();
    spntype.setAdapter(spntypespn);
}
    public void change_basic(){
        if(lev==0&&spnlesson==0){
            ArrayAdapter<String> adapterPage=new ArrayAdapter<String>
                    (this,android.R.layout.simple_spinner_item,mytest);

            adapterPage.notifyDataSetChanged();
            // 設定Spinner的資料來源
            page.setAdapter(adapterPage);
        }
        else if(lev==0&&spnlesson==2){
            ArrayAdapter<String> adapterPage=new ArrayAdapter<String>
                    (this,android.R.layout.simple_spinner_item,mytest2);

            adapterPage.notifyDataSetChanged();
            // 設定Spinner的資料來源
            page.setAdapter(adapterPage);
        }


    }
    private Spinner.OnItemSelectedListener typebtn= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.GONE);
            R3.setVisibility(View.GONE);
            R4.setVisibility(View.GONE);
            spnlesson=position;
          if(lev==0){
              if(spnlesson==0){
                  R1.setVisibility(View.GONE);
                  R4.setVisibility(View.VISIBLE);
                  change_basic();
                  num=0;
                  preR4();
              }
              else if(spnlesson==1){
                  R1.setVisibility(View.GONE);

                  R2.setVisibility(View.VISIBLE);
                  btsong.setVisibility(View.VISIBLE);
                  count=0;
                  next_lev0();
              }
              else if(spnlesson==2){
                  R1.setVisibility(View.GONE);
                  R4.setVisibility(View.VISIBLE);
                  change_basic();
                  num=0;
              }
              else if(spnlesson==3){
                  R1.setVisibility(View.GONE);

                  R2.setVisibility(View.VISIBLE);
                  btsong.setVisibility(View.VISIBLE);
                  count=0;
                  next_lev3();
              }
          }
          else if(lev==1){
              jsans.clear();
              for(int i=1;i<=12;i++){
                  Jsan_k jsan = new Jsan_k("Lesson"+i);

                  jsans.add(jsan);
              }
          }
          else if(lev==2){
              jsans.clear();
              for(int i=13;i<=25;i++){
                  Jsan_k jsan = new Jsan_k("Lesson"+i);
                  jsans.add(jsan);
              }
          }
          else if(lev==3){
              jsans.clear();
              for(int i=26;i<=38;i++){
                  Jsan_k jsan = new Jsan_k("Lesson"+i);
                  jsans.add(jsan);
              }
          }
          else if(lev==4){
              jsans.clear();
              for(int i=39;i<=50;i++){
                  Jsan_k jsan = new Jsan_k("Lesson"+i);
                  jsans.add(jsan);
              }
          }
            final JsansAdapter_k adapter = new JsansAdapter_k(getApplicationContext(), R.layout.jsant_k, jsans);
            list.setAdapter(adapter);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };

    private void pretest(int num) {


        String result = dbsantance.executeQuery(num+"",listpos+"");

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
    private void test(int num) {
        String result = dbsantance.executeQuery(num+"",listpos+"");
        if(result.contains("null")){ num--;
            mytoast("本題為最後一題");

        }
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

    }
    private void playSong(int song) {
        mediaplayer.reset();
        mediaplayer= MediaPlayer.create(Examk.this, song); //播放歌曲源
        try {
            mediaplayer.prepare();
        } catch (IllegalStateException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //  e.printStackTrace();
        }
        mediaplayer.start(); //開始播放


    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}