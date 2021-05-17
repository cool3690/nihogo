package com.nihon.aki2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.nihon.aki2.control.GlobalVariable;
import com.nihon.aki2.control.Team;
import com.nihon.aki2.control.TeamsAdapter;
import com.nihon.aki2.mydb.dbcourse;
import com.nihon.aki2.mydb.dbdelcart;
import com.nihon.aki2.mydb.dbincart;
import com.nihon.aki2.mydb.dbselcart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Classinfo extends AppCompatActivity {
    ArrayList<Team> teams = new ArrayList<Team>();
    ListView listview;
    String account="",passwd="",names="",course_num="";
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classinfo);
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
        listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(lvonclick);
        String result = dbcourse.executeQuery();
        // Team team;
        // Team team =new Team("課程","","","時間","價錢");
        // teams.add(team);
        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
            // bt.setText("更多資訊");
            //Balls= new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String name=jsonData.getString("name");

                String week=jsonData.getString("week");
                String time=jsonData.getString("stime");
                String price=jsonData.getString("price");
                String date=jsonData.getString("sdate");
                String tdate[]=date.split("-");
              //  Balls[i]=name+", 星期"+week+"  "+time+"  ,$"+price;
                Team  team = new Team(name,"星期"+ week, time,tdate[0],"$"+price);
               teams.add(team);

            }


            final TeamsAdapter adapter = new TeamsAdapter(this, R.layout.team, teams);
            listview.setAdapter(adapter);
            listview.setTextFilterEnabled(true);
            listview.setSelector(R.drawable.green);

        }

        catch(Exception e){}

    }
    private ListView.OnItemClickListener lvonclick= new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View v,
                                int position, long id) {
            // 顯示 ListView 的選項內容
            String sel=parent.getItemAtPosition(position).toString();
            String result =dbcourse.executeQuery();
            try{
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonData = jsonArray.getJSONObject(position);
                String name=jsonData.getString("name");
                String week=jsonData.getString("week");
                String time=jsonData.getString("stime");
                String price=jsonData.getString("price");
                String date=jsonData.getString("sdate");
                String content=jsonData.getString("content");
                String course_amount=jsonData.getString("course_amount");
                course_num=jsonData.getString("course_num");
                SpannableString spstr=new SpannableString("課程內容:");
                spstr.setSpan(new ForegroundColorSpan(Color.BLUE),0, spstr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mydialog(name+"詳細內容","日期:"+date+"\n時間:"+time+"\n星期:"+week+"    堂數:"+course_amount+
                        "\n費用:"+price+"\n\n",content);
            }

            catch(Exception e){}

        }
    };

    private void mydialog(String str1,String str2,String str3){
        SpannableString spstr=new SpannableString(str1);
        spstr.setSpan(new ForegroundColorSpan(Color.argb(128,128,42,42)),0, str1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString spstr1=new SpannableString("課程內容");
        spstr1.setSpan(new ForegroundColorSpan(Color.argb(128,128,42,42)),0, spstr1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spstr1.setSpan(new RelativeSizeSpan(1.2f),0, spstr1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        new AlertDialog.Builder(Classinfo.this)
                .setTitle(spstr)
                .setIcon(R.drawable.righticon)
                .setMessage(TextUtils.concat(str2,  spstr1, "\n", str3))
                .setPositiveButton("選課", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        if(account=="" ||account==null){
                            Intent intent2=new Intent();
                            intent2.setClass(Classinfo.this,Login.class);
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
                            intent2.setClass(Classinfo.this,Login.class);
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
            intent.setClass(Classinfo.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Classinfo.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Classinfo.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Classinfo.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Classinfo.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Classinfo.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Classinfo.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Classinfo.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Classinfo.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Classinfo.this)
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
