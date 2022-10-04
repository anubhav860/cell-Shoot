package com.anubhav.cellshoot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Viewg extends SurfaceView implements Runnable {
    private Thread thread;
    private Background background1,background2;
    private int score=0;
    public static float screenRatioX,screenRatioY;
    boolean isPlaying,isGover=false;
    private int cSpeed=4;
    private Cell[] cells;
    private SoundPool soundPool;
    private int screenX,screenY,sound,sound2;
    private List<Shooted> shoots;
    private Moving moving;
    private Random random;
    private Paint paint,paint1;

    @SuppressLint("ResourceAsColor")
    public Viewg(Context context, int screenX, int screenY) {
        super(context);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound = soundPool.load(context, R.raw.shoot, 1);
        sound2 = soundPool.load(context, R.raw.dying, 1);
        this.screenX=screenX;
        this.screenY=screenY;
        screenRatioX=1920f/screenX;
        screenRatioY=1080f/screenY;
        background1 = new Background(screenX,screenY,getResources());
        moving=new Moving(this,screenY,getResources());
        paint=new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.BLUE);
        paint1 = new Paint();
        paint1.setTextSize(100);
        paint1.setColor(Color.RED);
        shoots=new ArrayList<>();
        cells=new Cell[5];
        for(int i=0;i<5;i++){
            Cell cell=new Cell(getResources());
            cells[i]=cell;
        }
        random=new Random();

    }

    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }

    }

    private void update(){

        List<Shooted> trash=new ArrayList<>();
        for(Shooted shooted : shoots){
            if(shooted.x > screenX){
                trash.add(shooted);
            }
            shooted.x+=50*screenRatioX;

            for(Cell cell: cells){
                if(Rect.intersects(cell.getCollisionShape(),shooted.getCollisionShape())){
                    cell.x = -500;
                    shooted.x=screenX+50;
                    cell.wasShot=true;
                    score++;
                    if((score % 10)==0){
                        cSpeed+=5;
                    }
                }
            }
        }
        for(Shooted shooted : trash){
            shoots.remove(shooted);
        }
        for(Cell cell :cells){
            cell.x-=cell.speed;

            if(cell.x  < 0){

                if(!cell.wasShot){
                    isGover=true;
                    return;
                }
                int maxSpeed =(int) ((10 * screenRatioX)+cSpeed);
                cell.speed=random.nextInt(maxSpeed);
                if(cell.speed < 5 *screenRatioX)
                    cell.speed=(int)(10 * screenRatioX);
                cell.x=screenX;
                cell.y=random.nextInt(screenY-cell.height);
                cell.wasShot=false;
            }
            if(Rect.intersects(cell.getCollisionShape(),moving.getCollisionShape())){
                isGover=true;
                return;
            }
        }
    }
    private void draw(){
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);

            for(Cell cell:cells){
                canvas.drawBitmap(cell.getCell(),cell.x,cell.y,paint);
            }
            canvas.drawText( "Score :: " + score,0,60,paint);
            if (isGover) {
                isPlaying=false;
                canvas.drawBitmap(moving.getDead(),moving.x,moving.y,paint);
                soundPool.play(sound2,1,1,0,0,1);
                canvas.drawText( "GAME OVER" ,(screenX/2)-300,screenY/2,paint1);
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }
            canvas.drawBitmap(moving.getMoving(),moving.x,moving.y,paint);

            for(Shooted shooted : shoots)
                canvas.drawBitmap(shooted.shooted,shooted.x,shooted.y,paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
        if(moving.isGoingup)
            moving.y-=20*screenRatioY;
        if(moving.isGoingdown)
            moving.y+=20*screenRatioY;

        if(moving.y < 0)
            moving.y = 0;

        if(moving.y > screenY - moving.height )
            moving.y = screenY - moving.height;

    }
    private void sleep(){
        try {
            Thread.sleep(35);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < screenX / 2 ){
                    if(event.getY() < screenY/2){
                        moving.isGoingup=true ;
                        moving.isGoingdown=false;
                    }
                    else {
                        moving.isGoingup = false;
                        moving.isGoingdown = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                moving.isGoingup=false;
                moving.isGoingdown=false;
                
                if(event.getX() > screenX/2){
                    moving.toShoot++;
                }
                break;
        }
        return true;
    }

    public void newShoot() {
        soundPool.play(sound,1,1,0,0,1);
        Shooted shooted=new Shooted(getResources());
        shooted.x=moving.x+moving.width;
        shooted.y=moving.y+moving.height/15;
        shoots.add(shooted);
    }
}
