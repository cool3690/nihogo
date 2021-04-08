package com.nihon.aki2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import androidx.annotation.RequiresApi;

import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final String TAG="JobSchedulerService";
    private Handler handler = new Handler();
    List<String> buy = new ArrayList<String>();
    List<String>coin=new ArrayList<String>();
    private static final String url ="https://rate.bot.com.tw/xrt?Lang=zh-TW";
        double money=0,sum=0;

    private boolean jobcancel=false;
int i=0;
    private static final String TEST_NOTIFY_ID = "test_notyfy_id";
    private static final int NOTYFI_REQUEST_ID = 300;
    private int testNotifyId = 11;


    @Override
    public boolean onStartJob(JobParameters jobParameters ) {
     //   Log.d(TAG, "JobSchedulerService onStartJob");
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

       String g= jobParameters.getExtras().getString("INPUT");
       String tmp=jobParameters.getExtras().getString("SUM");
      money=Double.parseDouble(g);
      sum=Double.parseDouble(tmp);
        if(money<=0){
            SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
            String h = sharedPreferences.getString("input" , "0");
            money=Double.parseDouble(h);
        }

        handler.postDelayed(showTime2,5000);
        return true;
    }


    public void showNotification(String s) {

        Intent intent =new Intent(getApplicationContext(),Change.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                NOTYFI_REQUEST_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("匯率通知")
                .setContentText("目前匯率為: "+s)
                .setSmallIcon(R.drawable.ic_launcher)

                .setContentIntent(pendingIntent);

        NotificationChannel channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(TEST_NOTIFY_ID
                    , "katsu日本語"
                    , NotificationManager.IMPORTANCE_HIGH);
            builder.setChannelId(TEST_NOTIFY_ID);

            manager.createNotificationChannel(channel);
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        manager.notify(testNotifyId,
                builder.build());

    }

    private void testNotification(String s) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(false);

        builder.setVibrate(new long[] { 1000, 1000});

        //LED
       // builder.setLights(Color.RED, 3000, 3000);

        builder.setContentText(s);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
private Runnable showTime2=new Runnable() {
    @Override
    public void run() {
        if(money>=sum){
            testNotification(String.format("%.3f",sum)+"");
            showNotification(String.format("%.3f",sum)+"");
            i++;
            if(i==2)jobcancel=true;
            if(jobcancel){return;}
            handler.postDelayed(this, 1000);
            handler.removeCallbacks(showTime2);
        }

    }
};
    private Runnable showTime = new Runnable() {
        public void run() {

            try{
                Connection.Response response = Jsoup.connect(url).execute();
                String body = response.body();
                Document data =  Jsoup.parse(body);//visible-phone print_hide
                Elements country=data.select("div[class=visible-phone print_hide]");
                Elements tds = data.select("td[class=rate-content-cash text-right print_hide]");
                int i=0;
                buy.clear();
                coin.clear();
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
            }
            catch(Exception ex){} //  Log.d(TAG,""+tmp);
            double tmp=Double.parseDouble(buy.get(7));
           String str=buy.get(7);
           if(jobcancel){return;}
             if(money>=tmp) {
             testNotification(str);
             }

                handler.postDelayed(this,3600000);

        }
    };
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
jobcancel=true;
       return true;
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}