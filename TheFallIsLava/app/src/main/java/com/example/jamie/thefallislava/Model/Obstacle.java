package com.example.jamie.thefallislava.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.RemoteControlClient;

/**
 * Created by Jamie on 25-Apr-16.
 */
public class Obstacle {

    Vector position;
    Vector velocity;
    Vector acceleration;
    Paint p = new Paint();
    protected Bitmap sprite;
    protected Bitmap scaledSprite;
    float width;
    float height;
    boolean rotated = false;

    protected BoundingBox bounds;


    public Obstacle(){}
    public Obstacle(float x, float y)
    {
        position = new Vector(x,y);
        velocity = new Vector(0,5);


    }


    public void draw(Canvas c)
    {
       // c.drawRect(position.GetX(),position.GetY(),position.GetX()+30,position.GetY()+30, p);
        if(rotated)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(180,scaledSprite.getWidth()/2,scaledSprite.getHeight()/2);
            matrix.postTranslate(position.GetX(),position.GetY());
            c.drawBitmap(scaledSprite,matrix,null);
        }
        else {
            c.drawBitmap(scaledSprite, position.GetX(), position.GetY(), null);
        }
    }

    public void update()
    {
        move();
        bounds.update(position);
    }

    public void move()
    {

        position.add(velocity);

    }

    public BoundingBox GetBounds()
    {
        return  bounds;
    }

    public Vector GetPosition()
    {
        return position;
    }









}
