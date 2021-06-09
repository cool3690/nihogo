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

import com.nihon.aki2.control.GlobalVariable;
import com.nihon.aki2.control.MarqueeView;
import com.nihon.aki2.mydb.dbtango;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Menushow2 extends AppCompatActivity {
    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 800L;
    int i=0;
    DisplayMetrics dm = new DisplayMetrics();
    private RelativeLayout R3;

    ImageView pic3;

    TextView jp3,ch3,level3;

    String mypinyin;
    String myjp;
    String mych;
    String myen,mylevel;
    double faby=0;
    boolean tf=true;
    DragFloatActionButton fab;

    Timer timer = new Timer();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menushow2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pic3=(ImageView)findViewById(R.id.pic3);
        R3=findViewById(R.id.R3);
        ch3=(TextView)findViewById(R.id.ch3);
        jp3=(TextView)findViewById(R.id.jp3);
        level3=(TextView)findViewById(R.id.level3);
        fab = findViewById(R.id.fab);
        R3.setVisibility(View.GONE);
        ch3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        jp3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        new DownloadFileAsync().execute();
        R3.setOnClickListener(R3btn);
        fab.setOnClickListener(fabclick);
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);


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
                // mytoast(faby+"");
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
            Intent intent = new Intent(Menushow2.this, Tangoday.class);
            Bundle bundle=new Bundle();
            bundle.putString("JP", myjp);
            bundle.putString("CH", mych);
            bundle.putString("PINYIN", mypinyin);
            bundle.putString("LEVEL", mylevel);
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
        protected void onPostExecute(String unused) {

            ch3.setText(myjp);
            level3.setText("("+mylevel+")");
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

                    fab.setVisibility(View.VISIBLE);
                    tf=true;

                }
            });
        }
    };




}
