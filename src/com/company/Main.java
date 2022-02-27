package com.company;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        int n=200;
        double[][] first = generate(n);
        double[][] second = generate(n);
        long sBegin = System.currentTimeMillis();

        double[][] concMultiply=concurrentMultiply(first,second);
        long sEnd= System.currentTimeMillis();
        System.out.printf("multithreading = %d \n",sEnd-sBegin);
        double[][] multiply = multiply(first, second);
        long concurrentEnd= System.currentTimeMillis();
        System.out.printf("simple = %d\n",concurrentEnd-sEnd);
        System.out.println(checkResultsEqual(concMultiply,multiply));
    }


public static boolean checkResultsEqual(double[][] arr, double[][]arr2){
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr[0].length; j++) {
            if(arr[i][j]!=arr2[i][j])
                return false;
        }
    }
    return true;
}


    public static double[][] generate(int n){
        double[][] array=new double[n][n];
        Random random=new Random(new Date().getTime());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j]=random.nextInt(10000);
            }
        }
        return array;
    }

    public static double[][] concurrentMultiply(double[][] first, double[][] second) {
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

    public static double[][] multiply(double[][] first, double[][] second) {
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
