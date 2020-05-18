package com.example.android.dotsandboxes;

import android.graphics.Paint;

class Box {
    private float startX,startY,stopX,stopY;
    private Paint box=new Paint();

    Box(float x1,float y1,float x2, float y2,int color){
        startX=x1;
        startY=y1;
        stopX=x2;
        stopY=y2;
        box.setColor(color);
    }

    float getStartX() {
        return startX;
    }

    float getStartY() {
        return startY;
    }

    float getStopX() {
        return stopX;
    }

    float getStopY() {
        return stopY;
    }

    Paint getcolor() {
        return box;
    }
}
