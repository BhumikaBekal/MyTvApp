package com.example.mytv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  static  int SPLASH_SCREEN=5000;
        TextView splashtext;
        ImageView splashicon;
        Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        splashtext=findViewById(R.id.splashtext);
        splashicon=findViewById(R.id.splashicon);
        splashtext.setAnimation(animation);
        splashicon.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,LoginPage.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN);
    }
}