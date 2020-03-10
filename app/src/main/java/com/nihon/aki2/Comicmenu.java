package com.nihon.aki2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Comicmenu extends AppCompatActivity {
ImageView com1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comicmenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        com1=(ImageView) findViewById(R.id.com1);
        com1.setOnTouchListener(com1btn);
    }

    private ImageView.OnTouchListener com1btn=new ImageView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {//課程

                case MotionEvent.ACTION_DOWN:
                    com1.setImageResource(R.drawable.aki_primary1h);

                    break;
                case MotionEvent.ACTION_UP:
                    com1.setImageResource(R.drawable.aki_primary1);
                    Intent intent = new Intent();
                    intent.setClass(Comicmenu.this, Comic.class);
                    //Work.class
                    //  intent.setClass(Menushow.this,Myweb.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
}
