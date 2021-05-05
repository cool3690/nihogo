package com.nihon.aki2;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.mydb.dbbasich50;
import com.nihon.aki2.mydb.dbn5;
import com.nihon.aki2.mydb.dbsearchn5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Jlptword extends AppCompatActivity {
    private static final String url ="https://kei-sei.com/cram/n5.json";
    TextView ch,jp,pinyin,meaning;
    EditText input;
    String mypinyin;
    String myjp;
    String mych;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jlptword);
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
        send=(Button) findViewById(R.id.send);
        send.setOnClickListener(sendbtn);
        new Jlptword.DownloadFileAsync().execute();
    }
    private Button.OnClickListener sendbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            String str=input.getText().toString();
            String result = dbsearchn5.executeQuery( str+"");
            if(result.contains("null")){mytoast("N5沒有這單字" );}
            try{
                //建立一個JSONObject並帶入JSON格式文字，getString(String key)取出欄位的數值
                JSONArray array = new JSONArray(result);
              //  if(result.contains("null")){mytoast("N5沒有這單字" );}


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
                    // mytoast("title:" + word + ", tag:" + hiragana + ", info:" + romaji );
                    //}



            }
            catch(JSONException e) {
               // e.printStackTrace();
            }
        }
    };
    public void seraechsql2(){
        String result = dbn5.executeQuery( );
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
          //  mytoast("title:" + word + ", tag:" + hiragana + ", info:" + romaji );
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