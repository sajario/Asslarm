package alarm;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.*;
import queuemanager.HeapPQ;

import queuemanager.PriorityQueue;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;

/**
 *
 * @author Sara
 */
public class Clock {

    /**
     *
     * @param args
     * @throws QueueUnderflowException
     */
    public static void main(String[] args) {
        
        /*New Queue*/
        PriorityQueue<Object> q;
        q = new HeapPQ<>(15);
           
        Model model = new Model(q);
        MainView view = new MainView(model);
        model.addObserver(view);
        Controller controller = new Controller(q, model, view);
        
        view.setVisible(true);
        
    }
}
