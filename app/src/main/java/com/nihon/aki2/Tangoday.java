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

public class Tangoday extends AppCompatActivity {
    Toolbar toolbar;
    ImageView toback;
    TextView ch,jp,pinyin;
    WebView myweb;
    String mypinyin;
    String myjp;
    String mych;
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

        }
        jp=(TextView)findViewById(R.id.jp);
        ch=(TextView)findViewById(R.id.ch);
        pinyin=(TextView)findViewById(R.id.pinyin);
      //  myweb = (WebView) findViewById(R.id.myweb);
        ch.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        jp.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));
        pinyin.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/epminbld.ttf"));

        jp.setText(myjp+"");
        ch.setText(mych+"");
        pinyin.setText(mypinyin+"");
        /*
        myweb.getSettings().setBuiltInZoomControls(true);
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.setWebViewClient(new WebViewClient());

        myweb.loadUrl("https://jisho.org/word/"+myjp);

         */

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