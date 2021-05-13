package com.nihon.aki2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.VideoAdapter;
import com.nihon.aki2.control.YouTubeVideos;
import com.nihon.aki2.mydb.dbstudy;
import com.nihon.aki2.mydb.dbstudy2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Studymap extends AppCompatActivity {
    private ImageView img;
    private ScaleImage mScaleImage;
    TextView title,countdown,textView15,mydate,condition,myaddress,myweb;
    Spinner page;
    Button bt1 ,bt3,bt4;
    String project="",during="",fee="",attend="",mycondition="",account="",shokai="",address="",web="",names="" ;
    Context context;
    // private AdView mAdView;
    private Menu menu;
    Dialog dia;
    boolean tf=true;
    int num=1;
    WebView videoWebView;
    RecyclerView recyclerView;
    GifImageView imageView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();
    List<String>mypage=new ArrayList<>();
    //String[] mypage= new String[1];
  //  int[] mypoint=new int[1];
    List<Integer> mypoint = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studymap);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
       // mypage[0]="請選擇";
       // mypoint[0]= 0;
       // mScaleImage = (ScaleImage) findViewById(R.id.scale_image);
        img=(ImageView) findViewById(R.id.img);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        page=(Spinner)findViewById(R.id.page);

        title=(TextView)findViewById(R.id.title);
        countdown=(TextView)findViewById(R.id.countdown);
        myaddress=(TextView)findViewById(R.id.myaddress);
        myweb=(TextView)findViewById(R.id.myweb);
        /*
        textView15=(TextView)findViewById(R.id.textView15);
        mydate=(TextView)findViewById(R.id.mydate);
        condition=(TextView)findViewById(R.id.condition);

         */
        bt1=(Button)findViewById(R.id.bt1) ;

        bt3=(Button)findViewById(R.id.bt3);
        bt4=(Button)findViewById(R.id.bt4);
       // dbsel(num);
        //dbsel2();
        bt1.setOnClickListener(bt01);

        bt3.setOnClickListener(bt03);
        bt4.setOnClickListener(bt04);
        imageView = (GifImageView)findViewById(R.id.mygif);
        videoWebView=(WebView) findViewById(R.id.videoWebView);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE);
        videoWebView.setVisibility(View.GONE);
        startDownload();
    }
    private void startDownload() {

        new Studymap.DownloadFileAsync().execute();
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        //  ProgressDialog dialog = new ProgressDialog(Listening.this);


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {



            new Studymap.DownloadFileAsync().cancel(true);
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused)
        {  dbsel(num);
            dbsel2();

        }
    }
    private Button.OnClickListener bt04=new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, Myform.class);
            startActivity(intent);

        }
    };
    private Button.OnClickListener bt03=new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(num==1) {
                if (tf) {
                    bt3.setText("關閉");
                    tf = false;
                   play();
                    videoWebView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                } else {
                    bt3.setText("播放");
                    tf = true;
                    play0();
                    videoWebView.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.GONE);

                }
            }
           else if(num==2) {
                if (tf) {
                    bt3.setText("關閉");
                    tf = false;
                    play2();
                    videoWebView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                } else {
                    bt3.setText("播放");
                    tf = true;
                    play0();
                    videoWebView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);

                }
            }
             else if(num==3) {
                if (tf) {
                    bt3.setText("關閉");
                    tf = false;
                    play3();
                    videoWebView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                } else {
                    bt3.setText("播放");
                    tf = true;
                    play0();
                    videoWebView.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.GONE);

                }
            }
            else if(num==4) {
                if (tf) {
                    bt3.setText("關閉");
                    tf = false;
                    play4();
                    videoWebView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                } else {
                    bt3.setText("播放");
                    tf = true;
                    play0();
                    videoWebView.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.GONE);

                }
            }
        }
    };
    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e){  return null;}
    }
    private Button.OnClickListener bt01=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            context = Studymap.this;
            dia = new Dialog(context, R.style.edit_AlertDialog_style);
            dia.setContentView(R.layout.dailogshow);
            final ImageView imageView = (ImageView) dia.findViewById(R.id.start_img);
            TextView show=(TextView)dia.findViewById(R.id.show);
            Button btok=(Button)dia.findViewById(R.id.btok);
            Button download=(Button)dia.findViewById(R.id.btdownload);
//////////////////////////////////////////
            String myurl="https://kei-sei.com/images/study_abroad/";
            if(num==1){
                myurl+= "dm.png";
            }
            else if(num==2){
                myurl+= "dm2.png";
            }
            else if(num==3){
                myurl+= "dm3.png";
            }
            else if(num==4){
                myurl+= "dm4.png";
            }

            new AsyncTask<String, Void, Bitmap>()
            {
                @Override
                protected Bitmap doInBackground(String... params) //實作doInBackground
                {
                    String url = params[0];
                    return getBitmapFromURL(url);
                }

                @Override
                protected void onPostExecute(Bitmap result) //當doinbackground完成後
                {
                    imageView.setImageBitmap (result);
                    super.onPostExecute(result);
                }
            }.execute(myurl);
/*
            try {
                InputStream is = (InputStream) new URL("https://kei-sei.com/images/study_abroad/dm2.png").getContent();
                Drawable d = Drawable.createFromStream(is, "dm2.png");
                imageView.getWidth();
                imageView.setMinimumWidth(imageView.getWidth());
                imageView.setImageDrawable(d);

            } catch (Exception e) {

            }
*/
        ////////////////////////
/*
            if(num==1){
                imageView.setImageResource(R.drawable.testpic);
            }
            else if(num==2){
                imageView.setImageResource(R.drawable.testpic2);
            }
            else if(num==3){
                imageView.setImageResource(R.drawable.testpic3);
            }
*/
            show.setText(project+"\n參加費用:"+fee+"\n報名期限:"+attend+"\n參加條件:"+mycondition);
            dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
            Window w = dia.getWindow();
            WindowManager.LayoutParams lp = w.getAttributes();
            lp.x = 0;
            lp.y = 20;
            dia.show();
            dia.onWindowAttributesChanged(lp);
            imageView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia.dismiss();
                        }
                    });
            btok.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia.dismiss();
                        }
                    }
            );
            download.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(num==1){
                                Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("http://kei-sei.com/images/study_abroad/file/school2_2introduction.pdf"));
                                startActivity(intent);
                            }
                            else if(num==2){
                                Intent intent=new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("http://kei-sei.com/images/study_abroad/file/school1_introduction.pdf"));
                                startActivity(intent);
                            }
                            else if(num==3){
                                Intent intent=new Intent(Intent.ACTION_VIEW);

                                intent.setData(Uri.parse("http://kei-sei.com/images/study_abroad/file/school4_1_introduction.pdf"));
                                startActivity(intent);
                            }
                            else if(num==4){
                                Intent intent=new Intent(Intent.ACTION_VIEW);

                                intent.setData(Uri.parse("http://kei-sei.com/images/study_abroad/file/school5_1_introduction.pdf"));
                                startActivity(intent);
                            }
                        }
                    }
            );

        }
    };

    private Spinner.OnItemSelectedListener spnPreferListener=
            new Spinner.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View v,
                                           int position, long id) {
                    //String sel=parent.getSelectedItem().toString();
                    play0();
                     num=mypoint.get(position);

                   // mytoast(mypoint.get(position)+"");
                    dbsel(num);
                    bt3.setText("播放");
                    tf = true;
                    videoWebView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };
    public void play0(){

        videoWebView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        videoWebView.bringToFront();
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        youtubeVideos.clear();


        /* */
    }
    public void play3(){

        videoWebView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        videoWebView.bringToFront();
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        youtubeVideos.clear();
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://kei-sei.com/images/study_abroad/study_abroad42.mp4\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);//https://www.youtube.com/watch?v=xmkqU_M21lk&feature=youtu.be

        recyclerView.setAdapter(videoAdapter);

        /* */
    }
    public void play(){

        videoWebView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        videoWebView.bringToFront();
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        youtubeVideos.clear();
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://kei-sei.com/images/study_abroad/study_abroad1.mp4\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);//https://www.youtube.com/watch?v=xmkqU_M21lk&feature=youtu.be

        recyclerView.setAdapter(videoAdapter);

        /* */
    }
    public void play2(){

        videoWebView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        videoWebView.bringToFront();
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        youtubeVideos.clear();
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/xmkqU_M21lk\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);//https://www.youtube.com/watch?v=xmkqU_M21lk&feature=youtu.be

        recyclerView.setAdapter(videoAdapter);

        /* */
    }
    public void play4(){

        videoWebView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        videoWebView.bringToFront();
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        youtubeVideos.clear();
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/b3xu9_cobZI\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);//https://www.youtube.com/watch?v=xmkqU_M21lk&feature=youtu.be

        recyclerView.setAdapter(videoAdapter);

        /* */
    }
    public void dbsel2( ) {
        String result = dbstudy2.executeQuery( );

        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;
          //  int[]mypoint=new int[jsonArray.length()];
          //  String[]mypage=new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++)
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String project=jsonData.getString("project");
                int id=jsonData.getInt("id");
                mypage.add(project);
                mypoint.add(id);


            }
            ArrayAdapter<String> adapterPage=new ArrayAdapter<String>
                    (this,android.R.layout.simple_spinner_item,mypage);
          //  mytoast(mypoint[2]+"");
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
                 project=jsonData.getString("project");

                during=jsonData.getString("during");
                fee=jsonData.getString("fee");
                attend=jsonData.getString("attend");
                mycondition=jsonData.getString("mycondition");
                shokai=jsonData.getString("shokai");
                address=jsonData.getString("address");
                web=jsonData.getString("web");
                title.setText(project);
                countdown.setText(shokai);
                myaddress.setText(address);
                myweb.setText(web);
               /*  countdown.setText(during);
                textView15.setText(fee);
                mydate.setText(attend);
                condition.setText(mycondition);
               */
            }

        }

        catch(Exception e){}
    }

    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(Studymap.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null ||account==""){
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
        /*
            new AlertDialog.Builder(Menushow.this)
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
            context = Studymap.this;
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
    /* */
}