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
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class mediaplay extends AppCompatActivity {

    private static final int REQUEST_READ_STORAGE = 1;
    private static final int REQUEST_PICK_VIDEO = 2;

    private VideoView videoView;
    private Button btnSelectVideo;
    private Button btnPlayPause;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);
        btnPlayPause = findViewById(R.id.btnPlayPause);

        // 设置媒体控制器
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        btnSelectVideo.setOnClickListener(v -> checkPermissionAndPickVideo());
        btnPlayPause.setOnClickListener(v -> togglePlayPause());
    }

    private void checkPermissionAndPickVideo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE);
        } else {
            pickVideo();
        }
    }

    private void pickVideo() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, REQUEST_PICK_VIDEO);
       // Environment s=new Environment();
//        File sdCardVideo = new File(Environment.getExternalStorageDirectory(), "下载/1.mp4");
//        if (sdCardVideo.exists()) {
//            videoView.setVideoPath(sdCardVideo.getAbsolutePath());
//            videoView.start();
//        }


            String[] projection = {
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.SIZE
            };

            String selection = MediaStore.Video.Media.RELATIVE_PATH + "=?";
            String[] selectionArgs = new String[]{};
            String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";

            try (Cursor cursor = getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            )) {
                if (cursor != null && cursor.moveToFirst()) {
                    int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                    int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                    int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                    int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

                    do {
                        long id = cursor.getLong(idColumn);
                        String name = cursor.getString(nameColumn);
                        int duration = cursor.getInt(durationColumn);
                        int size = cursor.getInt(sizeColumn);

                        Uri contentUri = ContentUris.withAppendedId(
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

                        // 处理视频文件
                        Log.d("Video", "Name: " + name + ", URI: " + contentUri);
                        videoView.setVideoURI(contentUri);
                        videoView.start();

                    } while (cursor.moveToNext());
                }
            }
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
            Uri selectedVideoUri = data.getData();
            videoView.setVideoURI(selectedVideoUri);
            videoView.start();
            btnPlayPause.setText("暂停");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickVideo();
            } else {
                Toast.makeText(this, "需要存储权限才能选择视频", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
