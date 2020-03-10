package com.nihon.aki2;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Globalfunc {
    Context mContext;
    private AdView mAdView;
    // constructor
    public Globalfunc(Context context){
        this.mContext = context;
    }

    public void mytoast(String str) {
        Toast toast;
        toast = Toast.makeText(mContext, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public void myad(){
        String myid=mContext.getString(R.string.appid);
        MobileAds.initialize(mContext, myid);
       // mAdView = findViewById(R.id.adView);
       // mAdView =  layout.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
    }
}
