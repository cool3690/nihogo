package com.nihon.aki2;

import android.app.AlertDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Basicmenu extends AppCompatActivity {
    String account="",passwd="",names="";
    private Menu menu;
    private ImageView hira,kata,hiratest,katatest;
    private AdView mAdView;
    Context context;
    Dialog dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicmenu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();
        hira=(ImageView)findViewById(R.id.hira);
        kata=(ImageView)findViewById(R.id.kata);
        hiratest=(ImageView)findViewById(R.id.hiratest);
        katatest=(ImageView)findViewById(R.id.katatest);
        hira.setOnTouchListener(hirabtn);
        kata.setOnTouchListener(katabtn);
        hiratest.setOnTouchListener(hiratestbtn);
        katatest.setOnTouchListener(katatestbtn);

    }
        private ImageView.OnTouchListener hirabtn=new ImageView.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {//單字平假名

                    case MotionEvent.ACTION_DOWN:
                        hira.setImageResource(R.drawable.aki_hirarenh);

                        break;
                    case MotionEvent.ACTION_UP:
                        hira.setImageResource(R.drawable.aki_hiraren);
                        Intent intent = new Intent();
                        intent.setClass(Basicmenu.this, Basic50.class);
                        startActivity(intent);
                        break;

                }
                return true;
            }
        };

    private ImageView.OnTouchListener katabtn=new ImageView.OnTouchListener(){//單字
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//單字

                case MotionEvent.ACTION_DOWN:
                    kata.setImageResource(R.drawable.aki_katarenh);

                    break;
                case MotionEvent.ACTION_UP:
                    kata.setImageResource(R.drawable.aki_kataren);
                    Intent intent=new Intent();
                    intent.setClass(Basicmenu.this,Basich50.class);

                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener hiratestbtn=new ImageView.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {//單字平假名

                case MotionEvent.ACTION_DOWN:
                    hiratest.setImageResource(R.drawable.aki_hirah);

                    break;
                case MotionEvent.ACTION_UP:
                    hiratest.setImageResource(R.drawable.aki_hira);
                    Intent intent = new Intent();
                    intent.setClass(Basicmenu.this, Basic.class);
                    startActivity(intent);
                    break;

            }
            return true;
        }
    };
    private ImageView.OnTouchListener katatestbtn=new ImageView.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {//單字平假名

                case MotionEvent.ACTION_DOWN:
                    katatest.setImageResource(R.drawable.aki_katah);

                    break;
                case MotionEvent.ACTION_UP:
                    katatest.setImageResource(R.drawable.aki_kata);
                    Intent intent = new Intent();
                    intent.setClass(Basicmenu.this, Basich.class);
                    startActivity(intent);
                    break;

            }
            return true;
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
            intent.setClass(Basicmenu.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Basicmenu.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Basicmenu.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Basicmenu.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Basicmenu.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Basicmenu.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Basicmenu.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Basicmenu.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Basicmenu.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
              /*
            new AlertDialog.Builder(Basicmenu.this)
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

            context = Basicmenu.this;
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
