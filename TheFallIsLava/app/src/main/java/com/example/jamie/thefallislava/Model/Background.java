package com.example.jamie.thefallislava.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.jamie.thefallislava.R;

/**
 * Created by Jamie on 25-Apr-16.
 */
public class Background {


    Bitmap sprite;
    Bitmap scaledSprite;
    Vector position;
    Vector velocity;

    public Background(){}

    public Background(int x, int  y, Point screenSize, Context context)
    {
        position = new Vector(x,y);
        velocity = new Vector(0,1);
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        scaledSprite = Bitmap.createScaledBitmap(sprite,screenSize.x,screenSize.y,false);
    }

    public void update(Point screenSize)
    {
        position.add(velocity);
        if(position.GetY() > screenSize.y)
        {
            position.SetY(0-screenSize.y);
        }
    }


    public void draw(Canvas canvas)
    {

        canvas.drawBitmap(scaledSprite,position.GetX(),position.GetY(),null);


    }


}
