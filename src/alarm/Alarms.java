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
     * @param dtt Text Version of date fit for alarms and combo boxes etc.
     * @param dt Raw date version of date fit for breaking down.
     */
    public Alarms(String dtt, LocalDateTime dt) {
        this.datetimeTXT = dtt;
        this.datetime = dt;
    }

    /**
     *
     * @return Text Version of date
     */
    public String getDTT() {
        return datetimeTXT;
    }

    /**
     *
     * @return Raw date version
     */
    public LocalDateTime getDT() {
        return datetime;
    }
    
}
