package com.nihon.aki2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.Exam;
import com.nihon.aki2.mydb.dbcjlpt;
import com.nihon.aki2.mydb.dbstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Info_k extends AppCompatActivity {
    Dialog dia;
    Context context;
    String[] list2= {"關於" };
    TextView schooltxt,conschtxt,jobtxt,conjobtxt,examtxt,examyear,mydate,days;
    ImageView left,right,left2,right2,left3,right3;
    String project="",during="",fee="",attend="",mycondition="",account="",shokai="",address="",web="",names="" ;
    int num=1,n3=0;
    ArrayList ssign = new ArrayList();
    ArrayList sdate=new ArrayList();
    ArrayList status=new ArrayList();
    ArrayList memo=new ArrayList();
    ArrayList exam_type=new ArrayList();
    ArrayList myday=new ArrayList();
    ArrayList mykai2=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_k);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView menulist = (ImageView) findViewById(R.id.menulist);
        menulist.setOnClickListener(menulistbtn);
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
        left=(ImageView)findViewById(R.id.left);
        right=(ImageView)findViewById(R.id.right);
        left2=(ImageView)findViewById(R.id.left2);
        right2=(ImageView)findViewById(R.id.right2);
        left3=(ImageView)findViewById(R.id.left3);
        right3=(ImageView)findViewById(R.id.right3);
        schooltxt=(TextView) findViewById(R.id.schooltxt);
        conschtxt=(TextView) findViewById(R.id.conschttxt);
        jobtxt=(TextView) findViewById(R.id.jobtxt);
        conjobtxt=(TextView) findViewById(R.id.conjobttxt);
        examtxt=(TextView) findViewById(R.id.examtxt);
        examyear=(TextView) findViewById(R.id.examyear);
        mydate=(TextView) findViewById(R.id.mydate);
        days=(TextView) findViewById(R.id.days);
        conschtxt.setMovementMethod(new ScrollingMovementMethod());
        dbsel(num);
        left.setOnClickListener(n1btn);
        right.setOnClickListener(n1btn);
        left3.setOnClickListener(n3btn);
        right3.setOnClickListener(n3btn);
         new Info_k.DownloadFileAsync().execute();
        /////
        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
        nav_view.setSelectedItemId(R.id.btn7);
        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.btn5:
                        startActivity(new Intent(getApplicationContext(),Menushow.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn6:
                        startActivity(new Intent(getApplicationContext(),Tool_k.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn7:
                        startActivity(new Intent(getApplicationContext(),Info_k.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }
    private ImageView.OnClickListener menulistbtn=new ImageView.OnClickListener(){

        @Override
        public void onClick(View view) {
            AlertDialog.Builder dialog_list = new AlertDialog.Builder(Info_k.this);
            // dialog_list.setTitle(" ");
            dialog_list.setItems(list2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                        context = Info_k.this;
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
                }
            });

            dialog_list.show();
        }
    };
    private ImageView.OnClickListener n1btn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.right:
                    num++;
                    dbsel(num);
                    break;
                case R.id.left:
                    num--;
                    if(num<1){num=1;}
                    else{
                        dbsel(num);
                    }
                    break;
                default:
                    break;

            }
            // mytoast(num+"");
        }

    };
    private ImageView.OnClickListener n3btn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.right3:
                    n3++;
                    if(n3>3){n3=3;}
                    else{
                        examtxt.setText(exam_type.get(n3)+"");
                        String[] tmpp= sdate.get(n3).toString().split("/");
                        examyear.setText(tmpp[0]+ mykai2.get(n3));
                        mydate.setText(ssign.get(n3)+"");
                        days.setText(myday.get(n3)+"");
                    }
                    break;
                case R.id.left3:
                    n3--;
                    if(n3<0){num=0;}
                    else{
                        examtxt.setText(exam_type.get(n3)+"");
                        String[] tmpp= sdate.get(n3).toString().split("/");
                        examyear.setText(tmpp[0]+ mykai2.get(n3));
                        mydate.setText(ssign.get(n3)+"");
                        days.setText(myday.get(n3)+"");
                    }
                    break;
                default:
                    break;

            }
            // mytoast(num+"");
        }

    };
    public void dbsel(int a) {
        String result = dbstudy.executeQuery(a+"");

     if(result.contains("null") ){ num--;}

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

                schooltxt.setText(project);
                conschtxt.setText(shokai);

            }

        }

        catch(Exception e){}
    }
    public void dbjlpt(){
        String result = dbcjlpt.executeQuery();

        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;

            for(int i = 0; i < jsonArray.length(); i++) //代理或主管有工號者顯示
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                ssign.add(jsonData.getString("sign"));
                sdate.add(jsonData.getString("date"));
                memo.add(jsonData.getString("memo"));

                status.add(jsonData.getString("status"));
                String mykai=jsonData.getString("kai");
                mykai2.add(mykai+"");
                exam_type.add(jsonData.getString("exam"));

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
                Date dt=new Date();
                long day=0;
                // String dts=sdf.format(dt);
                if((status.get(i)+"").contains("/")){
                    java.util.Date endDate= sdf.parse(sdate.get(i)+"");
                    day=(endDate.getTime()-dt.getTime())/(24*60*60*1000);
                    if(day<0){

                        day=0;
                    }
                }

                myday.add(day);


            }


        }

        catch(Exception e){}


    }
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {
            dbjlpt();

            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused)
        {
            examtxt.setText(exam_type.get(n3)+"");
            String[] tmpp= sdate.get(n3).toString().split("/");
            examyear.setText(tmpp[0]+ mykai2.get(n3));
            mydate.setText(ssign.get(n3)+"");
            days.setText(myday.get(n3)+"");
        }
    }

    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}