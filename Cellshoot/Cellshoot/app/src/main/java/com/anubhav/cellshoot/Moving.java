package com.anubhav.cellshoot;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.anubhav.cellshoot.Viewg.screenRatioX;
import static com.anubhav.cellshoot.Viewg.screenRatioY;

public class Moving {
    public int toShoot=0;
    boolean isGoingup=false,isGoingdown=false;
    int x,y,width,height,wingCounter=0,shootCounter=1;
    Bitmap goku1,goku2,shoot1,shoot2,shoot3,dead;
    private Viewg viewg;
    Moving(Viewg viewg,int screenY, Resources res){
        this.viewg=viewg;
        goku1= BitmapFactory.decodeResource(res,R.drawable.goku1);
        goku2= BitmapFactory.decodeResource(res,R.drawable.goku2);
        width=goku1.getWidth();
        height=goku1.getHeight();
        width/=20;
        height/=20;
        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        goku1 = Bitmap.createScaledBitmap(goku1, width, height, false);
        goku2 = Bitmap.createScaledBitmap(goku2, width, height, false);
        shoot1=BitmapFactory.decodeResource(res,R.drawable.shoot1);
        shoot2=BitmapFactory.decodeResource(res,R.drawable.shoot2);
        shoot3=BitmapFactory.decodeResource(res,R.drawable.shoot3);

        shoot1 = Bitmap.createScaledBitmap(shoot1,width,height,false);
        shoot2 = Bitmap.createScaledBitmap(shoot2,width,height,false);
        shoot3 = Bitmap.createScaledBitmap(shoot3,width,height,false);

        dead=BitmapFactory.decodeResource(res,R.drawable.dead);
        dead=Bitmap.createScaledBitmap(dead,width,height,false);
        y = screenY / 2;
        x = (int) (64 * screenRatioX);

    }
    Bitmap getMoving(){
        if(toShoot!=0){
            if(shootCounter==1){
                shootCounter++;
                return shoot1;
            }
            if(shootCounter==2){
                shootCounter++;
                return shoot2;
            }
            shootCounter=1;
            toShoot--;
            viewg.newShoot();
            return shoot3;
        }
        if (wingCounter == 0) {
            wingCounter++;
            return goku1;
        }
        wingCounter--;
        return goku2;
    }

    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
    Bitmap getDead(){
        return dead;
    }
}

