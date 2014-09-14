package com.dront.fractals;

public class Point {

    public double x, y;

    public Point(double tx, double ty){
        x = tx;
        y = ty;
    }

    public void minus(final Point p){
        x = x - p.x;
        y = y - p.y;
    }

    public void divide(final Point p){
        x = x / p.x;
        y = y / p.y;
    }

}
