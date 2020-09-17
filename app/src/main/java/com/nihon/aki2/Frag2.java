package com.nihon.aki2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nihon.aki2.mydb.dbstudy;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag2 extends Fragment {
    Button bt1;
    TextView title,countdown,textView15,mydate,condition;
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
        title=(TextView)view.findViewById(R.id.title);
        countdown=(TextView)view.findViewById(R.id.countdown);
        textView15=(TextView)view.findViewById(R.id.textView15);
        mydate=(TextView)view.findViewById(R.id.mydate);
        condition=(TextView)view.findViewById(R.id.condition);
        dbsel();
        return view;
       // return inflater.inflate(R.layout.frag2_layout, container, false);
    }
    public void dbsel() {
        String result = dbstudy.executeQuery("1");

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
    private Button.OnClickListener btlink=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse("http://akkyschool.com/images/study_abroad/file/school1_introduction.pdf"));
            //http://akkyschool.com/images/study_abroad/file/school1_introduction
            //school1_introduction
            startActivity(intent);
        }
    };
}
