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
   

    protected String datetimeTXT;
    protected LocalDateTime datetime;

    public Alarms(String dtt, LocalDateTime dt) {
        this.datetimeTXT = dtt;
        this.datetime = dt;
    }

    public String getDTT() {
        return datetimeTXT;
    }

    public LocalDateTime getDT() {
        return datetime;
    }

    @Override
    public String toString() {
        return getDTT();
    }  
    
    public LocalDateTime toDate() {
        return getDT();
    } 
}
