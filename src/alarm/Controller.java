package alarm;

import java.awt.event.*;
import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import queuemanager.HeapPQ;
import queuemanager.PriorityQueue;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;

/**
 *
 * @author Sara
 */
public class Controller {
    
    ActionListener listener,actionpress;
    Timer timer;
    Model model;
    MainView view;
    PriorityQueue<Alarms> heap;
    
    /**
     *
     * @param q
     * @param m
     * @param v
     */
    public Controller(PriorityQueue<Alarms> q,Model m, MainView v) {
        model = m;
        view = v;
        heap = q;
        
        /*
        LocalDateTime now  = LocalDateTime.now();
        LocalDateTime alarm = LocalDateTime.parse(input1,fmtDateTime);
        
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
            */
       
        
        
        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                                
                try {
                    view.getAlarm().setText(heap.head().getDTT());
                    
                } catch (QueueUnderflowException ex) {
                    view.getAlarm().setText("No Alarms Set");
                }
                
                model.update(String.valueOf(view.getAlarm().getText()));
                
                if(String.valueOf(model.hour).length() == 1){
                    view.getHour().setText("0"+String.valueOf(model.hour));
                } else {
                    view.getHour().setText(String.valueOf(model.hour));
                }
                
                if(String.valueOf(model.minute).length() == 1){
                    view.getMin().setText("0"+String.valueOf(model.minute));
                } else {
                    view.getMin().setText(String.valueOf(model.minute));
                }
                
                if(String.valueOf(model.second).length() == 1){
                    view.getSecond().setText("0"+String.valueOf(model.second));
                } else {
                    view.getSecond().setText(String.valueOf(model.second));
                }
                
                view.getDate().setText(model.datelbl);
                view.getAlarm().setText(String.valueOf(model.alarmin));
                
            }
            
        };
        
        actionpress = new ActionListener() {
            
            public void actionPerformed(ActionEvent ae) {
                
                if (ae.getActionCommand() == "add"){
                    
                } else if (ae.getActionCommand() == "edit"){
                
                } else if (ae.getActionCommand() == "cancel"){
                    
                }
                 System.out.println(ae.getClass());
                 
            }
        };
        
        
        view.getBtnAction().addActionListener(actionpress);
        view.getBtnCancel().addActionListener(actionpress);
        
        timer = new Timer(100, listener);
        timer.start();
    }
}