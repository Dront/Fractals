package com.dront.fractals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private EditText edtKochIterations;

    public static ArrayList<Segment> segments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInterfaceResources();

        segments = new ArrayList<Segment>();
    }

    private void getInterfaceResources(){
        edtKochIterations = (EditText) findViewById(R.id.edtTextKochNum);
    }

    public void DrawKoch(View v){
        int iterKoch = Integer.parseInt(edtKochIterations.getText().toString());
        if (iterKoch > 7){
            String msg = "Max number of iterations = 7";
            Log.d(LogTags.APP, msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        segments.clear();
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

}
