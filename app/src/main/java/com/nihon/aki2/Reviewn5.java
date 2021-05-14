package com.nihon.aki2;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.mydb.dbn5;
import com.nihon.aki2.mydb.dbsearchn5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
      String url ="https://kei-sei.com/cram/n5.json";
    TextView ch,jp,pinyin,meaning,level;
    EditText input;
    String mypinyin;
    String myjp;
    String mych;
    boolean tf=false;
    Button auto,hand,N3btn,N2btn,N1btn,send;
    Spinner sellevel,sec;
    String[] mylev= new String []{"N5","N4","N3","N2","N1" };
    String[] mysec= new String []{"5秒","6秒","7秒","8秒","9秒","10秒"};
    int LEV=0,SEC=0,Ltmp=0,stmp=5;
    Timer timer = new Timer();
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
        jp=(TextView)findViewById(R.id.jp);
        ch=(TextView)findViewById(R.id.ch);
        pinyin=(TextView)findViewById(R.id.pinyin);
        meaning=(TextView)findViewById(R.id.meaning);
        input=(EditText) findViewById(R.id.input);
        auto=(Button) findViewById(R.id.auto);
        hand=(Button) findViewById(R.id.hand);
        send=(Button) findViewById(R.id.send);
        level=(TextView)findViewById(R.id.level);
        sellevel=(Spinner) findViewById(R.id.sellevel);
        input=(EditText) findViewById(R.id.input);
        sec=(Spinner) findViewById(R.id.sec);
        auto.setOnClickListener(autobtn);
        hand.setOnClickListener(handbtn);
        send.setOnClickListener(sendbtn);
        ArrayAdapter<String> adapterPage=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,mylev);

        // 設定Spinner顯示的格式
        adapterPage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 設定Spinner的資料來源
        sellevel.setAdapter(adapterPage);

        // 設定 spnPrefer 元件 ItemSelected 事件的 listener 為  spnPreferListener
        sellevel.setOnItemSelectedListener(spnlev);
        ///////////////////
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
    private Button.OnClickListener sendbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            String str=input.getText().toString();
            String tmp="";
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

                //  for (int i = 0; i < array.length(); i++) {

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
            if(tf){
                //timer.cancel();
                //timer = null;
                tf=false;
            }
            if(timer != null) {
                timer.cancel();
              //  timer.purge();
              //  timer = null;
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
               // level.setText("N5");
                //}
            }
            catch(JSONException e) {
                // e.printStackTrace();
            }
        }
    };
    public void begin() {
        if(Ltmp!=LEV || stmp!=SEC || tf){
            Ltmp=LEV;
            stmp=SEC;
            timer.cancel();
            //timer=null;
           // timer.schedule(task, SEC*1000, SEC*1000) ;
              timer = new Timer();
        }

            timer.schedule(new MyTimerTask(), SEC*1000, SEC*1000) ;
    //  else{  }

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
                //    level.setText("N5");
                //}
            }
            catch(JSONException e) {
                // e.printStackTrace();
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
                    //    level.setText("N5");
                        //}
                    }
                    catch(JSONException e) {
                        // e.printStackTrace();
                    }

                }
            });
        }
    };
    private Button.OnClickListener autobtn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            /**/
            if(!tf){


                if(Ltmp!=LEV || stmp!=SEC  ){
                    Ltmp=LEV;
                    stmp=SEC;
                    timer.cancel();
                    // timer=null;
                    // timer.schedule(task, SEC*1000, SEC*1000) ;
                }
                timer = new Timer();
                timer.schedule(new MyTimerTask(), SEC*1000, SEC*1000) ;
                //auto.setText("暫停");
                tf=true;
            }
            else{
                //auto.setText("自動");
            }


            //timer.schedule(task, SEC*1000, SEC*1000) ;


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
            //}
        }
        catch(JSONException e) {
            // e.printStackTrace();
        }
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
        protected void onPostExecute(String unused)
        {seraechsql2();

        }
    }


    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}