package com.nihon.aki2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Basic extends AppCompatActivity {
    TextView Q1,txtResult;
    ImageView nextpage,prepage;
    Button btsong;
    RadioGroup radioGroup;
    RadioButton a1,a2,a3,a4;
    public MediaPlayer mediaplayer;
   int[] songfile=new int[] {R.raw.a, R.raw.i, R.raw.u, R.raw.e, R.raw.o,R.raw.ka, R.raw.ki, R.raw.ku, R.raw.ke, R.raw.ko,R.raw.sa, R.raw.shi, R.raw.su, R.raw.se, R.raw.so,
    R.raw.ta, R.raw.chi, R.raw.tsu, R.raw.te, R.raw.to,R.raw.na, R.raw.ni, R.raw.nu, R.raw.ne, R.raw.no,R.raw.ha, R.raw.hi, R.raw.hu, R.raw.he, R.raw.ho,R.raw.ma, R.raw.mi, R.raw.mu,
    R.raw.me, R.raw.mo ,R.raw.ya, R.raw.yu, R.raw.yo,R.raw.ra, R.raw.ri, R.raw.ru, R.raw.re, R.raw.ro,R.raw.wa,R.raw.n,R.raw.wo};

   // int[] songfile=new int[] {R.raw.a, R.raw.i, R.raw.u, R.raw.e, R.raw.o,R.raw.ka, R.raw.ki, R.raw.ku, R.raw.ke, R.raw.ko};
    boolean lock=true;
    String ans="",account="",passwd="";
    int num=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
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

        Q1=(TextView)findViewById(R.id.Q1);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup) ;
        a1=(RadioButton)findViewById(R.id.a1);
        a2=(RadioButton)findViewById(R.id.a2);
        a3=(RadioButton)findViewById(R.id.a3);
        a4=(RadioButton)findViewById(R.id.a4);
        btsong=(Button)findViewById(R.id.btsong);
        nextpage=(ImageView)findViewById(R.id.nextpage);
        prepage=(ImageView)findViewById(R.id.prepage);
        txtResult=(TextView)findViewById(R.id.txtResult);
        mediaplayer=new MediaPlayer();
        String result = dbbasic.executeQuery(num+"");
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

            playSong(songfile[num-1]);
        }

        catch(Exception e){}
        radioGroup.setOnCheckedChangeListener(answer);
        nextpage.setOnTouchListener(page);
        prepage.setOnTouchListener(pagepre);
        btsong.setOnClickListener(btnsong);

        // prepage.setOnClickListener(pagepre2);

    }
    private RadioGroup.OnCheckedChangeListener answer=
            new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int  p = group.indexOfChild((RadioButton) findViewById(checkedId));	// 選項的索引值
                    int count = group.getChildCount(); // 清單總共有多少項
                    p++;
                    if(lock==true){ show (p);}
                    else{}
                    txtResult.setText("正確答案是:  "+ans  );

                }
            };

    void show (int p){
        if(ans.equals(p+"")){mytoast("正解!");}
        else{mytoast("錯誤!");}

    }


    private Button.OnClickListener btnsong=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {

                case R.id.btsong:  //播放
                        playSong(songfile[num-1]);
                    break;
            }
        }
    };
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
    private void playSong(int song) {
        mediaplayer.reset();
        mediaplayer= MediaPlayer.create(Basic.this, song); //播放歌曲源
        try {
            mediaplayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
    public void pre(){
        num--;
        lock=false;
        radioGroup.clearCheck();
        txtResult.setText(" ");
        String result = dbbasic.executeQuery(num+"");
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
                ans=jsonData.getString("ans");
            }
            playSong(songfile[num-1]);
        }

        catch(Exception e){}
        lock=true;
    }
    public void next(){
        num++;
        lock=false;
        radioGroup.clearCheck();

        String result = dbbasic.executeQuery(num+"");
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
            playSong(songfile[num-1]);
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
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Basic.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Basic.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Basic.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Basic.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Basic.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Basic.this, Mymenu.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Basic.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Basic.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Basic.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
