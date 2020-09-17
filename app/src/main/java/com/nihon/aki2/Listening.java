package com.nihon.aki2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;


import com.nihon.aki2.control.Listenlist;
import com.nihon.aki2.control.ListenlistAdapter;
import com.nihon.aki2.control.Team;
import com.nihon.aki2.control.TeamsAdapter;
import com.nihon.aki2.mydb.dblisten;

import androidx.annotation.RequiresApi;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class Listening extends AppCompatActivity  {
    ArrayList<Listenlist> listenlists = new ArrayList<Listenlist>();
    ListView listview;
    Dialog dia;
    ImageView play,btnpre,btnnext,pic;
    private Dialog dialog;
  //  TransparentProgressDialog mProgressDialog;
  //  private  AlertDialog dia ;
    private Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11,bt12,bt13;
    // ImageView []btarr=new ImageView[]{bt0,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9};
    Button[] btarr=new Button[]{bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10,bt11,bt12} ;
    SeekBar sbar;
    int count=0;
    boolean tf=true;
    int totalTime=0;
    int L=0,T=0,length=0;
    String les="",account="";
    String track="";
    TextView remainingTimeLabel;
    public MediaPlayer mediaplayer;
    String []filename=new String[45];
    String []lessons=new String[13];
    int []p1=new int[]{3,4,3,5,4,3,4,4,3,4,4,3};

    String url="https://akkyschool.com/images/listening/",ans="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listening);
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
        bt9=(Button)findViewById(R.id.bt9);
        bt10=(Button)findViewById(R.id.bt10);
        bt11=(Button)findViewById(R.id.bt11);
        bt12=(Button)findViewById(R.id.bt12);
        bt13=(Button)findViewById(R.id.bt13);
        bt1.setOnClickListener(bt1btn);
        bt2.setOnClickListener(bt1btn);
        bt3.setOnClickListener(bt1btn);
        bt4.setOnClickListener(bt1btn);
        bt5.setOnClickListener(bt1btn);
        bt6.setOnClickListener(bt1btn);
        bt7.setOnClickListener(bt1btn);
        bt8.setOnClickListener(bt1btn);
        bt9.setOnClickListener(bt1btn);
        bt10.setOnClickListener(bt1btn);
        bt11.setOnClickListener(bt1btn);
        bt12.setOnClickListener(bt1btn);
        bt13.setOnClickListener(bt1btn);
          /* */

        listview = (ListView) findViewById(R.id.list);
        listview.setOnItemClickListener(lvonclick);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        ans= bundle.getString("ANS");
        if(ans.equals("A")){
            int j=0;
            for(int i=1;i<=12;i++){

                String t="L"+i;
                lessons[j]=t;
                j++;


            } length=45;
            bt13.setVisibility(View.GONE);
            pic.setImageResource(R.drawable.aki_p1);
            url="https://akkyschool.com/images/listening/primary1/";
        }
        else if(ans.equals("B")) {
            int j = 0;
            for (int i = 13; i <= 25; i++) {
              //  btarr[j].setText("ook");
                lessons[j] = "L" + i;
                j++;

            } length=42;
            bt1.setText("L13");
            bt2.setText("L14");
            bt3.setText("L15");
            bt4.setText("L16");
            bt5.setText("L17");
            bt6.setText("L18");
            bt7.setText("L19");
            bt8.setText("L20");
            bt9.setText("L21");
            bt10.setText("L22");
            bt11.setText("L23");
            bt12.setText("L24");
            bt13.setText("L25");
            url="https://akkyschool.com/images/listening/primary2/";
            p1=new int[]{2,4,3,4,3,3,3,3,3,4,3,3};
            pic.setImageResource(R.drawable.aki_p2);
        }
        else if(ans.equals("C")) {
            int j = 0;
            for (int i = 26; i <= 38; i++) {
               // btarr[j].setText(i+"");
                lessons[j] = "L" + i;
                j++;

            } length=39;
            bt1.setText("L26");
            bt2.setText("L27");
            bt3.setText("L28");
            bt4.setText("L29");
            bt5.setText("L30");
            bt6.setText("L31");
            bt7.setText("L32");
            bt8.setText("L33");
            bt9.setText("L34");
            bt10.setText("L35");
            bt11.setText("L36");
            bt12.setText("L37");
            bt13.setText("L38");
            p1=new int[]{2,3,3,3,3,3,3,3,3,3,3,3};
            url="https://akkyschool.com/images/listening/advance1/";
            pic.setImageResource(R.drawable.aki_a1);
        }
        else if(ans.equals("D")){
            int j=0;
            for(int i=39;i<=50;i++){
               // btarr[j].setText(i+"");
                lessons[j]="L"+i;
                j++;
            }
            length=36;
            bt1.setText("L39");
            bt2.setText("L40");
            bt3.setText("L41");
            bt4.setText("L42");
            bt5.setText("L43");
            bt6.setText("L44");
            bt7.setText("L45");
            bt8.setText("L46");
            bt9.setText("L47");
            bt10.setText("L48");
            bt11.setText("L49");
            bt12.setText("L50");
            bt13.setVisibility(View.GONE);
            p1=new int[]{2,3,3,3,3,3,3,3,3,3,3,3};
            url="https://akkyschool.com/images/listening/advance2/";
            pic.setImageResource(R.drawable.aki_a2);
        }
        play.setOnClickListener(playbyn);
        sbar.setOnSeekBarChangeListener(sbarbtn);
        for(int i=0;i<45;i++){
            int j=i+1;
            String tmp="T"+j+".mp3";
            filename[i]=tmp;
        }
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
        /*
        for(int i=1;i<=p1[0];i++){
            int j=i+1;
            Listenlist team = new Listenlist("T"+i+".mp3");
            listenlists.add(team);
        }

//seraechsql2();

        Listenlist team = new Listenlist("s");
        listenlists.add(team);
        final ListenlistAdapter adapter = new ListenlistAdapter(this, R.layout.listenlist, listenlists);
        listview.setAdapter(adapter);
        listview.setTextFilterEnabled(true);
        listview.setSelector(R.drawable.green);

*/

    }
    private Button.OnClickListener bt1btn=new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt1:
                    listview.smoothScrollToPositionFromTop(0,0);
                    break;
                case  R.id.bt2:
                    listview.smoothScrollToPositionFromTop(p1[0]+1,0);
                    break;
                case  R.id.bt3:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+1,0);
                    break;
                case  R.id.bt4:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+1,0);
                    break;
                case  R.id.bt5:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+1,0);
                    break;
                case  R.id.bt6:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+p1[4]+1,0);
                    break;
                case  R.id.bt7:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+p1[4]+p1[5]+1,0);
                    break;
                case  R.id.bt8:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+p1[4]+p1[5]+p1[6]+1,0);
                    break;
                case  R.id.bt9:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+p1[4]+p1[5]+p1[6]+p1[7]+1,0);
                    break;
                case  R.id.bt10:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+p1[4]+p1[5]+p1[6]+p1[7]+p1[8]+1,0);
                    break;
                case  R.id.bt11:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+p1[4]+p1[5]+p1[6]+p1[7]+p1[8]+p1[9]+1,0);
                    break;
                case  R.id.bt12:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+p1[4]+p1[5]+p1[6]+p1[7]+p1[8]+p1[9]+p1[10]+1,0);
                    break;
                case  R.id.bt13:
                    listview.smoothScrollToPositionFromTop(p1[0]+p1[1]+p1[2]+p1[3]+p1[4]+p1[5]+p1[6]+p1[7]+p1[8]+p1[9]+p1[10]+1,0);
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
                seraechsql(count);
                tmp+="L"+les+"/T"+track+".mp3";



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
                seraechsql(count);
                tmp+="L"+les+"/T"+track+".mp3";



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

        try{

            JSONArray jsonArray = new JSONArray(result);
            length=jsonArray.length();
            for(int i =0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                les=jsonData.getString("lesson");
                track=jsonData.getString("track");
                Listenlist team = new Listenlist("L"+les+"/T"+track+".mp3");

                listenlists.add(team);
            }

        }

        catch(Exception e){}
        final ListenlistAdapter adapter = new ListenlistAdapter(this, R.layout.listenlist, listenlists);
        listview.setAdapter(adapter);
        listview.setTextFilterEnabled(true);
        listview.setSelector(R.drawable.green);

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
        new DownloadFileAsync().execute(tmp);
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
      //  ProgressDialog dialog = new ProgressDialog(Listening.this);


        @Override
        protected void onPreExecute() {
            dia = new Dialog(Listening.this, R.style.edit_AlertDialog_style);
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

            new Listening.DownloadFileAsync().cancel(true);
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
            intent.setClass(Listening.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Listening.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Listening.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Listening.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Listening.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Listening.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Listening.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Listening.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Listening.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Listening.this)
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