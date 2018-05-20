/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sara
 */
public class ModelTest {
    
    public ModelTest() {
    }

    /**
     * Test of update method, of class Model.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String al = "";
        Model instance = null;
        boolean expResult = false;
        boolean result = instance.update(al);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHead method, of class Model.
     */
    @Test
    public void testGetHead() throws Exception {
        System.out.println("getHead");
        Model instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getHead();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testy method, of class Model.
     */
    @Test
    public void testTesty() {
        System.out.println("testy");
        Model instance = null;
        int expResult = 0;
        int result = instance.testy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildPop method, of class Model.
     */
    @Test
    public void testBuildPop() {
        System.out.println("buildPop");
        Alarms h = null;
        Model instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.buildPop(h);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of heaptoArray method, of class Model.
     */
    @Test
    public void testHeaptoArray() {
        System.out.println("heaptoArray");
        String editable = "";
        Model instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.heaptoArray(editable);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testData method, of class Model.
     */
    @Test
    public void testTestData() {
        System.out.println("testData");
        Model instance = null;
        instance.testData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chkUserInput method, of class Model.
     */
    @Test
    public void testChkUserInput() {
        System.out.println("chkUserInput");
        String tf = "";
        Model instance = null;
        boolean expResult = false;
        boolean result = instance.chkUserInput(tf);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAlarm method, of class Model.
     */
    @Test
    public void testAddAlarm() {
        System.out.println("addAlarm");
        String dtt = "";
        Model instance = null;
        instance.addAlarm(dtt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of comboPop method, of class Model.
     */
    @Test
    public void testComboPop() {
        System.out.println("comboPop");
        String dtt = "";
        Model instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.comboPop(dtt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
