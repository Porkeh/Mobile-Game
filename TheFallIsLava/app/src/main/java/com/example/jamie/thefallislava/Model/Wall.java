package com.example.jamie.thefallislava.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;

import com.example.jamie.thefallislava.R;

/**
 * Created by Jamie on 25-Apr-16.
 */
public class Wall {

    Vector position;
    Vector velocity;
    Bitmap sprite;
    Bitmap scaledSprite;
    Boolean rotated;

    public Wall(int x, int  y, Point screenSize, Context context, boolean rotate, Vector velocity)
    {
        position = new Vector(x,y);
        this.velocity = velocity;
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
        scaledSprite = Bitmap.createScaledBitmap(sprite,screenSize.x/5,screenSize.y,false);
        rotated = rotate;
        if(rotate)
        {
            int xScale = screenSize.x/5;
            position.SetX(position.GetX()-xScale);
        }
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
        if(rotated)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(180,scaledSprite.getWidth()/2,scaledSprite.getHeight()/2);
            matrix.postTranslate(position.GetX(),position.GetY());
            canvas.drawBitmap(scaledSprite,matrix,null);
        }
        else {
            canvas.drawBitmap(scaledSprite, position.GetX(), position.GetY(), null);
        }


    }

    public float GetWidth()
    {
        return scaledSprite.getWidth();
    }




}
