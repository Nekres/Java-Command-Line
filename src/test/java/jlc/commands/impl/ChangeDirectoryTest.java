/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.commands.impl;

import jlc.exceptions.BadCommandArgumentException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author desolation
 */
public class ChangeDirectoryTest {
    
    public ChangeDirectoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("user.dir", "/home");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of call method, of class ChangeDirectory.
     */
    @Test
    public void testCall() throws Exception {
        System.out.println("Testing method call");
        ChangeDirectory instance = new ChangeDirectory("..");
        Boolean expResult = true;
        Boolean result = instance.call();
        assertEquals(expResult, result);
    }
    @Test
    public void testChdir() throws BadCommandArgumentException{
        System.out.println("Testing method chdir()");
        ChangeDirectory cd = new ChangeDirectory("home");
        String expectedResult = "/home";
        String result = cd.chdir("/");
        assertEquals(expectedResult, result);
        
        ChangeDirectory cd2 = new ChangeDirectory("..");
        String expectedResult2 = "/";
        String result2 = cd2.chdir("/home");
        assertEquals(expectedResult2, result2);
        System.out.println("End of testing ");
    }
    
}
