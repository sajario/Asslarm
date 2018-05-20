package alarm;

import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Timer;
import queuemanager.PriorityQueue;
import queuemanager.QueueUnderflowException;

/**
 *
 * @author Sara
 */
public class Controller {
    
    /*Settin Variables*/
    ActionListener alarmy, combo, listener,actionpress,menuitem;
    Timer timer, timer1;
    Model model;
    MainView view;
    PriorityQueue<Alarms> heap;
    boolean isitnow = false;
    
    /**
     * FUNCTION alarmBeep - Unused as couldn't get multiple beeps functioning*/
    private void alarmBeep() {
        Toolkit t = view.getdiaNow().getToolkit();
        /*int i = LocalDateTime.now().getSecond();
        int j = 0;*/
        long start = System.currentTimeMillis();
        long end = start + 5000;
          do {
            Toolkit.getDefaultToolkit().beep();
            try {
                Thread.sleep( 100 );
            }
            catch ( InterruptedException x ) {}
        } while ( System.currentTimeMillis() < end );
        
            
        
    }

    /** 
     * FUNCTION refreshCombo - Makes sure combo box is up to date with the appropriate alarms*/
    public void refreshCombo() {
             ArrayList<String> fullHeap = new ArrayList<String>();
             
                /*No string passed as no comparisons needed*/
                fullHeap = model.heaptoArray("");
                
             view.getcomboAlarm().removeAllItems();
                
            view.getcomboAlarm().setModel(new DefaultComboBoxModel(fullHeap.toArray()));
            view.getcomboAlarm().setSelectedIndex(-1);
                
            };
    
    /** 
     * FUNCTION buildSingleArray - Builds an array of elements in one location of heap 
     * to be broken down and used in elements of vie
     * @param build*/
    public void buildSingleArray(ArrayList<String> build) {
        view.gettxtDD().setText(build.get(1));
        view.gettxtMM().setText(build.get(2));
        view.gettxtYY().setText(build.get(3));
        view.gettxtHH().setText(build.get(4));
        view.gettxtMin().setText(build.get(5));
    };
    
    /**
     * Controller
     * @param q heap link
     * @param m model link
     * @param v view link
     */    
    public Controller(PriorityQueue<Alarms> q, Model m, MainView v) {
        model = m;
        view = v;
        heap = q;
       
        /*Builds Some test data*/
        model.testData();
                       
        
        /**
         * listener - Action listener that runs every second
         * gets the appropriate head of the alarm heap,
         * updates the clock,
         * checks if alarm should go off
         */         
        listener = new ActionListener() {
             ArrayList<String> nextA = new ArrayList<String>();
              ArrayList<String> btnBuild = new ArrayList<String>();
            public void actionPerformed(ActionEvent e) {
                                
                 try {
                     /**
                      * Checks head of queue and inserts it into the label for next alarm on the main clock screen */
                     nextA = model.getHead();
                     view.getAlarm().setText(nextA.get(0));
                 } catch (QueueUnderflowException ex) {
                     view.getAlarm().setText("No Alarm Set");
                 }
                
                
                /**
                 * Calls update to get the current time and passes current next alarm to see if thats now!*/
                //model.update(String.valueOf(view.getAlarm().getText()));
                
                /**
                 * Calls update to get the current time and passes current next alarm to see if thats now!*/
                isitnow = model.update(view.getAlarm().getText());
                
                /**
                 * Sets the clock with new times and add 0 if its 1 digit time element*/
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
                /**
                 * Sets date and */
                view.getDate().setText(model.datelbl);
                view.getAlarm().setText(model.alarmin);
               
                
            /**
             * If the time matches the next alarm*/    
            if (isitnow){
                
                /*Set Now to the label in the popup and show it and produces a single beep*/
                view.getlblAlarmOFF().setText(view.getAlarm().getText());
                view.getdiaNow().pack();
                view.getdiaNow().setVisible(true);
                Toolkit.getDefaultToolkit().beep();
                //alarmBeep();
                     try {
                         /*Once the alarm has gone off it removes that alarm from the heap*/
                         model.heap.remove();
                     } catch (QueueUnderflowException ex) {}
                 
                 
                } //end of if now 
            } //end of action
            
        }; //end of listener
        
        
        /**
         * actionpress - Action listener that runs for buttons...Could have been more split 
         * out but they shared a lot of initialisers so have been combined
         */ 
        actionpress = new ActionListener() {
            
            public void actionPerformed(ActionEvent ae) {
                /**
                 * Builds current input fields into date time format and checks validity*/
                String tf = view.gettxtDD().getText()+"/"+view.gettxtMM().getText()+"/"+ view.gettxtYY().getText()+" "+view.gettxtHH().getText()+":"+view.gettxtMin().getText();
                boolean checkFormat = model.chkUserInput(tf);
                
                /**
                 * Arraylist for holding broken down single heap entity*/
                ArrayList<String> btnBuild = new ArrayList<String>();
                
                /**
                 * Get combobox value*/
                Object obj = view.getcomboAlarm().getSelectedItem();
                
                if (null != ae.getActionCommand())switch (ae.getActionCommand()) {
                    case "Add":
                        /**
                         * The format of the input boxes has already been checked so if it is ok it simply 
                        adds and displays relevant info and sorts out combo box with new information*/
                        if(checkFormat){
                            model.addAlarm(tf);
                            view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Alarm Added");
                            view.getlblPopp().setText("<HTML>Phew it worked! Your alarm has now been added.<HTML>");
                            view.getdiaPopup().setVisible(true);
                            refreshCombo();
                        /**
                         * Or shows error*/    
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
                                
                                /**
                                 * The new alarm is added from input fields (tf) then the comboxbox element is sent to heaptoArray 
                                 * to be removed which is the only work around for being able to delete elements not at the 
                                 * head of the queue as i could not change the library in accordance with the assignment guidelines
                                 * 
                                 */
                                model.addAlarm(tf);
                                model.heaptoArray(obj.toString());
                                

                                
                                if (!model.heap.isEmpty()){

                                        view.getdiaPopup().pack();
                                        view.gettitlePopup().setText("Alarm Added");
                                        view.getlblPopp().setText("<HTML>Phew it worked! Your alarm has been added.<HTML>");
                                        view.getdiaPopup().setVisible(true);
                                        refreshCombo();
                                        
                                    } else {
                                        /**
                                         * if the heap is empty and add function doesn't work
                                         */
                                        view.getdiaPopup().pack();
                                        view.gettitlePopup().setText("Uh Oh!");
                                        view.getlblPopp().setText("<HTML>Something went Wrong");
                                        view.getdiaPopup().setVisible(true);
                                    }

                                } else {
                                    /**
                                     * If user input is not formatted correctly*/
                                    view.getdiaPopup().pack();
                                    view.gettitlePopup().setText("Error has occurred!");
                                    view.getlblPopp().setText("<HTML>I'm afraid There is something wrong with your info.  The alarm has to be in the future.  The time has to be 24hr format.<HTML>");
                                    view.getdiaPopup().setVisible(true);
                                }
                        } else {    
                            /**
                             * If user input is the same as the selected one*/
                            view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Error has occurred!");
                            view.getlblPopp().setText("<HTML>You haven't made any changes to this alarm.<HTML>");
                            view.getdiaPopup().setVisible(true);
                        }
                        break;
                    
                    case "Cancel":
                        /**
                         * Clears form*/
                        view.gettxtDD().setText("");
                        view.gettxtHH().setText("");
                        view.gettxtMin().setText("");
                        view.gettxtMM().setText("");
                        view.gettxtYY().setText("");
                        view.getbtnDelete().setVisible(false);
                        view.getActionDi().setVisible(false);
                        break;
                    
                    case "Delete":
                        /**
                         * Basically the same as edit but without the adding of the input fields*/
                        if (obj.toString().equals(tf)){
                            
                            model.heaptoArray(obj.toString());
                            
                            if (!model.heap.isEmpty()){

                                view.getdiaPopup().pack();
                                view.gettitlePopup().setText("Alarm Deleted");
                                view.getlblPopp().setText("<HTML>Phew it worked! Your alarm has been deleted.<HTML>");
                                view.getdiaPopup().setVisible(true);
                                refreshCombo();
                                view.getcomboAlarm().setSelectedIndex(0); 
                            } else {
                                
                                obj = "";
                                tf = "";
                                view.getcomboAlarm().setSelectedIndex(-1);
                                view.getActionDi().setVisible(false);
                            }

                        } else {
                            /**
                             * If user has changed the input fields then delete
                             */
                             view.getdiaPopup().pack();
                            view.gettitlePopup().setText("Error has occurred!");
                            view.getlblPopp().setText("<HTML>You have made changes to this alarm before deleting..thats not really allowed.<HTML>");
                            view.getdiaPopup().setVisible(true);
                        }    
                           
                        break;
                    
                    case "Ok":
                        /**
                         * clears form and sets combo box to appropriate value
                         */                         
                        view.getdiaPopup().setVisible(false);
                        view.getActionDi().setVisible(false);
                        view.gettxtDD().setText("");
                        view.gettxtHH().setText("");
                        view.gettxtMin().setText("");
                        view.gettxtMM().setText("");
                        view.gettxtYY().setText("");
                        if("Add Alarm".equals(view.gettitleAction().getText())){
                        view.getcomboAlarm().setSelectedIndex(-1);
                        }else{
                            view.getcomboAlarm().setSelectedIndex(0);
                        }
                        break;
                    case "Turn Off":
                        view.getdiaNow().setVisible(false); 
                        break;
                    default:
                        break;
                }//end of switch case              
            }//end of action performed
        };//end of actionpress
        
        /**
         * menuitem - ActionListener for choosing menu items
         */
        menuitem = new ActionListener() {
            
            public void actionPerformed(ActionEvent ae) {
                ArrayList<String> menuBuild = new ArrayList<String>();
                /*Updates combo box*/
                refreshCombo();

                 /**
                  * Gets head of queue and breaks it down to its time elements for use in user input
                  */   
                try {
                    menuBuild = model.getHead();
                } catch (QueueUnderflowException ex) {
                   // Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                    /**
                     * If the head was empty don't give option to choose another
                     *  alarm in combobox and set all fields blank
                    */
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
                        view.getlblChoose().setText("or choose another alarm >>");
                    } 


                    /**
                     * Case to go through menu items
                     */
                    if (null != ae.getActionCommand())switch (ae.getActionCommand()) {
                        case "Add":
                            view.getActionDi().pack();
                            view.gettitleAction().setText("Add Alarm");
                            view.getbtnAction().setText("Add");
                            view.getbtnDelete().setVisible(false);
                            view.getActionDi().setVisible(true);
                            break;
                        
                        case "Edit":
                            
                            /**
                             * Condition if there are no alarms you should not be able to edit anything
                             */
                            if (menuBuild.isEmpty()){
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
                            /* UNDEVELOPED DUE TO TIME*/
                            System.out.println("Nope sorry I screwed this up");
                            break;

                        default:
                            break;
                    } //end of switch                 
            } // end of action performed               
        };// end of listener
        
        /**
         * combo - ActionListener for choosing items from dropdown
         */
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
        
        
        /*Action assigning*/
        view.getbtnAction().addActionListener(actionpress);
        view.getbtnCancel().addActionListener(actionpress);
        view.getbtnDelete().addActionListener(actionpress);
        view.getbtnOK().addActionListener(actionpress);
        view.getbtnAlarmOFF().addActionListener(actionpress);
        view.getmuEdit().addActionListener(menuitem);
        view.getmuAdd().addActionListener(menuitem);
        view.getmuExport().addActionListener(menuitem);
        view.getcomboAlarm().addActionListener(combo);
        
        timer = new Timer(100, listener);
        timer.start();  
    }
}