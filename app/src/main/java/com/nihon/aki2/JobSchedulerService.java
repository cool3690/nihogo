package com.nihon.aki2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

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
        double money=0;
    private boolean jobcancel=false;
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
      money=Double.parseDouble(g);

        SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
        String h = sharedPreferences.getString("input" , "0");
        money=Double.parseDouble(h);
        handler.postDelayed(showTime,3600000);
        return true;
    }



    private void testNotification(String s) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(false);

        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
       // builder.setLights(Color.RED, 3000, 3000);

        builder.setContentText(s);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

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