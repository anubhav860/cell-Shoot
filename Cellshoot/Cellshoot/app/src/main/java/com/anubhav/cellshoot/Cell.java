package com.anubhav.cellshoot;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.anubhav.cellshoot.Viewg.screenRatioX;
import static com.anubhav.cellshoot.Viewg.screenRatioY;

public class Cell {
    public int speed=20;
    public boolean wasShot=true;
    int x=0,y,width,height,cellCounter=1;
    Bitmap cell1,cell2,cell3,cell4;
    Cell(Resources res){
        cell1= BitmapFactory.decodeResource(res,R.drawable.cell1);
        cell2= BitmapFactory.decodeResource(res,R.drawable.cell2);
        cell3= BitmapFactory.decodeResource(res,R.drawable.cell3);
        cell4= BitmapFactory.decodeResource(res,R.drawable.cell4);
        width=(cell1.getWidth()/15);
        height=(cell1.getHeight()/15);

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        cell1=Bitmap.createScaledBitmap(cell1,width,height,false);
        cell2=Bitmap.createScaledBitmap(cell2,width,height,false);
        cell3=Bitmap.createScaledBitmap(cell3,width,height,false);
        cell4=Bitmap.createScaledBitmap(cell4,width,height,false);
        y=-height;

    }
    Bitmap getCell(){
        if(cellCounter==1){
            cellCounter++;
            return cell1;
        }
        if(cellCounter==2){
            cellCounter++;
            return cell2;
        }
        if(cellCounter==3){
            cellCounter++;
            return cell3;
        }
        cellCounter=1;
        return cell4;
    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
}