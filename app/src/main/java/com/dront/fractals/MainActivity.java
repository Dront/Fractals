package com.dront.fractals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Util.Complex;


public class MainActivity extends Activity {

    private static final int MAX_ITER_KOCH = 10;
    private static final int MAX_ITER_MANDEL = 500;
    private static final int MAX_ITER_TRIANGLE = 1000000;

    public static ArrayList<Segment> segments;
    public static int[] colors;
    public static Point[] points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customizeSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();

        segments = null;
        colors = null;
        points = null;

        interfaceControl(true);
    }

    private void customizeSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerFormula);
        int size = spinner.getChildCount();

        TextView tmp;
        for (int i = 0; i < size; i++){
            tmp = (TextView)spinner.getChildAt(i);
            tmp.setTextSize(25);
            tmp.setGravity(Gravity.END);
        }
    }

    private void interfaceControl(boolean state){
        Button btnKoch = (Button) findViewById(R.id.btnDrawKoch);
        Button btnMandel = (Button) findViewById(R.id.btnDrawMandel);
        Button btnTriangle = (Button) findViewById(R.id.btnDrawTriangle);

        btnKoch.setEnabled(state);
        btnMandel.setEnabled(state);
        btnTriangle.setEnabled(state);
    }

    public void computeKoch(View v){
        EditText edtKochIterations = (EditText) findViewById(R.id.edtTextKochNum);
        int iterKoch = Integer.parseInt(edtKochIterations.getText().toString());

        if (iterKoch > MAX_ITER_KOCH){
            String msg = "Max number of iterations = " + MAX_ITER_KOCH;
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        interfaceControl(false);

        KochComputer Koch = new KochComputer();
        Koch.execute(iterKoch);

    }

    public void computeMandel(View v){
        EditText edtMandelIterations = (EditText) findViewById(R.id.edtTextMandelNum);
        int iterMandel = Integer.parseInt(edtMandelIterations.getText().toString());

        if (iterMandel > MAX_ITER_MANDEL){
            String msg = "Max number of iterations = " + MAX_ITER_MANDEL;
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerFormula);
        long formulaNum = spinner.getSelectedItemId();
        Log.d("spinner", "formula num: " + formulaNum);

        interfaceControl(false);

        MandelbrotComputer Mandel = new MandelbrotComputer((int)formulaNum + 1);
        Mandel.execute(iterMandel);
    }

    public void computeTriangle(View v){
        EditText edtTriangleIterations = (EditText) findViewById(R.id.edtTextTriangleNum);
        int iterTriangle = Integer.parseInt(edtTriangleIterations.getText().toString());

        if (iterTriangle > MAX_ITER_TRIANGLE){
            String msg = "Max number of iterations = " + MAX_ITER_TRIANGLE;
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        interfaceControl(false);

        TriangleComputer Sierpinski = new TriangleComputer();
        Sierpinski.execute(iterTriangle);
    }

    private class KochComputer extends AsyncTask<Integer, Void, Void>{

        long startTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startTime = System.currentTimeMillis();
            String msg = "Started computing Koch snowflake";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int iterKoch = integers[0];

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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            long finishTime = System.currentTimeMillis();
            String msg = "Computing finished in " + (finishTime - startTime) + "ms";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(), PictureActivity.class);
            i.putExtra("type", "Koch");
            startActivity(i);
        }
    }

    private class MandelbrotComputer extends AsyncTask<Integer, Void, Void>{

        private int power;
        private long startTime;

        public MandelbrotComputer(int formulaNum){
            power = formulaNum;
        }

        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
            String msg = "Started computing Mandelbrot set";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            final int width = Constants.WIDTH;
            final int height = Constants.HEIGHT;

            int iterMandel = integers[0];

            colors = new int[width * height];

            double Ax, Bx, Ay, By;
            Ax = 2.5 / width; Bx = -1.5;
            Ay = 2.2 / height; By = -1.1;

            Complex z = new Complex();
            Complex tmp = new Complex();
            for (int x0 = 0; x0 < width; x0++)
            {
                z.setRe(Ax * x0 + Bx);
                for (int y0 = 0; y0 < height; y0++)
                {
                    z.setIm(Ay * y0 + By);
                    tmp.setRe(0);
                    tmp.setIm(0);
                    long iter = computeDepth(z, tmp, iterMandel);
                    int color = Color.rgb((int)(256 - iter*4 % 256), (int)(256 - iter * 6 % 256), (int)(256 - iter * 20 % 256));
                    colors[x0 * height + y0] = color;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            long finishTime = System.currentTimeMillis();
            String msg = "Computing finished in " + (finishTime - startTime) + "ms";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(), PictureActivity.class);
            i.putExtra("type", "Mandel");
            startActivity(i);
        }

        private int computeDepth(Complex z, Complex tmp, int maxIterations)
        {
            int res = 0;
            while ((tmp.lightAbs() < 4) && (res < maxIterations))
            {
                switch(power){
                    case 1:
                        tmp.lightPlus(z);
                        break;
                    case 2:
                        tmp.lightTimes(tmp);
                        tmp.lightPlus(z);
                        break;
                    case 3:
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightPlus(z);
                        break;
                    case 4:
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightPlus(z);
                        break;
                    case 5:
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightPlus(z);
                        break;
                    case 6:
                        tmp.lightSin();
                        tmp.lightPlus(z);
                        break;
                    case 7:
                        tmp.lightExp();
                        tmp.lightPlus(z);
                        break;
                }
                res++;
            }
            return res;
        }


    }

    private class TriangleComputer extends AsyncTask<Integer, Void, Void>{

        long startTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startTime = System.currentTimeMillis();
            String msg = "Started computing Sierpinski triangle";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            final int iterTriangle = integers[0];

            Point vertex1 = new Point(0, 0);
            Point vertex2 = new Point(1, 0);
            Point vertex3 = new Point(0.5, Math.sqrt(3.0) / 2);
            Point point = new Point(0.49, 0.51);

            points = new Point[iterTriangle];
            for (int i = 0; i < iterTriangle; i++){
                double rand = Math.random();
                if (rand < 1.0 / 3){
                    point = new Point((point.x + vertex1.x) / 2, (point.y + vertex1.y) / 2);
                } else if (rand > 1.0 / 3 && rand > 2.0 / 3){
                    point = new Point((point.x + vertex2.x) / 2, (point.y + vertex2.y) / 2);
                } else {
                    point = new Point((point.x + vertex3.x) / 2, (point.y + vertex3.y) / 2);
                }
                points[i] = point;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            long finishTime = System.currentTimeMillis();
            String msg = "Computing finished in " + (finishTime - startTime) + "ms";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(), PictureActivity.class);
            i.putExtra("type", "Triangle");
            startActivity(i);
        }
    }
}
