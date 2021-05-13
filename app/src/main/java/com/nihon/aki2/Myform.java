package com.nihon.aki2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nihon.aki2.mydb.dbaudit;
import com.nihon.aki2.mydb.dbeform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Myform extends AppCompatActivity {
EditText name,tel,mail,Q1;
String mname="",mtel="",mmail="",mQ1="";
ImageView btn;
    Context context;
    Dialog dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myform);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name=(EditText)findViewById(R.id.name);
        tel=(EditText)findViewById(R.id.tel);
        mail=(EditText)findViewById(R.id.mail);
        Q1=(EditText)findViewById(R.id.Q1);
        btn=(ImageView)findViewById(R.id.btn);
        btn.setOnTouchListener(btnsend);

    }

    private ImageView.OnTouchListener btnsend=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    btn.setImageResource(R.drawable.send);
                    break;
                case MotionEvent.ACTION_UP:
                    btn.setImageResource(R.drawable.send);
                    btok();
                    break;

            }
            return true;
        }
    };
    public void btok(){
        for(int i=0;i<1;i++){
            if(!TextUtils.isEmpty(name.getText().toString())){
                mname=name.getText().toString();
            }
            else{
                mytoast("請輸入姓名");
                break;
            }
            if(!TextUtils.isEmpty(mail.getText().toString())){
                mmail=mail.getText().toString();
            }
            else{
                mytoast("請輸入信箱");
                break;
            }
            if(!TextUtils.isEmpty(Q1.getText().toString())){
                mQ1=Q1.getText().toString();
            }
            else{
                mytoast("請輸入問題");
                break;
            }
            if(!TextUtils.isEmpty(tel.getText().toString())){
                mtel=tel.getText().toString();
            }
            try{
                dbeform.executeQuery(mname,mtel,mmail,mQ1);
                mytoast("成功送出");
                Intent intent=new Intent();
                intent.setClass(Myform.this, Info.class);

                startActivity(intent);
            }
            catch(Exception ex){}
        }

    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}