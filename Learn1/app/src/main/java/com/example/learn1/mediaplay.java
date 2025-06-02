package com.example.learn1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class mediaplay extends AppCompatActivity {

   // private LinearLayout df=findViewById(R.id.Linearlayout1);
    private static final int REQUEST_READ_STORAGE = 1;
    private static final int REQUEST_PICK_VIDEO = 2;

    private VideoView videoView;
    private Button btnSelectVideo;
    private Button btnPlayPause;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio);

        videoView = findViewById(R.id.videoView);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);
        btnPlayPause = findViewById(R.id.btnPlayPause);

        videoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(this, "播放错误", Toast.LENGTH_SHORT).show();
            return true; // 返回true表示已处理错误
        });

        videoView.setOnPreparedListener(mp -> {
            // 视频准备完成时回调
            videoView.start();
        });

        // 设置媒体控制器
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        btnSelectVideo.setOnClickListener(v -> pickVideo());
        btnPlayPause.setOnClickListener(v -> togglePlayPause());
    }


    private void pickVideo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent,"选择视频"), REQUEST_PICK_VIDEO);
    }


    private void togglePlayPause() {
        if (videoView.isPlaying()) {
            videoView.pause();
            btnPlayPause.setText("播放");
        } else {
            videoView.start();
            btnPlayPause.setText("暂停");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!videoView.isPlaying() && videoView.getCurrentPosition() > 0) {
            videoView.start();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_VIDEO && resultCode == RESULT_OK && data != null) {
            Uri videoUri = data.getData();
            // 使用这个URI播放视频
            videoView.setVideoURI(videoUri);
            videoView.start();
           // df.setScaleY(videoView.getHeight());
            btnPlayPause.setText("暂停");
           }
        }
    }

