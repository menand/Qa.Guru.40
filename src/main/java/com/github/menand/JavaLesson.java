package com.github.menand;

public class JavaLesson {
    public static void main(String[] args) {
        int i1 = 10;
        int i2 = -10;
        double d1 = 0.1;
        double dmax = Double.MAX_VALUE;
        int imax = Integer.MAX_VALUE;
        int imin = Integer.MIN_VALUE;

        System.out.println(i1 + i2);
        System.out.println(i1 - i2);
        System.out.println(i1 * i2);
        System.out.println(i1 / i2);
        System.out.println(i1 / d1);
        System.out.println(i1 * d1);
        System.out.println(i1 < d1);
        System.out.println(i1 > i2);
        System.out.println(i1 >= dmax);
        System.out.println(i1 <= dmax);
        System.out.println(--imin);
        System.out.println(++imax);
        System.out.println(dmax * dmax);
    }
}
