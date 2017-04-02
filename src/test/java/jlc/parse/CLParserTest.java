/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlc.parse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import jlc.CommandWrapper;
import jlc.commands.Command;
import jlc.commands.impl.ChangeDirectory;
import jlc.commands.impl.Slay;
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
public class CLParserTest {
    static List<String> settings = new ArrayList<>();
    public CLParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        settings.add("ls");
        settings.add("dir");
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
     * Test of analyze method, of class CLParser.
     */
    @Test
    public void testAnalyze() throws Exception {
        System.out.println("TEST METHOD \"testAnalyze()\"");
        String in = "cd dir && slay 20";
        ChangeDirectory cd = new ChangeDirectory("dir");
        cd.setSeparator("&&");
        Slay slay = new Slay(20);
        List<Command> expResult = new ArrayList<>();
        expResult.add(cd);
        expResult.add(slay);
        List<Command> result = CLParser.analyze(settings, in);
        assertEquals(expResult, result);
    }

    /**
     * Test of load method, of class CLParser.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("TEST METHOD \"testLoad()\"");
        String c = "ls";
        String[] input = {"-l"};
        int from = 0;
        int to = 1;
        String splitter = "&&";
        CommandWrapper expectedResult = new CommandWrapper(c, input);
        expectedResult.setNext(splitter);
        CommandWrapper result = CLParser.load(c, input, from, to, splitter);
        assertEquals(expectedResult, result);
    }

    /**
     * Test of parseUserInput method, of class CLParser.
     */
    @Test
    public void testParseUserInput() {
        System.out.println("TEST METHOD \"testParseUserInput():\"");
        String input = "dir && ls &";
        System.out.println("parseUserInput with: " + input);
        String[] expResult = {"dir","&&","ls","&"};
        String[] result = CLParser.parseUserInput(input);
        assertArrayEquals(expResult, result);
        
        //Test quotes
        String input2 = "git commit -m \"message and some spaces\"";
        System.out.println("parseUserInput with: " + input2);
        String[] expResult2 = {"git","commit","-m","\"message and some spaces\""};
        String[] result2 = CLParser.parseUserInput(input2);
        assertArrayEquals(expResult2, result2);
        
        //Test missing close quote
        String input3 = "git commit -m \"message and no quote closed";
        System.out.println("parsuUserInput with: " + input3);
        String[] expResult3 = {"git","commit","-m","\"message and no quote closed"};
        String[] result3 = CLParser.parseUserInput(input3);
        assertArrayEquals(expResult3, result3);
    }
    @Test
    public void testMerge() throws Exception{
        String[] s = new String[]{"Рабочий\\","стол"};
        Method m = CLParser.class.getDeclaredMethod("merge",s.getClass());
        m.setAccessible(true);
        String[] expResult = new String[]{"Рабочий стол"};
        String[] result = (String[])m.invoke(null,(Object)s);
        assertArrayEquals(expResult, result);
        
    }
    
}
