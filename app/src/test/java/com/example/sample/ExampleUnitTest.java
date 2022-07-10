package com.example.sample;

import com.example.sample.common.Dimension;
import com.example.sample.common.DimensionDef;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void use_dimension_is_correct() {
        int i = use_dimension_is_correct_inner(DimensionDef.PX, DimensionDef.SP);
        assertTrue(i == DimensionDef.PX || i == DimensionDef.DP || i == DimensionDef.SP);
        i = use_dimension_is_correct_inner(DimensionDef.SP + 1, DimensionDef.SP + 2);
        assertTrue(i != DimensionDef.PX && i != DimensionDef.DP && i != DimensionDef.SP);
    }

    @Dimension
    public int use_dimension_is_correct_inner(int from, int to) {
        int i = new Random().nextInt(to - from + 1) + from;
        System.out.println(i);
        return i;
    }

}