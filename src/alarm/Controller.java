package alarm;

import java.awt.event.*;
import javax.swing.Timer;

public class Controller {
    
    ActionListener listener;
    Timer timer;
    
    Model model;
    MainView view;
    
    public Controller(Model m, MainView v) {
        model = m;
        view = v;
        
        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.update(String.valueOf(model.alarmin));
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
        
        timer = new Timer(100, listener);
        timer.start();
    }
}