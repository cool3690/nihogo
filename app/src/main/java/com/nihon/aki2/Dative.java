package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Dative extends AppCompatActivity {
    String account="",passwd="",names="",ans="";
    private Menu menu;
    TextView Q1,A1,A2;
    ImageView left,right,prep,nextp;
    boolean lock=true;
    int count=0;
    int num=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dative);
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
        Q1=(TextView)findViewById(R.id.Q1);
        A1=(TextView)findViewById(R.id.A1);
        A2=(TextView)findViewById(R.id.A2);
        left=(ImageView)findViewById(R.id.left);
        right=(ImageView)findViewById(R.id.right);
        prep=(ImageView)findViewById(R.id.prepage);
        nextp=(ImageView)findViewById(R.id.nextpage);
        nextp.setOnTouchListener(page);
        prep.setOnTouchListener(pagepre);
        String result = dbdative.executeQuery(num+"");
        String topic="";
        try{

            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                topic=jsonData.getString("Q1");
                Q1.setText(topic);
                //   a1.setTypeface(Typeface.createFromAsset(getAssets(), "nihon.ttf"));
                A1.setText(jsonData.getString("A1"));
                A2.setText(jsonData.getString("A2"));
                ans=jsonData.getString("ans");
            }

           // playSong(songfile[num-1]);
        }

        catch(Exception e){}
        left.setOnClickListener(l1);
        right.setOnClickListener(r1);
    }
    private ImageView.OnClickListener l1=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(ans.equals("1")){mytoast("正解!");}
            else {mytoast("錯誤!");}
        }
    };
    private ImageView.OnClickListener r1=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(ans.equals("2")){mytoast("正解!");}
            else {mytoast("錯誤!");}
        }
    };
    private  ImageView.OnTouchListener page=new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    nextp.setImageResource(R.drawable.aki_nexth);

                    break;
                case MotionEvent.ACTION_UP:
                    nextp.setImageResource(R.drawable.aki_next);
                    next();
                    break;
            }
            return true;
        }
    };
    private  ImageView.OnTouchListener pagepre=new ImageView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    prep.setImageResource(R.drawable.aki_preh);

                    break;
                case MotionEvent.ACTION_UP:
                    prep.setImageResource(R.drawable.aki_pre);
                    pre();
                    break;
            }
            return true;
        }
    };
    public void pre(){
        num--;
        lock=false;

        String result = dbdative.executeQuery(num+"");
        if(result.contains("null")){ num++;
            mytoast("本題為第一題");
        }

        String topic="";
        try{
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                topic=jsonData.getString("Q1");
                Q1.setText(topic);
                A1.setText(jsonData.getString("A1"));
                A2.setText(jsonData.getString("A2"));

                ans=jsonData.getString("ans");
            }
           // playSong(songfile[num-1]);
        }

        catch(Exception e){}
        lock=true;
    }
    public void next(){
        num++;
        lock=false;
        count++;
       // if(count==10){loadInterstitialAd();}


        String result = dbdative.executeQuery(num+"");
        if(result.contains("null")){ num--;
            mytoast("本題為最後一題");
        }

       // txtResult.setText(" ");
        String topic="";
        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                topic=jsonData.getString("Q1");
                Q1.setText(topic);
                A1.setText(jsonData.getString("A1"));
                A2.setText(jsonData.getString("A2"));

                ans=jsonData.getString("ans");
            }
           // playSong(songfile[num-1]);
        }

        catch(Exception e){}
        lock=true;
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(names==null ||names=="" ){
            menu.findItem(R.id.login).setTitle("登入");
        }
        else{
            menu.findItem(R.id.login).setTitle("歡迎"+names);
        }
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Dative.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Dative.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Dative.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Dative.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Dative.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Dative.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Dative.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Dative.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Dative.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Dative.this)
                    .setTitle("版權所有")
                    .setIcon(R.drawable.ic_launcher)
                    .setMessage("新澄管理顧問公司"+"\n台南私立亞紀塾日語短期補習班"+"\nふじやま國際學院")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
