package com.nihon.aki2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.StrictMode;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Testweb extends AppCompatActivity {
    WebView myweb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testweb);

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
        myweb = (WebView) findViewById(R.id.myweb);
        myweb.getSettings().setBuiltInZoomControls(true);
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.setWebViewClient(new WebViewClient());
        //myweb.loadUrl("http://kei-sei.com/goods_select.php");
        myweb.loadUrl("https://jisho.org/search/");
    }
    public void onBackPressed() {
        if (myweb.canGoBack()) {
            myweb.goBack();
            return;
        }
        super.onBackPressed();
    }
}