package com.nihon.aki2;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Change extends AppCompatActivity {
  String account="",passwd="",names="";
  private Menu menu;
  private static final String TAG="Change";
ArrayList <Rate> rates=new ArrayList<Rate>();
    List<String> buy = new ArrayList<String>();
    List<String> sell=new ArrayList<String>();
    List<String>coin=new ArrayList<String>();
    ArrayList<Team> teams = new ArrayList<Team>();
    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();
    Timer timer = new Timer();
   //ListView listv;
   // Spinner spn;
   String sel="";
    RadioGroup rg;
    RadioButton jp,tw;
    EditText input;
    Spinner inputrate;
    TextView show,showtw,tshow;
    CheckBox check;
    private static final String url ="https://rate.bot.com.tw/xrt?Lang=zh-TW";
    private int secondLeft = 6;
    String[] Balls= new String[] {"0.24","0.25","0.26","0.27","0.28","0.29","0.3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        check=(CheckBox)findViewById(R.id.check);
        inputrate=(Spinner)findViewById(R.id.inputrate);
        rg=(RadioGroup)findViewById(R.id.rg);
        jp=(RadioButton)findViewById(R.id.jp);
        tw=(RadioButton)findViewById(R.id.tw);
        input=(EditText) findViewById(R.id.input);
        show=(TextView)findViewById(R.id.show);
        tshow=(TextView)findViewById(R.id.tshow);
        check.setOnCheckedChangeListener(checkbtn);
        showtw=(TextView)findViewById(R.id.showtw);
        SharedPreferences myrecord=getPreferences(Activity.MODE_PRIVATE);
        String name_str=myrecord.getString("inputrate", "");
        sel=name_str;
        //inputrate.setText(name_str);
        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();
/*
        spn=(Spinner) findViewById(R.id.spn);
        // 建立ArrayAdapter
        ArrayAdapter<CharSequence> adapterBalls = ArrayAdapter.createFromResource(this, R.array.money,android.R.layout.simple_spinner_item);
        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapterBalls);
      spn.setOnItemSelectedListener(spnListener);


    listv=(ListView)findViewById(R.id.listv);
   Rate rate=new Rate("國家貨幣","買入","賣出");
    rates.add(rate);
      for(i=0;i<coin.size();i++)
            {
                rate=new Rate(coin.get(i),buy.get(i),sell.get(i));
                rates.add(rate);
            }

            final RatesAdapter adapter = new RatesAdapter(this, R.layout.rate, rates);
            listv.setAdapter(adapter);
*/
        ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,Balls);

        // 設定Spinner顯示的格式
        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 設定Spinner的資料來源
        inputrate.setAdapter(adapterBalls);

        // 設定 spnPrefer 元件 ItemSelected 事件的 listener 為  spnPreferListener
        inputrate.setOnItemSelectedListener(spnPreferListener);
        jprate();
        input.addTextChangedListener(btinput);
rg.setOnCheckedChangeListener(mychange);
        schedulejob();
      begin();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
        Date date=new Date();
        String dts=sdf.format(date);
        tshow.setText("現價:"+ Double.parseDouble(buy.get(7))+ "   更新:"+dts);
    }

    public void begin() {
        timer.schedule(task, 1000, 1000);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    secondLeft--;

                    if (secondLeft < 1) {
                        secondLeft=50;
                        jprate();
                        // mytoast("run");



                    }
                }
            });
        }
    };
   // private EditText.
  private TextWatcher btinput= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //tw.setChecked(false);
            //jp.setChecked(false);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!TextUtils.isEmpty(input.getText().toString()))
            {
                tw.setEnabled(true);
                jp.setEnabled(true);
               //tw.setChecked(false);
               //jp.setChecked(false);

            }

        }
    };
    private Spinner.OnItemSelectedListener spnPreferListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                   sel=parent.getSelectedItem().toString();

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
    private CheckBox.OnCheckedChangeListener checkbtn=new CheckBox.OnCheckedChangeListener(){
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if(check.isChecked()){
            double money=0;
            //!TextUtils.isEmpty(inputrate.getText().toString())
            if(sel!=""){
                schedulejob();
            }
        }
        else{
            canceljob();
        }
    }
};
private RadioGroup.OnCheckedChangeListener mychange =new RadioGroup.OnCheckedChangeListener(){
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int  p = group.indexOfChild((RadioButton) findViewById(checkedId));	// 選項的索引值
        int money=0;
        if(!TextUtils.isEmpty(input.getText().toString())){


            money=Integer.parseInt(input.getText().toString());

        }

        if (checkedId == R.id.tw){

            double a= Double.parseDouble(buy.get(7));
            double tmp=money/a;
            show.setText( String.format("%.2f",tmp)+"日圓");
            showtw.setText(money+"台幣");
            tw.setChecked(false);

        }
         else if(checkedId==R.id.jp){

            double a= Double.parseDouble(buy.get(7));
            double tmp=money*a;
            showtw.setText( String.format("%.2f",tmp)+"台幣");
            show.setText(money+"日圓");
           jp.setChecked(false);
        }

    }
};
public void jprate(){
    buy.clear();
    coin.clear();
    try{
        Connection.Response response = Jsoup.connect(url).execute();
        String body = response.body();
        Document data =  Jsoup.parse(body);//visible-phone print_hide
        Elements country=data.select("div[class=visible-phone print_hide]");
        Elements tds = data.select("td[class=rate-content-cash text-right print_hide]");
        int i=0;

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
    catch(Exception ex){}
}
/*
    //  定義  onItemSelected 方法
    private Spinner.OnItemSelectedListener spnListener= new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                    if(position!=0){
                   mytoast(" "+coin.get(position-1)+"\n 買入:"+buy.get(position-1)+"\n 賣出:"+sell.get(position-1));
                    }

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
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

 public void schedulejob(){

     ComponentName componentName=new ComponentName(this,JobSchedulerService.class);
     PersistableBundle bundle = new PersistableBundle();
     String tmp="0";
     if(sel!=""){

         tmp=sel;
         SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
         sharedPreferences.edit().putString("input", tmp).apply();

         SharedPreferences myrecord=getPreferences(Activity.MODE_PRIVATE);
         SharedPreferences.Editor edit=myrecord.edit();
         edit.putString("inputrate", tmp);
         edit.commit();
         bundle.putString("INPUT",tmp);
     }
    else{
         bundle.putString("INPUT",tmp);
       }


     JobInfo jobInfo= new JobInfo.Builder(123,componentName)
             .setPersisted(true) // 重開機後是否執行
             .setMinimumLatency(3000) // 延遲多久執行
             .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) //網路條件
             .setExtras(bundle)
             .build();
     JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
   int result=scheduler.schedule(jobInfo);
   if(result==JobScheduler.RESULT_SUCCESS){
       //Log.d(TAG, "test ok!");

   }
   else {//Log.d(TAG,"fail");
   }
 }
 public void canceljob(){
        JobScheduler scheduler=(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
     //Log.d(TAG,"cancel");
 }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Change.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Change.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Change.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Change.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Change.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Change.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Change.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Change.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Change.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Change.this)
                    .setTitle("版權所有")
                    .setIcon(R.drawable.ic_launcher)
                    .setMessage("新澄管理顧問公司"+"\n台南私立亞紀塾日語短期補習班")
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
