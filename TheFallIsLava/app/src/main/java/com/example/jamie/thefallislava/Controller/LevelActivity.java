package com.example.jamie.thefallislava.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jamie.thefallislava.R;

public class LevelActivity extends AppCompatActivity {

    private int GAME_OVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
    }


    public void levelOne(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level",1);
        startActivityForResult(intent, GAME_OVER);
    }

    public void levelTwo(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level",2);
        startActivityForResult(intent, GAME_OVER);
    }
}
