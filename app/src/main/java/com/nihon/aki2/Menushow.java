package com.nihon.aki2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.transition.Explode;
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
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.MarqueeView;
import com.nihon.aki2.mydb.dbbasic50;
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
    private RelativeLayout parentView,R2,R3,R0;
    private MarqueeView marqueeView2;
    ImageView btn1,btn2,btn3,btn4,btn5,btn6,btn7,pic2,pic3;
    String account="",passwd="",names="";
    TextView  jp,ch,ch2,jp2,level2,jp3,ch3,level3;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();
        //mytoast(account+"\n"+passwd+"\n"+names);
        btn1=(ImageView)findViewById(R.id.btn1);

        btn2=(ImageView)findViewById(R.id.btn2);
        btn3=(ImageView)findViewById(R.id.btn3);
        btn4=(ImageView)findViewById(R.id.btn4);
        btn5=(ImageView)findViewById(R.id.btn5);
        btn6=(ImageView)findViewById(R.id.btn6);
        btn7=(ImageView)findViewById(R.id.btn7);
        pic2=(ImageView)findViewById(R.id.pic2);
        pic3=(ImageView)findViewById(R.id.pic3);
         parentView=findViewById(R.id.marqueeLayout);
        marqueeView2=findViewById(R.id.marquee_view2);
        R2=findViewById(R.id.R2);
        R3=findViewById(R.id.R3);
        R0=findViewById(R.id.R0);
        // pinyin=(TextView)findViewById(R.id.pinyin);
        jp=(TextView)findViewById(R.id.jp);
        ch=(TextView)findViewById(R.id.ch);
        ch2=(TextView)findViewById(R.id.ch2);
        jp2=(TextView)findViewById(R.id.jp2);
        level2=(TextView)findViewById(R.id.level2);
        ch3=(TextView)findViewById(R.id.ch3);
        jp3=(TextView)findViewById(R.id.jp3);
        level3=(TextView)findViewById(R.id.level3);
        fab = findViewById(R.id.fab);
        R2.setVisibility(View.GONE);
        R3.setVisibility(View.GONE);
        ch.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        jp.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        ch2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        jp2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        ch3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        jp3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        //btn6.setVisibility(View.GONE);
        btn1.setOnTouchListener(b1);
        btn2.setOnTouchListener(b2);
        btn3.setOnTouchListener(b3);
        btn4.setOnTouchListener(b4);
        btn5.setOnTouchListener(b5);
        btn6.setOnTouchListener(b6);
        new DownloadFileAsync().execute();
        parentView.setOnClickListener(marbtn);
        R2.setOnClickListener(R2btn);
        R3.setOnClickListener(R3btn);
       // fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(fabclick);
        parentView.setVisibility(View.GONE);
        marqueeView2.setVisibility(View.GONE);
      //  faby = R3.getHeight();

        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

    }

    private DragFloatActionButton.OnClickListener fabclick=new DragFloatActionButton.OnClickListener(){
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onClick(View view) {
          //  RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) R3.getLayoutParams();

          //  layoutParams.removeRule(RelativeLayout.BELOW);
            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(R3.getLayoutParams());

            long nowTime = System.currentTimeMillis();
            if (nowTime - mLastClickTime > TIME_INTERVAL) {

                mLastClickTime = nowTime;
                faby= dm.heightPixels;// height
                // mytoast(faby+"");
                faby=(faby-120)/3;

            if(fab.getY()<faby){
               // R3.setGravity(Gravity.TOP);
               // mytoast("a");

             //   layoutParams.removeRule(RelativeLayout.BELOW);
             //   layoutParams.addRule(RelativeLayout.BELOW, btn1.getId());

              //  R3.setLayoutParams(layoutParams);
                marginParams.setMargins(0, (int)fab.getY(), 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);

                R3.setLayoutParams(layoutParams);

            }
            else if(fab.getY()<faby*2){
              //  R3.setGravity(Gravity.CENTER);

             //   layoutParams.addRule(RelativeLayout.BELOW, fab.getId());
                marginParams.setMargins(0, (int)fab.getY(), 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);

                R3.setLayoutParams(layoutParams);

            }
            else{
               // R3.setGravity(Gravity.BOTTOM);

              //  layoutParams.removeRule(RelativeLayout.BELOW);
               // layoutParams.addRule(RelativeLayout.ABOVE, btn4.getId());
                marginParams.setMargins(0, (int)fab.getY()-400, 0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);

                R3.setLayoutParams(layoutParams);

            }

             /*    */


               if(tf){
                   R2.setVisibility(View.INVISIBLE);
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


           // faby=fab.getY();
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
            Intent intent = new Intent(Menushow.this, Tangoday.class);
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
    private RelativeLayout.OnClickListener R2btn=new RelativeLayout.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Menushow.this, Tangoday.class);
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
    private RelativeLayout.OnClickListener marbtn=new RelativeLayout.OnClickListener(){
        @Override
        public void onClick(View view) {
            /*
            context = Menushow.this;
            dia = new Dialog(context, R.style.edit_AlertDialog_style2);
            dia.setContentView(R.layout.tango);
            TextView pinyin=(TextView)dia.findViewById(R.id.pinyin);
            TextView jp=(TextView)dia.findViewById(R.id.jp);
            TextView ch=(TextView)dia.findViewById(R.id.ch);
            TextView en=(TextView)dia.findViewById(R.id.level);
            Button btok=(Button)dia.findViewById(R.id.btok);


            pinyin.setText(mypinyin);
            jp.setText(myjp);
            ch.setText(mych);

            // en.setText(myen);

            btok.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia.dismiss();
                        }
                    });

            dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
            Window w = dia.getWindow();
            WindowManager.LayoutParams lp = w.getAttributes();
            lp.width = 800;
            //lp.height = 800;

            dia.show();
            dia.onWindowAttributesChanged(lp);
            */
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
            // pinyin.setText(mypinyin);
            //  jp.setText(myjp);
            ch.setText(myjp);
            ch2.setText(myjp);
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
            /*
            marqueeView2.setParentView(parentView);
            marqueeView2.setScrollSpeed(25);
            marqueeView2.setScrollDirection(MarqueeView.DOWN_TO_UP);
            //marqueeView2.setViewMargin(15);//间距
            marqueeView2.startScroll();


             */
            R3.setVisibility(View.VISIBLE);
            R3.setGravity(Gravity.BOTTOM);
           // RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) R3.getLayoutParams();
            //layoutParams.addRule(RelativeLayout.ABOVE, btn4.getId());
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
           // layoutParams.removeRule(RelativeLayout.ABOVE);
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
                    // TODO Auto-generated method stub
                  //  parentView.setVisibility(View.GONE);
                   //  marqueeView2.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);
                 //  R2.setVisibility(View.VISIBLE);
                   tf=true;

                }
            });
        }
    };
    private ImageView.OnTouchListener b1=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//課程

                case MotionEvent.ACTION_DOWN:
                    btn1.setImageResource(R.drawable.aki_course);

                    break;
                case MotionEvent.ACTION_UP:
                    btn1.setImageResource(R.drawable.aki_courseh);

                    Intent intent=new Intent();
                    intent.setClass(Menushow.this, MainActivity.class);
                    //Work.class
                    //  intent.setClass(Menushow.this,Myweb.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b2=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//匯率
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn2.setImageResource(R.drawable.aki_rate);

                    break;
                case MotionEvent.ACTION_UP:
                    btn2.setImageResource(R.drawable.aki_rateh);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this,Change.class);
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
                    btn3.setImageResource(R.drawable.aki_news);

                    break;
                case MotionEvent.ACTION_UP:
                    btn3.setImageResource(R.drawable.aki_newh);
                    Intent intent=new Intent();
                    // mytoast("請稍後");
                    intent.setClass(Menushow.this,Myweb.class);
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
                    btn4.setImageResource(R.drawable.aki_jlpth);

                    break;
                case MotionEvent.ACTION_UP:
                    btn4.setImageResource(R.drawable.aki_jlpt);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this,Studymap.class);
                    //Work   Info
                    startActivity(intent);

                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b5=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//test
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn5.setImageResource(R.drawable.aki_test);

                    break;
                case MotionEvent.ACTION_UP:
                    btn5.setImageResource(R.drawable.aki_testh);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this,Shiken.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener b6=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//課程

                case MotionEvent.ACTION_DOWN:
                    btn6.setImageResource(R.drawable.aki_listenh);

                    break;
                case MotionEvent.ACTION_UP:
                    btn6.setImageResource(R.drawable.aki_listen);
                    Intent intent=new Intent();
                    //intent.setClass(Menushow.this, JRmap.class);
                    //Work.class
                    intent.setClass(Menushow.this,Listenmenu.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
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
            new AlertDialog.Builder(Menushow.this)
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
