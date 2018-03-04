package com.example.quizapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class ClockView extends View{

    //Begin Clock Settings
    private float RADIUS = 400;
    private float CENTER_POSITION_X = 500;
    private float CENTER_POSITION_Y = 500;
    private float SECOND_HAND_LENGTH = 400;
    private float MINUTE_HAND_LENGTH = 270;
    private float HOUR_HAND_LENGTH = 200;
    private int CIRCLE_COLOR = Color.GREEN;
    private int SECOND_HAND_COLOR = Color.GRAY;
    private int MINUTE_HAND_COLOR = Color.YELLOW;
    private int HOUR_HAND_COLOR = Color.BLUE;
    //End Clock Settings
    private Paint mPaintCircle;
    private Paint mPaintS;
    private Paint mPaintM;
    private Paint mPaintH;
    private Canvas canvas;
    private Bitmap bitmap;
    float h;
    float m;
    float s;

    public ClockView(Context context, String date) {
        super(context);
        canvas = new Canvas();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        initPaint();
        initDate(date);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawClock(canvas,h,m,s);
    }

    private void initDate(String date){
        String[] x = date.split(":");
        h = Float.valueOf(x[0]);
        m = Float.valueOf(x[1]);
        s = Float.valueOf(x[2]);
    }

    private void initPaint(){
        mPaintCircle = new Paint(Paint.DITHER_FLAG);
        mPaintCircle.setStyle(Style.STROKE);
        mPaintCircle.setStrokeWidth(4);
        mPaintCircle.setColor(CIRCLE_COLOR);
        mPaintCircle.setAntiAlias(true);
        mPaintS = new Paint(Paint.DITHER_FLAG);
        mPaintS.setStyle(Style.STROKE);
        mPaintS.setStrokeWidth(1);
        mPaintS.setColor(SECOND_HAND_COLOR);
        mPaintS.setAntiAlias(true);
        mPaintM = new Paint(Paint.DITHER_FLAG);
        mPaintM.setStyle(Style.STROKE);
        mPaintM.setStrokeWidth(2);
        mPaintM.setColor(MINUTE_HAND_COLOR);
        mPaintM.setAntiAlias(true);
        mPaintH = new Paint(Paint.DITHER_FLAG);
        mPaintH.setStyle(Style.STROKE);
        mPaintH.setStrokeWidth(3);
        mPaintH.setColor(HOUR_HAND_COLOR);
        mPaintH.setAntiAlias(true);
    }
    public void refreshClock(){
        refreshDate();
        invalidate();
    }
    public void drawClock(Canvas mCanvas,float h,float m,float s){
        mCanvas.drawCircle(CENTER_POSITION_X,CENTER_POSITION_Y, RADIUS , mPaintCircle);
        mCanvas.rotate(getDegree("s", s), CENTER_POSITION_X, CENTER_POSITION_Y);
        mCanvas.drawLine(CENTER_POSITION_X, CENTER_POSITION_Y,CENTER_POSITION_X, CENTER_POSITION_Y - SECOND_HAND_LENGTH, mPaintS);
        mCanvas.rotate(-getDegree("s", s), CENTER_POSITION_X, CENTER_POSITION_Y);
        mCanvas.rotate(getDegree("m", m), CENTER_POSITION_X, CENTER_POSITION_Y);
        mCanvas.drawLine(CENTER_POSITION_X, CENTER_POSITION_Y, CENTER_POSITION_X, CENTER_POSITION_Y - MINUTE_HAND_LENGTH, mPaintM);
        mCanvas.rotate(-getDegree("m", m), CENTER_POSITION_X, CENTER_POSITION_Y);
        mCanvas.rotate(getDegree("h", h), CENTER_POSITION_X, CENTER_POSITION_Y);
        mCanvas.drawLine(CENTER_POSITION_X, CENTER_POSITION_Y, CENTER_POSITION_X, CENTER_POSITION_Y - HOUR_HAND_LENGTH, mPaintH);
        mCanvas.rotate(-getDegree("h", h), CENTER_POSITION_X, CENTER_POSITION_Y);
        mCanvas.drawBitmap(bitmap,0,0,null);
    }

    private void refreshDate() {
        if (s < 59) {
            s = s + 1;
        } else {
            s = 0;
            if (m < 59) {
                m = m + 1;
            } else {
                m = 0;
                if (h < 11) {
                    h = h +1;
                } else {
                    h = 0;
                }
            }
        }
    }
    private float getDegree(String type, float f){
        float x = 60;
        float y = 360;
        float j = 6;
        float z = 3600;
        float q = 30;
        if (type.equals("s")){
            return  f/x*y;
        } else if (type.equals("m")) {
            return (m + (s/x))*j;
        } else if (type.equals("h")) {
            return(h + (m/x) + (s/z)) * q;
        } else {
            return 0;
        }
    }
}



