package com.nihon.aki2;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Toast;

import com.nihon.aki2.control.GlobalVariable;
import com.nihon.aki2.control.Jsan;
import com.nihon.aki2.control.JsansAdapter;
import com.nihon.aki2.mydb.dbsanid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sanmenu extends AppCompatActivity {
    ListView menulist;
    Button ok;
    String account="",passwd="",names="",level="";  private Menu menu;
    ArrayList<Jsan> jsans=new ArrayList<Jsan>();
   // private AdView mAdView;
   Context context;
    Dialog dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sanmenu);
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
        String bun=bundle.getString("BUN");
         level=bundle.getString("LEVEL");
        int k=Integer.parseInt(bun);
        menulist=(ListView)findViewById(R.id.menulist);
        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();
        if(level.equals("A")){
            for(int i=1;i<=12;i++){
                Jsan jsan = new Jsan("Lesson"+i);

                jsans.add(jsan);
            }
        }
        else if(level.equals("B")){
            for(int i=13;i<=25;i++){
                Jsan jsan = new Jsan("Lesson"+i);

                jsans.add(jsan);
            }
        }
        else if(level.equals("C")){
            for(int i=26;i<=38;i++){
                Jsan jsan = new Jsan("Lesson"+i);

                jsans.add(jsan);
            }
        }
        else{
            for(int i=39;i<=k;i++){
                Jsan jsan = new Jsan("Lesson"+i);

                jsans.add(jsan);
            }
        }
       final JsansAdapter adapter = new JsansAdapter(this, R.layout.jsant, jsans);
        menulist.setAdapter(adapter);
         menulist.setTextFilterEnabled(true);
       menulist.setSelector(R.drawable.green);
        menulist.setOnItemClickListener(lstPreferListener);
         /*
        String myid=getString(R.string.appid);
        MobileAds.initialize(this, myid);
        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
      */
    }
    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Intent intent= null;
                    String result="";
                    int p=0;
                    if(level.equals("A")){
                        result = dbsanid.executeQuery(position+1+"");
                        p=position+1;
                    }
                   else if(level.equals("B")){
                        result = dbsanid.executeQuery(position+13+"");
                        p=position+13;
                    }
                    else if(level.equals("C")){
                        result = dbsanid.executeQuery(position+26+"");
                        p=position+26;
                    }
                    else if(level.equals("D")){
                        result = dbsanid.executeQuery(position+39+"");
                        p=position+39;
                    }
                    String ans="";
                    try{
                        JSONArray jsonArray = new JSONArray(result);

                        int k=0;
                        for(int i = 0; i < 1; i++) //代理或主管有工號者顯示
                        {	 JSONObject jsonData = jsonArray.getJSONObject(i);

                           ans=jsonData.getString("id");

                        }

                    } catch (JSONException e) {
                      //  e.printStackTrace();
                    }
                    intent = new Intent(Sanmenu.this, Santance.class);
                    Bundle bundle=new Bundle();
                    //p=position+1;
                    String mych=p+"";
                   // mytoast(mych+"  "+ans);
                    bundle.putString("MYCH",mych);
                    bundle.putString("NUM",ans);
                    intent.putExtras(bundle);
                        startActivity(intent);
         /*
                    else{
                        try {String b="com.example.aki2.Santance"+position+1;
                            intent = new Intent(Sanmenu.this,  Class.forName(b));
                            startActivity(intent);
                             } catch (ClassNotFoundException e) {  e.printStackTrace();}
                          }
                    */


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
            intent.setClass(Sanmenu.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(Sanmenu.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(Sanmenu.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(Sanmenu.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(Sanmenu.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(Sanmenu.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(Sanmenu.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(Sanmenu.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(Sanmenu.this, MainActivity.class);
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
            context = Sanmenu.this;
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
