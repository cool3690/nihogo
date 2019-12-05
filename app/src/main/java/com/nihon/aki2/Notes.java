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
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Notes extends AppCompatActivity {
    ArrayList<Team> teams = new ArrayList<Team>();
    TextView sum;
    ListView listview;
    Button buy;
    String account,passwd="",names="",course_num="";
    private Menu menu;
    ArrayList num=new ArrayList();
    int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
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
        if(account.equals(null)){
            Intent intent=new Intent();
            intent.setClass(Notes.this, Login.class);
            startActivity(intent);
        }

        buy=(Button)findViewById(R.id.buy);
        buy.setOnClickListener(buybtn);
        sum=(TextView)findViewById(R.id.sum);
        listview = (ListView) findViewById(R.id.listview);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        Date dt=new Date();
        String dts=sdf.format(dt);
        String result2 =dbselorderid.executeQuery(account,dts);
        String result =dbcourse.executeQuery();

        Team team =new Team("課程","星期","時間","日期","價錢");
        teams.add(team);

        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONArray jsonArray2 = new JSONArray(result2);

            for(int j = 0; j < jsonArray2.length(); j++) {

                JSONObject jsonData2 = jsonArray2.getJSONObject(j);
                String course_num2=jsonData2.getString("course_num");
                int tmp=Integer.parseInt(course_num2);
                num.add(tmp);
                JSONObject jsonData = jsonArray.getJSONObject(tmp-1);
                String name=jsonData.getString("name");

                String week=jsonData.getString("week");
                String time=jsonData.getString("stime");
                String price=jsonData.getString("price");
                String date=jsonData.getString("sdate");
                team = new Team(name, week, time,date,price);
                teams.add(team);
                total+= Integer.parseInt(price);

            }


            final TeamsAdapter adapter = new TeamsAdapter(this, R.layout.team, teams);
            listview.setAdapter(adapter);

            sum.setText("總共:"+total+"元");
        }

        catch(Exception e){}

    }
    private Button.OnClickListener buybtn= new Button.OnClickListener(){
        public void onClick(View v)
        {
            Intent intent=new Intent();
            intent.setClass(Notes.this, MainActivity.class);
            startActivity(intent);
        }
    };
    private void refresh() {
        //	finish();
        Intent intent = new Intent(Notes.this, Mcart.class);
        startActivity(intent);
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
            intent.setClass(Notes.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Notes.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Notes.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Notes.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Notes.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Notes.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Notes.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Notes.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Notes.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Notes.this)
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
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
