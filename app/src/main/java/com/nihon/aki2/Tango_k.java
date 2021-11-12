package com.nihon.aki2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.mydb.dbn5;
import com.nihon.aki2.mydb.dbsantance;
import com.nihon.aki2.mydb.dbtango;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Tango_k extends AppCompatActivity {
    Dialog dia;
    Context context;
    String[] list2= {"關於" };

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 800L;
    String url ="https://kei-sei.com/cram/n5.json";
    int i=0;
    DisplayMetrics dm = new DisplayMetrics();
    Spinner spnlev,spntype;
    TextView ch,jp,pinyin, level,meaning;
    String mypinyin="";
    String myjp="";
    String mych="",myhiragana="",mylevel="";
    ////
    private RelativeLayout R3,R4,R5;
    ImageView pic3,showup,showoff;
    TextView jp3,ch3,level3;

    String myen;
    double faby=0;
    boolean tf=true;
    //DragFloatActionButton fab;

    Timer timer = new Timer();
    TextView  pretxt,nexttxt;
    View btnpre,btnnext;
    /////
    String course[]={"N5","N4","N3","N2","N1"};
    String typelist[]={"手動輪播","自動輪播5秒","自動輪播10秒"};
    int LEV=0,SEC=0,Ltmp=0,stmp=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tango_k);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView menulist = (ImageView) findViewById(R.id.menulist);
        menulist.setOnClickListener(menulistbtn);
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
        pretxt=(TextView)findViewById(R.id.pretxt);
        nexttxt=(TextView)findViewById(R.id.nexttxt);
        btnpre=(View)findViewById(R.id.btnpre);
        btnnext=(View)findViewById(R.id.btnnext);
        pretxt.setOnClickListener(pretxtbtn);
        btnpre.setOnClickListener(pretxtbtn);
        nexttxt.setOnClickListener(pretxtbtn);
        btnnext.setOnClickListener(pretxtbtn);
        ////////////////
        spnlev=(Spinner)findViewById(R.id.spnlev);
        spntype=(Spinner)findViewById(R.id.spntype);
        jp=(TextView)findViewById(R.id.jp);
        ch=(TextView)findViewById(R.id.ch);
        pinyin=(TextView)findViewById(R.id.pinyin);
        level=(TextView)findViewById(R.id.level);
        meaning=(TextView)findViewById(R.id.meaning);
////////////////////////////
        pic3=(ImageView)findViewById(R.id.pic3);
        R3=findViewById(R.id.R3);
        R4=findViewById(R.id.R4);
        R5=findViewById(R.id.R5);
        showup=(ImageView)findViewById(R.id.showup);
        showoff=(ImageView)findViewById(R.id.showoff);
        ch3=(TextView)findViewById(R.id.ch3);
        jp3=(TextView)findViewById(R.id.jp3);
        level3=(TextView)findViewById(R.id.level3);
        //fab = findViewById(R.id.fab);
        R4.setVisibility(View.GONE);
        showoff.setOnClickListener(showtfbtn);
        showup.setOnClickListener(showtfbtn);
       // ch3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
      //  jp3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        // new Reviewn5.DownloadFileAsync().execute();
       // R3.setOnClickListener(R3btn);
        R3.setVisibility(View.GONE);
        //fab.setVisibility(View.GONE);
      //  fab.setOnClickListener(fabclick);
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

/////////////////


        /////////////////
        ArrayAdapter<String> spnlevpn=new ArrayAdapter<String>(this, R.layout.tango_orange,course);

        spnlev.setAdapter(spnlevpn);
        spnlev.setOnItemSelectedListener(levbtn);

        ArrayAdapter<String> spntypespn=new ArrayAdapter<String>(this,R.layout.tango_orange,typelist);
        spntype.setAdapter(spntypespn);
        spntype.setOnItemSelectedListener(typebtn);
        ch3.setOnClickListener(ch3btn);
       new Tango_k.DownloadFileAsync().execute();
/////////////////////
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
                        startActivity(new Intent(getApplicationContext(),Info_k.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn8:
                        startActivity(new Intent(getApplicationContext(),Book_k.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private ImageView.OnClickListener menulistbtn=new ImageView.OnClickListener(){

        @Override
        public void onClick(View view) {
            AlertDialog.Builder dialog_list = new AlertDialog.Builder(Tango_k.this);
            // dialog_list.setTitle(" ");
            dialog_list.setItems(list2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                        context = Tango_k.this;
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
                }
            });

            dialog_list.show();
        }
    };
    private TextView.OnClickListener ch3btn=new TextView.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Tango_k.this, Tangoday.class);
            Bundle bundle=new Bundle();
            bundle.putString("JP", myjp);
            bundle.putString("CH", mych);
            bundle.putString("HIRA", myhiragana);
            bundle.putString("PINYIN", mypinyin);
            bundle.putString("LEVEL", mylevel);////
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
        }
    };
    private ImageView.OnClickListener showtfbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.showup:
                    R4.setVisibility(View.INVISIBLE);
                    R5.setVisibility(View.VISIBLE);
                break;

                case  R.id.showoff:
                    R4.setVisibility(View.VISIBLE);
                    R5.setVisibility(View.INVISIBLE);
                break;
                default:
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
                case R.id.nexttxt:
                case R.id.btnnext:
                    if(tf){

                        tf=false;
                    }
                    if(timer != null) {
                        timer.cancel();

                    }
                    searchhand();
                    break;

                default:
                    break;
            }
        }
    };
    private Spinner.OnItemSelectedListener levbtn= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {
          //  ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
          //  ((TextView) parent.getChildAt(0)).setTextSize(25);
            LEV=position;
            if(tf){

                tf=false;
            }
            if(timer != null) {
                timer.cancel();

            }
           if(LEV==4){
               url ="https://kei-sei.com/cram/n1.json";
               searchhand();

               level.setText("N1");
           }

           else if(LEV==3){
               url ="https://kei-sei.com/cram/n2.json";
               searchhand();

               level.setText("N2");
           }
           else if(LEV==2){
               url ="https://kei-sei.com/cram/n3.json";
               searchhand();

               level.setText("N3");
           }
           else if(LEV==1){
               url ="https://kei-sei.com/cram/n4.json";
               searchhand();

               level.setText("N4");
           }
           else if(LEV==0){
               url ="https://kei-sei.com/cram/n5.json";
               searchhand();

               level.setText("N5");
           }

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };
    private Spinner.OnItemSelectedListener typebtn= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {

            if(position==0){
                if(tf){

                    tf=false;
                }
                if(timer != null) {
                    timer.cancel();

                }

                searchhand();
            }
            else if(position==1){
                SEC=5;
                if(tf){

                    tf=false;
                }
                if(timer != null) {
                    timer.cancel();

                }


                if(!tf){


                    if(Ltmp!=LEV || stmp!=SEC  ){
                        Ltmp=LEV;
                        stmp=SEC;
                        timer.cancel();

                    }
                    timer = new Timer();
                    timer.schedule(new Tango_k.MyTimerTask(), SEC*1000, SEC*1000) ;

                    tf=true;
                }
                else{

                }

            }
            else if(position==2){
                SEC=10;
                if(tf){

                    tf=false;
                }
                if(timer != null) {
                    timer.cancel();

                }
                if(!tf){


                    if(Ltmp!=LEV || stmp!=SEC  ){
                        Ltmp=LEV;
                        stmp=SEC;
                        timer.cancel();

                    }
                    timer = new Timer();
                    timer.schedule(new Tango_k.MyTimerTask(), SEC*1000, SEC*1000) ;

                    tf=true;
                }
                else{}


            }

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
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
 /*
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




                if(tf){

                    R3.setVisibility(View.INVISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                    R3.startAnimation(animation);

                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    tf=false;
                }
                else{

                    //R3.setVisibility(View.VISIBLE);

                    Animation  animation = new ScaleAnimation(
                            0f, 1.0f, 0f, 1.0f,
                            0, fab.getX(), 0, fab.getY()-100
                    );
                    animation.setDuration(500);
                    R3.startAnimation(animation);
                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                    tf=true;
                }
*/
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
            Intent intent = new Intent(Tango_k.this, Tangoday.class);
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
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {
            /*
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

             */
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused)
        {

            seraechsql2();
          /* url ="https://kei-sei.com/cram/n5.json";
            String result = dbn5.executeQuery(url);
            try{

                JSONArray array = new JSONArray(result);

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

            } */


            Random random=new Random();
            int r=random.nextInt(30);
            int[] songfile=new int[] {R.drawable.a01, R.drawable.a02, R.drawable.a03, R.drawable.a04, R.drawable.a05,R.drawable.a06, R.drawable.a07,
                    R.drawable.a08, R.drawable.a09, R.drawable.a10,R.drawable.a11, R.drawable.a12,
                    R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19,
                    R.drawable.a20,R.drawable.a21, R.drawable.a22, R.drawable.a23, R.drawable.a24, R.drawable.a25,R.drawable.a26,
                    R.drawable.a27, R.drawable.a28, R.drawable.a29, R.drawable.a30,R.drawable.a31};
            pic3.setImageResource(songfile[r]);

           // R3.setVisibility(View.VISIBLE);
            R3.setGravity(Gravity.BOTTOM);
/*
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
*/
        }
    }
    public void seraechsql2(){

        url ="https://kei-sei.com/cram/n5.json";
        String result = dbn5.executeQuery(url);
        try{


            //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(result);
            Random random=new Random();
            int tmp=0;
            /*

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

             */
            tmp=random.nextInt(array.length()-1);
            JSONObject jsonObject = array.getJSONObject(tmp);
            myjp = jsonObject.getString("word");
             myhiragana = jsonObject.getString("hiragana");
            mypinyin= jsonObject.getString("romaji");
            mych= jsonObject.getString("meaning");
            mylevel="N5";
            ch3.setText(myjp);
            level3.setText("N5");
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
    TimerTask task2 = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {
                @SuppressLint("RestrictedApi")
                @Override
                public void run() {

                    //fab.setVisibility(View.GONE);

                    tf=true;

                }
            });
        }
    };
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}