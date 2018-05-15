package alarm;

import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
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
    
    ActionListener listener,actionpress,menuitem;
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
                        q.add(larmin, PQ);

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
                        q.add(larmin1, PQ);
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
                        q.add(larmin2, PQ);
                    } catch (QueueOverflowException e) {
                        System.out.println("Add operation failed: " + e);
                    } 
            }
         } else {
             System.out.println("Could not add "+input2+" as this is not a valid Date or Time.");
         }
        /*TEST END*/
        String str = "";
        String strarray[] = heap.toString().split(" ") ;
        
        
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
                
                if (null != ae.getActionCommand())switch (ae.getActionCommand()) {
                    case "Add":
                        
                        break;
                    case "Edit":
                        
                        break;
                    case "Cancel":
                        view.gettxtDD().setText("");
                        view.gettxtHH().setText("");
                        view.gettxtMin().setText("");
                        view.gettxtMM().setText("");
                        view.gettxtYY().setText("");
                        view.getbtnDelete().setVisible(false);
                        view.getActionDi().setVisible(false);
                        break;
                    case "Delete":
                        
                        break;
                    default:
                        break;
                }                 
            }
        };
        
        menuitem = new ActionListener() {
            
            public void actionPerformed(ActionEvent ae) {
                
                if (null != ae.getActionCommand())switch (ae.getActionCommand()) {
                    case "Add":
                        view.getPanel().getComponents().toString();
                        
                        view.getActionDi().pack();
                        view.gettitleAction().setText("Add Alarm");
                        view.getlblChoose().setText("or choose existing alarm>>");
                        view.getbtnAction().setText("Add");
                        view.getbtnDelete().setVisible(false);
                        view.getActionDi().setVisible(true);
                        break;
                    case "Edit":
                        view.getActionDi().pack();
                        view.gettitleAction().setText("Edit Alarm");
                        view.getlblChoose().setText("choose existing alarm>>");
                        view.getbtnAction().setText("Edit");
                        view.getbtnDelete().setVisible(true);
                        view.getActionDi().setVisible(true);
                {
                    try {
                        view.gettxtDD().setText(Integer.toString(heap.head().getDT().getDayOfMonth()));
                    } catch (QueueUnderflowException ex) {
                        view.gettxtDD().setText("");
                    }
                }
                
                {
                    try {
                        view.gettxtMM().setText(Integer.toString(heap.head().getDT().getMonthValue()));
                    } catch (QueueUnderflowException ex) {
                        view.gettxtMin().setText("");
                    }
                }
                
                {
                    try {
                        view.gettxtYY().setText(Integer.toString(heap.head().getDT().getYear()));
                    } catch (QueueUnderflowException ex) {
                        view.gettxtYY().setText("");
                    }
                }
                
                {
                    try {
                        view.gettxtHH().setText(Integer.toString(heap.head().getDT().getHour()));
                    } catch (QueueUnderflowException ex) {
                        view.gettxtHH().setText("");
                    }
                }
                
                {
                    try {
                        view.gettxtMin().setText(Integer.toString(heap.head().getDT().getMinute()));
                    } catch (QueueUnderflowException ex) {
                        view.gettxtMin().setText("");
                    }
                }
                        
                        break;
                    case "Export":
                        
                        break;
                    
                    default:
                        break;
                }                 
            }
        };
        
        
        view.getbtnAction().addActionListener(actionpress);
        view.getbtnCancel().addActionListener(actionpress);
        view.getbtnDelete().addActionListener(actionpress);
        view.getmuEdit().addActionListener(menuitem);
        view.getmuAdd().addActionListener(menuitem);
        view.getmuExport().addActionListener(menuitem);
        
        timer = new Timer(100, listener);
        timer.start();  
    }
}