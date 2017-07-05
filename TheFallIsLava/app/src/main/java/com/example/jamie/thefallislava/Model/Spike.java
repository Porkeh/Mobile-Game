package com.example.jamie.thefallislava.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.example.jamie.thefallislava.R;

/**
 * Created by Jamie on 25-Apr-16.
 */
public class Spike extends Obstacle {




    public Spike(float x, float y, Context context, Vector velocity, Point screenSize, Boolean rotate)
    {
        super(x,y);


        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike);
        scaledSprite = Bitmap.createScaledBitmap(sprite,screenSize.x/10,screenSize.y/10,false);
        this.velocity = velocity;
        width = scaledSprite.getWidth();
        height = scaledSprite.getHeight();

        rotated = rotate;
        if(rotated) {
            position.SetX(position.GetX() - width);
        }

        bounds = new BoundingBox(position.GetX()+width/2,position.GetY()+height/2,width/2,height/2);


    }

}
