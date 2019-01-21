/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver;

import battleshipsserver.core.Printer;
import battleshipsserver.core.ServerGlobals;
import battleshipsserver.game.Matchmaking;
import battleshipsserver.users.OnlineUsers;
import battleshipsserver.view.ServerGUI;

/**
 *
 * @author Louis
 */
public class BattleshipsServerMain {

    /**
     * @param args the command line arguments
     */
    private static final ServerListener SERVER_LISTENER= new ServerListener();
    private static final Matchmaking MATCHMAKING= new Matchmaking();
    
    public static ServerGUI gui;
    
    public static void main(String[] args) {
        if (ServerGlobals.USE_GUI) {
            gui = new ServerGUI();
            gui.setVisible(true);
        }
        Printer.println("Staring server " + ServerGlobals.VERSION);
        
        new Cleanup().start();
        
        OnlineUsers.initOnlineUsers();
        Thread pollForUsersThread = new Thread(SERVER_LISTENER);
        pollForUsersThread.start();
        Thread matchmakingThread = new Thread(MATCHMAKING);
        matchmakingThread.start();
    }
}
