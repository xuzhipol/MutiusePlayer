package com.example.learn1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private  String g="";
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 创建圆角矩形
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(30); // 圆角半径（像素）
        drawable.setColor(Color.BLUE);
        drawable.setStroke(5, 0xFF00FFFF); // 边框

// 应用到按钮
        Button button = findViewById(R.id.button2);
        //button.setBackground(drawable);

// 动态改变形状
        button.setOnClickListener(v -> {
            //drawable.setShape(GradientDrawable.OVAL); // 变为圆形
            //drawable.setColor(Color.GREEN);
            //button.invalidate(); // 刷新视图
            Intent m=new Intent(MainActivity.this,se2.class);
            startActivity(m);
        });
        FloatingActionButton c=findViewById(R.id.floatingActionButton);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "FAB Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText l=findViewById(R.id.editTextText);

        l.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 文本改变前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本变化时
                g=l.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文本改变后
            }
        });

        Button k=findViewById(R.id.button5);
        k.setOnClickListener(view->{
            Intent m=new Intent(this,vedioplay.class);
            m.putExtra("网页URI",g);
            startActivity(m);
        });

        Button e=findViewById(R.id.button3);
        e.setOnClickListener(view->{
            Intent df=new Intent(this,mediaplay.class);
            startActivity(df);
        });
    }
}