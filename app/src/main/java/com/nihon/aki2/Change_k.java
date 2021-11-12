package com.nihon.aki2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.control.GlobalVariable;
import com.nihon.aki2.mydb.dbchange2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Change_k extends AppCompatActivity {
    Dialog dia;
    Context context;
    String[] list2= {"關於" };
    String account="",passwd="",names="";
    EditText TWD,JPY;
    TextView trantxt;
    Button transbtn;
    double twd=0,jpy=0;

    private static final String url ="https://kei-sei.com/cram/a.php";
    double sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_k);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView menulist = (ImageView) findViewById(R.id.menulist);
        menulist.setOnClickListener(menulistbtn);
        TWD=(EditText)findViewById(R.id.TWD);
        JPY=(EditText)findViewById(R.id.JPY);
        trantxt=(TextView)findViewById(R.id.trantxt);
        trantxt.setText("<== 交換貨幣 ==>");
        transbtn=(Button)findViewById(R.id.trabsbtn);

        transbtn.setOnClickListener(transbtnbtn);
        new DownloadFileAsync().execute();
        //jprate2();
//////////////
        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
        nav_view.setSelectedItemId(R.id.btn6);
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
                    case R.id.btn8:
                        startActivity(new Intent(getApplicationContext(),Book_k.class));
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
            AlertDialog.Builder dialog_list = new AlertDialog.Builder(Change_k.this);
            // dialog_list.setTitle(" ");
            dialog_list.setItems(list2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                        context = Change_k.this;
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
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {
            String a= dbchange2.executeQuery();
            sum= Double.valueOf(a);


            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused) {

            if(sum>0){
                GlobalVariable Account = (GlobalVariable)getApplicationContext();
                Account.setDollar(sum);
                store(sum);

            }
            else{
                SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
                float name_str=remdname.getFloat("sum", 0.0f);

                sum=name_str;
            }
        }
    }
    private Button.OnClickListener transbtnbtn=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            double tmp=0;
            if(!TextUtils.isEmpty(TWD.getText().toString()) && TextUtils.isEmpty(JPY.getText().toString())){
                tmp=Double.parseDouble(TWD.getText().toString())/sum;
                jpy=Double.parseDouble(String.format("%.2f",tmp));
                twd=Double.parseDouble(String.format("%.2f",Double.parseDouble(TWD.getText().toString())));
                JPY.setText(jpy+"");
            }
            else if(TextUtils.isEmpty(TWD.getText().toString()) && !TextUtils.isEmpty(JPY.getText().toString())){
                tmp=Double.parseDouble(JPY.getText().toString())*sum;
                twd=Double.parseDouble(String.format("%.2f",tmp));
                jpy=Double.parseDouble(String.format("%.2f",Double.parseDouble(JPY.getText().toString())));
                TWD.setText(twd+"");
            }
            else if(!TextUtils.isEmpty(TWD.getText().toString()) && !TextUtils.isEmpty(JPY.getText().toString())){
                if(Double.parseDouble(JPY.getText().toString())!=jpy){
                    tmp=Double.parseDouble(JPY.getText().toString())*sum;
                    twd=Double.parseDouble(String.format("%.2f",tmp));
                    jpy=Double.parseDouble(String.format("%.2f",Double.parseDouble(JPY.getText().toString())));
                    TWD.setText(twd+"");
                }
                else if(Double.parseDouble(TWD.getText().toString())!=twd){
                    tmp=Double.parseDouble(TWD.getText().toString())/sum;
                    jpy=Double.parseDouble(String.format("%.2f",tmp));
                    twd=Double.parseDouble(String.format("%.2f",Double.parseDouble(TWD.getText().toString())));
                    JPY.setText(jpy+"");
                }

            }
        }
    };

    public void store(double str){
        SharedPreferences myrecord=getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit=myrecord.edit();
        edit.putFloat("sum", (float) str );
        edit.commit();
    }
    public void jprate2(){
        String a= dbchange2.executeQuery();

        // Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");

        // if( pattern.matcher(a).matches()){}

        sum= Double.valueOf(a);
        if(sum>0){
            GlobalVariable Account = (GlobalVariable)getApplicationContext();
            Account.setDollar(sum);
            store(sum);

        }
        else{
            SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
            float name_str=remdname.getFloat("sum", 0.0f);
            //  GlobalVariable Account = (GlobalVariable)getApplicationContext();
            //sum=Account.getDollar();
            sum=name_str;
        }


    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}