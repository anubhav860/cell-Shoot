package com.anubhav.cellshoot;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.anubhav.cellshoot.Viewg.screenRatioX;
import static com.anubhav.cellshoot.Viewg.screenRatioY;

public class Shooted {
    int x,y,width,height;
    Bitmap shooted;
    Shooted(Resources res){
        shooted= BitmapFactory.decodeResource(res,R.drawable.shooted);
        width=shooted.getWidth();
        height=shooted.getHeight();
        width/=20;
        height/=20;
        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);
        shooted=Bitmap.createScaledBitmap(shooted,width,height,false);
    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
}
