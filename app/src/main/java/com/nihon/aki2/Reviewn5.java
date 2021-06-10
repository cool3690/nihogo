package com.nihon.aki2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.mydb.dbn5;
import com.nihon.aki2.mydb.dbsearchn5;
import com.nihon.aki2.mydb.dbtango;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Reviewn5 extends AppCompatActivity {
    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 800L;
    int i=0;
    DisplayMetrics dm = new DisplayMetrics();
    private RelativeLayout R3;

    ImageView pic3;

    TextView jp3,ch3,level3;

    String mypinyin;
    String myjp;
    String mych,myhiragana;
    String myen,mylevel;
    double faby=0;
    boolean tf=true;
    DragFloatActionButton fab;

    Timer timer = new Timer();

    String url ="https://kei-sei.com/cram/n5.json";
    TextView ch,jp,pinyin,meaning,level;
    EditText input;

    Button auto,hand,send,bt1,bt2,bt3,bt4,bt5;

    Spinner sec;
    String[] mylev= new String []{"N5","N4","N3","N2","N1" };
    String[] mysec= new String []{"5秒","6秒","7秒","8秒","9秒","10秒"};
    int LEV=0,SEC=0,Ltmp=0,stmp=5;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewn5);
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
        /////////
        pic3=(ImageView)findViewById(R.id.pic3);
        R3=findViewById(R.id.R3);
        ch3=(TextView)findViewById(R.id.ch3);
        jp3=(TextView)findViewById(R.id.jp3);
        level3=(TextView)findViewById(R.id.level3);
        fab = findViewById(R.id.fab);
        R3.setVisibility(View.GONE);
        ch3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        jp3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
       // new Reviewn5.DownloadFileAsync().execute();
        R3.setOnClickListener(R3btn);
        fab.setOnClickListener(fabclick);
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        ///
        jp=(TextView)findViewById(R.id.jp);
        ch=(TextView)findViewById(R.id.ch);
        pinyin=(TextView)findViewById(R.id.pinyin);
        meaning=(TextView)findViewById(R.id.meaning);
        fab = findViewById(R.id.fab);
        auto=(Button) findViewById(R.id.auto);
        hand=(Button) findViewById(R.id.hand);
        send=(Button) findViewById(R.id.send);
        bt1=(Button) findViewById(R.id.bt1);
        bt2=(Button) findViewById(R.id.bt2);
        bt3=(Button) findViewById(R.id.bt3);
        bt4=(Button) findViewById(R.id.bt4);
        bt5=(Button) findViewById(R.id.bt5);
        level=(TextView)findViewById(R.id.level);
        R3=findViewById(R.id.R3);
        pic3=(ImageView)findViewById(R.id.pic3);
        ch3=(TextView)findViewById(R.id.ch3);
        level3=(TextView)findViewById(R.id.level3);
        R3.setVisibility(View.GONE);

        input=(EditText) findViewById(R.id.input);
        sec=(Spinner) findViewById(R.id.sec);
        input.setVisibility(View.GONE);
        send.setVisibility(View.GONE);
        auto.setOnClickListener(autobtn);
        hand.setOnClickListener(handbtn);
        send.setOnClickListener(sendbtn);
        bt1.setOnClickListener(btbtn);
        bt2.setOnClickListener(btbtn);
        bt3.setOnClickListener(btbtn);
        bt4.setOnClickListener(btbtn);
        bt5.setOnClickListener(btbtn);
        fab.setOnClickListener(fabclick);
        bt5.setBackgroundColor(Color.YELLOW);
        R3.setOnClickListener(R3btn);
        ArrayAdapter<String> adapterPage=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,mylev);

        // 設定Spinner顯示的格式
        adapterPage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adaptersec=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,mysec);

        // 設定Spinner顯示的格式
        adaptersec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 設定Spinner的資料來源
        sec.setAdapter(adaptersec);

        // 設定 spnPrefer 元件 ItemSelected 事件的 listener 為  spnPreferListener
        sec.setOnItemSelectedListener(spnsec);
        new Reviewn5.DownloadFileAsync().execute();
      //  begin();
    }
    private DragFloatActionButton.OnClickListener fabclick=new DragFloatActionButton.OnClickListener(){
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onClick(View view) {
            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(R3.getLayoutParams());

            long nowTime = System.currentTimeMillis();
            if (nowTime - mLastClickTime > TIME_INTERVAL) {

                mLastClickTime = nowTime;
                faby= dm.heightPixels;// height

                faby=(faby-120)/3;

                if(fab.getY()<faby){

                    marginParams.setMargins(0, (int)fab.getY(), 0, 0);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);

                    R3.setLayoutParams(layoutParams);

                }
                else if(fab.getY()<faby*2){

                    marginParams.setMargins(0, (int)fab.getY(), 0, 0);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);

                    R3.setLayoutParams(layoutParams);

                }
                else{

                    marginParams.setMargins(0, (int)fab.getY()-400, 0, 0);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);

                    R3.setLayoutParams(layoutParams);

                }

                /*    */


                if(tf){

                    R3.setVisibility(View.INVISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                    R3.startAnimation(animation);

                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    tf=false;
                }
                else{

                    R3.setVisibility(View.VISIBLE);

                    Animation  animation = new ScaleAnimation(
                            0f, 1.0f, 0f, 1.0f,
                            0, fab.getX(), 0, fab.getY()-100
                    );
                    animation.setDuration(500);
                    R3.startAnimation(animation);
                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                    tf=true;
                }

            }
            else{
                mLastClickTime = nowTime;
                i++;
            }
        }
    };
    private RelativeLayout.OnClickListener R3btn=new RelativeLayout.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Reviewn5.this, Tangoday.class);
            Bundle bundle=new Bundle();
            bundle.putString("JP", myjp);
            bundle.putString("CH", mych);
            bundle.putString("HIRA", myhiragana);
            bundle.putString("PINYIN", mypinyin);
            bundle.putString("LEVEL", level.getText().toString());////
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);

        }
    };

    private Button.OnClickListener btbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            bt1.setBackgroundColor(Color.WHITE);
            bt2.setBackgroundColor(Color.WHITE);
            bt3.setBackgroundColor(Color.WHITE);
            bt4.setBackgroundColor(Color.WHITE);
            bt5.setBackgroundColor(Color.WHITE);

            switch (view.getId()){

                case R.id.bt1:
                    bt1.setBackgroundColor(Color.YELLOW);
                    LEV=4;
                    url ="https://kei-sei.com/cram/n1.json";
                    level.setText("N1");
                    break;
                case R.id.bt2:
                    bt2.setBackgroundColor(Color.YELLOW);
                    url ="https://kei-sei.com/cram/n2.json";
                    level.setText("N2");
                    LEV=3;
                    break;
                case R.id.bt3:
                    bt3.setBackgroundColor(Color.YELLOW);
                    LEV=2;
                    url ="https://kei-sei.com/cram/n3.json";
                    level.setText("N3");
                    break;
                case R.id.bt4:
                    bt4.setBackgroundColor(Color.YELLOW);
                    LEV=1;
                    url ="https://kei-sei.com/cram/n4.json";
                    level.setText("N4");
                    break;
                case R.id.bt5:
                    bt5.setBackgroundColor(Color.YELLOW);
                    LEV=0;
                    url ="https://kei-sei.com/cram/n5.json";
                    level.setText("N5");
                    break;
                default:
                    break;

            }

            search();
        }
    };
    private Button.OnClickListener sendbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            String str=input.getText().toString();
            String tmp="";
            if(timer != null) {
                timer.cancel();
                //  timer.purge();
                //  timer = null;
            }
            if(LEV==0){
                tmp="n5.json";
            }
            else if(LEV==1){
                tmp="n4.json";
            }
            else if(LEV==2){
                tmp="n3.json";
            }
            else if(LEV==3){
                tmp="n2.json";
            }
            else if(LEV==4){
                tmp="n1.json";
            }
            String result = dbsearchn5.executeQuery( str+"",tmp);
            if(result.equals("null")){mytoast("N5沒有這單字" );}
            try{
                //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
                JSONArray array = new JSONArray(result);
                JSONObject jsonObject = array.getJSONObject(0);
                String word = jsonObject.getString("word");
                String hiragana = jsonObject.getString("hiragana");
                String romaji = jsonObject.getString("romaji");
                String mymeaning = jsonObject.getString("meaning");
                jp.setText(word);
                ch.setText(hiragana);
                pinyin.setText(romaji);
                meaning.setText(mymeaning);

                //}



            }
            catch(JSONException e) {

            }
        }
    };
    private Spinner.OnItemSelectedListener spnlev=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                  //  timer.cancel();
                    LEV=position;


                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };
    private Spinner.OnItemSelectedListener spnsec=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                   // timer.cancel();
                    SEC=position+5;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };
    private Button.OnClickListener handbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            int[] colors = {Color.parseColor("#58DFD4"),Color.parseColor("#F0EAA6")};

            //create a new gradient color
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors);

            gd.setCornerRadius(30f);
            hand.setText("手動輪播");
            Drawable d = getResources().getDrawable(R.drawable.btn_primary);
            d.setColorFilter(Color.parseColor("#FD13AAD1"), PorterDuff.Mode.SRC_ATOP);

            auto.setBackground(d);
            hand.setBackground(gd);
            hand.setText("下一個");
            if(tf){

                tf=false;
            }
            if(timer != null) {
                timer.cancel();

            }

            if(LEV==0){
                url ="https://kei-sei.com/cram/n5.json";
                level.setText("N5");
            }
           else if(LEV==1){
                url ="https://kei-sei.com/cram/n4.json";
                level.setText("N4");
            }
            else if(LEV==2){
                url ="https://kei-sei.com/cram/n3.json";
                level.setText("N3");
            }
            else if(LEV==3){
                url ="https://kei-sei.com/cram/n2.json";
                level.setText("N2");
            }
            else if(LEV==4){
                url ="https://kei-sei.com/cram/n1.json";
                level.setText("N1");
            }
            searchhand();
        }
    };
    public void searchhand(){
        String result = dbn5.executeQuery(url);
        try{
            //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(result);

            Random random=new Random();
            int tmp=random.nextInt(array.length()-1);
            JSONObject jsonObject = array.getJSONObject(tmp);
            String word = jsonObject.getString("word");
            String hiragana = jsonObject.getString("hiragana");
            String romaji = jsonObject.getString("romaji");
            String mymeaning = jsonObject.getString("meaning");
            jp.setText(word);
            ch.setText(hiragana);
            pinyin.setText(romaji);
            meaning.setText(mymeaning);

        }
        catch(JSONException e) {

        }
    }
    public void search(){
        String result = dbn5.executeQuery(url);
        try{
            //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(result);

            Random random=new Random();
            int tmp=random.nextInt(array.length()-1);
            JSONObject jsonObject = array.getJSONObject(tmp);
            String word = jsonObject.getString("word");
            String hiragana = jsonObject.getString("hiragana");
            String romaji = jsonObject.getString("romaji");
            String mymeaning = jsonObject.getString("meaning");
            myjp = jsonObject.getString("word");
            myhiragana = jsonObject.getString("hiragana");
            mypinyin = jsonObject.getString("romaji");
            mych = jsonObject.getString("meaning");
            jp.setText(word);
            ch.setText(hiragana);
            pinyin.setText(romaji);
            meaning.setText(mymeaning);
            ///
            ch3.setText(myjp);
            level3.setText(level.getText().toString());

        }
        catch(JSONException e) {

        }



        ///
    }
    public void begin() {
        timer.schedule(task2, 3000, 6000) ;
        if(Ltmp!=LEV || stmp!=SEC || tf){
            Ltmp=LEV;
            stmp=SEC;
            timer.cancel();
            //timer=null;
           // timer.schedule(task, SEC*1000, SEC*1000) ;
              timer = new Timer();
        }

            timer.schedule(new MyTimerTask(), SEC*1000, SEC*1000) ;

    }
    public class MyTimerTask extends TimerTask
    {
        public void run()
        {

            if(LEV==0){
                url ="https://kei-sei.com/cram/n5.json";
                level.setText("N5");
            }
            else if(LEV==1){
                url ="https://kei-sei.com/cram/n4.json";
                level.setText("N4");
            }
            else if(LEV==2){
                url ="https://kei-sei.com/cram/n3.json";
                level.setText("N3");
            }
            else if(LEV==3){
                url ="https://kei-sei.com/cram/n2.json";
                level.setText("N2");
            }
            else if(LEV==4){
                url ="https://kei-sei.com/cram/n1.json";
                level.setText("N1");
            }
            String result = dbn5.executeQuery(url);
            try{
                //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
                JSONArray array = new JSONArray(result);
                //  for (int i = 0; i < array.length(); i++) {
                Random random=new Random();
                int tmp=random.nextInt(array.length()-1);
                JSONObject jsonObject = array.getJSONObject(tmp);
                String word = jsonObject.getString("word");
                String hiragana = jsonObject.getString("hiragana");
                String romaji = jsonObject.getString("romaji");
                String mymeaning = jsonObject.getString("meaning");
                jp.setText(word);
                ch.setText(hiragana);
                pinyin.setText(romaji);
                meaning.setText(mymeaning);

            }
            catch(JSONException e) {

            }
        }
    };
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {
                @SuppressLint("RestrictedApi")
                @Override
                public void run() {
                    if(LEV==0){
                        url ="https://kei-sei.com/cram/n5.json";
                        level.setText("N5");
                    }
                    else if(LEV==1){
                        url ="https://kei-sei.com/cram/n4.json";
                        level.setText("N4");
                    }
                    else if(LEV==2){
                        url ="https://kei-sei.com/cram/n3.json";
                        level.setText("N3");
                    }
                    else if(LEV==3){
                        url ="https://kei-sei.com/cram/n2.json";
                        level.setText("N2");
                    }
                    else if(LEV==4){
                        url ="https://kei-sei.com/cram/n1.json";
                        level.setText("N1");
                    }
                    String result = dbn5.executeQuery(url);
                    try{
                        //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
                        JSONArray array = new JSONArray(result);

                        Random random=new Random();
                        int tmp=random.nextInt(array.length()-1);
                        JSONObject jsonObject = array.getJSONObject(tmp);
                        String word = jsonObject.getString("word");
                        String hiragana = jsonObject.getString("hiragana");
                        String romaji = jsonObject.getString("romaji");
                        String mymeaning = jsonObject.getString("meaning");
                        jp.setText(word);
                        ch.setText(hiragana);
                        pinyin.setText(romaji);
                        meaning.setText(mymeaning);

                    }
                    catch(JSONException e) {

                    }

                }
            });
        }
    };
    private Button.OnClickListener autobtn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            /**/
            int[] colors = {Color.parseColor("#58DFD4"),Color.parseColor("#F0EAA6")};

            //create a new gradient color
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors);

            gd.setCornerRadius(30f);
            hand.setText("手動輪播");
            Drawable d = getResources().getDrawable(R.drawable.btn_primary);
            d.setColorFilter(Color.parseColor("#FD13AAD1"), PorterDuff.Mode.SRC_ATOP);

            auto.setBackground(gd);
            hand.setBackground(d);



            if(!tf){


                if(Ltmp!=LEV || stmp!=SEC  ){
                    Ltmp=LEV;
                    stmp=SEC;
                    timer.cancel();

                }
                timer = new Timer();
                timer.schedule(new MyTimerTask(), SEC*1000, SEC*1000) ;

                tf=true;
            }
            else{

            }


        }
    };
    public void seraechsql2(){
        url ="https://kei-sei.com/cram/n5.json";
        String result = dbn5.executeQuery(url);
        try{
            //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(result);
            //  for (int i = 0; i < array.length(); i++) {
            Random random=new Random();
            int tmp=random.nextInt(array.length()-1);
            JSONObject jsonObject = array.getJSONObject(tmp);
            String word = jsonObject.getString("word");
            String hiragana = jsonObject.getString("hiragana");
            String romaji = jsonObject.getString("romaji");
            String mymeaning = jsonObject.getString("meaning");
            jp.setText(word);
            ch.setText(hiragana);
            pinyin.setText(romaji);
            meaning.setText(mymeaning);
            level.setText("N5");

        }
        catch(JSONException e) {

        }
    }

    TimerTask task2 = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {
                @SuppressLint("RestrictedApi")
                @Override
                public void run() {

                    fab.setVisibility(View.VISIBLE);

                    tf=true;

                }
            });
        }
    };
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {
            String result = dbtango.executeQuery();
            Random random=new Random();
            try{
                JSONArray jsonArray = new JSONArray(result);
                int num= random.nextInt(jsonArray.length());

                JSONObject jsonData = jsonArray.getJSONObject(num);


                mypinyin =jsonData.getString("jp_kana");
                myjp=jsonData.getString("jp_kanji");
                mych=jsonData.getString("zh_word");
                myen=jsonData.getString("en_word");
                mylevel=jsonData.getString("level");


                if(myjp.equals("null")){
                    myjp=jsonData.getString("jp_kana");
                }
            }

            catch(Exception e){}
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused)
        {seraechsql2();
            url ="https://kei-sei.com/cram/n5.json";
            String result = dbn5.executeQuery(url);
            try{
                //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
                JSONArray array = new JSONArray(result);
                //  for (int i = 0; i < array.length(); i++) {
                Random random=new Random();
                int tmp=random.nextInt(array.length()-1);
                JSONObject jsonObject = array.getJSONObject(tmp);
                myjp = jsonObject.getString("word");
               myhiragana = jsonObject.getString("hiragana");
                mypinyin = jsonObject.getString("romaji");
                mych = jsonObject.getString("meaning");
                jp.setText(myjp);
                ch.setText(myhiragana);
                pinyin.setText(mypinyin);
                meaning.setText(mych);
                level.setText("N5");

            }
            catch(JSONException e) {

            }
            ch3.setText(myjp);
            level3.setText("N5");
            Random random=new Random();
            int r=random.nextInt(30);
            int[] songfile=new int[] {R.drawable.a01, R.drawable.a02, R.drawable.a03, R.drawable.a04, R.drawable.a05,R.drawable.a06, R.drawable.a07,
                    R.drawable.a08, R.drawable.a09, R.drawable.a10,R.drawable.a11, R.drawable.a12,
                    R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19,
                    R.drawable.a20,R.drawable.a21, R.drawable.a22, R.drawable.a23, R.drawable.a24, R.drawable.a25,R.drawable.a26,
                    R.drawable.a27, R.drawable.a28, R.drawable.a29, R.drawable.a30,R.drawable.a31};
            pic3.setImageResource(songfile[r]);

            R3.setVisibility(View.VISIBLE);
            R3.setGravity(Gravity.BOTTOM);

            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(R3.getLayoutParams());

            marginParams.setMargins(0, (int)fab.getY()-400, 0, 0);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);

            R3.setLayoutParams(layoutParams);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);


            animation = new ScaleAnimation(
                    0f, 1.0f, 0f, 1.0f,
                    0, fab.getX(), 0, fab.getY()
            );
            animation.setDuration(500);
            R3.startAnimation(animation);
            tf=true;

            begin();
        }
    }


    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}