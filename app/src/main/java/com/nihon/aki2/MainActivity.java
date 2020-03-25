package com.nihon.aki2;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nihon.aki2.mydb.dbcjlpt;
import com.nihon.aki2.mydb.dbdelcart;
import com.nihon.aki2.mydb.dbincart;
import com.nihon.aki2.mydb.dbselcart;


public class MainActivity extends AppCompatActivity {

    ImageView classinfo;
    TextView testinfo,jointime,testtime,countday,more;
    int cc=0;
    String account="",passwd="",names="",course_num="";
    private Menu menu;

    String[] Balls= new String[1] ;

    int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Balls[0]="請選擇";
        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();
        testinfo=(TextView)findViewById(R.id.testinfo);
        jointime=(TextView)findViewById(R.id.jointime);
        testtime=(TextView)findViewById(R.id.testtime);
        countday=(TextView)findViewById(R.id.countday);
        more=(TextView)findViewById(R.id.more);
        classinfo=(ImageView)findViewById(R.id.classinfo);
        classinfo.setOnClickListener(classinfobtn);
        more.setOnClickListener(morebtn);
        String result = dbcjlpt.executeQuery();

        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String ssign=jsonData.getString("sign");
                String sdate=jsonData.getString("date");
                jointime.setText("網路報名時間:\n"+ssign);

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
                Date dt=new Date();
                // String dts=sdf.format(dt);
                java.util.Date endDate= sdf.parse(sdate);
                long day=(endDate.getTime()-dt.getTime())/(24*60*60*1000);
                if(day<0){
                    countday.setText("2020年日檢第一回考試等候公告");
                }
                else{
                    countday.setText("   "+day);
                }

            }


        }

        catch(Exception e){}


    }
    private ImageView.OnClickListener classinfobtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,Classinfo.class);
            startActivity(intent);
        }
    };
    private TextView.OnClickListener morebtn=new TextView.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.jlpt.tw/"));
            startActivity(intent);
        }
    };

    private void mydialog(String str1,String str2,String str3){
        SpannableString spstr=new SpannableString(str1);
        spstr.setSpan(new ForegroundColorSpan(Color.argb(128,128,42,42)),0, str1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString spstr1=new SpannableString("課程內容");
        spstr1.setSpan(new ForegroundColorSpan(Color.argb(128,128,42,42)),0, spstr1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spstr1.setSpan(new RelativeSizeSpan(1.2f),0, spstr1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(spstr)
                .setIcon(R.drawable.ic_launcher)
                .setMessage(TextUtils.concat(str2,  spstr1, "\n", str3))
                .setPositiveButton("選課", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        if(account=="" ||account==null){
                            Intent intent2=new Intent();
                            intent2.setClass(MainActivity.this,Login.class);
                            startActivity(intent2);
                        }
                        else{//加選
                            int tmp=0;
                            String	result= dbselcart.executeQuery(account,course_num);

                            try{
                                JSONArray jsonArray = new JSONArray(result);

                                tmp=jsonArray.length();
                            }
                            catch(Exception e){}
                            if(tmp>0){mytoast("已選過此課程");}
                            else{
                                dbincart.executeQuery(account,course_num);
                                mytoast("選課成功!");
                            }
                        }
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                    }
                })
                .setNeutralButton("退選", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {//退選
                        if(account==""){
                            Intent intent2=new Intent();
                            intent2.setClass(MainActivity.this,Login.class);
                            startActivity(intent2);
                        }
                        else{
                            //退選
                            dbdelcart.executeQuery(account,course_num);
                            mytoast("退選成功!");
                        }

                    }
                })
                .show();
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
            intent.setClass(MainActivity.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(MainActivity.this)
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
