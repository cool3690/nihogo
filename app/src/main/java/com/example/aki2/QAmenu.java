package com.example.aki2;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QAmenu extends AppCompatActivity {

    ListView menulist;
    Button ok;
    String account="",passwd="";
    ArrayList<Jsan> jsans=new ArrayList<Jsan>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qamenu);
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
        int k=0;
       Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        String bun=bundle.getString("QA");
        k=Integer.parseInt(bun);/* */
        menulist=(ListView)findViewById(R.id.menulist);
        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();




        for(int i=1;i<=k;i++){
            Jsan jsan = new Jsan("Lesson"+i);

            jsans.add(jsan);
        }
        final JsansAdapter adapter = new JsansAdapter(this, R.layout.jsant, jsans);
        menulist.setAdapter(adapter);
        menulist.setTextFilterEnabled(true);
        menulist.setSelector(R.drawable.green);
       menulist.setOnItemClickListener(lstPreferListener);

    }
    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Intent intent= null;
                    String result = dbqaid.executeQuery(position+1+"");
                    String ans="";
                    try{
                        JSONArray jsonArray = new JSONArray(result);

                        int k=0;
                        for(int i = 0; i < 1; i++) //代理或主管有工號者顯示
                        {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                            ans=jsonData.getString("num");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent = new Intent(QAmenu.this, QA.class);
                    Bundle bundle=new Bundle();
                    int p=position+1;

                    String mych=p+"";
                    bundle.putString("MYCH",mych);
                    bundle.putString("NUM",ans);
                    intent.putExtras(bundle);
                    startActivity(intent);

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
            intent.setClass(QAmenu.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(QAmenu.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(QAmenu.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(QAmenu.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(QAmenu.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(QAmenu.this, Mymenu.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(QAmenu.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(QAmenu.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(QAmenu.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
