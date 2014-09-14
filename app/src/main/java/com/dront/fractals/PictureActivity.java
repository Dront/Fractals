package com.dront.fractals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;


public class PictureActivity extends Activity {

    private static final int SIZE = 1000;

    ImageView imgViewMain;
    Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        getInterfaceResources();

        Intent i = getIntent();
        final String type = i.getStringExtra("type");


        new Thread(new Runnable() {
            @Override
            public void run() {
                if (type.equals("Koch")){
                    drawKoch(MainActivity.segments);
                } else if (type.equals("Mandel")){
                    drawMandel(MainActivity.colors);
                } else if (type.equals("Julia")){
                    drawMandel(MainActivity.colors);
                } else if (type.equals("IFS")) {
                    drawTriangle(MainActivity.points);
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (pic != null){
            pic.recycle();
        }
    }

    private void getInterfaceResources(){
        imgViewMain = (ImageView) findViewById(R.id.imgViewMain);
    }

    private void drawKoch(ArrayList<Segment> data){

        pic = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(pic);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3.0f);
        paint.setStyle(Paint.Style.STROKE);

        for (Segment seg: data){
            canvas.drawLine(SIZE * (float)seg.getX1(), SIZE * (float)seg.getY1(),
                    SIZE * (float)seg.getX2(), SIZE * (float)seg.getY2(), paint);
        }
        imgViewMain.post(new Runnable() {
            @Override
            public void run() {
                imgViewMain.setImageBitmap(pic);
            }
        });
    }

    private void drawMandel(int[] data){
        pic = Bitmap.createBitmap(Constants.WIDTH, Constants.HEIGHT, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < Constants.WIDTH; x++){
            for (int y = 0; y < Constants.HEIGHT; y++){
                pic.setPixel(x, y, data[x * Constants.HEIGHT + y]);
            }
        }
        imgViewMain.post(new Runnable() {
            @Override
            public void run() {
                imgViewMain.setImageBitmap(pic);
            }
        });
    }

    private void drawTriangle(Point[] data){
        pic = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);

        for (Point tmp: data){
            if (tmp.x >= 1 || tmp.y >= 1 || tmp.x < 0 || tmp.y < 0){
                Log.d(LogTags.APP, "point with wrong coords in MainActivity.points");
                continue;
            }

            int x = (int)(tmp.x * SIZE);
            int y = (int)(tmp.y * SIZE);
            pic.setPixel(x, y, Color.RED);
        }

        imgViewMain.post(new Runnable() {
            @Override
            public void run() {
                imgViewMain.setImageBitmap(pic);
            }
        });
    }
}
