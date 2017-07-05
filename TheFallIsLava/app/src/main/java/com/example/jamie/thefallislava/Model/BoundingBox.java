package com.example.jamie.thefallislava.Model;

import android.util.Log;

/**
 * Created by Jamie on 26-Apr-16.
 */
public class BoundingBox {

    Vector centre;
    Vector halfExtents;

    public BoundingBox(float x,float y,float halfWidth,float halfHeight)
    {
        centre = new Vector(x,y);
        halfExtents = new Vector(halfWidth,halfHeight);
    }

    public void update(Vector newPos)
    {
        centre.SetBoth(newPos.GetX()+halfExtents.GetX(),newPos.GetY()+halfExtents.GetY());

    }

    public boolean Collided(BoundingBox other)
    {
       // Log.d("Checking","Checking");
        if ( this.Max().GetX() < other.Min().GetX())
        {
            return false;
        }
        if ( this.Max().GetY() < other.Min().GetY() )
        {
            return false;
        }
        if ( this.Min().GetX() > other.Max().GetX() )
        {
            return false;
        }
        if (this.Min().GetY() > other.Max().GetY())
        {
            return  false;
        }

        return  true;
    }

    public Vector Max()
    {
        float x = centre.GetX() + halfExtents.GetX();
        float y = centre.GetY() + halfExtents.GetY();
        Vector max = new Vector(x,y);
        return max;
    }

    public Vector Min()
    {
        float x = centre.GetX() - halfExtents.GetX();
        float y = centre.GetY() - halfExtents.GetY();
        Vector min = new Vector(x,y);
        return min;
    }


}
