/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm;

import java.time.LocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sara
 */
public class AlarmsTest {
    
    public AlarmsTest() {
    }

    /**
     * Test of getDTT method, of class Alarms.
     */
    @Test
    public void testGetDTT() {
        System.out.println("getDTT");
        Alarms instance = null;
        String expResult = "";
        String result = instance.getDTT();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDT method, of class Alarms.
     */
    @Test
    public void testGetDT() {
        System.out.println("getDT");
        Alarms instance = null;
        LocalDateTime expResult = null;
        LocalDateTime result = instance.getDT();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
