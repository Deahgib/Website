/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver.game;

import battleshipsserver.core.Printer;
import battleshipsserver.core.ServerGlobals;
import battleshipsserver.users.OnlineUsers;
import battleshipsserver.users.User;

/**
 *
 * @author Louis
 */
public class Matchmaking implements Runnable {

    private boolean isKillThisThread;
    
    @Override
    public void run() {
        this.isKillThisThread = false;
        while (!ServerGlobals.isShutdownServer() && !ServerGlobals.isKillAllThreads() && !this.isKillThisThread) {
            try{
                Thread.sleep(50);
            }catch(Exception ex){
                ex.printStackTrace();
                System.err.println("Couldnt sleep in Matchmaking Thread.");
            }
            if (this.areEnoughEligibleUsers()) {
                Printer.println("Looking for a match.");
                User[] readyUsers = retrieveEligibleUsers();
                Printer.println("Found a match!");
              
                this.verifyUser(readyUsers[0]);
                this.verifyUser(readyUsers[1]);

                if(!readyUsers[0].getSocket().isClosed() && 
                        !readyUsers[1].getSocket().isClosed()){
                    Thread chatInstanceThread = new Thread(new GameInstance(readyUsers));
                    chatInstanceThread.start();
                }else{
                    if(!readyUsers[0].getSocket().isClosed()){
                        OnlineUsers.getOnlineUsers().queueUser(readyUsers[0]);
                    }
                    else if(!readyUsers[1].getSocket().isClosed()){
                        OnlineUsers.getOnlineUsers().queueUser(readyUsers[1]);
                    }
                }
            }
        }
    }
    
    private void verifyUser(User u) {
        if (u.getSocket().isClosed()) {
            OnlineUsers.getOnlineUsers().disconnectUser(u);
        }
    }

    private User[] retrieveEligibleUsers(){
        User[] tmpUsers = new User[2];
        int x = 0;
        for (int i=0; i<OnlineUsers.getOnlineUsers().getQueueLength(); i++){
            if(OnlineUsers.getOnlineUsers().getQueueAt(i)!=null && 
                    !OnlineUsers.getOnlineUsers().getQueueAt(i).getSocket().isClosed()){
                
                tmpUsers[x] = OnlineUsers.getOnlineUsers().getQueueAt(i);
                x++;
                if(x > 1){
                    break;
                }
            }
        }
        tmpUsers[0].setLastMessageTime(System.currentTimeMillis());
        tmpUsers[1].setLastMessageTime(System.currentTimeMillis());
        if(tmpUsers[0]!=null && tmpUsers[1]!=null){
            OnlineUsers.getOnlineUsers().removeUserFromQueue(tmpUsers[0]);
            OnlineUsers.getOnlineUsers().removeUserFromQueue(tmpUsers[1]);
            return tmpUsers;
        }
        return null;
    }
    
    private boolean areEnoughEligibleUsers(){
        int count = 0;
        if(OnlineUsers.getOnlineUsers().areUsersWaiting()){
            for(int i=0; i<OnlineUsers.getOnlineUsers().getQueueLength();i++){
                if(OnlineUsers.getOnlineUsers().getQueueAt(i)!=null){
                    count++;
                }
            }
        }
        return count > 1;
    }
    
}
