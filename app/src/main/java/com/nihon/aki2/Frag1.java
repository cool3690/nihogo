package com.nihon.aki2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nihon.aki2.control.Team;
import com.nihon.aki2.control.TeamsAdapter;
import com.nihon.aki2.mydb.dbcourse;
import com.nihon.aki2.mydb.dbstudy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class Frag1 extends Fragment {
    Button bt1,bt2;
    TextView title,countdown,textView15,mydate,condition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag1_layout, null);

        bt1= (Button)view.findViewById(R.id.bt1);
      //  bt1.setText(account);
        bt2=(Button)view.findViewById(R.id.bt2);
        bt1.setOnClickListener(bt1x);
        title=(TextView)view.findViewById(R.id.title);
        countdown=(TextView)view.findViewById(R.id.countdown);
        textView15=(TextView)view.findViewById(R.id.textView15);
        mydate=(TextView)view.findViewById(R.id.mydate);
        condition=(TextView)view.findViewById(R.id.condition);
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btlink2(v);}
        });
        dbsel();
        return view;

      //  return inflater.inflate(R.layout.frag1_layout, container, false);
    }
    public void dbsel() {
        String result = dbstudy.executeQuery("0");

        try{
            JSONArray jsonArray = new JSONArray(result);

            int k=0;

            for(int i = 0; i < jsonArray.length(); i++)
            {	 JSONObject jsonData = jsonArray.getJSONObject(i);
                String project=jsonData.getString("project");

                String during=jsonData.getString("during");
                String fee=jsonData.getString("fee");
                String attend=jsonData.getString("attend");
                String mycondition=jsonData.getString("mycondition");
                 title.setText(project);
                 countdown.setText(during);
                 textView15.setText(fee);
                 mydate.setText(attend);
                 condition.setText(mycondition);
            }

        }

        catch(Exception e){}
    }
    private Button.OnClickListener bt1x=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            btlink(view);
        }
    };
    public void btlink(View view)
    {
        Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://kei-sei.com/images/study_abroad/file/school2_2introduction.pdf"));
        startActivity(intent);
    }
    public void btlink2(View view)
    {
        Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://kei-sei.com/images/study_abroad/file/school2_introduction.pdf"));
        startActivity(intent);
    }
}
