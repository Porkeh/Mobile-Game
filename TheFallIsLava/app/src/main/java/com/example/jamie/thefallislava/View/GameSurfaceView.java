package com.example.jamie.thefallislava.View;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.jamie.thefallislava.Model.Lava;
import com.example.jamie.thefallislava.Model.Obstacle;
import com.example.jamie.thefallislava.Model.Player;
import com.example.jamie.thefallislava.Model.Rock;
import com.example.jamie.thefallislava.Model.Spike;
import com.example.jamie.thefallislava.Model.Vector;
import com.example.jamie.thefallislava.Model.Wall;
import com.example.jamie.thefallislava.Model.Background;
import com.example.jamie.thefallislava.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Jamie on 24-Apr-16.
 */
    public class GameSurfaceView extends SurfaceView implements Runnable{

    private final static int    MAX_FPS = 50;                   // desired fps
    private final static int    MAX_FRAME_SKIPS = 5;            // maximum number of frames to be skipped
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;  // the frame period

    SurfaceHolder holder;                                       //Surface holder for the canvas
    private boolean ok = false;                                 //Boolean to control pause and resume
    Thread t = null;                                            //Thread for the game logic
    MediaPlayer mediaPlayer;

    private Point screenSize;                                   //Holds the screen size
    private Paint paint = new Paint();
    private Player player;
    private LinkedList<Obstacle> obstacles;
    private ArrayList<Background> backgrounds;
    private ArrayList<Wall> walls;
    private Vector screenVelocity = new Vector(0,15);
    private Lava lava;

    private long beginTime;                                     // the time when the cycle began
    private long timeDiff;                                      // the time it took for the cycle to execute
    private int sleepTime;                                      // ms to sleep
    private int framesSkipped;                                  // number of frames being skipped

    private long prevTime = 0;
    private long deltaT;
    private float wallWidth;
    private boolean gameOver = false;
    private boolean gameWon = false;


    private ArrayList<Integer> level;




    public GameSurfaceView(Context context, Point screenS, String levelData, int musicID)
    {
            super(context);

            holder = getHolder();//Used for the screenview
            screenSize = screenS;

            obstacles = new LinkedList<Obstacle>();

            backgrounds = new ArrayList<Background>();
            backgrounds.add(new Background(0,0,screenSize,context));
            backgrounds.add(new Background(0,0-screenSize.y,screenSize,context));
            walls = new ArrayList<>();
            walls.add(new Wall(0,0,screenS,context,false,screenVelocity));
            walls.add(new Wall(0,0-screenSize.y,screenSize,context,false,screenVelocity));
            walls.add(new Wall(screenSize.x,0,screenSize,context,true,screenVelocity));
            walls.add(new Wall(screenSize.x,0-screenSize.y,screenSize,context,true,screenVelocity));
            lava = new Lava(0,screenSize.y,screenSize,context);

            wallWidth = walls.get(0).GetWidth();
            int playerOffset = screenSize.y/10 * 7;
            player = new Player(wallWidth,playerOffset, context, screenSize);
            //Place items in here for the constructor
            level = new ArrayList<Integer>();

            InputStream inputStream = null;
            AssetManager assetManager = context.getAssets();

        try {
            inputStream = assetManager.open(levelData);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);

                int line;
                while((line = br.read())!= 48) {

                    if(line != 32) {
                        int i = line - 48;
                        level.add(i);
                    }


                }

        } catch (IOException e) {
            e.printStackTrace();
        }

        MakeLevel(context);



            mediaPlayer = MediaPlayer.create(context, musicID);



    }

    private void MakeLevel(Context context)
    {
        int offset = screenSize.y/10;
        for(int i = 0; i < level.size(); i++ )
        {
            switch (level.get(i))
            {
                case 1 :
                    obstacles.push(new Spike(wallWidth,0 - offset*i, context,screenVelocity,screenSize,false));
                    break;
                case 2 :
                    obstacles.push(new Spike(screenSize.x-wallWidth,0-offset * i,context,screenVelocity,screenSize,true));
                    break;
                case 3 :
                    obstacles.push(new Spike(wallWidth,0 - offset*i, context,screenVelocity,screenSize,false));
                    obstacles.push(new Spike(screenSize.x-wallWidth,0-offset*i,context,screenVelocity,screenSize,true));
                    break;
                case 5 :
                    obstacles.push(new Rock(screenSize.x/2,0-offset*i,context,screenSize,screenVelocity));
                    break;

            }
        }


    }
        private void updateCanvas (){
            //Update the items in the canvas
            player.update(screenSize);
            lava.update(screenSize);
            if(!gameOver) {
                for (Background b : backgrounds) {
                    b.update(screenSize);
                }
                for (Wall w : walls) {
                    w.update(screenSize);
                }


                for (Obstacle o : obstacles) {
                    o.update();
                }

                if(obstacles.get(0).GetPosition().GetY() > screenSize.y)
                {
                    gameWon = true;
                }


                CheckCollision();
            }




        }

        protected void drawCanvas(Canvas canvas){
            //Draw the items to the canvas
            canvas.drawARGB(255,100,100,0);
            for(Background b : backgrounds)
            {
                b.draw(canvas);
            }
            for (Obstacle o: obstacles
                    ) {
                o.draw(canvas);

            }

            for(Wall w : walls)
            {
                w.draw(canvas);
            }

            player.draw(canvas);
            lava.draw(canvas);

            if(gameWon)
            {
                paint.setColor(Color.WHITE);
                paint.setTextSize(72);

                canvas.drawText("You won!",screenSize.x/4,screenSize.y/2,paint);
            }


        }

        public void handleEvent()
        {

            player.jump(screenSize);
        }


        public void CheckCollision()
        {
            for (Obstacle o:
                 obstacles) {
               // Log.d("Checking","checking");
               if( o.GetBounds().Collided(player.GetBounds()) == true)
               {
                   player.Kill();
                   gameOver = true;
               }
            }
        }



    @Override
    public void run() {
        //Remove conflict between the UI thread and the game thread.
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        while (ok) {
            //perform canvas drawing
            if (!holder.getSurface().isValid()) {//if surface is not valid
                continue;//skip anything below it
            }
            Canvas c = holder.lockCanvas(); //Lock canvas, paint canvas, unlock canvas
            synchronized (holder) {
                beginTime = System.currentTimeMillis();
                deltaT = beginTime - prevTime;
                framesSkipped = 0;  // resetting the frames skipped
                // update game state
                this.updateCanvas();
                // render state to the screen
                // draws the canvas on the panel
                this.drawCanvas(c);
                // calculate how long did the cycle take
                timeDiff = System.currentTimeMillis() - beginTime;
                // calculate sleep time
                sleepTime = (int) (FRAME_PERIOD - timeDiff);
                if (sleepTime > 0) {
                    // if sleepTime > 0 put to sleep for short period of time
                    try {
                        // send the thread to sleep for a short period
                        // very useful for battery saving
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }

                //ADD THIS IF WE ARE DOING LOTS OF WORK
                //If sleeptime is greater than a frame length, skip a number of frames
               /* while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                    // we need to catch up
                    // update without rendering
                    this.updateCanvas();
                    // add frame period to check if in next frame
                    sleepTime += FRAME_PERIOD;
                    framesSkipped++;
                }*/
                prevTime = System.currentTimeMillis();
                holder.unlockCanvasAndPost(c);
            }
        }
    }

    public void pause(){

        ok = false;
        mediaPlayer.pause();
        while(true){
            try{
                t.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }

    public void resume(){
        mediaPlayer.start();
        ok = true;
        t = new Thread(this);
        t.start();
    }

    public boolean GameOver()
    {
        if(player.IsDead() == true || gameWon == true) {
            return true;
        }

        return  false;
    }
}
