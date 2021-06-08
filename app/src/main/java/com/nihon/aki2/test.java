package com.nihon.aki2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.nihon.aki2.mydb.dbcourse;
import com.nihon.aki2.mydb.dbsearchall;
import com.nihon.aki2.mydb.dbsearchn5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class test extends AppCompatActivity {
    AutoCompleteTextView autotxt;
    String[] str = {"Xavier","Xaee","Xaww","Xina","Nina","GGININ","Ming","Joe"};//定義資料內容
    List<String> responseList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
       // String result = dbsearchall.executeQuery("all.json");
        mytoast(loadJSONFromAsset()+"");
        autotxt = (AutoCompleteTextView)findViewById(R.id.autotxt);
      
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray countries=obj.getJSONArray("romaji");

            ///////////////////////////
            ArrayAdapter<JSONArray> adapter
                    = new ArrayAdapter<JSONArray>(this,
                    android.R.layout.simple_dropdown_item_1line,
                    (List<JSONArray>) countries); //宣告 Adapter
            autotxt.setAdapter(adapter); //設定 Adapter 給 autotxt
            autotxt.setThreshold(1); //設定輸入幾個字後開始比對
            //  autotxt.setCompletionHint("提示訊息"); //設定提示訊息
            // autotxt.setDropDownBackgroundResource(R.drawable.yellow);//設定背景圖片
            //autotxt.setDropDownBackgroundDrawable(getResource().getDrawable(R.drawable.yellow));//設定背景圖片
            autotxt.setDropDownWidth(800); //設定寬度
            autotxt.setDropDownHeight(400); //設定高度
        } catch (JSONException e) {

        }


        autotxt.setOnItemClickListener(AutoCompleteTextViewOnItemClickListener);
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("all.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {

            return null;
        }

        return json;

    }
    //定義 Item 的 OnClick 事件監聽器
    private AdapterView.OnItemClickListener AutoCompleteTextViewOnItemClickListener
            = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            int Threshold = autotxt.getThreshold(); //取得比對字元數
            int DropDownWidth = autotxt.getDropDownWidth(); //取得寬度
            int DropDownHeight = autotxt.getDropDownHeight(); //取得高度


        }
    };
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}