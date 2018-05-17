package alarm;

import java.util.Observable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import queuemanager.HeapPQ;
import queuemanager.PriorityQueue;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;

//import java.util.GregorianCalendar;

/**
 *
 * @author Sara
 */

public class Model extends Observable {
       
    DateTimeFormatter fmtDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime now  = LocalDateTime.now();
            
    int hour = 0;
    int minute = 0;
    int second = 0;
    String datelbl, alarmin;
    int oldSecond = 0;
    PriorityQueue<Alarms> heap;
    
    
    /**
     *
     * @param al
     */
    public Model(PriorityQueue<Alarms> q) {
        heap = q;   
    }
    
    /**
     *
     * @param al
     */
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
    
    public void heaptoArray() throws QueueUnderflowException {
        
        Alarms strarray[] = new Alarms [15];
        boolean check = false;
        int i = 0;
        for (i = 0; (i < 15 && check == false) ; i++){
            if(heap.isEmpty()){
                System.out.println("No More Alarms");
                check = true;
            } else {
                strarray[i] = heap.head();
                heap.remove();
            }
        }
        
        System.out.println (Arrays.toString(strarray));
        //System.out.println (heap.toString());
    }
    
    
    public void testData() {
        /*Alarm Test Data*/
        boolean checkFormat, checkFormat1, checkFormat2;
        String StringCheck = "[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}[ ]{1}([01]?[0-9]|2[0-3]):[0-5][0-9]";
        DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        LocalDateTime now  = LocalDateTime.now();
        
        String input = "20/05/2018 27:59";
        String input1 = "20/05/2018 09:32";
        String input2 = "20/05/2018 12:32";
        checkFormat = input.matches(StringCheck);
        checkFormat1 = input1.matches(StringCheck);
        checkFormat2 = input2.matches(StringCheck);
       
        if (checkFormat) {
            LocalDateTime alarm = LocalDateTime.parse(input,fmtDateTime);                
            if (LocalDateTime.now().isAfter(alarm)){
                {System.out.println("Cannot Set alarm before now");} 
            } else {
                long minutesBetween = MINUTES.between(now, alarm);
                int PQ = (int)minutesBetween;
                PQ = -PQ;
                //System.out.println(PQ);
                Alarms larmin = new Alarms(input,alarm);
                try {
                        heap.add(larmin, PQ);

                } catch (QueueOverflowException e) {
                    System.out.println("Add operation failed: " + e);
                } 
            }
        } else {
             System.out.println("Could not add "+input+" as this is not a valid Date or Time.");
         }    
        
        if (checkFormat1) {
            LocalDateTime alarm1 = LocalDateTime.parse(input1,fmtDateTime);
            if (LocalDateTime.now().isAfter(alarm1)){
                {System.out.println("Cannot Set alarm before now");} 
            } else {
                long minutesBetween = MINUTES.between(now, alarm1);
                int PQ = (int)minutesBetween;
                PQ = -PQ;
                //System.out.println(PQ);
                Alarms larmin1 = new Alarms(input1,alarm1);
                try {
                        heap.add(larmin1, PQ);
                    } catch (QueueOverflowException e) {
                        System.out.println("Add operation failed: " + e);
                    } 
            }
        } else {
             System.out.println("Could not add "+input1+" as this is not a valid Date or Time.");
         }
        
         if (checkFormat2) {
            LocalDateTime alarm2 = LocalDateTime.parse(input2,fmtDateTime);
            if (LocalDateTime.now().isAfter(alarm2)){
                {System.out.println("Cannot Set alarm before now");} 
            } else {
                long minutesBetween = MINUTES.between(now, alarm2);
                int PQ = (int)minutesBetween;
                PQ = -PQ;
                //System.out.println(PQ);
                Alarms larmin2 = new Alarms(input2,alarm2);
                try {
                        heap.add(larmin2, PQ);
                    } catch (QueueOverflowException e) {
                        System.out.println("Add operation failed: " + e);
                    } 
            }
         } else {
             System.out.println("Could not add "+input2+" as this is not a valid Date or Time.");
         }
        /*TEST END*/
        }
    
    public boolean chkUserInput(String tf) {
        boolean checkFormat;
        DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now  = LocalDateTime.now();
        String input = tf;
        String StringCheck = "[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}[ ]{1}([01]?[0-9]|2[0-3]):[0-5][0-9]";
        checkFormat = input.matches(StringCheck);
        
        if (checkFormat){
            LocalDateTime alarm = LocalDateTime.parse(input,fmtDateTime);
            if(LocalDateTime.now().isBefore(alarm)){
                return checkFormat = true;
            }
        }
        return checkFormat = false;
    }
    
    public void addAlarm(String dtt) {
        DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime alarm = LocalDateTime.parse(dtt,fmtDateTime);
        long minutesBetween = MINUTES.between(now, alarm);
        int PQ = (int)minutesBetween;
        PQ = -PQ;
        //System.out.println(PQ);
        Alarms larmin = new Alarms(dtt,alarm);
        try {
                heap.add(larmin, PQ);

        } catch (QueueOverflowException e) {
            System.out.println("Add operation failed: " + e);
        } 
        
    }
    
}
    
