package com.nihon.aki2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nihon.aki2.control.Exam;
import com.nihon.aki2.control.ExamsAdapter;
import com.nihon.aki2.control.Team;
import com.nihon.aki2.mydb.dbcjlpt;
import com.nihon.aki2.mydb.dbdelcart;
import com.nihon.aki2.mydb.dbincart;
import com.nihon.aki2.mydb.dbselcart;


public class MainActivity extends AppCompatActivity {
    Dialog dia;
    Context context;
    ImageView toright,toleft;
    String url="https://www.jlpt.tw/";;
    ImageView classinfo;
    RelativeLayout R1;
    TextView testinfo,jointime,testtime,countday,title;
    ImageView more;
    int cc=0,check=0;
    Button btjlpt,bteju;
    String account="",passwd="",names="",course_num="",mykai="";
    private Menu menu;
    String myurl="https://akkyschool.com/images/study_abroad/cs_jlpt.png";
    ArrayList ssign = new ArrayList();
    ArrayList sdate=new ArrayList();
    ArrayList status=new ArrayList();
    ArrayList memo=new ArrayList();
    ArrayList exam_type=new ArrayList();
    String[] Balls= new String[1] ;
  //  ListView list;
   // ArrayList<Exam> teams = new ArrayList<Exam>();
    private List<Exam> teams1 = new ArrayList<>();
    int x=0,max=0;
    RecyclerView list;
    int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Balls[0]="請選擇";
        GlobalVariable Account = (GlobalVariable)getApplicationContext();
        account=Account.getAccount();
        passwd=Account.getPasswd();
        names=Account.getNames();
      //  list=(ListView)findViewById(R.id.list);
        /*
        testinfo=(TextView)findViewById(R.id.testinfo);
        jointime=(TextView)findViewById(R.id.jointime);
        testtime=(TextView)findViewById(R.id.testtime);
        countday=(TextView)findViewById(R.id.countday);
        title=(TextView)findViewById(R.id.textView7);
        more=(ImageView)findViewById(R.id.more);*/
        classinfo=(ImageView)findViewById(R.id.classinfo);

        btjlpt=(Button)findViewById(R.id.btjlpt) ;
        bteju=(Button)findViewById(R.id.bteju) ;
        R1=(RelativeLayout) findViewById(R.id.R1);
        btjlpt.setBackgroundColor(Color.YELLOW);
       list= findViewById(R.id.list);
        toleft=(ImageView)findViewById(R.id.toleft);
        toright=(ImageView)findViewById(R.id.toright);
        btjlpt.setOnClickListener(btjlptclick);
         bteju.setOnClickListener(btejuclick);

        classinfo.setOnClickListener(classinfobtn);
        toright.setOnClickListener(torightbtn);
        toleft.setOnClickListener(toleftbtn);
    /*  more.setOnClickListener(morebtn);

         */


        String result = dbcjlpt.executeQuery();

        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                ssign.add(jsonData.getString("sign"));
                 sdate.add(jsonData.getString("date"));
                 memo.add(jsonData.getString("memo"));
               // jointime.setText("網路報名時間:\n"+ssign.get(0));
                 status.add(jsonData.getString("status"));
                 mykai=jsonData.getString("kai");
                exam_type.add(jsonData.getString("exam_type"));
               // testtime.setText(status.get(0)+"");
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
                Date dt=new Date();
                // String dts=sdf.format(dt);
                java.util.Date endDate= sdf.parse(sdate.get(i)+"");
                long day=(endDate.getTime()-dt.getTime())/(24*60*60*1000);
                if(day<0){
                //    countday.setTextSize(16);
                  //  countday.setText("   "+"0");
                    day=0;
                }
                else{
                 //   countday.setText("   "+day );
                }
                if(exam_type.get(i).toString().contains("留學")){
                    Exam team =new Exam(exam_type.get(i).toString(),sdate.get(i).toString().substring(0,4)+"年"+mykai,
                            "網路報名時間:\n"+ssign.get(i),status.get(i)+"","   "+day,R.drawable.aki_eju3);
                    teams1.add(team);
                    max++;
                }
                else if(exam_type.get(i).toString().contains("能力")){
                    Exam team =new Exam(exam_type.get(i).toString(),sdate.get(i).toString().substring(0,4)+"年"+mykai,
                            "網路報名時間:\n"+ssign.get(i),status.get(i)+"","   "+day,R.drawable.aki_jlpt3);
                    teams1.add(team);
                    max++;
                }

            }


        }

        catch(Exception e){}
        /*
        final ExamsAdapter adapter = new ExamsAdapter(this, R.layout.exam, teams);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);


         */
        //list.setOnItemClickListener(lstPreferListener);
        ExamsAdapter mAdapter;

        mAdapter = new ExamsAdapter(teams1);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(mAdapter);
      /////

        list.setOnClickListener(lstPreferListener);

        //mAdapter.notifyDataSetChanged();
        toright.setVisibility(View.VISIBLE);
        toleft.setVisibility(View.INVISIBLE);
    }
    private ImageView.OnClickListener torightbtn =new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {

            if(x>=max)x=-1;
            if(++x<max){
                list.scrollToPosition(x);
                if(x>1){
                    R1.setBackgroundResource(R.drawable.aki_eju2);
                    //toright.setImageResource(R.drawable.aki_point);
                    btjlpt.setBackgroundColor(Color.WHITE);
                    bteju.setBackgroundColor(Color.YELLOW);
                    if(x==2){
                        toleft.setVisibility(View.INVISIBLE);

                        toright.setVisibility(View.VISIBLE);
                        toright.setImageResource(R.drawable.aki_point);
                    }
                    if(x==3){
                        toleft.setVisibility(View.VISIBLE);
                        toleft.setImageResource(R.drawable.aki_point3);
                        toright.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    R1.setBackgroundResource(R.drawable.aki_jlpt2);
                    //toright.setImageResource(R.drawable.aki_point2);
                    btjlpt.setBackgroundColor(Color.YELLOW);
                    bteju.setBackgroundColor(Color.WHITE);
                    if(x==1){
                        toleft.setVisibility(View.VISIBLE);
                        toleft.setImageResource(R.drawable.aki_point4);
                        toright.setVisibility(View.INVISIBLE);
                    }
                    if(x==0){
                        toleft.setVisibility(View.INVISIBLE);

                        toright.setVisibility(View.VISIBLE);
                        toright.setImageResource(R.drawable.aki_point2);
                    }
                }
            }

        }
    };
    private ImageView.OnClickListener toleftbtn =new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
            if(x<0) x=0;
            if(--x>-1){
                list.scrollToPosition(x);
                if(x>1){//eju
                    R1.setBackgroundResource(R.drawable.aki_eju2);
                    btjlpt.setBackgroundColor(Color.WHITE);
                    bteju.setBackgroundColor(Color.YELLOW);
                    if(x==2){
                        toleft.setVisibility(View.INVISIBLE);

                        toright.setVisibility(View.VISIBLE);
                        toright.setImageResource(R.drawable.aki_point);
                    }
                    if(x==3){
                        toleft.setVisibility(View.VISIBLE);
                        toleft.setImageResource(R.drawable.aki_point3);
                        toright.setVisibility(View.INVISIBLE);
                    }

                }
                else{//jlpt
                    R1.setBackgroundResource(R.drawable.aki_jlpt2);
                    btjlpt.setBackgroundColor(Color.YELLOW);
                    bteju.setBackgroundColor(Color.WHITE);
                    if(x==1){
                        toleft.setVisibility(View.VISIBLE);
                        toleft.setImageResource(R.drawable.aki_point4);
                        toright.setVisibility(View.INVISIBLE);
                    }
                    if(x==0){
                        toleft.setVisibility(View.INVISIBLE);

                        toright.setVisibility(View.VISIBLE);
                        toright.setImageResource(R.drawable.aki_point2);
                    }
                }
            }

        }
    };
    private RecyclerView.OnClickListener lstPreferListener=new RecyclerView.OnClickListener(){
        @Override
        public void onClick(View view) {

            context = MainActivity.this;
            dia = new Dialog(context, R.style.edit_AlertDialog_style);
            dia.setContentView(R.layout.dailogshow);
            final ImageView imageView = (ImageView) dia.findViewById(R.id.start_img);
            TextView show=(TextView)dia.findViewById(R.id.show);
            Button btok=(Button)dia.findViewById(R.id.btok);
            Button download=(Button)dia.findViewById(R.id.btdownload);
            show.setVisibility(View.GONE);

//////////////////////////////////////////

            //int w=list.get(holder.absoluteAdapterPosition);
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
                    //  saveImage(getApplicationContext(), result, "my_image.png");
                    super.onPostExecute(result);

                }
            }.execute(myurl);


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
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }
                    }
            );
            download.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(myurl));
                            startActivity(intent);

                        }
                    }
            );
        }
    };
    private Button.OnClickListener btjlptclick=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            btjlpt.setBackgroundColor(Color.YELLOW);
            bteju.setBackgroundColor(Color.WHITE);
            list.scrollToPosition(0);
            R1.setBackgroundResource(R.drawable.aki_jlpt2);
            x=0;
            if(x==0){
                toleft.setVisibility(View.INVISIBLE);

                toright.setVisibility(View.VISIBLE);
                toright.setImageResource(R.drawable.aki_point2);
            }
            /*
            jointime.setText("網路報名時間:\n"+ssign.get(0)+"\n"+memo.get(0));
            title.setText("日本語能力測驗");
            testinfo.setText(sdate.get(0).toString().substring(0,4)+"年"+mykai);

            myurl= "https://akkyschool.com/images/study_abroad/cs_jlpt.png";
            testtime.setText(status.get(0)+"");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
            more.setImageResource(R.drawable.aki_jlpt3);
            url="https://www.jlpt.tw/";
            Date dt=new Date();
            // String dts=sdf.format(dt);
            Date endDate= null;
            try {
                endDate = sdf.parse(sdate.get(0)+"");
            } catch (ParseException e) {

            }
            long day=(endDate.getTime()-dt.getTime())/(24*60*60*1000);
            if(day<0){
                //    countday.setTextSize(16);
                countday.setText("   "+"0");
            }
            else{
                countday.setText("   "+day );
            }

             */


        }
    };
    private Button.OnClickListener btejuclick=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            btjlpt.setBackgroundColor(Color.WHITE);
            bteju.setBackgroundColor(Color.YELLOW);
            list.scrollToPosition(2);
            R1.setBackgroundResource(R.drawable.aki_eju2);
            x=2;
            if(x==2){
                toleft.setVisibility(View.INVISIBLE);

                toright.setVisibility(View.VISIBLE);
                toright.setImageResource(R.drawable.aki_point);
            }
            /*

            jointime.setText("網路報名時間:\n"+ssign.get(1)+"\n"+memo.get(1));
            title.setText("日本留學試驗");
            myurl= "https://akkyschool.com/images/study_abroad/cs_eju.png";
            url="https://www.lttc.ntu.edu.tw/EJU_registration.htm";
            testtime.setText(status.get(1)+"");
            testinfo.setText(sdate.get(0).toString().substring(0,4)+"年"+mykai);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
            Date dt=new Date();
            // String dts=sdf.format(dt);
            more.setImageResource(R.drawable.aki_eju3);
            Date endDate= null;
            try {
                endDate = sdf.parse(sdate.get(1)+"");
            } catch (ParseException e) {

            }
            long day=(endDate.getTime()-dt.getTime())/(24*60*60*1000);
            if(day<0){
                //    countday.setTextSize(16);
                countday.setText("   "+"0");
            }
            else{
                countday.setText("   "+day );
            }

             */
        }
    };
    private ImageView.OnClickListener classinfobtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,Classinfo.class);
            startActivity(intent);
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
    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {

        }
    }
    private ImageView.OnClickListener morebtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {

            context = MainActivity.this;
            dia = new Dialog(context, R.style.edit_AlertDialog_style);
            dia.setContentView(R.layout.dailogshow);
            final ImageView imageView = (ImageView) dia.findViewById(R.id.start_img);
            TextView show=(TextView)dia.findViewById(R.id.show);
            Button btok=(Button)dia.findViewById(R.id.btok);
            Button download=(Button)dia.findViewById(R.id.btdownload);
            show.setVisibility(View.GONE);

//////////////////////////////////////////

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
                  //  saveImage(getApplicationContext(), result, "my_image.png");
                    super.onPostExecute(result);

                }
            }.execute(myurl);


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
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }
                    }
            );
            download.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(myurl));
                            startActivity(intent);

                        }
                    }
            );
        }
    };

    private void mydialog(String str1,String str2,String str3){
        SpannableString spstr=new SpannableString(str1);
        spstr.setSpan(new ForegroundColorSpan(Color.argb(128,128,42,42)),0, str1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString spstr1=new SpannableString("課程內容");
        spstr1.setSpan(new ForegroundColorSpan(Color.argb(128,128,42,42)),0, spstr1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spstr1.setSpan(new RelativeSizeSpan(1.2f),0, spstr1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(spstr)
                .setIcon(R.drawable.ic_launcher)
                .setMessage(TextUtils.concat(str2,  spstr1, "\n", str3))
                .setPositiveButton("選課", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        if(account=="" ||account==null){
                            Intent intent2=new Intent();
                            intent2.setClass(MainActivity.this,Login.class);
                            startActivity(intent2);
                        }
                        else{//加選
                            int tmp=0;
                            String	result= dbselcart.executeQuery(account,course_num);

                            try{
                                JSONArray jsonArray = new JSONArray(result);

                                tmp=jsonArray.length();
                            }
                            catch(Exception e){}
                            if(tmp>0){mytoast("已選過此課程");}
                            else{
                                dbincart.executeQuery(account,course_num);
                                mytoast("選課成功!");
                            }
                        }
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                    }
                })
                .setNeutralButton("退選", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i)
                    {//退選
                        if(account==""){
                            Intent intent2=new Intent();
                            intent2.setClass(MainActivity.this,Login.class);
                            startActivity(intent2);
                        }
                        else{
                            //退選
                            dbdelcart.executeQuery(account,course_num);
                            mytoast("退選成功!");
                        }

                    }
                })
                .show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Login.class);
            startActivity(intent);
        }
        if (id == R.id.cart) {
            if(account==null){
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,Login.class);
                Bundle bundle=new Bundle();
                bundle.putString("CART", "cart");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, Mcart.class);
                Bundle bundle=new Bundle();
                bundle.putString("ACCOUNT", account);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        if (id == R.id.mon) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Change.class);
            startActivity(intent);
        }
        if (id == R.id.news) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Myweb.class);
            startActivity(intent);
        }
        if (id == R.id.mymenu) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Menushow.class);
            startActivity(intent);
        }
        if (id == R.id.apply) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Apply.class);
            startActivity(intent);
        }

        if (id == R.id.lesson) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, Shiken.class);
            startActivity(intent);
        }
        if (id == R.id.menu) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.about) {
            new AlertDialog.Builder(MainActivity.this)
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

}
