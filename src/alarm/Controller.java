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
        
        model.testData();
        
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
                String tf = view.gettxtDD().getText()+"/"+view.gettxtMM().getText()+"/"+ view.gettxtYY().getText()+" "+view.gettxtHH().getText()+":"+view.gettxtMin().getText();
                boolean checkFormat = model.chkUserInput(tf);
                
                if (null != ae.getActionCommand())switch (ae.getActionCommand()) {
                    case "Add":
                        if(checkFormat){
                            model.addAlarm(tf);
                            view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Alarm Added");
                            view.getlblPopp().setText("<HTML>Phew it worked! Your alarm has now been added.<HTML>");
                            view.getdiaPopup().setVisible(true);
                            
                        } else {
                            view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Error has occurred!");
                            view.getlblPopp().setText("<HTML>I'm afraid you have not filled out all the right information.  The alarm has to be in the future.  The time has to be 24hr format.  I'll clear the form for you and you can start again.<HTML>");
                            view.getdiaPopup().setVisible(true);
                        }
                        break;
                    case "Edit":
                        
                        {
                            try {
                                model.heaptoArray();
                            } catch (QueueUnderflowException ex) {
                                //add something
                            }
                        }
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
                    
                    case "Ok":
                        view.getdiaPopup().setVisible(false);
                        view.gettxtDD().setText("");
                        view.gettxtHH().setText("");
                        view.gettxtMin().setText("");
                        view.gettxtMM().setText("");
                        view.gettxtYY().setText("");
                        
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
                    
                    case "Ok":
                        view.getdiaPopup().setVisible(false);
                        break;
                        
                    default:
                        break;
                }                 
            }
        };
        
        
        view.getbtnAction().addActionListener(actionpress);
        view.getbtnCancel().addActionListener(actionpress);
        view.getbtnDelete().addActionListener(actionpress);
        view.getbtnOK().addActionListener(actionpress);
        view.getmuEdit().addActionListener(menuitem);
        view.getmuAdd().addActionListener(menuitem);
        view.getmuExport().addActionListener(menuitem);
        
        timer = new Timer(100, listener);
        timer.start();  
    }
}