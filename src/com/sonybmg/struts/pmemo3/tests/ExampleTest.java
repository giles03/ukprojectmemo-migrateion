package com.sonybmg.struts.pmemo3.tests;

import junit.framework.*;
import net.sourceforge.groboutils.junit.v1.*;

public class ExampleTest extends TestCase {
    public ExampleTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private TestRunnable testRunnable;

    private class DelayedHello
            extends TestRunnable {

        private String name;
        private DelayedHello(
                String name) {

            
        }

        public void runTest() throws Throwable {
            long l;
            l = Math.round(2 + Math.random() * 3);

            // Sleep between 2-5 seconds
            Thread.sleep(l * 1000);
            System.out.println(
                "Delayed Hello World " + name);
        }
    }

    /**
     * You use the MultiThreadedTestRunner in
     * your test cases.  The MTTR takes an array
     * of TestRunnable objects as parameters in
     * its constructor.
     *
     * After you have built the MTTR, you run it
     * with a call to the runTestRunnables()
     * method.
     */
    public void testExampleThread()
        throws Throwable {

        //instantiate the TestRunnable classes
        TestRunnable tr1, tr2, tr3;
        tr1 = new DelayedHello("1");
        tr2 = new DelayedHello("2");
        tr3 = new DelayedHello("3");

        //pass that instance to the MTTR
        TestRunnable[] trs = {tr1, tr2, tr3};
        MultiThreadedTestRunner mttr =
            new MultiThreadedTestRunner(trs);

        //kickstarts the MTTR & fires off threads
        mttr.runTestRunnables();
    }

    /**
     * Standard main() and suite() methods
     */
    public static void main (String[] args) {
        String[] name =
            { ExampleTest.class.getName() };

        junit.textui.TestRunner.main(name);
    }

    public static Test suite() {
        return new TestSuite(ExampleTest.class);
    }
}