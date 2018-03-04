package com.example.quizapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;


public class CanvasView extends View{

    private int mov_x;
    private int mov_y;
    private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap;

    public CanvasView(Context context) {
        super(context);
        paint = new Paint(Paint.DITHER_FLAG);
        canvas = new Canvas();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        bitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            canvas.drawLine(mov_x, mov_y, event.getX(), event.getY(), paint);
            invalidate();
        }
        mov_x=(int) event.getX();
        mov_y=(int) event.getY();
        return true;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bp) {
        canvas.drawBitmap(bp,0,0,null);
        invalidate();
    }
}



