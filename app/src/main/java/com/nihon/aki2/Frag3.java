package com.nihon.aki2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.nihon.aki2.control.VideoAdapter;
import com.nihon.aki2.control.YouTubeVideos;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Frag3 extends Fragment {
    Button bt1,bt2;
    WebView videoWebView;
    RecyclerView recyclerView;
    private int secondLeft = 7;
    Timer timer = new Timer();
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.frag3_layout,null);
        bt1=(Button)view.findViewById(R.id.bt1);
        bt1.setOnClickListener(btlink);
        bt2=(Button)view.findViewById(R.id.bt2);
        bt2.setOnClickListener(btlink2);


        videoWebView=(WebView)view.findViewById(R.id.videoWebView);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.GONE);
        videoWebView.setVisibility(View.GONE);
         begin();
        return view;
       // return inflater.inflate(R.layout.frag2_layout, container, false);
    }

    public void begin() {
        timer.schedule(task, 1000, 1000) ;       }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    secondLeft--;

                    if (secondLeft==0) {
                        play();

                    }
                }
            });
        }
    };
    public void play(){

        videoWebView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        videoWebView.bringToFront();
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://akkyschool.com/images/study_abroad/study_abroad4.mp4\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);//https://www.youtube.com/watch?v=xmkqU_M21lk&feature=youtu.be

        recyclerView.setAdapter(videoAdapter);

         /* */
    }
    private Button.OnClickListener btlink=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse("http://akkyschool.com/images/study_abroad/file/school4_1_introduction.pdf"));
            startActivity(intent);
        }
    };
    private Button.OnClickListener btlink2=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse("http://akkyschool.com/images/study_abroad/file/school4_2_introduction.pdf"));
            startActivity(intent);
        }
    };

}
