package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.journeyapplicationfyp.R;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDisplay();

        Intent i = new Intent(SplashActivity.this, Main_HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void initDisplay() {

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(
                    ContextCompat.getColor(
                            this,
                            R.color.system
                    )
            );

            window.setNavigationBarColor(
                    ContextCompat.getColor(
                            this,
                            R.color.system
                    ));
        }

    }
}
