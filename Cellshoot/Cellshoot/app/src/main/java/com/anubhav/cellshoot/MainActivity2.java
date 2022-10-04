package com.anubhav.cellshoot;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
    private Viewg viewg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Point point=new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        viewg= new Viewg(this,point.x,point.y);
        setContentView(viewg);
    }

    @Override
    protected void onPause() {

        super.onPause();
        viewg.pause();
    }

    @Override
    protected void onResume() {

        super.onResume();
        viewg.resume();
    }

}
