package com.nihon.aki2;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageService {

    // 獲取網路圖片的資料
    public static byte[] getImage(String picturepath) throws Exception {
        URL url = new URL(picturepath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 基於http協議的連線物件
        conn.setConnectTimeout(30);// 10秒；
        conn.setRequestMethod("GET");// 大寫
        if (conn.getResponseCode() == 200) {
            InputStream ins = conn.getInputStream();
            return StreamTool.read(ins);
        }
        return null;
    }
}