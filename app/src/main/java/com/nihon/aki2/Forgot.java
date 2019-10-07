package com.nihon.aki2;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Forgot extends AppCompatActivity {
    TextView mymail;
    String account="",passwd="";
    ImageView send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
       // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
      //  toolbar.setNavigationIcon(R.drawable.icon);
      //  getSupportActionBar().setDisplayShowTitleEnabled(false);
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
       mymail=(TextView)findViewById(R.id.mymail);
       send=(ImageView)findViewById(R.id.send);
       send.setOnClickListener(sendbtn);
       GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account= Account.getAccount();
        passwd=Account.getPasswd();
    }

    private ImageView.OnClickListener sendbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            String tmp=mymail.getText().toString();

            String result = dbseluser.executeQuery(tmp);
            String account="",passwd="",name="";
            try{
                JSONArray jsonArray = new JSONArray(result);

                int k=0;
                // bt.setText("更多資訊");

                for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
                  {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                    account=jsonData.getString("account");
                    passwd=jsonData.getString("passwd");
                    name=jsonData.getString("name");

                  }
               }

            catch(Exception e){}
  /*
            SmsManager smsManager = SmsManager.getDefault();
            try{
                mytoast(tmp);
                smsManager.sendTextMessage(tmp,
                        null, "hello",
                        PendingIntent.getBroadcast(getApplicationContext(), 0,new Intent(), 0),
                        null);

                mytoast("tmp");
            }
           */
         if(account=="" || account==null){mytoast("無此帳號!");}
         else{
             GMailSender sender = new GMailSender("chansingmis@gmail.com", "chansingmis5781821");
             try
             {
                 sender.sendMail("你的密碼", name+"你好:\n 你的密碼是:"+passwd, "亞紀塾日語", tmp);
                 mytoast("密碼已寄到您信箱");
             }

             catch (Exception e)
             {e.printStackTrace();
             }
         }

        }
    };
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
