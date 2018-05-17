package alarm;

import java.util.Observable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import queuemanager.HeapPQ;
import queuemanager.PriorityItem;
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
    private Object[] blah;
    
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
    
    public ArrayList<String> getHead() throws QueueUnderflowException{
        ArrayList<String> strarray = new ArrayList<String>();
        
            strarray=buildPop(heap.head());
        
            
        
        return strarray;
    }
    
    public int testy() {
        int i = 0;
        System.out.println(heap.toString());
        
            
        
        return i;
    }
    
    
    public ArrayList<String> buildPop(Alarms h) {
        ArrayList<String> strarray = new ArrayList<String>();
        strarray.add(h.getDTT());
        if (Integer.toString(h.getDT().getDayOfMonth()).length()==1){
            strarray.add("0"+Integer.toString(h.getDT().getDayOfMonth()));
        } else {
            strarray.add(Integer.toString(h.getDT().getDayOfMonth()));
        }
        
        if (Integer.toString(h.getDT().getMonthValue()).length()==1){
            strarray.add("0"+Integer.toString(h.getDT().getMonthValue()));
        } else {
            strarray.add(Integer.toString(h.getDT().getMonthValue()));
        }
        
        if (Integer.toString(h.getDT().getYear()).length()==1){
            strarray.add("0"+Integer.toString(h.getDT().getYear()));
        } else {
            strarray.add(Integer.toString(h.getDT().getYear()));
        }
        
        if (Integer.toString(h.getDT().getHour()).length()==1){
            strarray.add("0"+Integer.toString(h.getDT().getHour()));
        } else {
            strarray.add(Integer.toString(h.getDT().getHour()));
        }
        
        if (Integer.toString(h.getDT().getMinute()).length()==1){
            strarray.add("0"+Integer.toString(h.getDT().getMinute()));
        } else {
            strarray.add(Integer.toString(h.getDT().getMinute()));
        }
        
        return strarray;
    }
    
    public ArrayList<String> heaptoArray(String editable) {
        
        ArrayList<String> strarray = new ArrayList<String>();
        boolean check = false;
        int i= 0;
        int j = 0;
        String bi;
        for (i = 0; check == false ; i++){
            try {
                if (editable==heap.head().getDTT()){
                    heap.remove();
                }
            } catch (QueueUnderflowException ex) {
                //Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(heap.isEmpty()){
                for (j = 0; j < strarray.size(); j++){
                    
                        bi = strarray.get(j);
                        addAlarm(bi);
                    
                }
                //System.out.println("No More Alarms");
                check = true;
                return strarray;
            } else {
                
                try {
                    strarray.add(heap.head().getDTT());
                    heap.remove();
                } catch (QueueUnderflowException ex) {
                    //
                }
            }
        }
        
        
        
        
        return strarray;

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
    
    public ArrayList<String> comboPop(String dtt){
        DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime alarm = LocalDateTime.parse(dtt,fmtDateTime);
        long minutesBetween = MINUTES.between(now, alarm);
        int PQ = (int)minutesBetween;
        PQ = -PQ;
        
        //System.out.println(PQ);
        Alarms larmin = new Alarms(dtt,alarm);
        
        ArrayList<String> strarray = new ArrayList<String>();
        strarray=buildPop(larmin);
        return strarray;
    }
    
    
}
    
