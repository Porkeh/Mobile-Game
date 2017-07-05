package com.example.jamie.thefallislava.Controller;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.jamie.thefallislava.R;
import com.example.jamie.thefallislava.View.GameSurfaceView;

public class GameActivity extends AppCompatActivity {

    private GameSurfaceView GSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int level = getIntent().getExtras().getInt("level");
        String levelData = new String();
        int musicID = 0;
        switch(level)
        {
            case 1:
                levelData = "levels/level_one.txt";
                musicID = R.raw.music;
                break;
            case 2:
                levelData = "levels/level_two.txt";
                musicID = R.raw.music;
                break;
        }




        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                this.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            }
            catch (NoSuchMethodError e)
            {
                this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            }
        }
        else {

            getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }

        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        Point screenSize = new Point(widthPixels,heightPixels);
        GSV = new GameSurfaceView(this, screenSize, levelData,musicID);
        GSV.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(GSV);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSV.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSV.pause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();

        switch(eventAction)
        {
            case MotionEvent.ACTION_DOWN:

                if(GSV.GameOver() == true)
                {
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    this.finish();
                }
                else {
                    GSV.handleEvent();
                }
        }

        return super.onTouchEvent(event);


    }


}
