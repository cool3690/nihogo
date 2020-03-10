package com.nihon.aki2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Comic extends AppCompatActivity {
    WebView myweb;
    String account="",passwd="",names="",course_num="";
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comic);
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
        names=Account.getNames();
        myweb = (WebView) findViewById(R.id.webview);

        myweb.getSettings().setBuiltInZoomControls(true);
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.setWebViewClient(new WebViewClient());
       // https://www.ganganonline.com/
        myweb.loadUrl("https://tonarinoyj.jp/episode/13932016480028985383");



    }
    public void onBackPressed() {
        if (myweb.canGoBack()) {
            myweb.goBack();
            return;
        }

        super.onBackPressed();
    }
}
