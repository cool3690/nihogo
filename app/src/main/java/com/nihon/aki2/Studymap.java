package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.mydb.dbstudy;
import com.nihon.aki2.mydb.dbstudy2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Studymap extends AppCompatActivity {
    private ImageView img;
    private ScaleImage mScaleImage;
    TextView title,countdown,textView15,mydate,condition;
    Spinner page;
    String account="",names="";
    int num=1;

    String[] mypage= new String[1];
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studymap);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        mypage[0]="請選擇";
       // mScaleImage = (ScaleImage) findViewById(R.id.scale_image);
        img=(ImageView) findViewById(R.id.img);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

         img.setOnClickListener(imgbtn2);
        page=(Spinner)findViewById(R.id.page);

        title=(TextView)findViewById(R.id.title);
        countdown=(TextView)findViewById(R.id.countdown);
        textView15=(TextView)findViewById(R.id.textView15);
        mydate=(TextView)findViewById(R.id.mydate);
        condition=(TextView)findViewById(R.id.condition);
        dbsel(num);
        dbsel2();

    }
    private Spinner.OnItemSelectedListener spnPreferListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    //String sel=parent.getSelectedItem().toString();
                    int num=position;


                    dbsel(num );

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

    public void dbsel2( ) {
        String result = dbstudy2.executeQuery( );

        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;

            String[]mypage=new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++)
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String project=jsonData.getString("project");
                mypage[i]=project;


            }
            ArrayAdapter<String> adapterPage=new ArrayAdapter<String>
                    (this,android.R.layout.simple_spinner_item,mypage);

            // 設定Spinner顯示的格式
            adapterPage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // 設定Spinner的資料來源
            page.setAdapter(adapterPage);

            // 設定 spnPrefer 元件 ItemSelected 事件的 listener 為  spnPreferListener
            page.setOnItemSelectedListener(spnPreferListener);
        }

        catch(Exception e){}
    }
    public void dbsel(int num) {
        String result = dbstudy.executeQuery(num+"");

        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;

            for(int i = 0; i < jsonArray.length(); i++)
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String project=jsonData.getString("project");

                String during=jsonData.getString("during");
                String fee=jsonData.getString("fee");
                String attend=jsonData.getString("attend");
                String mycondition=jsonData.getString("mycondition");
                title.setText(project);
                countdown.setText(during);
                textView15.setText(fee);
                mydate.setText(attend);
                condition.setText(mycondition);
            }

        }

        catch(Exception e){}
    }
    private ImageView.OnClickListener imgbtn2=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Studymap.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Studymap.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(Studymap.this)
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
    /* */
}