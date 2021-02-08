package com.jamilton.gestiondeltiempo.view.iu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.jamilton.gestiondeltiempo.R;
import com.jamilton.gestiondeltiempo.view.iu.activities.MainActivity;

public class AnimacionInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animacion_inicio);



        LottieAnimationView animationView = findViewById(R.id.animacionLottie);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);
        animationView.setAnimation(animation);

        Intent intent = new Intent(this, MainActivity.class);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}