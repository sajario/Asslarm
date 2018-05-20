package alarm;

import java.util.Observable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import queuemanager.PriorityQueue;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;

//import java.util.GregorianCalendar;

/**
 *
 * @author Sara
 */

public class Model extends Observable {
       
    DateTimeFormatter fmtDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            .withResolverStyle(ResolverStyle.STRICT);;
    DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
    int hour = 0;
    int minute = 0;
    int second = 0;
    String datelbl, alarmin;
    int oldSecond = 0;
    PriorityQueue<Alarms> heap;
    private Object[] blah;
    
    /**
     *
     * @param q priority queue
     */
    public Model(PriorityQueue<Alarms> q) {
        heap = q;   
    }
    
    /**
     * @return boolean to see if it is indeed time for an alarm to go off
     * @param al passed next alarm
     */
    public boolean update(String al) {
        /*Get appropriate date and time elements from NOW*/
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
        
        /**
         * check to is if now is the next alarm (disregards seconds)
         */
        boolean isitnow = false;
            if (alarmin.equals(fmtDateTime.format(now))){
                    isitnow = true;
            }

        return isitnow;
    }
    
    /**
     * 
     * @return time parts of head in array
     * @throws QueueUnderflowException to cover the head element if there is an issue 
     */
    public ArrayList<String> getHead() throws QueueUnderflowException{
        ArrayList<String> strarray = new ArrayList<String>();
        
            strarray=buildPop(heap.head());

        return strarray;
    }
     
    /**
     * Builds the elements needed for the combo box and calls function to break it down 
     * @param dtt Date Time as String comes through
     * @return time parts of head in array
     */
    public ArrayList<String> comboPop(String dtt){

        DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
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
    
    
    /**
     * Builds array for one element in array in the correct setup to use in other areas
     * @param h is the head of the queue
     * @return array of time elements to be used in other functions
     */
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
    
    /**
     * This function is to get around the unchangeable library so items can 
     * be edited and deleted freely no matter where they are in the array 
     * as the library only has remove head function
     * This basically breaks down the heap from top down removing the head and 
     * inserting it into an array and for editing and deleting editable is used 
     * to compare the head and remove it without putting into array.  
     * Then the array builds back up into the heap
     * @param editable is the string for comparisons
     * @return returns array for combo box
     */
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
            } catch (QueueUnderflowException ex) {}
            
            if(heap.isEmpty()){
                for (j = 0; j < strarray.size(); j++){
                        /*Rebuild heap when the heap is empty*/
                        bi = strarray.get(j);
                        addAlarm(bi);
                    
                }
                //System.out.println("No More Alarms");
                check = true;
            } else {
                
                try {
                    /*Build array*/
                    strarray.add(heap.head().getDTT());
                    heap.remove();
                } catch (QueueUnderflowException ex) {
                    //
                }
            }
        }
        
        return strarray;

    }
    
   /**
    * Just test data
    */
    public void testData() {
        /*Alarm Test Data*/
        boolean checkFormat, checkFormat1, checkFormat2;
        String StringCheck = "[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}[ ]{1}([01]?[0-9]|2[0-3]):[0-5][0-9]";
        DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        LocalDateTime now  = LocalDateTime.now();
        
        String input = fmtDateTime.format(now.plusMinutes(10));
        String input1 = fmtDateTime.format(now.plusMinutes(5));
        String input2 = fmtDateTime.format(now.plusMinutes(1));
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
    
    /**
     * Checks the input fields to make sure the date can be formatted correctly later on
     * @param tf user input from text fields
     * @return true or false depending
     */
    public LocalDateTime chkUserInput(String tf) {
        
        String input = tf;
        
        try {
            LocalDateTime alarm = LocalDateTime.parse(input,fmtDateTime);
            /* Must be formatted correctly and be in the future*/
            if(LocalDateTime.now().isBefore(alarm)){
                return alarm;
            }
        } catch (DateTimeParseException e){}
        return (LocalDateTime.now().minusDays(1));
    }
    
    /**
     * Used to create the PQ integer that will determine queue position
     * @param dtt  passed new alarm
     */
    public void addAlarm(String dtt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime alarm = LocalDateTime.parse(dtt,fmtDateTime);
        long minutesBetween = MINUTES.between(now, alarm);
        int PQ = (int)minutesBetween;
        /*Creating negative number for MIN heap*/
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