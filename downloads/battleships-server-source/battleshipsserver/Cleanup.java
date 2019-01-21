/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver;

import battleshipsserver.core.Printer;
import battleshipsserver.core.ServerGlobals;
import battleshipsserver.users.OnlineUsers;
import battleshipsserver.users.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author Deahgib
 */
public class Cleanup implements ActionListener {
    private Timer cleanupTimer;
    public Cleanup(){
        cleanupTimer = new Timer(10000, this);
    }
    
    public void start(){
        cleanupTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Printer.println("*** Cleanup ***");
        // Do cleanup
        OnlineUsers.getOnlineUsers().cleanup();
        //Printer.println("*** Done Cleanup ***");
    }

    
}
