package com.nihon.aki2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
///no use
public class Countjlpt extends AppCompatActivity {
    String account="",passwd="",names="";
    private Menu menu;
    Button bt1,bt2;
    Button return0;
    long x=0;
    Context context;
    Dialog dia;
    Spinner spinner;
    TextView sign,date,countdown,textview15,mydate,condition;

    private String[] titles = new String[]{"京都民際日本語学校","富士山國際學院"+"\n連絡：sonyzone2004@gmail.com"};
    String[] Balls= new String[] {"京都民際日本語学校","富士山國際學院"+"\n連絡：sonyzone2004@gmail.com"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countjlpt);
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
        /* */
        sign=(TextView)findViewById(R.id.sign);
        date=(TextView)findViewById(R.id.date);
        countdown=(TextView)findViewById(R.id.countdown);
        textview15=(TextView)findViewById(R.id.textView15);
        mydate=(TextView)findViewById(R.id.mydate);
        condition=(TextView)findViewById(R.id.condition);
        spinner=(Spinner)findViewById(R.id.spinner);
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt1.setOnClickListener(bt01);
        bt2.setOnClickListener(bt02);
        ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,Balls);

        // 設定Spinner顯示的格式
        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 設定Spinner的資料來源
        spinner.setAdapter(adapterBalls);
        spinner.setOnItemSelectedListener(spnPreferListener);

    //    init();
    }
    /*
    private void init() {

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        for(int i=0;i<titles.length;i++){
            fragments.add(new TabFragment());
            tabLayout.addTab(tabLayout.newTab());
        }

        tabLayout.setupWithViewPager(viewPager,false);
        pagerAdapter = new FmPagerAdapter(fragments,getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        for(int i=0;i<titles.length;i++){
            tabLayout.getTabAt(i).setText(titles[i]);
        }
    }
    */
    private Spinner.OnItemSelectedListener spnPreferListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {

                x=id;
                  if(id==0){
                      sign.setText("京都民際日本語学校");
                      date.setText("2020年春季短期遊學“櫻”");
                      countdown.setText("2020年3月25日（三）～4月3日（五） \\n每位 125000日圓(含10%消費稅)");
                      textview15.setText("每位 125000日圓(含10%消費稅)");
                      mydate.setText("2020年2月28日 (星期五）");
                      condition.setText("- 原則需滿18歲以上。未滿18歳之未成年者有意願報名者請與本校聯絡。\\n\n" +
                              "- 從初學者到中級。");
                  }
                  else{
                      sign.setText("富士山國際學院"+"\n連絡：sonyzone2004@gmail.com");
                      date.setText("");
                      countdown.setText("每年10 月或 4月開學");
                      textview15.setText("1,441,000日圓\n（入學繳納金，2年學費)");
                      mydate.setText("每年1月～4月底 \n 8月～11月底");
                      condition.setText("需通過日本語能力測驗N5");
                  }

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                }
            };
    private Button.OnClickListener bt01=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            if(x==0){
                intent.setData(Uri.parse("http://kei-sei.com/228.pdf"));
            }
            else{
                intent.setData(Uri.parse("http://kei-sei.com/301.pdf"));
            }
            startActivity(intent);
        }
    };
    private Button.OnClickListener bt02=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(x==0){
                Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://kei-sei.com/2282.pdf"));
                startActivity(intent);
            }
           else{}
        }
    };
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
            intent.setClass(Countjlpt.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Countjlpt.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Countjlpt.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Countjlpt.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Countjlpt.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Countjlpt.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Countjlpt.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Countjlpt.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Countjlpt.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
              /*
            new AlertDialog.Builder(Countjlpt.this)
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
            context = Countjlpt.this;
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
}
