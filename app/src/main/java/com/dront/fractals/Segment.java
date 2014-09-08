package com.dront.fractals;

public class Segment {
    private double x1, y1, x2, y2;
    private double height, width;
    private double len;

    public Segment(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        countHeight();
        countWidth();
        countLength();
    }

    private void countLength(){
        len = Math.sqrt(height*height + width*width);
    }

    private void countHeight(){
        height = y2 - y1;
    }

    private void countWidth(){
        width = x2 - x1;
    }

    public double getX1(){
        return x1;
    }
    public double getX2(){
        return x2;
    }
    public double getY1(){
        return y1;
    }
    public double getY2(){
        return y2;
    }
    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }
    public double getLen() {
        return len;
    }
}
