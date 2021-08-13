package com.nihon.aki2;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.Listenlist;
import com.nihon.aki2.control.ListenlistAdapter;
import com.nihon.aki2.mydb.dblisten;
import com.nihon.aki2.mydb.dbsantance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Listen_k extends AppCompatActivity {
    public MediaPlayer mediaplayer;
    Spinner spnlev,spntype;
    TextView pretxt,nexttxt;
    View btnpre,btnnext;
    ImageView play;
    String course[]={"初級一","初級二","進階一","進階二"};
    ArrayList<String>typelist=new ArrayList();
    /////
    String level="";
    SeekBar sbar,volbar;
    int count=0;
    boolean tf=true,fin=false;
    int totalTime=0;
    int L=0,T=0,length=0;
    String les="",account="";
    String track="";
    TextView remainingTimeLabel,totaltime,show;
    Context context;
    Dialog dia;
    AudioManager audioManager;
    String url="https://kei-sei.com/images/listening/primary1/L1/T1.mp3",ans="";
    String []filename=new String[45];
    String []lessons=new String[13];

    String[] list2= {"關於" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listen_k);
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
        mediaplayer=new MediaPlayer();
        play = (ImageView) findViewById(R.id.play);
       remainingTimeLabel= (TextView) findViewById(R.id.remainingTimeLabel);
        totaltime= (TextView) findViewById(R.id.totaltime);
        show= (TextView) findViewById(R.id.show);
        sbar = (SeekBar) findViewById(R.id.sbar);
        volbar = (SeekBar) findViewById(R.id.volbar);
         audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volbar.setMax(maxVolume);
        volbar.setProgress(curVolume);
        volbar.setOnSeekBarChangeListener(volbarbtn);
        ///////////
        spnlev=(Spinner)findViewById(R.id.spnlev);
        spntype=(Spinner)findViewById(R.id.spntype);
        pretxt=(TextView)findViewById(R.id.pretxt);
        nexttxt=(TextView)findViewById(R.id.nexttxt);
        btnpre=(View)findViewById(R.id.btnpre);
        btnnext=(View)findViewById(R.id.btnnext);
        pretxt.setOnClickListener(pretxtbtn);
        btnpre.setOnClickListener(pretxtbtn);
        nexttxt.setOnClickListener(pretxtbtn);
        btnnext.setOnClickListener(pretxtbtn);
        ArrayAdapter<String> spnlevpn=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,course);
        spnlev.setAdapter(spnlevpn);
        spnlev.setOnItemSelectedListener(levbtn);
        play.setOnClickListener(playbyn);
        ArrayAdapter<String> spntypespn=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,typelist);
        spntype.setAdapter(spntypespn);
        spntype.setOnItemSelectedListener(typebtn);
        ans="A";
        if(ans.equals("A")){
            int j=0;
            for(int i=1;i<=12;i++){

                String t="L"+i;
                lessons[j]=t;
                j++;


            } length=45;


            url="https://kei-sei.com/images/listening/primary1/";
        }
        for(int i=0;i<45;i++){
            int j=i+1;
            String tmp="T"+j+".mp3";
            filename[i]=tmp;
        }

       // seraechsql2();
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

///////////////////////////////
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
       /*  */
    }
    private ImageView.OnClickListener menulistbtn=new ImageView.OnClickListener(){

        @Override
        public void onClick(View view) {
            AlertDialog.Builder dialog_list = new AlertDialog.Builder(Listen_k.this);
            // dialog_list.setTitle(" ");
            dialog_list.setItems(list2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                        context = Listen_k.this;
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
    private SeekBar.OnSeekBarChangeListener volbarbtn=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private Spinner.OnItemSelectedListener levbtn= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {

                if(position==0){
                    ans="A";
                    int j=0;
                    for(int i=1;i<=12;i++){

                        String t="L"+i;
                        lessons[j]=t;
                        j++;


                    } length=45;
                    url="https://kei-sei.com/images/listening/primary1/";
                    if(fin){

                        seraechsql2();

                    }
                    level="初級一";
                }
                else if(position==1){
                    ans="B";
                    int j = 0;
                    for (int i = 13; i <= 25; i++) {
                        //  btarr[j].setText("ook");
                        lessons[j] = "L" + i;
                        j++;

                    } length=42;
                    url="https://kei-sei.com/images/listening/primary2/";
                    seraechsql2();
                    level="初級二";
                }
                else if(position==2){
                    ans="C";
                    int j = 0;
                    for (int i = 26; i <= 38; i++) {
                        //  btarr[j].setText("ook");
                        lessons[j] = "L" + i;
                        j++;

                    }  length=39;
                    url="https://kei-sei.com/images/listening/advance1/";
                    seraechsql2();
                    level="進階一";
                }
                else if(position==3){
                    ans="D";
                    int j = 0;
                    for(int i=39;i<=50;i++){
                        // btarr[j].setText(i+"");
                        lessons[j]="L"+i;
                        j++;
                    }
                    length=36;
                    url="https://kei-sei.com/images/listening/advance2/";
                    seraechsql2();
                    level="進階二";
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

        spntypespn.notifyDataSetChanged();
        spntype.setAdapter(spntypespn);
    }
    private Spinner.OnItemSelectedListener typebtn= new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v,
                                   int position, long id) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=connManager.getActiveNetworkInfo();
            if (info == null || !info.isConnected())
            {
                mytoast("網路未開啟或網路不穩，請到訊號良好的地方!");
            }
            else{
                String str = typelist.get(position);
                play.setImageResource(R.drawable.pause);
                tf=true;
                String tmp=url;
                tmp=tmp+str;
                count=position+1;
                try {//+filename[0]

                    totalTime=0;
                    mediaplayer.reset();
                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaplayer.setDataSource(tmp);

                    mediaplayer.prepare();

                    totalTime=mediaplayer.getDuration();
                    sbar.setMax(totalTime);
                    String total = createTimeLabel(totalTime);
                    totaltime.setText(total);
                    mediaplayer.start();


                    show.setText(level+"/"+parent.getItemAtPosition(position).toString());

                }
                catch (Exception e){}
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };
    private TextView.OnClickListener pretxtbtn=new TextView.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.pretxt:
                case R.id.btnpre:
                    String tmp=url;
                    count--;
                    if(count<0){mytoast("這是第一個");count=0;}
                    else{
                        seraechsql(count);
                        tmp+="L"+les+"/T"+track+".mp3";

                        show.setText(level+"/L" + les + "/T" + track+ ".mp3" );

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
                    break;
                case R.id.nexttxt:
                case R.id.btnnext:
                    tmp=url;
                    count++;
                    if(count>=length){mytoast("最後一個了");count--;}
                    else {
                        seraechsql(count);
                        tmp += "L" + les + "/T" + track + ".mp3";
                        show.setText(level+"/L" + les + "/T" + track+ ".mp3" );

                        try {//+filename[0]
                            totalTime = 0;
                            mediaplayer.reset();
                            mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaplayer.setDataSource(tmp);

                            mediaplayer.prepare();

                            totalTime = mediaplayer.getDuration();
                            sbar.setMax(totalTime);
                            mediaplayer.start();
                        } catch (Exception e) {
                        }
                    }
                    break;
                default:
                    break;
            }
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
    public void seraechsql(int count){

        String result = dblisten.executeQuery(ans+"");

        try{
            JSONArray jsonArray = new JSONArray(result);
            length=jsonArray.length();
            for(int i =count; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                les=jsonData.getString("lesson");
                track=jsonData.getString("track");
                break;

            }

        }

        catch(Exception e){}


    }

    public void seraechsql2(){
        //count
        String result = dblisten.executeQuery(ans+"");

        try{typelist.clear();

            JSONArray jsonArray = new JSONArray(result);
            length=jsonArray.length();
            for(int i =0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                les=jsonData.getString("lesson");
                track=jsonData.getString("track");

                typelist.add("L"+les+"/T"+track+".mp3");

            }

        }

        catch(Exception e){}


    }


    private void playSong(int song) {


        String tmp=url;

        tmp+=lessons[L]+"/"+filename[T];

        try {//+filename[0]

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
        tmp+=lessons[L]+"/"+filename[T];
        show.setText(lessons[L]+"/"+filename[T]);
        new Listen_k.DownloadFileAsync().execute(tmp);
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        //  ProgressDialog dialog = new ProgressDialog(Listening.this);


        @Override
        protected void onPreExecute() {
            /* */
            dia = new Dialog(Listen_k.this, R.style.edit_AlertDialog_style);
            dia.setContentView(R.layout.imgshow);

            GifImageView imageView = (GifImageView) dia.findViewById(R.id.start_img);
            try {

                 GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.loading);
               imageView.setImageDrawable(gifDrawable);
             //   dia.setCanceledOnTouchOutside(false); // Sets whether this dialog is
                Window w = dia.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.x = 0;
                lp.y = 20;
                dia.show();
                dia.onWindowAttributesChanged(lp);

            } catch (Exception e) {}


 /*
         */

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {

            playSong(0);
            //play.setImageResource(R.drawable.pause);

            tf=true;


            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused)
        { seraechsql2();
            new Listen_k.DownloadFileAsync().cancel(true);

            if(dia  != null && dia.isShowing()){
                dia.dismiss();
                fin=true;
                change();
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
}