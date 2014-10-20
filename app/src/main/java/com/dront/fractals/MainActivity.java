package com.dront.fractals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import Util.Complex;


public class MainActivity extends Activity {

    private static final int MAX_ITER_KOCH = 9;
    private static final int MAX_ITER_MANDEL = 500;

    private static final int MAX_ITER_JULIA = 500;
    private static final int MAX_SLIDES_JULIA = 8;

    private static final int MAX_ITER_IFS = 1000000;
    private static final int MAX_SLIDES_IFS = 10;

    public static Bitmap[] pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pic != null){
            for (Bitmap tmp: pic){
               tmp.recycle();
            }

        }

        interfaceControl(true);
    }

    private void interfaceControl(boolean state){
        Button btnKoch = (Button) findViewById(R.id.btnDrawKoch);
        Button btnMandel = (Button) findViewById(R.id.btnDrawMandel);
        Button btnJulia = (Button) findViewById(R.id.btnDrawJulia);
        Button btnIFS = (Button) findViewById(R.id.btnDrawIFS);

        btnKoch.setEnabled(state);
        btnMandel.setEnabled(state);
        btnJulia.setEnabled(state);
        btnIFS.setEnabled(state);
    }

    public void computeKoch(View v){
        EditText edtKochIterations = (EditText) findViewById(R.id.edtTextKochNum);
        int iterKoch;

        try{
            iterKoch = Integer.parseInt(edtKochIterations.getText().toString());
        } catch(Exception e){
            Log.d(LogTags.APP, e.getMessage());
            String msg = "Must be integer value > 0 and < " + MAX_ITER_KOCH;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (iterKoch > MAX_ITER_KOCH){
            String msg = "Must be integer value > 0 and < " + MAX_ITER_KOCH;
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        interfaceControl(false);

        pic = new Bitmap[1];
        KochComputer Koch = new KochComputer();
        Koch.execute(iterKoch);

    }

    public void computeMandel(View v){
        EditText edtMandelIterations = (EditText) findViewById(R.id.edtTextMandelNum);
        int iterMandel;

        try{
            iterMandel = Integer.parseInt(edtMandelIterations.getText().toString());
        } catch (Exception e){
            Log.d(LogTags.APP, e.getMessage());
            String msg = "Must be integer value > 0 and < " + MAX_ITER_MANDEL;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (iterMandel > MAX_ITER_MANDEL || iterMandel< 1){
            String msg = "Must be integer value > 0 and < " + MAX_ITER_MANDEL;
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerFormulaMandel);
        long formulaNum = spinner.getSelectedItemId();

        interfaceControl(false);

        pic = new Bitmap[1];
        MandelbrotComputer Mandel = new MandelbrotComputer((int)formulaNum + 1);
        Mandel.execute(iterMandel);
    }

    public void computeJulia(View v){
        EditText edtJuliaIterations = (EditText) findViewById(R.id.edtTextMandelNum);
        int iterJulia;

        try{
            iterJulia = Integer.parseInt(edtJuliaIterations.getText().toString());
        } catch (Exception e){
            Log.d(LogTags.APP, e.getMessage());
            String msg = "Must be integer value > 0 and < " + MAX_ITER_JULIA;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (iterJulia > MAX_ITER_MANDEL || iterJulia< 1){
            String msg = "Must be integer value > 0 and < " + MAX_ITER_JULIA;
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerFormulaJulia);
        long formulaNum = spinner.getSelectedItemId();

        interfaceControl(false);

        pic = new Bitmap[MAX_SLIDES_JULIA];
        for (int i = 0; i < MAX_SLIDES_JULIA; i++){
            JuliaComputer Julia = new JuliaComputer((int)formulaNum + 1, i);
            Julia.execute(iterJulia);
        }
    }

    public void computeIFS(View v){
        EditText edtIFSIterations = (EditText) findViewById(R.id.edtTextIFSNum);

        int iterIFS;
        try{
            iterIFS = Integer.parseInt(edtIFSIterations.getText().toString());
        } catch (Exception e){
            Log.d(LogTags.APP, e.getMessage());
            String msg = "Must be integer value > 0 and < " + MAX_ITER_IFS;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        if (iterIFS > MAX_ITER_IFS || iterIFS < 1){
            String msg = "Must be integer value > 0 and < " + MAX_ITER_IFS;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerIFS);
        long fractalNum = spinner.getSelectedItemId() + 1;
        Log.d("spinner", "fractal num: " + fractalNum);

        interfaceControl(false);

        int N;
        double[][] attractors;

        switch ((int)fractalNum){
            case 1:
                N = FractalData.SierpinskiTriangleN;
                attractors = FractalData.SierpinskiTriangle;
                break;
            case 2:
                N = FractalData.SierpinskiSquareN;
                attractors = FractalData.SierpinskiSquare;
                break;
            case 3:
                N = FractalData.DragonN;
                attractors = FractalData.Dragon;
                break;
            case 4:
                N = FractalData.SpiralN;
                attractors = FractalData.Spiral;
                break;
            case 5:
                N = FractalData.FirN;
                attractors = FractalData.Fir;
                break;
            case 6:
                N = FractalData.MapleN;
                attractors = FractalData.Maple;
                break;
            case 7:
                N = FractalData.StairwayN;
                attractors = FractalData.Stairway;
                break;
            case 8:
                N = FractalData.Fern2N;
                attractors = FractalData.Fern2;
                break;
            default:
                String msg = "Wrong element chosen from IFSSpinner";
                Log.d(LogTags.APP, msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                return;
        }

        pic = new Bitmap[MAX_SLIDES_IFS];
        for (int i = 0; i < MAX_SLIDES_IFS; i++){
            IFSComputer IFS = new IFSComputer(attractors, N, i);
            IFS.execute(iterIFS);
        }

    }

    private class KochComputer extends AsyncTask<Integer, Void, Void>{

        private static final int SIZE = 500;

        long startTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startTime = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int iterKoch = integers[0];

            ArrayList<Segment> segments = new ArrayList<Segment>();
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

            pic[0] = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(pic[0]);

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(3.0f);
            paint.setStyle(Paint.Style.STROKE);

            for (Segment seg: segments){
                canvas.drawLine(SIZE * (float)seg.getX1(), SIZE * (float)seg.getY1(),
                        SIZE * (float)seg.getX2(), SIZE * (float)seg.getY2(), paint);
            }

            segments.clear();

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

        private final int power;
        private long startTime;

        public MandelbrotComputer(int formulaNum){
            power = formulaNum;
        }

        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            final int width = Constants.WIDTH;
            final int height = Constants.HEIGHT;

            int iterMandel = integers[0];

            pic[0] = Bitmap.createBitmap(Constants.WIDTH, Constants.HEIGHT, Bitmap.Config.ARGB_8888);

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
                    int color = Color.rgb((int)(iter * 4 % 256), (int)(iter * 6 % 256), (int)(iter * 20 % 256));
                    pic[0].setPixel(x0, y0, color);
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

        private int computeDepth(Complex z, Complex tmp, int maxIterations){
            int res = 0;
            while ((tmp.lightAbs() < 4) && (res < maxIterations))
            {
                switch(power){
                    case 1:
                        tmp.lightPlus(z);
                        break;
                    case 2:
                        tmp.selfTimes();
                        tmp.lightPlus(z);
                        break;
                    case 3:
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.lightPlus(z);
                        break;
                    case 4:
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.lightPlus(z);
                        break;
                    case 5:
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.selfTimes();
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

    private class JuliaComputer extends AsyncTask<Integer, Void, Void>{

        private int power;
        private int picNum;
        private long startTime;

        public JuliaComputer(int formulaNum, int picNum){
            power = formulaNum;
            this.picNum = picNum;
        }

        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            final int width = Constants.WIDTH;
            final int height = Constants.HEIGHT;

            int iterJulia = integers[0];

            pic[picNum] = Bitmap.createBitmap(Constants.WIDTH, Constants.HEIGHT, Bitmap.Config.ARGB_8888);

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
                    int iter = computeDepth(z, tmp, iterJulia);
                    int r = iter * iter % 256;
                    int g = (iter + iter) % 256;
                    int b = (iter + iter * iter) % 256;
                    pic[picNum].setPixel(x0, y0, Color.rgb(r, g, b));
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

            if (picNum == MAX_SLIDES_JULIA - 1){
                Intent i = new Intent(getApplicationContext(), PictureActivity.class);
                i.putExtra("count", MAX_SLIDES_JULIA);
                startActivity(i);
            }
        }

        private int computeDepth(Complex z, Complex tmp, int maxIterations){
            int res = 0;
            tmp.setRe(z.re());
            tmp.setIm(z.im());
            Complex c = new Complex(-0.8 + (double)picNum / 60.0, 0.2 + (double)picNum / 60.0);
            while ((tmp.lightAbs() < 4) && (res < maxIterations))
            {
                switch(power){
                    case 1:
                        tmp.lightPlus(c);
                        break;
                    case 2:
                        tmp.selfTimes();
                        tmp.lightPlus(c);
                        break;
                    case 3:
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.lightPlus(c);
                        break;
                    case 4:
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.lightPlus(c);
                        break;
                    case 5:
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.selfTimes();
                        tmp.lightPlus(c);
                        break;
                    case 6:
                        tmp.lightSin();
                        tmp.lightPlus(c);
                        break;
                    case 7:
                        tmp.lightExp();
                        tmp.lightPlus(c);
                        break;
                }
                res++;
            }
            return res;
        }


    }

    public class IFSComputer extends AsyncTask<Integer, Void, Void>{

        private static final int ROW_SIZE = 7;
        private static final int FIRST_ITERATIONS = 50;
        private static final double SHIFT = 0.1;
        private static final int SIZE = 1000;

        private long startTime;
        private final int N;
        private final double [][] IFSData;
        private double[] prob;
        private int picNum = 0;

        public IFSComputer(double[][] attractors, int N, int picNum){
            this.N = N;
            IFSData = attractors;
            this.picNum = picNum;

            prob = new double[N + 1];

            prob[0] = 0;
            double sum = 0;
            for (int i = 0; i < N; i++){
                prob[i + 1] = sum += IFSData[i][ROW_SIZE - 1];
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startTime = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            final int IFSIterations = integers[0];
            Point point = new Point(0.5, 0.5);

            for (int i = 0; i < FIRST_ITERATIONS; i++){
                point = iterate(point);
            }

            Point minValues = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
            Point maxValues = new Point(-Double.MAX_VALUE, -Double.MAX_VALUE);

            Point[] points = new Point[IFSIterations];
            for (int i = 0; i < IFSIterations; i++) {
                point = iterate(point);

                if (point.x > maxValues.x){
                    maxValues.x = point.x;
                }
                if (point.y > maxValues.y){
                    maxValues.y = point.y;
                }
                if (point.x < minValues.x){
                    minValues.x = point.x;
                }
                if (point.y < minValues.y){
                    minValues.y = point.y;
                }
                points[i] = point;
            }

            pic[picNum] = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);

            minValues.x -= SHIFT / 2;
            minValues.y -= SHIFT / 2;
            maxValues.x += SHIFT / 2;
            maxValues.y += SHIFT / 2;
            Point scale = new Point(maxValues.x - minValues.x, maxValues.y - minValues.y);
            for (Point tmp: points){
                tmp.minus(minValues);
                tmp.divide(scale);
                pic[picNum].setPixel((int)(tmp.x * SIZE), (int)(tmp.y * SIZE), Color.RED);
            }

            return null;
        }

        private Point iterate(final Point point){
            int num = getFunctionNumber();
            double a = IFSData[num][0];
            double b = IFSData[num][1];
            double c = IFSData[num][2];
            double d = IFSData[num][3];
            double e = IFSData[num][4] + (double)(picNum - MAX_SLIDES_IFS / 2) / 10.0;
            double f = IFSData[num][5] - (double)(picNum - MAX_SLIDES_IFS / 2) / 10.0;
            double newX = a * point.x + b * point.y + e;
            double newY = c * point.x + d * point.y + f;

            return new Point(newX, newY);
        }

        private int getFunctionNumber(){
            double rand = Math.random();
            for (int i = 0; i < N; i++){
                if (rand >  prob[i]&& rand < prob[i + 1]){
                    return i;
                }
            }
            Log.d(LogTags.APP, "Wrong func number");
            return (int) (rand * N);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            long finishTime = System.currentTimeMillis();
            String msg = "Computing finished in " + (finishTime - startTime) + "ms";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            if (picNum == MAX_SLIDES_IFS - 1){
                Intent i = new Intent(getApplicationContext(), PictureActivity.class);
                i.putExtra("count", MAX_SLIDES_IFS);
                startActivity(i);
            }
        }

    }
}
