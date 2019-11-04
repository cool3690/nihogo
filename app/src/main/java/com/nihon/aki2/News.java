package com.nihon.aki2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class News extends AppCompatActivity {
ListView newlist;
Button ok;
    private AdView mAdView;
    String account="",passwd="",names="";  private Menu menu;
    private static final String trgUrl ="https://www3.nhk.or.jp/rss/news/cat0.xml";
    private static final String TAG = "XMLParserTest";
    private static final String url ="https://www3.nhk.or.jp/news/";
    String link[]=new String [10];
    RssNews[] Arr_RssNews;
ArrayList <Jnew> jpnews=new ArrayList<Jnew>();

    protected static final int REFRESH_DATA = 0x00000001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        newlist=(ListView)findViewById(R.id.newlist);
        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();
        for(int i=0;i<10;i++){link[i]="";}
        Arr_RssNews = getRssNews();
        if (Arr_RssNews != null)
        {
            for (int i = 0; i < Arr_RssNews.length; i++)
            { Jnew team = new Jnew(Arr_RssNews[i].getTitle());
                link[i]= Arr_RssNews[i].getLink();
                jpnews.add(team);
            }
        }

        new MyTask().execute();


       /* new Thread()
        { @Override
        public void run()
        {
            Arr_RssNews = getRssNews();
            if (Arr_RssNews != null)
                mHandler.sendEmptyMessage(REFRESH_DATA);
        }
        }.start();

*/

    final JnewAdapter adapter = new JnewAdapter(this, R.layout.mynew, jpnews);
        newlist.setAdapter(adapter);
        newlist.setTextFilterEnabled(true);
        newlist.setSelector(R.drawable.green);




        // 設定 lstPrefer 元件 ItemClick 事件的 listener 為 lstPreferListener
        newlist.setOnItemClickListener(lstPreferListener);
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
    }/*
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {// 更新資料，將Rss新聞的標題用迴圈依序印出來
                case REFRESH_DATA:

                    for (int i = 0; i < Arr_RssNews.length; i++)
                    { team = new Jnew(Arr_RssNews[i].getTitle());
                        jpnews.add(team);

                        mytoast(Arr_RssNews[i].getTitle() + "\n");
                    }
                    break;
            }
        }
    };
*/
    //  定義  onItemClick 方法
    private ListView.OnItemClickListener lstPreferListener=
            new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Uri uri=Uri.parse(link[position]);
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);

                }
            };
    public RssNews[] getRssNews()
    {
        if (trgUrl == null)
            return null;
        try
        {
            // 建立一個Parser物件，並指定擷取規則 (ParsingHandler)
            SimpleXMLParser dataXMLParser = new SimpleXMLParser(
                    new RssNewsXMLParsingHandler());
            // 呼叫getData方法取得物件陣列
            Object[] data = (Object[]) dataXMLParser.getData(trgUrl);
            if (data != null)
            {
                // 如果資料形態正確，就回傳
                if (data[0] instanceof RssNews[])
                {
                    return (RssNews[]) data[0];
                }
            }
        } catch (SAXException e) {}
        catch (IOException e) {}
        catch (ParserConfigurationException e) { }

        // 若有錯誤則回傳null

        return null;

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

    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Connection.Response response = Jsoup.connect(url).execute();
                String body = response.body();

                Document data = Jsoup.parse(body);//visible-phone print_hide
                Elements country = data.select("main");
                Elements main = data.select("div[id=module module--news-main index-main]");

                for (final Element e1 : main) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mytoast(e1.toString());
                        }
                    });
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return null;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(News.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(News.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(News.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(News.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(News.this, News.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(News.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(News.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(News.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(News.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(News.this)
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
