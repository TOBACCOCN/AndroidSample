package com.example.sample.common;

public @interface DimensionDef {

    int[] value() default {};

    int PX = 0;
    int DP = 1;
    int SP = 2;

}
