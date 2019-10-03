package com.example.aki2;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

    public static byte[] read(InputStream ins) throws Exception {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = ins.read(buffer)) > -1) {
            outstream.write(buffer, 0, length);
        }
        outstream.close();
        return outstream.toByteArray();
    }
}