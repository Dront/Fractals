package com.dront.fractals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Util.Complex;


public class MainActivity extends Activity {

    private static final int MAX_ITER_KOCH = 9;
    private static final int MAX_ITER_MANDEL = 500;
    private static final int MAX_ITER_JULIA = 500;
    private static final int MAX_ITER_IFS = 1000000;

    public static ArrayList<Segment> segments;
    public static int[] colors;
    public static Point[] points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        segments = null;
        colors = null;
        points = null;

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

        JuliaComputer Julia = new JuliaComputer((int)formulaNum + 1);
        Julia.execute(iterJulia);
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
            default:
                String msg = "Wrong element chosen from IFSSpinner";
                Log.d(LogTags.APP, msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                return;
        }

        IFSComputer IFS = new IFSComputer(attractors, N);
        IFS.execute(iterIFS);
    }

    private class KochComputer extends AsyncTask<Integer, Void, Void>{

        long startTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startTime = System.currentTimeMillis();
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

    private class JuliaComputer extends AsyncTask<Integer, Void, Void>{

        private int power;
        private long startTime;

        public JuliaComputer(int formulaNum){
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

            int iterJulia = integers[0];

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
                    int iter = computeDepth(z, tmp, iterJulia);
                    int r = iter*iter % 256;
                    int g = (iter  + iter) % 256;
                    int b = (iter + iter * iter) % 256;
                    int color = Color.rgb(r, g, b);
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
            i.putExtra("type", "Julia");
            startActivity(i);
        }

        private int computeDepth(Complex z, Complex tmp, int maxIterations)
        {
            int res = 0;
            tmp.setRe(z.re());
            tmp.setIm(z.im());
            Complex c = new Complex(-0.8, 0.2);
            while ((tmp.lightAbs() < 4) && (res < maxIterations))
            {
                switch(power){
                    case 1:
                        tmp.lightPlus(c);
                        break;
                    case 2:
                        tmp.lightTimes(tmp);
                        tmp.lightPlus(c);
                        break;
                    case 3:
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightPlus(c);
                        break;
                    case 4:
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightPlus(c);
                        break;
                    case 5:
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
                        tmp.lightTimes(tmp);
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

        public static final int ROW_SIZE = 7;

        private long startTime;
        final int N;
        final double [][] IFSData;

        public IFSComputer(double[][] attractors, int N){
            this.N = N;
            IFSData = attractors;
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

            Point minValues = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
            Point maxValues = new Point(-Double.MAX_VALUE, -Double.MAX_VALUE);

            points = new Point[IFSIterations];
            for (int i = 0; i < IFSIterations; i++) {
                int num = getFunctionNumber();
                double a = IFSData[num][0];
                double b = IFSData[num][1];
                double c = IFSData[num][2];
                double d = IFSData[num][3];
                double e = IFSData[num][4];
                double f = IFSData[num][5];
                double newX = a * point.x + b * point.y + e;
                double newY = c * point.x + d * point.y + f;
                point = new Point(newX, newY);
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

            Point scale = new Point(maxValues.x - minValues.x, maxValues.y - minValues.y);
            for (Point tmp: points){
                tmp.minus(minValues);
                tmp.divide(scale);
            }

            return null;
        }

        private int getFunctionNumber(){
            double rand = Math.random();
            double lowBorder = 0;
            double highBorder = 0;
            for (int i = 0; i < N; i++){
                lowBorder = countProbSum(i);
                highBorder = countProbSum(i + 1);
                if (rand > lowBorder && rand < highBorder){
                    return i;
                }
            }
            DecimalFormat df = new DecimalFormat("#.##");
            Log.d(LogTags.APP, "Wrong func number: " + df.format(lowBorder) + " " + df.format(highBorder) + " " + df.format(rand));
            return (int) (rand * N);
        }

        private double countProbSum(int num){
            double sum = 0;
            for (int i = 0; i < num; i++){
                sum += IFSData[i][ROW_SIZE - 1];
            }
            return sum;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            long finishTime = System.currentTimeMillis();
            String msg = "Computing finished in " + (finishTime - startTime) + "ms";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(), PictureActivity.class);
            i.putExtra("type", "IFS");
            startActivity(i);
        }

    }
}
