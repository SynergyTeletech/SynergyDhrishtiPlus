package com.synergy.synergydhrishtiplus;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
public class SplashActivity extends AppCompatActivity {
    View bg_anim_view;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
        setContentView(R.layout.activity_splash);
        init();
        setAnimationToBackScreen();
    }

    private void init() {
        bg_anim_view = findViewById(R.id.bg_anim_view);
    }
    private void setAnimationToBackScreen() {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(0);
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.zoom_out_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shape.setCornerRadius(bg_anim_view.getWidth() / 2f);
                    }
                }, 100);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        bg_anim_view.setBackground(shape);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bg_anim_view.startAnimation(animation);
            }
        }, 500);
    }
}