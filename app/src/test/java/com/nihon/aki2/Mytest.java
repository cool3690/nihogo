package com.nihon.aki2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Mytest {
    Change mychange;
    @Before
    public void setup(){
        mychange=new Change();
    }
    @Test
    public void addition_add() {
      double a=  mychange.sum;
      // String str=mychange.input.getText().toString();
        System.out.println(a );
        assertEquals(4, 2 + 2);

    }
}