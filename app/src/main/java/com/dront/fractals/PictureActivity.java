package com.dront.fractals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;


public class PictureActivity extends Activity {

    private static final int SIZE = 512;

    ImageView imgViewMain;
    Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        getInterfaceResources();

        pic = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);

        Intent i = getIntent();
        String type = i.getStringExtra("type");

        if (type.equals("Koch")){
            drawKoch(MainActivity.segments);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (pic != null){
            pic.recycle();
        }

        MainActivity.segments.clear();
    }

    private void getInterfaceResources(){
        imgViewMain = (ImageView) findViewById(R.id.imgViewMain);
    }

    private void drawKoch(ArrayList<Segment> data){

        Canvas canvas = new Canvas(pic);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3.0f);
        paint.setStyle(Paint.Style.STROKE);

        for (Segment seg: data){
            canvas.drawLine(SIZE * (float)seg.getX1(), SIZE * (float)seg.getY1(),
                    SIZE * (float)seg.getX2(), SIZE * (float)seg.getY2(), paint);
        }
        imgViewMain.setImageBitmap(pic);
    }
}
