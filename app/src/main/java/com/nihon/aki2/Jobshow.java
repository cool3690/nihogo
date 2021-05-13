package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.nihon.aki2.control.Job;
import com.nihon.aki2.control.Rate;
import com.nihon.aki2.control.RatesAdapter;
import com.nihon.aki2.mydb.dbjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Jobshow extends AppCompatActivity {
    ListView show;
    ArrayList<Job> jobs = new ArrayList<Job>();
    ArrayList <Rate> rates=new ArrayList<Rate>();
    String account="",passwd="",names="";
    private Menu menu;
    String tmp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobshow);
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
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        tmp=bundle.getString("PASS");
        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account= Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();



        show = (ListView) findViewById(R.id.show);
       // listview.setOnItemClickListener(lvonclick);
       // Rate rate=new Rate("工作職稱    地點    價錢","","");
        //rates.add(rate);

/**/


        String result = dbjob.executeQuery(tmp);


        try{
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String name=jsonData.getString("name");
                String place=jsonData.getString("place");
                String price=jsonData.getString("price");
                String type=jsonData.getString("hire_type");
                String require=jsonData.getString("requirement");
                Rate rate=new Rate(name,"條件:"+require+"\n"+"職缺:"+type+"\n"+"地點:"+place,price);
                rates.add(rate);


            }
            final RatesAdapter adapter = new RatesAdapter(this, R.layout.rate, rates);
            show.setAdapter(adapter);
            show.setTextFilterEnabled(true);
            show.setSelector(R.drawable.green);

        }

        catch(Exception e){}

    }
/*
    private ListView.OnItemClickListener lvonclick= new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View v,
                                int position, long id) {
            // 顯示 ListView 的選項內容
            String sel=parent.getItemAtPosition(position).toString();
            String result = dbjob.executeQuery(tmp);

            int k=0;
            try{
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonData = jsonArray.getJSONObject(position);

                String name=jsonData.getString("name");
                String place=jsonData.getString("place");
                String price=jsonData.getString("price");
              //  SpannableString spstr=new SpannableString("課程內容:");
               // spstr.setSpan(new ForegroundColorSpan(Color.BLUE),0, spstr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            catch(Exception e){}


        }
    };
*/

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

    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Jobshow.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Jobshow.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Jobshow.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Jobshow.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Jobshow.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Jobshow.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Jobshow.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Jobshow.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Jobshow.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Jobshow.this)
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
