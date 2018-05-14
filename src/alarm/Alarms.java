/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm;

import java.time.LocalDateTime;

/**
 *
 * @author Sara
 */
public class Alarms {
   
    /**
     *
     */
    protected String datetimeTXT;

    /**
     *
     */
    protected LocalDateTime datetime;

    /**
     *
     * @param dtt
     * @param dt
     */
    public Alarms(String dtt, LocalDateTime dt) {
        this.datetimeTXT = dtt;
        this.datetime = dt;
    }

    /**
     *
     * @return
     */
    public String getDTT() {
        return datetimeTXT;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getDT() {
        return datetime;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getDTT();
    }  
    
    /**
     *
     * @return
     */
    public LocalDateTime toDate() {
        return getDT();
    } 
}
