package com.app.azza.builditbigger;

import android.test.AndroidTestCase;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends AndroidTestCase {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testJokeFetched() {

        try {
            EndpointsAsyncTask myTask = new EndpointsAsyncTask();
            myTask.execute();

            String joke = myTask.get(5000, TimeUnit.MILLISECONDS);
            assertTrue(!joke.isEmpty());

        } catch (Exception e) {
            fail("Time out exceed 5 seconds");
        }
    }
}