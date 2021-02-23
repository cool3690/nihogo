package com.nihon.aki2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.Listenlist;
import com.nihon.aki2.control.ListenlistAdapter;
import com.nihon.aki2.mydb.dblisten;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Listenchild extends AppCompatActivity {
    ArrayList<Listenlist> listenlists = new ArrayList<Listenlist>();
    ListView listview;
    Dialog dia;
    ImageView play,btnpre,btnnext,pic;
    private Dialog dialog;
    //  TransparentProgressDialog mProgressDialog;
    //  private  AlertDialog dia ;
    private Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8;
    // ImageView []btarr=new ImageView[]{bt0,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9};,bt9,bt10,bt11,bt12,bt13
    String child_a[]={
            "01_L1 大家來說.mp3","02_L1 大家來讀.mp3","03_L1 語彙的韻律.mp3","04_L1 大家來寫.mp3","05_L1 大家來玩.mp3",
            "06_L2 大家來說.mp3","07_L2 大家來讀.mp3","08_L2 語彙的韻律.mp3","09_L2 大家來寫.mp3","10_L2 大家來唱.mp3","11_L3 大家來說.mp3",
            "12_L3 大家來讀.mp3","13_L3 語彙的韻律.mp3","14_L3 大家來寫.mp3","15_L3 大家來玩.mp3","16_L4 大家來說.mp3","17_L4 這些是什麼.mp3",
            "18_L4 大家來讀.mp3","19_L4 語彙的韻律.mp3","20_L4 大家來唱.mp3","21_L4 大家來玩.mp3","22_L5 大家來說.mp3","23_L5 形容詞.mp3",
            "24_L5 大家來讀.mp3","25_L5 語彙的韻律.mp3","26_L5 大家來唱.mp3","27_L5 人稱.mp3","28_L6 大家來說.mp3","29_L6 食物、飲料.mp3",
            "30_L6 大家來讀.mp3","31_L6 語彙的韻律.mp3","32_L6 大家來玩.mp3","33_L7 大家來說.mp3","34_L7 這是什麼顏色.mp3",
            "35_L7 大家來讀.mp3","36_L7 語彙的韻律.mp3","37_L7 大家來唱.mp3","38_L7 大家來玩.mp3","39_L8 大家來說.mp3",
            "40_L8 大家來讀.mp3","41_L8 語彙的韻律.mp3","42_L8 大家來唱.mp3","43_平假名.mp3","44_附錄 打招呼.mp3","45_附錄 動物.mp3",
            "46_附錄 食物、飲料.mp3","47_附錄 動詞.mp3","48_附錄 形容詞.mp3",
            "49_附錄顏色.mp3","50_1～3課練習1.mp3","51_1～3課練習2.mp3","52_1～3課練習3.mp3","53_4～6課練習1.mp3","54_4～6課練習2.mp3",
            "55_4～6課練習3.mp3","56_7～8課練習1.mp3","57_7～8課練習2.mp3","58_7～8課練習3.mp3",
            "59_7～8課練習4.mp3","60_7～8課練習5.mp3","61_7～8課練習6.mp3"};
    String child_b[]={
            "01_L9 大家來說.mp3","02_L9 身體.mp3","03_L9 臉.mp3","04_L9 大家來讀.mp3","05_L9 語彙的韻律.mp3","06_L9 大家來唱.mp3","07_L9 大家來玩.mp3",
            "08_L10 大家來說.mp3","09_L10 大家來讀.mp3","10_L10 語彙的韻律.mp3","11_L10 你怎麼了.mp3","12_L10 大家來寫.mp3","13_L10 大家來玩.mp3",
            "14_L11 大家來說.mp3","15_L11 大家來讀.mp3","16_L11 語彙的韻律.mp3","17_L11 漂亮嗎.mp3","18_L11 大家來寫.mp3","19_L11 大家來唱.mp3",
            "20_L12 大家來說.mp3","21_L12 數字.mp3","22_L12 大家來讀.mp3","23_L12 語彙的韻律.mp3","24_L12 大家來玩.mp3","25_L12 大家來寫.mp3",
            "26_L13 大家來說.mp3","27_L13 大家來讀.mp3","28_L13 語彙的韻律.mp3","29_L13 來玩吧.mp3","30_L13 大家來唱.mp3","31_L14 大家來說.mp3",
            "32_L14 交通工具.mp3","33_L14 大家來讀.mp3","34_L14 語彙的韻律.mp3","35_L14 大家來玩.mp3","36_L15 大家來說.mp3","37_L15 大家來讀.mp3",
            "38_L15 語彙的韻律.mp3","39_L15 商店.mp3","40_L15 大家來玩.mp3","41_L15 大家來寫.mp3","42_L16 大家來說.mp3","43_L16 大家來讀.mp3",
            "44_L16 語彙的韻律.mp3","45_L16 你高興嗎.mp3","46_L16 大家來唱.mp3","47_L16 大家來玩.mp3","48_平假名.mp3","49_附錄 打招呼.mp3",
            "50_附錄 身體.mp3","51_附錄 臉.mp3","52_附錄 數字.mp3","53_附錄 交通工具.mp3","54_附錄 商店.mp3","55_附錄 動詞.mp3","56_附錄 形容詞.mp3",
            "57_附錄 形容動詞.mp3","58_9～11課練習.mp3","59_9～11課練習2.mp3","60_9～11課練習3.mp3","61_9～11課練習4.mp3","62_9～11課練習5.mp3",
            "63_12～14課練習1.mp3","64_12～14課練習2.mp3","65_12～14課練習3.mp3","66_15～16課練習1.mp3","67_15～16課練習2.mp3","68_15～16課練習3.mp3",
            "69_15～16課練習4.mp3"};

    String child_c[]={
            "01_L17 大家來說.mp3","02_L17 大家來讀.mp3","03_L17 語彙的韻律.mp3","04_L17 幾點鐘？.mp3","05_L17 大家來玩.mp3","06_L17 大家來唱.mp3",
            "07_L18 大家來說.mp3","08_L18 大家來讀.mp3","09_L18 語彙的韻律.mp3","10_L18 加油.mp3","11_L18 大家來玩.mp3","12_L19 大家來說.mp3",
            "13_L19 大家來讀.mp3","14_L19 語彙的韻律.mp3","15_L19 大家來唱.mp3","16_L19 天氣.mp3","17_L20 大家來說.mp3","18_L20 大家來讀.mp3",
            "19_L20 語彙的韻律.mp3","20_L20 上、下.mp3","21_L20 小翔在哪裡？.mp3","22_L20 大家來寫.mp3","23_L20 大家來玩.mp3","24_L21 大家來說.mp3",
            "25_L21 大家來讀.mp3","26_L21 語彙的韻律.mp3","27_L21 大家來玩.mp3","28_L21 在？不在？.mp3","29_L21 大家來唱.mp3",
            "30_L21 一個人、兩個人.mp3","31_L22 大家來說.mp3","32_L22 有、沒有.mp3","33_L22 大家來讀.mp3","34_L22 語彙的韻律.mp3","35_L22 大家來寫.mp3",
            "36_L23 大家來說.mp3","37_L23 大家來讀.mp3","38_L23 語彙的韻律.mp3","39_L23 會？不會？.mp3","40_L23 大家來寫.mp3","41_L23 大家來玩.mp3",
            "42_L24 大家來說.mp3","43_L24 大家來讀.mp3","44_L24 語彙的韻律.mp3","45_L24 你想要什麼？.mp3","46_L24 好吃？看起來很好吃？.mp3",
            "47_L24 大家來唱.mp3","48_L24 大家來玩.mp3","49_平假名.mp3","50_附錄 動詞.mp3","51_附錄 形容詞.mp3","52_附錄 時間.mp3","53_附錄 人數.mp3",
            "54_附錄 位置.mp3","55_附錄 建築物.mp3",
            "56_附錄 天氣.mp3","57_17～19課練習1.mp3","58_17～19課練習2.mp3","59_17～19課練習3.mp3","60_17～19課練習4.mp3",
            "61_17～19課練習5.mp3","62_20～22課練習1.mp3","63_20～22課練習2.mp3","64_20～22課練習3.mp3","65_20～22課練習4.mp3","66_23～24課練習1.mp3",
            "67_23～24課練習2.mp3","68_23～24課練習3.mp3","69_23～24課練習4.mp3","70_23～24課練習5.mp3","71_23～24課練習6.mp3","72_23～24課練習7.mp3"};
    SeekBar sbar;
    int count=0;
    boolean tf=true;
    int totalTime=0;
    int L=0,T=0,length=0;
    String les="",account="";
    String track="";
    TextView remainingTimeLabel;
    public MediaPlayer mediaplayer;
  //  String []filename=new String[45];
    //String []lessons=new String[13];
   // int []p1=new int[]{3,4,3,5,4,3,4,4,3,4,4,3};

    String url="https://akkyschool.com/images/listening/",ans="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listenchild);
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
        mediaplayer = new MediaPlayer();
        play = (ImageView) findViewById(R.id.play);
        pic=(ImageView)findViewById(R.id.pic);
        sbar = (SeekBar) findViewById(R.id.sbar);
        btnpre=(ImageView)findViewById(R.id.btnpre);
        btnnext=(ImageView)findViewById(R.id.btnnext);
        remainingTimeLabel=(TextView)findViewById(R.id.remainingTimeLabel);
        bt1=(Button)findViewById(R.id.bt1);

        bt2=(Button)findViewById(R.id.bt2);
        bt3=(Button)findViewById(R.id.bt3);
        bt4=(Button)findViewById(R.id.bt4);
        bt5=(Button)findViewById(R.id.bt5);
        bt6=(Button)findViewById(R.id.bt6);
        bt7=(Button)findViewById(R.id.bt7);
        bt8=(Button)findViewById(R.id.bt8);

        bt1.setOnClickListener(bt1btn);
        bt2.setOnClickListener(bt1btn);
        bt3.setOnClickListener(bt1btn);
        bt4.setOnClickListener(bt1btn);
        bt5.setOnClickListener(bt1btn);
        bt6.setOnClickListener(bt1btn);
        bt7.setOnClickListener(bt1btn);
        bt8.setOnClickListener(bt1btn);

        /* */

        listview = (ListView) findViewById(R.id.list);
        listview.setOnItemClickListener(lvonclick);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        ans= bundle.getString("ANS");
        if(ans.equals("A")){

           length=child_a.length;

            pic.setImageResource(R.drawable.aki_p1);
            url="https://akkyschool.com/images/listening/child_1/";
            bt8.setVisibility(View.GONE);

        }
       else if(ans.equals("B")){

            length=child_b.length;

            pic.setImageResource(R.drawable.aki_p1);
            url="https://akkyschool.com/images/listening/child_2/";
            bt8.setVisibility(View.GONE);
        }
        else if(ans.equals("C")){

            length=child_c.length;

            pic.setImageResource(R.drawable.aki_p1);
            url="https://akkyschool.com/images/listening/child_3/";
            bt8.setVisibility(View.VISIBLE);
        }
        play.setOnClickListener(playbyn);
        sbar.setOnSeekBarChangeListener(sbarbtn);

        startDownload();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaplayer != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mediaplayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();
        btnnext.setOnClickListener(nextbtn);
        btnpre.setOnClickListener(prebtn);


    }
    private Button.OnClickListener bt1btn=new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt1:
                    listview.smoothScrollToPositionFromTop(0,0);
                    break;
                case  R.id.bt2:
                    listview.smoothScrollToPositionFromTop(10,0);
                    break;
                case  R.id.bt3:
                    listview.smoothScrollToPositionFromTop(20,0);
                    break;
                case  R.id.bt4:
                    listview.smoothScrollToPositionFromTop(30,0);
                    break;
                case  R.id.bt5:
                    listview.smoothScrollToPositionFromTop(40,0);
                    break;
                case  R.id.bt6:
                    listview.smoothScrollToPositionFromTop(50,0);
                    break;
                case  R.id.bt7:
                    listview.smoothScrollToPositionFromTop(60,0);
                    break;
                case  R.id.bt8:
                    listview.smoothScrollToPositionFromTop(71,0);
                    break;

                default:
                    break;
            }


        }
    };
    private ImageView.OnClickListener nextbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            String tmp=url;
            count++;
            if(count>=length){mytoast("最後一個了");count--;}
            else {
                if(ans.equals("A")){
                    tmp+=child_a[count];
                }
                else if(ans.equals("B")){
                    tmp+=child_b[count];
                }
                else if(ans.equals("C")){
                    tmp+=child_c[count];
                }



                try {//+filename[0]
                    totalTime=0;
                    mediaplayer.reset();
                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaplayer.setDataSource(tmp);

                    mediaplayer.prepare();

                    totalTime=mediaplayer.getDuration();
                    sbar.setMax(totalTime);
                    mediaplayer.start();
                }
                catch (Exception e){}
            }
        }
    };
    private ListView.OnItemClickListener lvonclick= new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View v,
                                int position, long id) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=connManager.getActiveNetworkInfo();
            if (info == null || !info.isConnected())
            {
                mytoast("網路未開啟或網路不穩，請到訊號良好的地方!");
            }
            else{
                String str = ((TextView) v.findViewById(R.id.name)).getText().toString();
                play.setImageResource(R.drawable.pause);
                tf=true;
                String tmp=url;
                if(ans.equals("A")){
                    tmp=tmp+child_a[position];
                }
                else if(ans.equals("B")){
                    tmp=tmp+child_b[position];
                }
                else if(ans.equals("C")){
                    tmp=tmp+child_c[position];
                }

                count=position+1;
                try {//+filename[0]

                    totalTime=0;
                    mediaplayer.reset();
                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaplayer.setDataSource(tmp);

                    mediaplayer.prepare();

                    totalTime=mediaplayer.getDuration();
                    sbar.setMax(totalTime);
                    mediaplayer.start();
                }
                catch (Exception e){}
            }


        }
    };
    private ImageView.OnClickListener prebtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {

            String tmp=url;
            count--;
            if(count<0){mytoast("這是第一個");count=0;}
            else{
              //  seraechsql(count);
                if(ans.equals("A")){
                    tmp+=child_a[count];
                }
                else if(ans.equals("B")){
                    tmp+=child_b[count];
                }
                else if(ans.equals("C")){
                    tmp+=child_c[count];
                }

                try {//+filename[0]
                    totalTime=0;
                    mediaplayer.reset();
                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaplayer.setDataSource(tmp);

                    mediaplayer.prepare();

                    totalTime=mediaplayer.getDuration();
                    sbar.setMax(totalTime);
                    mediaplayer.start();
                }
                catch (Exception e){}
            }

        }
    };

    public void seraechsql2(){
        //count



        try{
            if(ans.equals("A")){
                for(int i =0; i < child_a.length; i++) //代理或主管有工號者顯示
                    {
                        Listenlist team = new Listenlist(child_a[i]);
                        listenlists.add(team);
                    }
            }
            else if(ans.equals("B")){
                for(int i =0; i < child_b.length; i++) //代理或主管有工號者顯示
                {
                    Listenlist team = new Listenlist(child_b[i]);
                    listenlists.add(team);
                }
            }
            else if(ans.equals("C")){
                for(int i =0; i < child_c.length; i++) //代理或主管有工號者顯示
                {
                    Listenlist team = new Listenlist(child_c[i]);
                    listenlists.add(team);
                }
            }


        }

        catch(Exception e){}
        final ListenlistAdapter adapter = new ListenlistAdapter(this, R.layout.listenlist, listenlists);
        listview.setAdapter(adapter);
        listview.setTextFilterEnabled(true);
        listview.setSelector(R.drawable.green);

    }
    private  SeekBar.OnSeekBarChangeListener sbarbtn=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                mediaplayer.seekTo(progress);
                sbar.setProgress(progress);
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private ImageView.OnClickListener playbyn = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(tf==true){
                /*  */
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info=connManager.getActiveNetworkInfo();
                if (info == null || !info.isConnected())
                {
                    mytoast("網路未開啟或網路不穩，請到訊號良好的地方!");
                }
                else {
                    tf=false;
                    play.setImageResource(R.drawable.play);
                }



            }

            else{
                tf=true;
                play.setImageResource(R.drawable.pause);
            }
            pauseplay();

        }
    };

    private void playSong(int song) {

        String tmp="";
        if(ans.equals("A")){
            url="https://akkyschool.com/images/listening/child_1/";
              tmp=url;
            tmp+=child_a[count];
        }
        else if(ans.equals("B")){
            url="https://akkyschool.com/images/listening/child_2/";
            tmp=url;
            tmp+=child_b[count];
        }
        else if(ans.equals("C")){
            url="https://akkyschool.com/images/listening/child_3/";
            tmp=url;
            tmp+=child_c[count];
        }


        try {

            mediaplayer.reset();
            mediaplayer.setDataSource(tmp);
            mediaplayer.prepare();
            totalTime=mediaplayer.getDuration();
            sbar.setMax(totalTime);
            mediaplayer.start();
        } catch (IllegalStateException e) { }
        catch (IOException e) { }





    }
    private void startDownload() {
        String tmp=url;

        if(ans.equals("A")){
            tmp+=child_a[0];
        }
        else if(ans.equals("B")){

            tmp+=child_b[0];
        }
        else if(ans.equals("C")){

            tmp+=child_c[0];
        }
        new Listenchild.DownloadFileAsync().execute(tmp);
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        //  ProgressDialog dialog = new ProgressDialog(Listenchild.this);


        @Override
        protected void onPreExecute() {
            dia = new Dialog(Listenchild.this, R.style.edit_AlertDialog_style);
            dia.setContentView(R.layout.imgshow);
            /**/
            GifImageView imageView = (GifImageView) dia.findViewById(R.id.start_img);
            try {

                GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.loading);
                imageView.setImageDrawable(gifDrawable);
                dia.setCanceledOnTouchOutside(false); // Sets whether this dialog is
                Window w = dia.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.x = 0;
                lp.y = 20;
                dia.show();
                dia.onWindowAttributesChanged(lp);

            } catch (Exception e) {}



            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {

            playSong(0);
            //play.setImageResource(R.drawable.pause);
            tf=true;

            new Listenchild.DownloadFileAsync().cancel(true);
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused)
        { seraechsql2();
/*
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
*/
            if(dia  != null && dia.isShowing()){
                dia.dismiss();
            }
        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            sbar.setProgress(currentPosition);
            String remainingTime = createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("" + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void pauseplay(){
        if(mediaplayer.isPlaying()){
            mediaplayer.pause();
        }
        else{
            mediaplayer.start();
        }
    }
    /* */
    @Override
    public void onPause(){
        super.onPause();
        mediaplayer.pause();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Listenchild.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Listenchild.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Listenchild.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Listenchild.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Listenchild.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Listenchild.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Listenchild.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Listenchild.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Listenchild.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Listenchild.this)
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