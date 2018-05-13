package alarm;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.*;


public class Clock {

    public static void main(String[] args) {
        
        /*DATE TIME TESTING*/
        DateTimeFormatter fmtDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter fmtTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        long start = (System.currentTimeMillis()/1000)/60;
        int change = (int)start;
        System.out.println(start);
        System.out.println(change);

        LocalDateTime now  = LocalDateTime.now();
        
        int nowsec = now.toLocalTime().toSecondOfDay();
        
       
        System.out.println(now);
        String input = "13/05/2018 23:59:02";
        String input1 = "14/05/2018 00:00:01";
        String input2 = "23:59:00";
        String input3 = "00:00:01";

        
        
       // LocalTime time = LocalTime.parse(input, fmtDateTime);
        LocalDateTime time = LocalDateTime.parse(input,fmtDateTime);
        LocalDateTime alarm = LocalDateTime.parse(input1,fmtDateTime);
        
        
        
        //System.out.println("Days "+daysBetween);
        /*
        LocalTime time2 = LocalTime.parse(input2,fmtTime);
        LocalTime time3 = LocalTime.parse(input3,fmtTime);

        int time4 = alarm.toLocalTime().toSecondOfDay();
        
        int sec = time2.toSecondOfDay();
        int sec1 = time3.toSecondOfDay();

        
        System.out.println("Seconds Past" +sec+" stuff "+sec1);*/
        //Use this to compare dates and can be for checking the alarm is set for the future and then concatonate with seconds to create PQ Integer
        if (LocalDateTime.now().isAfter(alarm))
        {System.out.println("Cannot Set alarm before now");} 
        else {
            long daysBetween = DAYS.between(now, alarm)+1;
            long minutesBetween = MINUTES.between(now, alarm);
            String build = ""+daysBetween+minutesBetween;
            int PQ = Integer.parseInt( build );
            System.out.println(alarm);
        }

       

       

        Model model = new Model();
        View view = new View(model);
        model.addObserver(view);
        Controller controller = new Controller(model, view);
    }
}
