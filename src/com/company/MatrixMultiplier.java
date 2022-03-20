package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class MatrixMultiplier {
    public  double[][] concurrentMultiply(double[][] first, double[][] second) {
        AtomicInteger atomicInteger=new AtomicInteger(0);
        if (first[0].length != second.length)
            throw new IllegalArgumentException("Count of columns at first matrix is not equal to rows count at second matrix");
        double[][] result = new double[first.length][second[0].length];
        int i = 0, j = 0;
        while (atomicInteger.get()!=result.length  && i< result.length) {
            final int copyI = i;
            Thread thread = new Thread(() -> {
                int row = copyI;
                for (int col = 0; col < second[0].length; col++) {
                    double num=0;
                    for (int fcol = 0; fcol < second.length; fcol++) {
                        num+=first[row][fcol]*second[fcol][col];
                    }
                    result[row][col]=num;
                }
                atomicInteger.incrementAndGet();
            });

            thread.start();
            i++;
        }
        return result;
    }

    public  double[][] multiply(double[][] first, double[][] second) {
        if (first[0].length != second.length)
            throw new IllegalArgumentException("Count of columns at first matrix is not equal to rows count at second matrix");
        double[][] result = new double[first.length][second[0].length];
        int i = 0, j = 0;
        for (int row = j; row < result.length; row++) {
            for (int fCol = j; fCol < second[0].length; fCol++) {
                double num = 0;
                for (int sRow = j; sRow < second.length; sRow++) {
                    num += first[row][sRow] * second[sRow][fCol];
                }
                result[row][fCol] = num;
            }
        }
        return result;
    }
}
