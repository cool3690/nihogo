package com.nihon.aki2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Listenmenu extends AppCompatActivity {
    ImageView btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listenmenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn1=(ImageView)findViewById(R.id.btn1);
        btn2=(ImageView)findViewById(R.id.btn2);
        btn3=(ImageView)findViewById(R.id.btn3);
        btn4=(ImageView)findViewById(R.id.btn4);
        btn5=(ImageView)findViewById(R.id.btn5);
        btn6=(ImageView)findViewById(R.id.btn6);
        btn7=(ImageView)findViewById(R.id.btn7);
        btn1.setOnTouchListener(b1);
        btn2.setOnTouchListener(b2);
        btn3.setOnTouchListener(b3);
        btn4.setOnTouchListener(b4);
        btn5.setOnTouchListener(b5);
        btn6.setOnTouchListener(b6);
        btn7.setOnTouchListener(b7);


    }
    private ImageView.OnTouchListener b5=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//課程

                case MotionEvent.ACTION_DOWN:
                    btn5.setImageResource(R.drawable.aki_child1h);

                    break;
                case MotionEvent.ACTION_UP:
                    btn5.setImageResource(R.drawable.aki_child1);
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("ANS","A");
                    intent.putExtras(bundle);
                       intent.setClass(Listenmenu.this,Listenchild.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener b6=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//課程

                case MotionEvent.ACTION_DOWN:
                    btn6.setImageResource(R.drawable.aki_child2h);

                    break;
                case MotionEvent.ACTION_UP:
                    btn6.setImageResource(R.drawable.aki_child2);
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("ANS","B");
                    intent.putExtras(bundle);
                    intent.setClass(Listenmenu.this,Listenchild.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener b7=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//課程

                case MotionEvent.ACTION_DOWN:
                    btn7.setImageResource(R.drawable.aki_child3h);

                    break;
                case MotionEvent.ACTION_UP:
                    btn7.setImageResource(R.drawable.aki_child3);
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("ANS","C");
                    intent.putExtras(bundle);
                    intent.setClass(Listenmenu.this,Listenchild.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
    private ImageView.OnTouchListener b1=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){
            switch (event.getAction()){//課程

                case MotionEvent.ACTION_DOWN:
                    btn1.setImageResource(R.drawable.aki_primary1h);

                    break;
                case MotionEvent.ACTION_UP:
                    btn1.setImageResource(R.drawable.aki_primary1);
                    Intent intent=new Intent();
                    intent.setClass(Listenmenu.this, Listening.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("ANS","A");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b2=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//匯率
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn2.setImageResource(R.drawable.aki_primary2h);

                    break;
                case MotionEvent.ACTION_UP:
                    btn2.setImageResource(R.drawable.aki_primary2);
                    Intent intent=new Intent();
                    intent.setClass(Listenmenu.this,Listening.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("ANS","B");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    //  mytoast("維護中");
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b3=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//新聞
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    btn3.setImageResource(R.drawable.aki_advance1h);

                    break;
                case MotionEvent.ACTION_UP:
                    btn3.setImageResource(R.drawable.aki_advance1);
                    Intent intent=new Intent();
                    // mytoast("請稍後");
                    intent.setClass(Listenmenu.this,Listening.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("ANS","C");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    private ImageView.OnTouchListener b4=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){//JLPT倒數
            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    // btn4.setImageResource(R.drawable.aki_shop);
                    btn4.setImageResource(R.drawable.aki_advance2h);

                    break;
                case MotionEvent.ACTION_UP:
                    btn4.setImageResource(R.drawable.aki_advance2);
                    Intent intent=new Intent();
                    intent.setClass(Listenmenu.this,Listening.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("ANS","D");
                    intent.putExtras(bundle);
                    startActivity(intent);

                    break;
            }
            return true;
        }
    };

}
