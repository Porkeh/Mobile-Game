package com.example.jamie.thefallislava.Model;

/**
 * Created by Jamie on 01-Apr-16.
 */
public class Vector {

    private float x;
    private float y;



    public Vector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }


    public float GetX()
    {
        return x;
    }

    public float GetY()
    {
        return y;
    }

    public void SetX(float x)
    {
        this.x = x;
    }

    public void SetY(float y)
    {
        this.y = y;
    }

    public void SetBoth(float x, float y) { this.x = x; this.y = y;}

    public void add(Vector other)
    {
        this.x += other.GetX();
        this.y += other.GetY();
    }
    public void scale(float scalar)
    {
        this.x = x*scalar;
        this.y = y*scalar;
    }

    public float dot(Vector other)
    {
        float result;
        result = this.x*other.x + this.y*other.y;
        return result;
    }





}
