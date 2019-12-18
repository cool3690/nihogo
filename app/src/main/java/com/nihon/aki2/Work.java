package com.nihon.aki2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Work extends AppCompatActivity {
    ImageView sell,fin,it,manf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sell=(ImageView)findViewById(R.id.sell);
        fin=(ImageView)findViewById(R.id.fin);
        it=(ImageView)findViewById(R.id.it);
        manf=(ImageView)findViewById(R.id.manf);

        sell.setOnClickListener(btn);
        fin.setOnClickListener(btn);
        it.setOnClickListener(btn);
        manf.setOnClickListener(btn);


    }

    private Button.OnClickListener btn=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(Work.this,Jobshow.class);
            Bundle bundle=new Bundle();
           switch (v.getId()){

               case R.id.sell:
                bundle.putString("PASS","S");
                   break;
               case R.id.fin:
                   bundle.putString("PASS","F");
                   break;
               case R.id.it:
                   bundle.putString("PASS","C");
                   break;
               case R.id.manf:
                   bundle.putString("PASS","M");
                   break;

            }
            intent.putExtras(bundle);
           startActivity(intent);
        }
    };
}
