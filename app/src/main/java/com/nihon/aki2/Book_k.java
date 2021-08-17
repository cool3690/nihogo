package com.nihon.aki2;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Book_k extends AppCompatActivity {
    SurfaceView surfaceView;
    TextView show;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    public MediaPlayer mediaplayer;
    ImageView play,replay,replay2;
    SeekBar sbar ;
    int count=0;
    boolean tf=true,fin=false;
    int totalTime=0;
    TextView remainingTimeLabel,totaltime;
    Context context;
    Dialog dia;
    String[] list2= {"關於" };
    String url="https://kei-sei.com/images/listening/primary1/L1/T1.mp3" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_k);
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

        Toolbar toolbar = findViewById(R.id.toolbar);
         //getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(toolbar);
        ImageView menulist = (ImageView) findViewById(R.id.menulist);
        menulist.setOnClickListener(menulistbtn);
        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        show=(TextView)findViewById(R.id.show);
        show.setOnClickListener(showclick);
        mediaplayer=new MediaPlayer();
        play = (ImageView) findViewById(R.id.play);
        replay = (ImageView) findViewById(R.id.replay);
        replay2 = (ImageView) findViewById(R.id.replay2);
        remainingTimeLabel= (TextView) findViewById(R.id.remainingTimeLabel);
        totaltime= (TextView) findViewById(R.id.totaltime);
        play.setOnClickListener(playbyn);
        sbar = (SeekBar) findViewById(R.id.sbar);
        getPermission();
        replay.setOnClickListener(fastbtn);
        replay2.setOnClickListener(fastbtn);
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
                if(ActivityCompat.checkSelfPermission(Book_k.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
                    new AlertDialog.Builder(Book_k.this)
                            .setCancelable(false)
                            .setTitle("需要相機權限")
                            .setMessage("有相機權限才有辦法掃描")
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(Book_k.this,new String[]{Manifest.permission.CAMERA},1);
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

                            ////////////////////////////
                            try {
                                if(!TextUtils.isEmpty(show.getText().toString())){
                                    if(show.getText().toString().equals(qrCodes.valueAt(0).displayValue)){
                                        //mytoast( qrCodes.size()+"");

                                        //show.setText("");
                                    }
                                    else{
                                        show.setText(qrCodes.valueAt(0).displayValue.replace("https://kei-sei.com/images/listening/",""));
                                        /*
                                        String word[]=qrCodes.valueAt(0).displayValue.split("_");
                                        Intent intent=new Intent();//com.nihon.aki2.Listening
                                        intent.setClass(getApplicationContext(),Class.forName(word[0]));
                                        Bundle bundle=new Bundle();
                                        bundle.putString("ANS", word[1]);
                                        bundle.putString("L", word[2]+"");
                                        bundle.putString("T", word[3]+"");
                                        intent.putExtras(bundle);
                                        startActivity(intent);

                                         */
                                        totalTime=0;
                                        mediaplayer.reset();
                                        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                                            mediaplayer.setDataSource(url);
                                            mediaplayer.prepare();

                                            totalTime=mediaplayer.getDuration();
                                            sbar.setMax(totalTime);
                                            mediaplayer.start();



                                    }
                                }
                                else{
                                    show.setText(qrCodes.valueAt(0).displayValue.replace("https://kei-sei.com/images/listening/",""));
                                       /*
                                    String word[]=qrCodes.valueAt(0).displayValue.split("_");
                                    Intent intent=new Intent();
                                    intent.setClass(getApplicationContext(),Class.forName(word[0]));
                                    Bundle bundle=new Bundle();
                                    bundle.putString("ANS", word[1]);
                                    bundle.putString("L", word[2]+"");
                                    bundle.putString("T", word[3]+"");
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                     */
                                    totalTime=0;
                                    mediaplayer.reset();
                                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                                        mediaplayer.setDataSource(url);
                                        mediaplayer.prepare();

                                        totalTime=mediaplayer.getDuration();
                                        sbar.setMax(totalTime);
                                        mediaplayer.start();

                                }
                                /////////////

                            } catch (IOException e) { }
                        }


                    });
                    // show.postDelayed(Runnable, 1000);

                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaplayer != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mediaplayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();

        BottomNavigationView nav_view=(BottomNavigationView)findViewById(R.id.nav_view);
        nav_view.setSelectedItemId(R.id.btn8);
        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.btn5:
                        startActivity(new Intent(getApplicationContext(),Menushow.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn6:
                        startActivity(new Intent(getApplicationContext(),Tool_k.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn7:
                        startActivity(new Intent(getApplicationContext(),Info_k.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.btn8:
                        startActivity(new Intent(getApplicationContext(),Book_k.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private ImageView.OnClickListener fastbtn=new ImageView.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.replay:
                    pauseplay();

                    int t=0;
                    if(mediaplayer.getCurrentPosition()-15000 >0){
                        t=(mediaplayer.getCurrentPosition()-15000);

                        mediaplayer.seekTo(t);
                    }
                    else{
                        mediaplayer.seekTo(0);
                    }

                    mediaplayer.start();

                    break;
                case R.id.replay2:
                    pauseplay();


                    if(mediaplayer.getCurrentPosition()+15000 <totalTime){
                        t=(mediaplayer.getCurrentPosition()+15000);

                        mediaplayer.seekTo(t);
                    }
                    else{
                        mediaplayer.seekTo(totalTime);
                    }

                    mediaplayer.start();
                    break;
                default:
                    break;
            }
        }
    };
    private ImageView.OnClickListener menulistbtn=new ImageView.OnClickListener(){

        @Override
        public void onClick(View view) {
            AlertDialog.Builder dialog_list = new AlertDialog.Builder(Book_k.this);
            // dialog_list.setTitle(" ");
            dialog_list.setItems(list2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {
                        context = Book_k.this;
                        dia = new Dialog(context, R.style.rightcopystyle);
                        dia.setContentView(R.layout.copyright);
                        Button btok=(Button)dia.findViewById(R.id.btok);
                        dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
                        Window w = dia.getWindow();
                        WindowManager.LayoutParams lp = w.getAttributes();
                        lp.x = 0; // 新位置X坐標
                        lp.width =950; // 寬度
                        dia.show();
                        dia.onWindowAttributesChanged(lp);
                        btok.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dia.dismiss();
                                    }
                                }
                        );
                    }
                }
            });

            dialog_list.show();
        }
    };

    private TextView.OnClickListener showclick=new TextView.OnClickListener(){
        @Override
        public void onClick(View view) {
            if(!TextUtils.isEmpty(show.getText().toString())){

                if(show.getText().toString().contains("http://") ||show.getText().toString().contains("https://")
                        ||show.getText().toString().contains("www.")  ){

                    try {  Intent intent=new Intent();
                        intent.setClass(getApplicationContext(),Class.forName("com.nihon.aki2.Menushow"));
                        startActivity(intent);
                    } catch (ClassNotFoundException e) { }




                }

            }
        }
    };
    private ImageView.OnClickListener playbyn = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(tf==true){
                /*  */
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info=connManager.getActiveNetworkInfo();
                if (info == null || !info.isConnected())
                {
                    mytoast("網路未開啟或網路不穩，請到訊號良好的地方!");
                }
                else {
                    tf=false;
                    play.setImageResource(R.drawable.playpurple);
                }
            }

            else{
                tf=true;
                play.setImageResource(R.drawable.pausepurple);
            }
            pauseplay();

        }
    };

    public void onBackPressed() {


        super.onBackPressed();
    }
    public void getPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);

        }
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            sbar.setProgress(currentPosition);
            String remainingTime = createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("" + remainingTime);

        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }
    private void mytoast(String str)
    {
        Toast toast=Toast.makeText(this, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void pauseplay(){
        if(mediaplayer.isPlaying()){
            mediaplayer.pause();
        }
        else{
            mediaplayer.start();
        }
    }
    private void playSong(int song) {


        String tmp=url;

        //tmp+=lessons[L]+"/"+filename[T];

        try {//+filename[0]

            mediaplayer.reset();
            mediaplayer.setDataSource(tmp);
            mediaplayer.prepare();
            totalTime=mediaplayer.getDuration();
            sbar.setMax(totalTime);
            mediaplayer.start();
        } catch (IllegalStateException e) { }
        catch (IOException e) { }
    }
    /* */
    @Override
    public void onPause(){
        super.onPause();
        mediaplayer.pause();
    }
}