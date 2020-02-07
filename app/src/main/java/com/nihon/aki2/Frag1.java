package com.nihon.aki2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class Frag1 extends Fragment {
    Button bt1,bt2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.frag1_layout, null);

        bt1= (Button)view.findViewById(R.id.bt1);
        bt2=(Button)view.findViewById(R.id.bt2);
        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btlink(v);}
        });
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btlink2(v);}
        });
        return view;

      //  return inflater.inflate(R.layout.frag1_layout, container, false);
    }
    public void btlink(View view)
    {
        Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://akkyschool.com/228.pdf"));
        startActivity(intent);
    }
    public void btlink2(View view)
    {
        Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://akkyschool.com/2282.pdf"));
        startActivity(intent);
    }
}
