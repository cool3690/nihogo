package com.nihon.aki2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag2 extends Fragment {
    Button bt1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.frag2_layout,null);
        bt1=(Button)view.findViewById(R.id.bt1);
        bt1.setOnClickListener(btlink);
        return view;
       // return inflater.inflate(R.layout.frag2_layout, container, false);
    }
    private Button.OnClickListener btlink=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse("http://akkyschool.com/301.pdf"));
            startActivity(intent);
        }
    };
}
