package com.example.aki2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Menushow extends AppCompatActivity {
    ImageView btn1,btn2,btn3,btn4,btn5;
    String account="",passwd="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menushow);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        btn1=(ImageView)findViewById(R.id.btn1);

       btn2=(ImageView)findViewById(R.id.btn2);
        btn3=(ImageView)findViewById(R.id.btn3);
       // btn4=(ImageView)findViewById(R.id.btn4);
        btn5=(ImageView)findViewById(R.id.btn5);
        btn1.setOnTouchListener(b1);
        btn2.setOnTouchListener(b2);
        btn3.setOnTouchListener(b3);
       // btn4.setOnTouchListener(b4);
        btn5.setOnTouchListener(b5);

    }

    private ImageView.OnTouchListener b1=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//課程

                case MotionEvent.ACTION_DOWN:
                    btn1.setImageResource(R.drawable.aki_course);

                    break;
                case MotionEvent.ACTION_UP:
                    btn1.setImageResource(R.drawable.aki_courseh);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this, MainActivity.class);
                    //  intent.setClass(Menushow.this,Myweb.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b2=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//匯率
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn2.setImageResource(R.drawable.aki_rate);

                    break;
                case MotionEvent.ACTION_UP:
                    btn2.setImageResource(R.drawable.aki_rateh);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this,Change.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b3=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//新聞
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn3.setImageResource(R.drawable.aki_news);

                    break;
                case MotionEvent.ACTION_UP:
                    btn3.setImageResource(R.drawable.aki_newh);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this,News.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b4=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//購物
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                   // btn4.setImageResource(R.drawable.aki_shop);
                    btn4.setImageResource(R.drawable.aki_basich);

                    break;
                case MotionEvent.ACTION_UP:
                    btn4.setImageResource(R.drawable.aki_basic);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this,Basicmenu.class);
                    startActivity(intent);
                    /*
                    btn4.setImageResource(R.drawable.aki_carth);
                    if(account=="" || account==null){
                        Intent intent=new Intent();
                        intent.setClass(Menushow.this,Login.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("CART", "cart");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent();
                        intent.setClass(Menushow.this, Mcart.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("ACCOUNT", account);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    */
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b5=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//test
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn5.setImageResource(R.drawable.aki_test);
                    break;
                case MotionEvent.ACTION_UP:
                    btn5.setImageResource(R.drawable.aki_testh);
                    Intent intent=new Intent();
                    intent.setClass(Menushow.this,Shiken.class);
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
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null ||account==""){
                Intent intent=new Intent();
                intent.setClass(Menushow.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Menushow.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Mymenu.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Menushow.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
