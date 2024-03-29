package com.nihon.aki2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tangoday extends AppCompatActivity {
    Toolbar toolbar;
    ImageView toback;
    TextView ch,jp,pinyin,hira,level;
    WebView myweb;
    String mypinyin;
    String myjp;
    String mych,myhiragana,mylevel;
    private  String url ="https://jisho.org/search/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tangoday);
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
         toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
        toback=(ImageView)findViewById(R.id.toback);
        toback.setOnClickListener(backbtn);
        Intent intent=this.getIntent();

        if(intent != null && intent.getExtras() != null ){
            Bundle bundle=intent.getExtras();
            myjp=bundle.getString("JP");
            mych=bundle.getString("CH");
            mypinyin=bundle.getString("PINYIN");
            myhiragana=bundle.getString("HIRA");
            mylevel=bundle.getString("LEVEL");
        }
        jp=(TextView)findViewById(R.id.jp);
        ch=(TextView)findViewById(R.id.ch);
        pinyin=(TextView)findViewById(R.id.pinyin);
        hira=(TextView)findViewById(R.id.hira);
        level=(TextView)findViewById(R.id.level);
        url+=jp;
      //  myweb = (WebView) findViewById(R.id.myweb);
        ch.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        jp.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        pinyin.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));

        jp.setText(myjp+"");
        ch.setText(mych+"");
        pinyin.setText(mypinyin+"");
        hira.setText(myhiragana+"");
        level.setText(mylevel+"");
        /*
        myweb.getSettings().setBuiltInZoomControls(true);
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.setWebViewClient(new WebViewClient());

        myweb.loadUrl("https://jisho.org/word/"+myjp);

         */
       // jprate();
    }
    public void jprate(){
     String body="";
        try{
            Connection.Response response = Jsoup.connect(url).execute();
          body = response.body();

            Document doc = Jsoup.connect(url).get();
            /*
            Element link = doc.select("source").first();
            String relHref = link.attr("src"); // == "/"
            String absHref = link.attr("abs:href"); // "http://jsoup.org/"


             */
            Document data =  Jsoup.parse(body);//visible-phone print_hide

          //
            Elements country=data.select("audio");

           //ch.setText(country+"");
             mytoast(country+"");


            for(Element e1: country)
            {
             //   mytoast(e1.text());
            }

  /*
            //  Document data =  Jsoup.parse(body);//visible-phone print_hide
            // mytoast(body);

            Elements country=data.select("div[class=visible-phone print_hide]");
            Elements tds = data.select("td[class=rate-content-cash text-right print_hide]");
            int i=0;

            for(Element e2: tds)
            {
                if(i%2==0 )
                {buy.add(e2.text());
                }
                i++;
            }

            for(Element e1: country)
            {
                coin.add(e1.text());
            }
            */
        }
        catch(Exception ex){}
    }
    private ImageView.OnClickListener backbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
           /*
            Intent intent = new Intent(Tangoday.this, Menushow.class);
            startActivity(intent);

            */
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);

        }
    };

    public void onBackPressed() {


        super.onBackPressed();
    }

    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}