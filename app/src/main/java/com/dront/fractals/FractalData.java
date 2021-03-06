package com.dront.fractals;

public class FractalData {

    public static final double[][] SierpinskiTriangle = new double[][]{
            {0.5, 0.0, 0.0, 0.5, 0.0, 0.0, 1.0 / 3},
            {0.5, 0.0, 0.0, 0.5, 0.5, 0.0, 1.0 / 3},
            {0.5, 0.0, 0.0, 0.5, 0.25, Math.sqrt(3.0) / 2 / 2, 1.0 / 3}
    };
    public static final int SierpinskiTriangleN = 3;



    public static final double[][] SierpinskiSquare = new double[][]{
            {0.333333, 0, 0, 0.333333, -0.333333, 0.733333, 0.125},
            {0.333333, 0, 0, 0.333333, -0.033333, 0.733333, 0.125},
            {0.333333, 0, 0, 0.333333,  0.266667, 0.733333, 0.125},
            {0.333333, 0, 0, 0.333333, -0.333333, 0.433333, 0.125},
            {0.333333, 0, 0, 0.333333,  0.266667, 0.433333, 0.125},
            {0.333333, 0, 0, 0.333333, -0.333333, 0.133333, 0.125},
            {0.333333, 0, 0, 0.333333, -0.033333, 0.133333, 0.125},
            {0.333333, 0, 0, 0.333333,  0.266667, 0.133333, 0.125}
    };
    public static final int SierpinskiSquareN = 8;



    public static final double[][] Spiral = new double[][]{
            {-0.61, 0.7, -0.7, -0.61, 0.00, 0.0, 0.9},
            {0.21, 0.0,  0.0,  0.21, 0.79, 0.0, 0.1}
    };
    public static final int SpiralN = 2;



    public static final double[][] Dragon = new double[][]{
            {0.824074, 0.281482, -0.212346,  0.864198, -1.882290, -0.110607, 0.787473},
            {0.088272, 0.520988, -0.463889, -0.377778,  0.785360,  8.095795, 0.212527}
    };
    public static final int DragonN = 2;



    public static final double[][] Fir = new double[][]{
            {0.024000,  0.000000,  0.000000, 0.432000, -0.036000, -0.748000, 0.011383},
            {0.767883,  0.014660, -0.013403, 0.839872, -0.058041,  1.703451, 0.708329},
            {-0.058172,  0.359454,  0.329910, 0.063381,  0.178422,  2.002845, 0.134255},
            {0.078732, -0.370260,  0.341029, 0.085481, -0.077863,  2.091658, 0.146031}
    };
    public static final int FirN = 4;



    public static final double[][] Maple = new double[][]{
            {0.51, -0.01,  0,   0.62, 0.25,  0.02, 0.316},
            {0.27, -0.52,  0.4, 0.36, 0,    -0.56, 0.316},
            {0.18,  0.73, -0.5, 0.26, 0.88, -0.08, 0.316},
            {0.04,  0.01, -0.5, 0,    0.52, -0.32, 0.052}
    };
    public static final int MapleN = 4;



    public static final double[][] Stairway = new double[][]{
            {0.5,   0.5,  0,    0,     0,   0.5, 0.08},
            {0.5,   0.5,  0,    0,     0,  -0.5, 0.08},
            {0,     0,    0.5,  0.5,   0.5, 0,   0.08},
            {0,     0,    0.5,  0.5,  -0.5, 0,   0.08},
            {0.85, -0.15, 0.15, 0.85,  0,   0,   0.68}
    };
    public static final int StairwayN = 5;

    public static final double Fern2[][] = {
        {0,      0,       0,      0.16,   0, 0,    0.01},
        {0.8235, 0.1629, -0.1324, 0.8977, 0, 1.6,  0.85},
        {0.2,   -0.3901,  0.23,   0.2239, 0, 1.6,  0.07},
        {-0.15,  0.28,    0.26,   0.24,   0, 0.44, 0.07}
    };
    public static final int Fern2N = 4;
}
