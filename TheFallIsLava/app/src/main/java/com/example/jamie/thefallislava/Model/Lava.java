package com.example.jamie.thefallislava.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.example.jamie.thefallislava.R;

/**
 * Created by Jamie on 25-Apr-16.
 */
public class Lava {

    Bitmap sprite;
    Bitmap scaledSprite;
    Vector position;
    Vector velocity;
    Vector startPos;

    public Lava(){}

    public Lava(int x, int  y, Point screenSize, Context context)
    {
        position = new Vector(x,y);
        velocity = new Vector(5,2);
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.lava);
        scaledSprite = Bitmap.createScaledBitmap(sprite,screenSize.x+100,screenSize.y/10,false);
        position.SetX(position.GetX()-50);
        position.SetY(position.GetY()-scaledSprite.getHeight());
        startPos = new Vector(position.GetX(),position.GetY());
    }

    public void update(Point screenSize)
    {
        position.add(velocity);
        float check = startPos.GetY()+20;

        if( position.GetY() >= check)
        {

            velocity.SetY(-2);
        }

        if(position.GetY() <= startPos.GetY())
        {
                velocity.SetY(2);
        }
        if(position.GetX() < -50)
        {

            velocity.SetX(5);
        }
        if(position.GetX() > startPos.GetX()+50)
        {

            velocity.SetX(-5);
        }
    }


    public void draw(Canvas canvas)
    {

        canvas.drawBitmap(scaledSprite,position.GetX(),position.GetY(),null);


    }

}
