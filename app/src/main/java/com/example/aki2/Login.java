package com.example.aki2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private TextView acc,pwd;
    private  ImageView  btn;
    private Button  forget;
    String mycart="",account="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        if(intent != null && intent.getExtras() != null ) {
            mycart = bundle.getString("CART");
        }
        forget=(Button)findViewById(R.id.forget);
        acc=(TextView)findViewById(R.id.acc);
        pwd=(TextView)findViewById(R.id.pwd);
        btn=(ImageView)findViewById(R.id.btn);
        btn.setOnClickListener(btnlogin);
        forget.setOnClickListener(btnforget);
        SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        String name_str=remdname.getString("emp_id", "");
        String pass_str=remdname.getString("passwd", "");
        acc.setText(name_str);
        pwd.setText(pass_str);


    }
    private Button.OnClickListener btnforget=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Login.this,Forgot.class);
            startActivity(intent);
        }
    };
    private ImageView.OnClickListener btnlogin=new ImageView.OnClickListener(){
        public void onClick(View v){
            String account=acc.getText().toString();
            String passwd=pwd.getText().toString();
            SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
            SharedPreferences.Editor edit=remdname.edit();
            edit.putString("emp_id", acc.getText().toString());
            edit.putString("passwd", pwd.getText().toString());
            edit.commit();
            GlobalVariable Account = (GlobalVariable)getApplicationContext();
            Account.setAccount(account);
            Account.setPasswd(passwd);
            String result =dblogin.executeQuery(account,passwd);

            try{
                JSONArray jsonArray = new JSONArray(result);

                for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
                {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                    String name=jsonData.getString("name");
                    String email=jsonData.getString("email");



                    /////
                    if(mycart==""){
                        Intent intent= new Intent();
                        intent.setClass(Login.this, MainActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("ACCOUNT", account);
                        bundle.putString("NAME", name);
                        bundle.putString("EMAIL", email);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        Intent intent= new Intent();
                        intent.setClass(Login.this, Mcart.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("ACCOUNT", account);
                        bundle.putString("NAME", name);
                        bundle.putString("EMAIL", email);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    //mytoast(account+name+email);
                }
            }

            catch(Exception e){}



        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            intent.setClass(Login.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Login.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Login.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Login.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Mymenu.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Login.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Login.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
