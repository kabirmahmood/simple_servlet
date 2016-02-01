package com.mycompany.mywebapp.web;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SimpleServletTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimpleServletTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SimpleServletTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testC()
    {
        assertTrue( true );
    }
    
    public void testB(){
        String expected = "Hello, JUnit";
        String hello = "Hello, JUnit";
        assertEquals(hello, expected);

    }

   public void testA(){

        assertTrue(true);
   }    



}
