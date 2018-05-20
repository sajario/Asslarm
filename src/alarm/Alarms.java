package alarm;

import java.time.LocalDateTime;

/**
 *
 * @author Sara
 */
public class Alarms {
   
    protected String datetimeTXT;

    protected LocalDateTime datetime;

    /**
     * Alarm constructor and methods
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
    
    /**
     * To make sure when toString is called it just prints the date
     * @return String Version of date
     */
    @Override
    public String toString() {
        return getDTT();
    }  
    
    /**
     * If date is called from alarm then it just gives the rawstuff
     * @return date version
     */
    public LocalDateTime toDate() {
        return getDT();
    } 
}
