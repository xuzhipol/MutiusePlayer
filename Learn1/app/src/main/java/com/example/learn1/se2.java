package com.example.learn1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class se2 extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.se);
        Button a = findViewById(R.id.button);
        a.setOnClickListener(view -> {
                    Intent b = new Intent(se2.this, MainActivity.class);
                    startActivity(b);
                    //finish();
                   // return true;
                }
        );
        ImageView view=findViewById(R.id.imageView);
        view.setOnTouchListener(new View.OnTouchListener() {
            private float lastX, lastY;
            private int parentWidth, parentHeight;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取父容器尺寸
                if (parentWidth == 0) {
                    ViewGroup parent = (ViewGroup) v.getParent();
                    parentWidth = parent.getWidth();
                    parentHeight = parent.getHeight();
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getRawX() - lastX;
                        float dy = event.getRawY() - lastY;

                        float newX = v.getX() + dx;
                        float newY = v.getY() + dy;

                        // 边界检查
                        newX = Math.max(0, Math.min(newX, parentWidth - v.getWidth()));
                        newY = Math.max(0, Math.min(newY, parentHeight - v.getHeight()));

                        v.setX(newX);
                        v.setY(newY);

                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        break;
                }
                return true;
            }
        });
    }
}
