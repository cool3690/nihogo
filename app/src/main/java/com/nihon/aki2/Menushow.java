package com.nihon.aki2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nihon.aki2.control.GlobalVariable;
import com.nihon.aki2.control.MarqueeView;
import com.nihon.aki2.mydb.dbtango;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Menushow extends AppCompatActivity {
    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 800L;
    int i=0;
    DisplayMetrics dm = new DisplayMetrics();
    private RelativeLayout   R3,R0;
   // private MarqueeView marqueeView2;
    ImageView btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8 ;
    String account="",passwd="",names="";
    TextView  jp,ch,  jp3,ch3,level3;
    Context context;
    String mypinyin;
    String myjp;
    String mych;
    String myen,mylevel;
    double fabx=0,faby=0,fabx2=0,faby2=0;
    boolean tf=true;
    DragFloatActionButton fab;
    private Menu menu;
    Timer timer = new Timer();
    Dialog dia;
    int x=0;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menushow);
        /**/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();

        btn1=(ImageView)findViewById(R.id.btn1);

        btn2=(ImageView)findViewById(R.id.btn2);
        btn3=(ImageView)findViewById(R.id.btn3);
        btn4=(ImageView)findViewById(R.id.btn4);




        btn1.setOnTouchListener(b1);
        btn2.setOnTouchListener(b2);
        btn3.setOnTouchListener(b3);
        btn4.setOnTouchListener(b4);


 /*
        new DownloadFileAsync().execute();

        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
         getPermission();
       */
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
                        startActivity(new Intent(getApplicationContext(),Myweb.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn7:
                        startActivity(new Intent(getApplicationContext(),Change.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn8:
                        startActivity(new Intent(getApplicationContext(),Studymap.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
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


        }
    }
    public void begin() {
        timer.schedule(task, 3000, 6000) ;
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {
                @SuppressLint("RestrictedApi")
                @Override
                public void run() {


                }
            });
        }
    };
    private ImageView.OnTouchListener b1=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//測驗

                case MotionEvent.ACTION_DOWN:
                    btn1.setImageResource(R.drawable.k_examh);

                    break;
                case MotionEvent.ACTION_UP:
                    btn1.setImageResource(R.drawable.k_exam);

                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("ANS","A");
                    intent.putExtras(bundle);//MainActivity
                    intent.setClass(Menushow.this, Examk.class);
                    //Work.class
                    //intent.setClass(Menushow.this,Listenchild.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b2=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//單字
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn2.setImageResource(R.drawable.k_tango);

                    break;
                case MotionEvent.ACTION_UP:
                    btn2.setImageResource(R.drawable.k_tango);
                    Intent intent=new Intent();
                    //  intent.setClass(Menushow.this,Qrcode.class);
                  intent.setClass(Menushow.this,Shiken.class);
                    startActivity(intent);
                    //  mytoast("維護中");
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b3=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//新聞
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn3.setImageResource(R.drawable.k_listenh);

                    break;
                case MotionEvent.ACTION_UP:
                    btn3.setImageResource(R.drawable.k_listen);
                    Intent intent=new Intent();
                    // mytoast("請稍後");
                    intent.setClass(Menushow.this,Listening.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b4=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//JLPT倒數
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    // btn4.setImageResource(R.drawable.aki_shop);
                    btn4.setImageResource(R.drawable.k_jishoh);

                    break;
                case MotionEvent.ACTION_UP:
                    btn4.setImageResource(R.drawable.k_jisho);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this,Jishoweb.class);
                    //Work   Info
                    startActivity(intent);

                    break;
            }
            return true;
        }
    };



    public void getPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);

        }
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
            intent.setClass(Menushow.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null ||account==""){
                Intent intent=new Intent();
                intent.setClass(Menushow.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Menushow.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            /*
            new AlertDialog.Builder(Menushow.this)
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
            context = Menushow.this;
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
