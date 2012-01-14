package org.duckweedcoll;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Sum_Test {

    Sum sum = new Sum();

    @Test
    public void test_fieldsSet_sumsCorrectly() {
        sum.setA(1);
        sum.setB(2);
        assertEquals(1 + 2, sum.sum());
    }

    @Test
    public void test_aNotSet_sumsToB() {
        sum.setB(2);
        assertEquals(2, sum.sum());
    }

    @Test
    public void test_bNotSet_sumsToA() {
        sum.setA(2);
        assertEquals(2, sum.sum());
    }

    @Test
    public void test_fieldsNotSet_sumsToZero() {
        assertEquals(0, sum.sum());
    }


}
