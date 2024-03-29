package com.nihon.aki2.mydb;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class dbinapply {
    public static String executeQuery(String account,String passwd,String name,String email) {
        String result = ""; 
       
        try {   
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://kei-sei.com/cram/inapply.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
           // params.add(new BasicNameValuePair("emp_id", emp_id));
            params.add(new BasicNameValuePair("account",account));
            params.add(new BasicNameValuePair("passwd",passwd));
            params.add(new BasicNameValuePair("name",name));
            params.add(new BasicNameValuePair("email",email));
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //view_account.setText(httpResponse.getStatusLine().toString());
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }
        
        return result;
    }
}
