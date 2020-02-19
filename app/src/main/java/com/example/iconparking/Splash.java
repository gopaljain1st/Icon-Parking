package com.example.iconparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity
{
    ImageView iv1,iv2;
    Animation fromBottom,fromTop,blink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        iv1=findViewById(R.id.iv1);
        iv2=findViewById(R.id.iv2);
        fromTop=AnimationUtils.loadAnimation(this,R.anim.fromtop);
        iv1.setAnimation(fromTop);
        blink=AnimationUtils.loadAnimation(this,R.anim.blink);
        iv2.setAnimation(blink);

        //getSupportActionBar().hide();
        new MyThread().start();
    }
    class MyThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            try {
                Thread.sleep(2000);
                Intent in=new Intent(Splash.this,SignIn.class);
                startActivity(in);
                finish();
            }
            catch (Exception e)
            {

            }
        }
    }
}
