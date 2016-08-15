package com.base.basket.basketbase1.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.base.basket.basketbase1.R;

public class TextFlashes {
    Context context;
    private TextView texto = null;
    private Animation fadeIn = null;
    private Animation fadeOut = null;

    private LocalFadeInAnimationListener myFadeInAnimationListener = new LocalFadeInAnimationListener();
    private LocalFadeOutAnimationListener myFadeOutAnimationListener = new LocalFadeOutAnimationListener();

    public TextFlashes(Context context, TextView text){
        this.context = context;
        this.texto = (TextView)text;
        runAnimations();
    }

    private void launchOutAnimation() {
        texto.startAnimation(fadeOut);
    }

    private void launchInAnimation() {
        texto.startAnimation(fadeIn);
    }

    private void runAnimations() {
        fadeIn = AnimationUtils.loadAnimation(this.context, R.anim.fadein);
        fadeIn.setAnimationListener(myFadeInAnimationListener);
        fadeOut = AnimationUtils.loadAnimation(this.context, R.anim.fadeout);
        fadeOut.setAnimationListener(myFadeOutAnimationListener);
        launchInAnimation();
    }

    private Runnable mLaunchFadeOutAnimation = new Runnable() {
        public void run() {
            launchOutAnimation();
        }
    };

    private Runnable mLaunchFadeInAnimation = new Runnable() {
        public void run() {
            launchInAnimation();
        }
    };

    private class LocalFadeInAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            texto.post(mLaunchFadeOutAnimation);
        }
        public void onAnimationRepeat(Animation animation){
        }
        public void onAnimationStart(Animation animation) {
        }
    };

    private class LocalFadeOutAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            texto.post(mLaunchFadeInAnimation);
        }
        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }
    };
}
