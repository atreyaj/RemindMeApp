package com.atreya.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import static com.atreya.myapp.SharedPref.REMIND_ME_SHARED_PREF;

public class SplashScreen extends AppCompatActivity {
//
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
SharedPreferences sharedPreferences;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences(REMIND_ME_SHARED_PREF, 0);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                int login=sharedPreferences.getInt("isLogin",0);
                  if (login==1) {
                      Intent mainIntent = new Intent(SplashScreen.this, HomeActivity.class);
                      SplashScreen.this.startActivity(mainIntent);
                      overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                      SplashScreen.this.finish();
                  }else
                  {
                      Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                      SplashScreen.this.startActivity(mainIntent);
                      overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                      SplashScreen.this.finish();
                  }
            }

        }, SPLASH_DISPLAY_LENGTH);
    }

}































// extends Activity {
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        Window window = getWindow();
//        window.setFormat(PixelFormat.RGBA_8888);
//    }
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//        StartAnimations();
//        Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
//        SplashScreen.this.startActivity(mainIntent);
//        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
//        SplashScreen.this.finish();
//    }
//    private void StartAnimations() {
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        anim.reset();
//        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
//        l.clearAnimation();
//        l.startAnimation(anim);
//
//        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
//        anim.reset();
//        ImageView iv = (ImageView) findViewById(R.id.logo);
//        iv.clearAnimation();
//        iv.startAnimation(anim);
//
//
//    }
//
//}
//
//
//
//
//
//
//
//

//
//
//
//
//
//
//
//}