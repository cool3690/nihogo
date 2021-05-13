package com.nihon.aki2;

import android.app.Dialog;
import android.content.Context;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.nihon.aki2.control.Team;
import com.nihon.aki2.control.TeamsAdapter;
import com.nihon.aki2.mydb.dbcourse;
import com.nihon.aki2.mydb.dbdelcart;
import com.nihon.aki2.mydb.dbinorder;
import com.nihon.aki2.mydb.dbselcartshow;
import com.nihon.aki2.mydb.dbselorder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Mcart extends AppCompatActivity {
    ArrayList<Team> teams = new ArrayList<Team>();
    TextView sum;
    ListView listview;
    Button buy;
    Context context;
    Dialog dia;
    // private  Button  bt;
    String account="",passwd="",names="",course_num="";
    private Menu menu;
    ArrayList num=new ArrayList();
    int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mcart);
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
            intent.setClass(Mcart.this, Login.class);

            startActivity(intent);
        }

 /*
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        account=bundle.getString("ACCOUNT");
        if(intent != null && intent.getExtras() != null ){ }

*/

        buy=(Button)findViewById(R.id.buy);
        buy.setOnClickListener(buybtn);
        sum=(TextView)findViewById(R.id.sum);
        listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(lvonclick);
        String result2 = dbselcartshow.executeQuery(account);
        String result = dbcourse.executeQuery();

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
            listview.setTextFilterEnabled(true);
            listview.setSelector(R.drawable.green);
            sum.setText("總共:"+total+"元");
        }

        catch(Exception e){}

    }

    private Button.OnClickListener buybtn= new Button.OnClickListener(){
        public void onClick(View v)
        {  String a= last("");
            String result2 =dbselcartshow.executeQuery(account);
            try{
                JSONArray jsonArray2 = new JSONArray(result2);
                //mytoast(jsonArray2.length()+"");
                for(int j = 0; j < jsonArray2.length(); j++) {


                    JSONObject jsonData2 = jsonArray2.getJSONObject(j);
                    String course_num=jsonData2.getString("course_num");
                    String 	cart_num=jsonData2.getString("cart_num");
                    String unit=jsonData2.getString("quantity");/**/
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                    Date dt=new Date();
                    String dts=sdf.format(dt);



                   dbinorder.executeQuery(cart_num,dts+a,account,course_num,unit);
                  dbdelcart.executeQuery(account, course_num);

                }
             mytoast("選課完成!");
                Intent intent=new Intent();
                intent.setClass(Mcart.this, Notes.class);
                startActivity(intent);
            }

            catch(Exception e){}
        }
    };
    private String last(String str1){
        String result = dbselorder.executeQuery();
        try{
            JSONArray jsonArray2 = new JSONArray(result);

            for(int j = 0; j < jsonArray2.length(); j++) {
                JSONObject jsonData2 = jsonArray2.getJSONObject(j);
                String sdate=jsonData2.getString("sdate");
                String order_id=jsonData2.getString("order_id");
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
                Date dt=new Date();
                String dts=sdf.format(dt);
                if(sdate.contains(dts)){
                   int a=Integer.parseInt(order_id.substring(8,11)) ;
                   a++;
                   if(a<10){str1="00"+a;}
                   else if(a<100 && a>9){str1="0"+a;}
                    else{str1=a+"";}
                }
                else{str1="001";}
            }

        }

        catch(Exception e){}
        return str1;
    }
    private ListView.OnItemClickListener lvonclick=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    // 顯示 ListView 的選項內容
                    String sel=parent.getItemAtPosition(position).toString();
                    String result =dbcourse.executeQuery();
                    try{
                        JSONArray jsonArray = new JSONArray(result);
                        Object t=num.get(position-1);
                        //mytoast(num.get(position-1)+"");
                        int tmp=(Integer)t;
                        JSONObject jsonData = jsonArray.getJSONObject(tmp-1);
                        String name=jsonData.getString("name");
                        String week=jsonData.getString("week");
                        String time=jsonData.getString("stime");
                        String price=jsonData.getString("price");
                        String date=jsonData.getString("sdate");
                        String content=jsonData.getString("content");
                        String course_amount=jsonData.getString("course_amount");
                        course_num=jsonData.getString("course_num");
                        mydialog(name+"詳細內容","日期:"+date+"\n時間:"+time+"\n星期:"+week+"\n堂數:"+course_amount+
                                "\n課程內容:"+content+"\n價錢:"+price);
                    }

                    catch(Exception e){}

                    sum.setText("總共:"+total+"元");
                }
            };

    private void mydialog(String str1,String str2){
        new AlertDialog.Builder(Mcart.this)
                .setTitle(str1)
                .setIcon(R.drawable.righticon)
                .setMessage(str2)
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {

                    }
                })
                .setNeutralButton("退選", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {//退選
                        if(account=="" || account==null){
                            Intent intent2=new Intent();
                            intent2.setClass(Mcart.this,Login.class);
                            startActivity(intent2);
                        }
                        else{
                            //退選
                            dbdelcart.executeQuery(account,course_num);
                            mytoast("退選成功!");
                           refresh();

                            sum.setText("總共:"+total+"元");
                        }

                    }


                })
                .show();
    }
    private void refresh() {
        //	finish();
        Intent intent = new Intent(Mcart.this, Mcart.class);
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
            intent.setClass(Mcart.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Mcart.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Mcart.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Mcart.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Mcart.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Mcart.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Mcart.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Mcart.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Mcart.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
             /*
            new AlertDialog.Builder(Mcart.this)
                    .setTitle("版權所有")
                    .setIcon(R.drawable.righticon)
                    .setMessage("新澄管理顧問公司"+"\n臺南市私立慶誠文理短期補習班"+"\n連絡：sonyzone2004@gmail.com")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                        }
                    })
                    .show();
            */
            context = Mcart.this;
            dia = new Dialog(context, R.style.rightcopystyle);
            dia.setContentView(R.layout.copyright);
            Button btok=(Button)dia.findViewById(R.id.btok);
            dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
            Window w = dia.getWindow();
            WindowManager.LayoutParams lp = w.getAttributes();
            lp.x = 0; // 新位置X坐標
            lp.width =950; // 寬度
            dia.show();
            dia.onWindowAttributesChanged(lp);
            btok.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia.dismiss();
                        }
                    }
            );
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
