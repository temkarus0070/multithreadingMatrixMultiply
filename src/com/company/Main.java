package com.company;

import java.util.Date;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        MatrixMultiplier matrixMultiplier=new MatrixMultiplier();
        int n=200;
        double[][] first = generate(n);
        double[][] second = generate(n);
        long sBegin = System.currentTimeMillis();

        double[][] concMultiply=matrixMultiplier.concurrentMultiply(first,second);
        long sEnd= System.currentTimeMillis();
        System.out.printf("multithreading = %d \n",sEnd-sBegin);
        double[][] multiply = matrixMultiplier.multiply(first, second);
        long concurrentEnd= System.currentTimeMillis();
        System.out.printf("simple = %d\n",concurrentEnd-sEnd);
        System.out.println("results are equal = "+checkResultsEqual(concMultiply,multiply));
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






}
