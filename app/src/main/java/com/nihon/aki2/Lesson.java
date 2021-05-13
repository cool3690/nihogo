package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nihon.aki2.control.Jnew;
import com.nihon.aki2.control.JnewAdapter;
import com.nihon.aki2.mydb.dblesson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lesson extends AppCompatActivity {
    ListView learn;
    ImageView next,pre;
    String account="",passwd="",names="";  private Menu menu;
    TextView t;
    int num=1;
    ArrayList<Jnew> jpnews=new ArrayList<Jnew>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson);
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
        t=(TextView) findViewById(R.id.textView5);
       learn=(ListView)findViewById(R.id.learn);
       next=(ImageView)findViewById(R.id.nextpage);
       pre=(ImageView)findViewById(R.id.prepage);
       t.setText("中級電子報-第二回");
        String result = dblesson.executeQuery("A1");
      
        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String texts=jsonData.getString("texts");

                Jnew team = new Jnew(texts);

                jpnews.add(team);

            }

           
        }

        catch(Exception e){}
        final JnewAdapter adapter = new JnewAdapter(this,  R.layout.mynew, jpnews);
        learn.setAdapter(adapter);
        learn.setTextFilterEnabled(true);
        next.setOnTouchListener(nextbtn);
        pre.setOnTouchListener(prebtn);
    }

    private ImageView.OnTouchListener nextbtn=new ImageView.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    next.setImageResource(R.drawable.aki_nexth);

                    break;
                case MotionEvent.ACTION_UP:
                    next.setImageResource(R.drawable.aki_next);
                    testadd();
                    break;
            }
            return true;
        }
    };
    public void testadd(){
        jpnews.clear();
        num++;
        String result = dblesson.executeQuery("A"+num);
        if(result.contains("null")){ num--;}

        String texts="";
        try{
            if(num==2){   Jnew  team = new Jnew("句型練習");

            jpnews.add(team);}
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                texts=jsonData.getString("texts");
                Jnew  team = new Jnew(texts);
                jpnews.add(team);
            }
            final JnewAdapter adapter = new JnewAdapter(this,  R.layout.mynew, jpnews);
            learn.setAdapter(adapter);

        }

        catch(Exception e){}
    }
    private ImageView.OnTouchListener prebtn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    pre.setImageResource(R.drawable.aki_preh);

                    break;
                case MotionEvent.ACTION_UP:
                    pre.setImageResource(R.drawable.aki_pre);
                    testminus();
                    break;
            }
            return true;
        }
    };
    public void testminus(){
        jpnews.clear();
        num--;
        String result = dblesson.executeQuery("A"+num);
        if(result.contains("null")){ num++;}

        String texts="";
        try{
            if(num==2){   Jnew  team = new Jnew("句型練習");
                jpnews.add(team);}
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                texts=jsonData.getString("texts");
                Jnew  team = new Jnew(texts);
                jpnews.add(team);
            }
            final JnewAdapter adapter = new JnewAdapter(this,  R.layout.mynew, jpnews);
            learn.setAdapter(adapter);

        }

        catch(Exception e){}
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
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
            intent.setClass(Lesson.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Lesson.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Lesson.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Lesson.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Lesson.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Lesson.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Lesson.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Lesson.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Lesson.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Lesson.this)
                    .setTitle("版權所有")
                    .setIcon(R.drawable.righticon)
                    .setMessage("新澄管理顧問公司"+"\n臺南市私立慶誠文理短期補習班"+"\n連絡：sonyzone2004@gmail.com")
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
