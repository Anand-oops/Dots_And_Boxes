package com.example.android.dotsandboxes;

import android.graphics.Color;
import android.graphics.Paint;

class Line  {

    private float start_x,start_y,stop_x,stop_y;
    private Paint line=new Paint();

    Line(float start_x, float start_y, float stop_x, float stop_y, int number){
        this.start_x=start_x;
        this.start_y=start_y;
        this.stop_x=stop_x;
        this.stop_y=stop_y;
        if(number==0){
            line.setColor(Color.BLUE);
        }
        else {
            this.line.setColor(0xFFF06292);
        }
        line.setStrokeWidth(15);
    }

    float getStart_x() {
        return start_x;
    }

    float getStart_y() {
        return start_y;
    }

    float getStop_x() {
        return stop_x;
    }

    float getStop_y() {
        return stop_y;
    }

    Paint getColor() {
        return line;
    }
}
