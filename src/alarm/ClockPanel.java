package alarm;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.font.*;
import javax.swing.*;

/**
 *
 * @author Sara
 */
public class ClockPanel extends JPanel {
    
    Model model;
    MainView view;
    
    /**
     *
     * @param m pass model
     * @param v pass view
     */
    public ClockPanel(Model m, MainView v) {
        model = m;
        view = v;
        
        
    }
    
}