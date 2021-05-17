package com.nihon.aki2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.nihon.aki2.control.GlobalVariable;

public class Shiken extends AppCompatActivity {
    ImageView bt1,bt2,bt3,bt4,bt5;
    String account="",passwd="",names="";
    private Menu menu;
    Context context;
    Dialog dia;
    String mychbun="",mychqa="";
    //  private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shiken);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();
        bt1=(ImageView)findViewById(R.id.bt01);
        bt2=(ImageView)findViewById(R.id.bt02);
        bt3=(ImageView)findViewById(R.id.bt03);
        bt4=(ImageView)findViewById(R.id.bt04);
        bt5=(ImageView)findViewById(R.id.bt05);
        bt1.setOnTouchListener(bt_1);
        bt2.setOnTouchListener(bt_2);
        bt3.setOnTouchListener(bt_3);
        bt4.setOnTouchListener(bt_4);
        bt5.setOnTouchListener(bt_5);
    }


    private ImageView.OnTouchListener bt_1=new ImageView.OnTouchListener(){//單字
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//單字

                case MotionEvent.ACTION_DOWN:
                    bt1.setImageResource(R.drawable.aki_basic);

                    break;
                case MotionEvent.ACTION_UP:
                    bt1.setImageResource(R.drawable.aki_basich);
                    Intent intent=new Intent();
                    intent.setClass(Shiken.this,Basicmenu.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener bt_2=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//句子重組1

                case MotionEvent.ACTION_DOWN:
                    bt2.setImageResource(R.drawable.aki_primary1h);

                    break;
                case MotionEvent.ACTION_UP:
                    bt2.setImageResource(R.drawable.aki_primary1);
                    Intent intent=new Intent();

                    Bundle bundle=new Bundle();
                    bundle.putString("LEVEL","A");
                    intent.putExtras(bundle);
                    intent.setClass(Shiken.this,Grading.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener bt_3=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//句子重組2

                case MotionEvent.ACTION_DOWN:
                    bt3.setImageResource(R.drawable.aki_primary2h);

                    break;
                case MotionEvent.ACTION_UP:
                    bt3.setImageResource(R.drawable.aki_primary2);
                    Intent intent=new Intent();

                    Bundle bundle=new Bundle();
                    bundle.putString("LEVEL","B");
                    intent.putExtras(bundle);
                    intent.setClass(Shiken.this,Grading.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener bt_4=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//句子重組2

                case MotionEvent.ACTION_DOWN:
                    bt4.setImageResource(R.drawable.aki_advance1h);

                    break;
                case MotionEvent.ACTION_UP:
                    bt4.setImageResource(R.drawable.aki_advance1);
                    Intent intent=new Intent();

                    Bundle bundle=new Bundle();
                    bundle.putString("LEVEL","C");
                    intent.putExtras(bundle);
                    intent.setClass(Shiken.this,Grading.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener bt_5=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//句子重組2

                case MotionEvent.ACTION_DOWN:
                    bt5.setImageResource(R.drawable.aki_advance2h);

                    break;
                case MotionEvent.ACTION_UP:
                    bt5.setImageResource(R.drawable.aki_advance2);
                    Intent intent=new Intent();

                    Bundle bundle=new Bundle();
                    bundle.putString("LEVEL","D");
                    intent.putExtras(bundle);
                    intent.setClass(Shiken.this,Grading.class);
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
            intent.setClass(Shiken.this, Login.class);
            startActivity(intent);
        }
        /*
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Shiken.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Shiken.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        */
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Shiken.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
             /*
            new AlertDialog.Builder(Basic.this)
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
            context = Shiken.this;
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
