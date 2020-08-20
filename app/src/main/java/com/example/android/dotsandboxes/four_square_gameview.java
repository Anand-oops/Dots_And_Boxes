package com.example.android.dotsandboxes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class four_square_gameview extends View {

    private static final String TAG = "four_square_view";
    ArrayList<Line> line_holder = new ArrayList<>();
    int score1=0,score2=0;
    int box_number;
    ArrayList<Box> box_holder = new ArrayList<>();
    int player_no = 0;
    boolean[][] vertical_line_state = new boolean[4][4];
    boolean[][] horizontal_line_state = new boolean[4][4];
    Paint dots = new Paint();
    Paint text1= new Paint();
    Paint text2=new Paint();

    public four_square_gameview(Context context) {
        super(context);
    }

    public four_square_gameview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public four_square_gameview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint({"SetTextI18n", "DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        text1.setColor(Color.BLUE);
        text1.setTextSize(60);
        text1.setAntiAlias(true);

        text2.setColor(0xFFF06292);
        text2.setTextSize(60);
        text2.setAntiAlias(true);

        canvas.drawText(Integer.toString(score1),250,100,text1);
        canvas.drawText(Integer.toString(score2),getWidth()-300,100,text2);
        Log.i(TAG, "onDraw: score 1: "+score1+" score 2: "+score2);

        dots.setColor(Color.BLACK);
        float cx = getWidth() / 2f - 300, cy = getHeight() / 2f - 300;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                canvas.drawCircle(cx, cy, 15, dots);
                cy += 200;
            }
            cy = getHeight() / 2f - 300;
            cx += 200;
        }

        for (int i = 0; i < line_holder.size(); i++) {
            canvas.drawLine(line_holder.get(i).getStart_x(), line_holder.get(i).getStart_y(), line_holder.get(i).getStop_x(), line_holder.get(i).getStop_y(), line_holder.get(i).getColor());
        }
        box_number=0;
        for (int i = 0; i < box_holder.size(); i++) {
            canvas.drawRect(box_holder.get(i).getStartX(), box_holder.get(i).getStartY(), box_holder.get(i).getStopX(), box_holder.get(i).getStopY(), box_holder.get(i).getcolor());
            box_number++;
        }
        if(box_number<9)
            invalidate();
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String result;
                    if (score1 > score2)
                        result = "Player 1 wins by " + score1 + ":" + score2;
                    else if (score2 > score1)
                        result = "Player 2 wins by " + score2 + ":" + score1;
                    else
                        result = "Its a draw " + score1 + " each";
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setMessage(result);
                    dialog.setTitle("Completed...");
                    dialog.setIcon(android.R.drawable.ic_dialog_info);
                    dialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Vibrator vibe = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                                    assert vibe != null;
                                    vibe.vibrate(80);
                                    Activity activity = (Activity) getContext();
                                    activity.finish();
                                }
                            });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            },1000);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        int color;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float x_touch = event.getX(), y_touch = event.getY();
            Log.i(TAG, "onTouchEvent: " + x_touch + "  " + y_touch);

            float cx = getWidth() / 2f - 300, cy = getHeight() / 2f - 300;

            if (x_touch <= getWidth() / 2f + 300 && y_touch <= getHeight() / 2f + 300) {

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {

                        if (cy <= y_touch && cy + 200 >= y_touch
                                && cx - 35 <= x_touch && cx + 35 >= x_touch) {                 //vertical line
                            if (!vertical_line_state[j][i]) {
                                vertical_line_state[j][i] = true;
                                line_holder.add(new Line(cx, cy, cx, cy + 200, player_no));
                                int flag = 1;
                                if (i - 1 >= 0) {
                                    if (player_no == 0) color = 0xFF0288D1;
                                    else color = 0xFFF48FB1 ;
                                    if (horizontal_line_state[j][i - 1] && vertical_line_state[j][i] && horizontal_line_state[j + 1][i - 1] && vertical_line_state[j][i - 1]) {
                                        if(player_no==0)    score1++;
                                        else score2++;
                                        box_holder.add(new Box(cx - 15, cy + 15, cx - 200 + 15, cy + 200 - 15, color));
                                        player_no++;
                                        player_no %= 2;
                                        flag = 0;
                                    }
                                }
                                if (i + 1 <= 3) {
                                    if (horizontal_line_state[j + 1][i] && vertical_line_state[j][i] && horizontal_line_state[j][i] && vertical_line_state[j][i + 1]) {
                                        if (flag == 0) {
                                            player_no++;
                                            player_no %= 2;
                                        }
                                        if (player_no == 0) color = 0xFF0288D1;
                                        else color = 0xFFF48FB1;
                                        if(player_no==0)    score1++;
                                        else score2++;
                                        box_holder.add(new Box(cx + 15, cy + 15, cx + 200 - 15, cy + 200 - 15, color));
                                        player_no++;
                                        player_no %= 2;
                                    }
                                }
                                player_no++;
                                player_no %= 2;

                            }
                        } else if (cx <= x_touch && cx + 200 >= x_touch
                                && cy - 35 <= y_touch && cy + 35 >= y_touch) {                        //horizontal line
                            if (!horizontal_line_state[j][i]) {
                                horizontal_line_state[j][i] = true;
                                line_holder.add(new Line(cx, cy, cx + 200, cy, player_no));
                                int flag = 1;
                                if (j + 1 <= 3) {
                                    if (player_no == 0) color = 0xFF0288D1;
                                    else color = 0xFFF48FB1;
                                    if (horizontal_line_state[j][i] && vertical_line_state[j][i] && horizontal_line_state[j + 1][i] && vertical_line_state[j][i + 1]) {
                                        if(player_no==0)    score1++;
                                        else score2++;
                                        box_holder.add(new Box(cx + 15, cy + 15, cx + 200 - 15, cy + 200 - 15, color));
                                        player_no++;
                                        player_no %= 2;
                                        flag = 0;
                                    }
                                }
                                if (j - 1 >= 0) {
                                    if (horizontal_line_state[j][i] && vertical_line_state[j - 1][i] && horizontal_line_state[j - 1][i] && vertical_line_state[j - 1][i + 1]) {
                                        if (flag == 0) {
                                            player_no++;
                                            player_no %= 2;
                                        }
                                        if (player_no == 0) color = 0xFF0288D1;
                                        else color = 0xFFF48FB1;
                                        if(player_no==0)    score1++;
                                        else score2++;
                                        box_holder.add(new Box(cx + 15, cy - 15, cx + 200 - 15, cy - 200 + 15, color));
                                        player_no++;
                                        player_no %= 2;
                                    }
                                }
                                player_no++;
                                player_no %= 2;
                            }
                        }
                        cy += 200;
                    }
                    cy = getHeight() / 2f - 300;
                    cx += 200;
                }
            }

        }
        return value;
    }
}

