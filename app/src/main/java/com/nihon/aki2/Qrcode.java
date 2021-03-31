package com.nihon.aki2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Qrcode extends AppCompatActivity {
    Toolbar toolbar;
    ImageView toback;
    SurfaceView surfaceView;
    TextView show;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toback=(ImageView)findViewById(R.id.toback);
        toback.setOnClickListener(backbtn);
        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        show=(TextView)findViewById(R.id.show);
        getPermission();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(1000,1000).build();
        cameraSource = new CameraSource.Builder(this,barcodeDetector).setAutoFocusEnabled(true).build();
        //cameraSource.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){

                    return;
                }
                if(ActivityCompat.checkSelfPermission(Qrcode.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
                    new AlertDialog.Builder(Qrcode.this)
                            .setCancelable(false)
                            .setTitle("需要相機權限")
                            .setMessage("有相機權限才有辦法掃描")
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(Qrcode.this,new String[]{Manifest.permission.CAMERA},1);
                                }
                            })
                            .show();
                }
                getPermission();
                try{
                    cameraSource.start(surfaceHolder);
                }catch (IOException e){
                    // e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    show.post(new Runnable() {
                        @Override
                        public void run() {
                            show.setText(qrCodes.valueAt(0).displayValue);
                            if(show.getText().toString().contains("http://") ||show.getText().toString().contains("https://")
                               ||show.getText().toString().contains("www.")  ){
                                Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(show.getText().toString()));
                                startActivity(intent);
                            }

                        }
                    });
                }
            }
        });

    }

    private ImageView.OnClickListener backbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);

        }
    };

    public void onBackPressed() {


        super.onBackPressed();
    }
    public void getPermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);

        }
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}