package com.example.jamie.thefallislava.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.jamie.thefallislava.R;

/**
 * Created by Jamie on 01-Apr-16.
 */
public class Player {

    private Vector position;
    private Vector velocity;
    private Vector startPos;
    private int width ;
    private int height ;
    private int acceleration = 10;
    private boolean jump = false;
    private boolean jumpRight = true;
    private boolean isAlive = true;
    private  boolean reallyDead = false;
    Paint p = new Paint();

    private Bitmap sprite;
    private Bitmap scaledSprite;
    private BoundingBox bounds;

    public Player(float x, float y, Context context,Point screenSize)
    {
        position = new Vector(x,y);
        velocity = new Vector(0,0);
        p.setARGB(255,255,0,0);
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.mellow);


        int scaleX = screenSize.x/10;
        scaledSprite = Bitmap.createScaledBitmap(sprite,scaleX,scaleX,false);
        width = scaledSprite.getWidth();
        height = scaledSprite.getHeight();
        bounds = new BoundingBox(position.GetX()+width/2,position.GetY()+height/2,width/2,height/2);
        startPos = new Vector( position.GetX(),position.GetY());
        //scaledSprite = Bitmap.createScaledBitmap(sprite,screenSize.x,screenSize.y,false);
    }

    public void draw(Canvas c)
    {
        //c.drawRect(position.GetX(),position.GetY(),position.GetX()+width,position.GetY()+height,p);
        c.drawBitmap(scaledSprite,position.GetX(),position.GetY(), null);
    }

    public void update(Point screenSize)
    {
        if(isAlive) {
            if (jump) {
                velocity.SetY(velocity.GetY() + screenSize.y / 60);
            }

            move();

            float xRight = screenSize.x - startPos.GetX() - width;
            float xLeft = startPos.GetX();
            if (position.GetX() > xRight) {
                jump = false;
                position.SetX(xRight);
                position.SetY(startPos.GetY());
                velocity.SetBoth(0, 0);
                jumpRight = !jumpRight;
            }
            if (position.GetX() < xLeft) {
                jump = false;
                position.SetX(xLeft);
                position.SetY(startPos.GetY());
                velocity.SetBoth(0, 0);
                jumpRight = !jumpRight;
            }

            bounds.update(position);
        }
        else
        {
            if(jumpRight) {
                velocity.SetBoth(10, 10);
            }
            else
            {
                velocity.SetBoth(-10,10);
            }
            if(jump)
            {
                velocity.SetBoth(-velocity.GetX(),-velocity.GetY());
            }

            int middle = screenSize.x/2;
            if(position.GetX() > middle - 11 && position.GetX() < middle +10)
            {
                velocity.SetBoth(0,20);
            }
            if(position.GetX() <0)
            {
                velocity.SetBoth(0,20);

            }
            if(position.GetX() > screenSize.x-width)
            {
                velocity.SetBoth(0,20);
            }

            if(position.GetY() > screenSize.y)
            {
                reallyDead = true;
            }
            move();
        }


    }

    public void move()
    {

        position.add(velocity);
    }


    public void jump(Point screenSize)
    {
        if(isAlive) {
            if (!jump) {
                float xSize = screenSize.x / 10;
                float ySize = screenSize.y / 20;
                jump = true;
                if (jumpRight) {
                    velocity.SetBoth(xSize, -ySize);
                } else {
                    velocity.SetBoth(-xSize, -ySize);
                }
            }
        }


    }

    public BoundingBox GetBounds()
    {
        return  bounds;
    }

    public void Kill()
    {
        isAlive = false;
    }

    public boolean IsDead()
    {
        return reallyDead;
    }







}
