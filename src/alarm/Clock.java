package alarm;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.*;
import queuemanager.HeapPQ;

import queuemanager.PriorityQueue;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;


public class Clock {

    public static void main(String[] args) throws QueueUnderflowException {
        
        /*DATE TIME TESTING*/
        PriorityQueue<Alarms> q;
        q = new HeapPQ<>(15);
        DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        long start = (System.currentTimeMillis()/1000)/60;
        int change = (int)start;
        System.out.println(start);
        System.out.println(change);

        LocalDateTime now  = LocalDateTime.now();
        
        int nowsec = now.toLocalTime().toSecondOfDay();
        
       
        System.out.println(now);
        String input = "13/05/2018 23:59";
        String input1 = "14/05/2018 14:32";
        String input2 = "23:59:00";
        String input3 = "00:00:01";

        
        
       // LocalTime time = LocalTime.parse(input, fmtDateTime);
        LocalDateTime time = LocalDateTime.parse(input,fmtDateTime);
        LocalDateTime alarm = LocalDateTime.parse(input1,fmtDateTime);
        
        //Use this to compare dates and can be for checking the alarm is set for the future and then concatonate with seconds to create PQ Integer
        if (LocalDateTime.now().isAfter(alarm))
        {System.out.println("Cannot Set alarm before now");} 
        else {
            long daysBetween = DAYS.between(now, alarm)+1;
            long minutesBetween = MINUTES.between(now, alarm);
            String build = ""+daysBetween+minutesBetween;
            int PQ = Integer.parseInt( build );
            System.out.println(PQ);
            Alarms larmin = new Alarms(input1,alarm);
            try {
                    q.add(larmin, PQ);
                } catch (QueueOverflowException e) {
                    System.out.println("Add operation failed: " + e);
                }
            
            
        }

       
        String head = q.head().getDTT();
       

        Model model = new Model(head);
        //View view = new View(model);
        MainView view = new MainView(model);
        
        model.addObserver(view);
        Controller controller = new Controller(model, view);
        view.setVisible(true);
        
    }
}
