package alarm;

import java.util.Observable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.logging.Level;
import java.util.logging.Logger;
import queuemanager.PriorityQueue;

//import java.util.GregorianCalendar;

public class Model extends Observable {
       
    DateTimeFormatter fmtDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    /*TEST*/
    LocalDateTime now  = LocalDateTime.now();
        /*Test End*/
            
    int hour = 0;
    int minute = 0;
    int second = 0;
    String datelbl, alarmin;
    int oldSecond = 0;
    
    public Model(String al) {
        update(al);
    }
    
    public void update(String al) {
        LocalDateTime now = LocalDateTime.now();
        
            
        
        datelbl = now.format(fmtDate);
        hour = now.getHour();
        minute = now.getMinute();
        oldSecond = second;
        second = now.getSecond();
        alarmin = al;
        if (oldSecond != second) {
            setChanged();
            notifyObservers();
        }
    }
}