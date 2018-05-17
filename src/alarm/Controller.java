package alarm;

import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
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
    
    ActionListener combo, listener,actionpress,menuitem;
    Timer timer;
    Model model;
    MainView view;
    PriorityQueue<Alarms> heap;
    
    public void refreshCombo() {
             ArrayList<String> fullHeap = new ArrayList<String>();
             
                 fullHeap = model.heaptoArray("");
                
             view.getcomboAlarm().removeAllItems();
                
            view.getcomboAlarm().setModel(new DefaultComboBoxModel(fullHeap.toArray()));
            view.getcomboAlarm().setSelectedIndex(-1);
                
            };
    
    public void buildSingleArray(ArrayList<String> build) {
        view.gettxtDD().setText(build.get(1));
        view.gettxtMM().setText(build.get(2));
        view.gettxtYY().setText(build.get(3));
        view.gettxtHH().setText(build.get(4));
        view.gettxtMin().setText(build.get(5));
    };
    
    /**
     *
     * @param q
     * @param m
     * @param v
     */    
    public Controller(PriorityQueue<Alarms> q, Model m, MainView v) {
        model = m;
        view = v;
        heap = q;
       
        
        model.testData();
        
        int i = model.testy();
        
        listener = new ActionListener() {
             ArrayList<String> nextA = new ArrayList<String>();
            public void actionPerformed(ActionEvent e) {
                                
                 try {
                     nextA = model.getHead();
                     view.getAlarm().setText(nextA.get(0));
                 } catch (QueueUnderflowException ex) {
                     view.getAlarm().setText("No Alarm Set");
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
                int i= 0;
                ArrayList<String> btnBuild = new ArrayList<String>();
                Object obj = view.getcomboAlarm().getSelectedItem();
                
                
                
                if (null != ae.getActionCommand())switch (ae.getActionCommand()) {
                    case "Add":
                        if(checkFormat){
                            model.addAlarm(tf);
                            view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Alarm Added");
                            view.getlblPopp().setText("<HTML>Phew it worked! Your alarm has now been added.<HTML>");
                            view.getdiaPopup().setVisible(true);
                            refreshCombo();
                            
                        } else {
                            view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Error has occurred!");
                            view.getlblPopp().setText("<HTML>The alarm has to be in the future.  There must be 2 digits for the day and month and 4 digits for the Year.  The time has to be 24hr format.  I'll clear the form for you and you can start again.<HTML>");
                            view.getdiaPopup().setVisible(true);
                            
                        }
                        break;
                    case "Edit":
                        
                        if (!obj.toString().equals(tf)){
                            if(checkFormat){

                            //model.addAlarm(view.getcomboAlarm().getSelectedItem().toString());
                            if (obj != null) {
                                model.heaptoArray(obj.toString());
                            }
                            btnBuild = model.heaptoArray("");

                                    model.addAlarm(tf);

                                    if (!btnBuild.isEmpty()){

                                        view.getdiaPopup().pack();
                                        view.gettitlePopup().setText("Alarm Added");
                                        view.getlblPopp().setText("<HTML>Phew it worked! Your alarm has been added.<HTML>");
                                        view.getdiaPopup().setVisible(true);
                                        refreshCombo();
                                        
                                    } else {
                                        view.getdiaPopup().pack();
                                        view.gettitlePopup().setText("Uh Oh!");
                                        view.getlblPopp().setText("<HTML>Something went Wrong");
                                        view.getdiaPopup().setVisible(true);
                                    }

                                } else {
                                    view.getdiaPopup().pack();
                                    view.gettitlePopup().setText("Error has occurred!");
                                    view.getlblPopp().setText("<HTML>I'm afraid There is something wrong with your info.  The alarm has to be in the future.  The time has to be 24hr format.<HTML>");
                                    view.getdiaPopup().setVisible(true);
                                }
                        } else {    
                            view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Error has occurred!");
                            view.getlblPopp().setText("<HTML>You haven't made any changes to this alarm.<HTML>");
                            view.getdiaPopup().setVisible(true);
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
                        if (obj.toString().equals(tf)){

                            if (obj != null) {
                                model.heaptoArray(obj.toString());
                            }
                            btnBuild = model.heaptoArray("");

                            if (!btnBuild.isEmpty()){

                                view.getdiaPopup().pack();
                                view.gettitlePopup().setText("Alarm Deleted");
                                view.getlblPopp().setText("<HTML>Phew it worked! Your alarm has been deleted.<HTML>");
                                view.getdiaPopup().setVisible(true);
                                refreshCombo();
                                 
                            } else {
                                
                                obj = "";
                                tf = "";
                                
                                view.getActionDi().setVisible(false);
                            }

                        } else {
                             view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Error has occurred!");
                            view.getlblPopp().setText("<HTML>You have made changes to this alarm before deleting..thats not really allowed.<HTML>");
                            view.getdiaPopup().setVisible(true);
                        }    
                        view.getcomboAlarm().setSelectedIndex(0);   
                        break;
                    
                    case "Ok":
                        view.getdiaPopup().setVisible(false);
                        view.gettxtDD().setText("");
                        view.gettxtHH().setText("");
                        view.gettxtMin().setText("");
                        view.gettxtMM().setText("");
                        view.gettxtYY().setText("");
                        if(view.gettitleAction().getText()=="AddAlarm"){
                        view.getcomboAlarm().setSelectedIndex(-1);
                        }else{
                            view.getcomboAlarm().setSelectedIndex(0);
                        }
                        break;
                    default:
                        break;
                }                 
            }
        };
        
        menuitem = new ActionListener() {
            
            public void actionPerformed(ActionEvent ae) {
                ArrayList<String> menuBuild = new ArrayList<String>();
                refreshCombo();
                 

                 
                try {
                    menuBuild = model.getHead();
                } catch (QueueUnderflowException ex) {
                   // Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                 
                if (menuBuild.isEmpty()){
                    
                    view.getcomboAlarm().setVisible(false);
                    view.getlblChoose().setVisible(false);
                    view.gettxtDD().setText("");
                    view.gettxtHH().setText("");
                    view.gettxtMin().setText("");
                    view.gettxtMM().setText("");
                    view.gettxtYY().setText("");
                } else {
                    view.getcomboAlarm().setVisible(true);
                    view.getlblChoose().setVisible(true);
                } 
                 
                 
               
                 
                if (null != ae.getActionCommand())switch (ae.getActionCommand()) {
                    case "Add":
                        view.getActionDi().pack();
                        view.gettitleAction().setText("Add Alarm");
                        view.getlblChoose().setText("or choose another alarm>>");
                        view.getbtnAction().setText("Add");
                        view.getbtnDelete().setVisible(false);
                        view.getActionDi().setVisible(true);
                        break;
                    case "Edit":
                                               
                        if (menuBuild.size()== 0){
                            view.getdiaPopup().pack();
                            view.gettitlePopup().setText("You want to do what now??");
                            view.getlblPopp().setText("<HTML>Sorry but you have no alarms to edit.<HTML>");
                            view.getdiaPopup().setVisible(true);
                        } else {
                            view.getcomboAlarm().setSelectedIndex(0);
                            view.getActionDi().pack();
                            view.gettitleAction().setText("Edit Alarm");
                            view.getbtnAction().setText("Edit");
                            view.getbtnDelete().setVisible(true);
                            view.getActionDi().setVisible(true);
                        
                            buildSingleArray(menuBuild);   
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
        
        combo = new ActionListener() {
             
            public void actionPerformed(ActionEvent e) {
                Object obj = view.getcomboAlarm().getSelectedItem();
                if (obj != null){
                    buildSingleArray(model.comboPop(obj.toString()));
                    view.gettitleAction().setText("Edit Alarm");
                    view.getbtnAction().setText("Edit");
                    view.getbtnDelete().setVisible(true);
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
        view.getcomboAlarm().addActionListener(combo);
        
        timer = new Timer(100, listener);
        timer.start();  
    }
}