package com.example.jamie.thefallislava.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.jamie.thefallislava.R;

/**
 * Created by Jamie on 26-Apr-16.
 */
public class Rock extends Obstacle {


    public Rock(float x, float y, Context context, Point screenSize, Vector screenVel)
    {
        super(x,y);


        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock);
        scaledSprite = Bitmap.createScaledBitmap(sprite,screenSize.x/10,screenSize.y/10,false);
        this.velocity.SetBoth(0,8);
        this.velocity.add(screenVel);
        width = scaledSprite.getWidth();
        height = scaledSprite.getHeight();


        bounds = new BoundingBox(position.GetX()+width/2,position.GetY()+height/2,width/2,height/2);


    }

}
