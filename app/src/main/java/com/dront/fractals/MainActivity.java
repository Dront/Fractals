package com.dront.fractals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final int MAX_ITER_KOCH = 7;
    private static final int MAX_ITER_MANDEL = 500;

    private EditText edtKochIterations, edtMandelIterations;

    public static ArrayList<Segment> segments;
    public static int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInterfaceResources();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (segments != null){
            segments.clear();
        }

        if (colors != null){
            colors = new int[1];
        }
    }

    private void getInterfaceResources(){
        edtKochIterations = (EditText) findViewById(R.id.edtTextKochNum);
        edtMandelIterations = (EditText) findViewById(R.id.edtTextMandelNum);
    }

    private class ComputeKoch extends AsyncTask<Integer, Void, Void>{
        @Override
        protected Void doInBackground(Integer... integers) {
            return null;
        }
    }

    public void drawKoch(View v){
        int iterKoch = Integer.parseInt(edtKochIterations.getText().toString());
        if (iterKoch > MAX_ITER_KOCH){
            String msg = "Max number of iterations = " + MAX_ITER_KOCH;
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        segments = new ArrayList<Segment>();
        final double sqrt36 = Math.sqrt(3.0) / 6;

        Point startPoint1 = new Point(0, sqrt36);
        Point startPoint2 = new Point(1, sqrt36);
        Point startPoint3 = new Point(1.0 / 2, 1);

        segments.add(new Segment(startPoint1.x, startPoint1.y, startPoint2.x, startPoint2.y));
        segments.add(new Segment(startPoint2.x, startPoint2.y, startPoint3.x, startPoint3.y));
        segments.add(new Segment(startPoint3.x, startPoint3.y, startPoint1.x, startPoint1.y));

        for (int i = 0; i < iterKoch; i++){
            ArrayList<Segment> newSegments = new ArrayList<Segment>();
            for (Segment seg: segments){
                double length = seg.getLen() / 3;
                double angle = Math.atan2(seg.getHeight(), seg.getWidth()) - Math.PI / 3;

                Point p1 = new Point(seg.getX1() + seg.getWidth() / 3, seg.getY1() + seg.getHeight() / 3);
                Point p2 = new Point(seg.getX1() + seg.getWidth() * 2 / 3, seg.getY1() + seg.getHeight() * 2 / 3);

                Segment cutSeg = new Segment(p1.x, p1.y, p2.x, p2.y);

                Point p = new Point(cutSeg.getX1() + length*Math.cos(angle),
                        cutSeg.getY1() + length*Math.sin(angle));

                newSegments.add(new Segment(seg.getX1(), seg.getY1(), p1.x, p1.y));
                newSegments.add(new Segment(p1.x, p1.y, p.x, p.y));
                newSegments.add(new Segment(p.x, p.y, p2.x, p2.y));
                newSegments.add(new Segment(p2.x, p2.y, seg.getX2(), seg.getY2()));
            }
            segments.clear();
            segments.addAll(newSegments);
        }

        Intent i = new Intent(getApplicationContext(), PictureActivity.class);
        i.putExtra("type", "Koch");
        startActivity(i);
    }

    public void drawMandel(View v){
        int iterMandel = Integer.parseInt(edtMandelIterations.getText().toString());
        if (iterMandel > MAX_ITER_MANDEL){
            String msg = "Max number of iterations = " + MAX_ITER_MANDEL;
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        final int width = Constants.WIDTH;
        final int height = Constants.HEIGHT;

        final long time1 = System.currentTimeMillis();

        colors = new int[width * height];

        double Ax, Bx, Ay, By;
        Ax = 2.5 / width; Bx = -2;
        Ay = 2.0 / height; By = -1;

        for (int x0 = 0; x0 < width; x0++)
        {
            double x = Ax * x0 + Bx;
            for (int y0 = 0; y0 < height; y0++)
            {
                double y = Ay * y0 + By;
                long iter = mandelbrotIteration(x, y, iterMandel);
                int color = Color.rgb((int)(256 - iter*4 % 256), (int)(256 - iter * 6 % 256), (int)(256 - iter * 20 % 256));
                colors[x0 * height + y0] = color;
            }
        }

        final long time2 = System.currentTimeMillis();
        String msg = "Time used: " + (time2 - time1) + " ms.";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getApplicationContext(), PictureActivity.class);
        i.putExtra("type", "Mandel");
        startActivity(i);

    }

    private int mandelbrotIteration(double X, double Y, int maxIterations)
    {
        int res = 0;
        double a = 0, b = 0;
        while ((a * a + b * b < 4) && (res < maxIterations))
        {
            double tmp = a * a - b * b + X;
            b = 2 * a * b + Y;
            a = tmp;
            res++;
        }
        return res;
    }
}
