/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Sara
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({alarm.AlarmsTest.class, alarm.ControllerTest.class, alarm.ModelTest.class, alarm.MainViewTest.class, alarm.ClockTest.class, alarm.ClockPanelTest.class})
public class AlarmSuite {
    
}
