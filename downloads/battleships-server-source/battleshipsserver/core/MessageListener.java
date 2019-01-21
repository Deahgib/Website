/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver.core;

import battleshipsserver.users.OnlineUsers;
import battleshipsserver.users.User;
import java.util.ArrayList;

/**
 *
 * @author Louis
 */
public class MessageListener implements Runnable {
    private User user;
    private boolean isKillThisThread;
    private ArrayList<String> unreadMessages = new ArrayList<String>();
    
    public MessageListener(User u) {
        this.user = u;
        this.isKillThisThread = false;
    }

    @Override
    public void run() {
        while(!ServerGlobals.isShutdownServer() && !this.isIsKillThisThread()){
                //System.out.println("Message Looping");
                String message = MessageIOHandler.getMessage(this.user.getSocket());
                if(message!=null){
                    unreadMessages.add(message);
                    user.setLastMessageTime(System.currentTimeMillis());
                    if(Parser.getMessageType(message) == Parser.PLAYERtoSERVER){
                        this.checkServerCommand(message);
                    }
                }
                else
                {
                    this.setIsKillThisThread(true);
                }
        }
    }
    
    public boolean hasNewMessage(){
        int totalUnread = unreadMessages.size();
        if(totalUnread > 0){
            int counter = 0;
            for(int x = 0; x < totalUnread; x++){
                if(unreadMessages.get(x)!=null){
                    return true;
                }
            }
        }
        return false;
    }
    
    public String getNewMessage(){
        String messageStr = "";
        if(this.hasNewMessage()){
            for(String msg : unreadMessages){
                if(msg != null){
                    messageStr = msg;
                }
            }
            unreadMessages.remove(messageStr);
        }
        return messageStr;
    }

    public boolean isIsKillThisThread() {
        return isKillThisThread;
    }

    public void setIsKillThisThread(boolean isKillThisThread) {
        this.isKillThisThread = isKillThisThread;
    }

    private void checkServerCommand(String message) {
        if(Parser.doesMessageHaveVariable(message)){
            
        }else{
            switch(Parser.getMessage(message)){
                case "dco":
                    OnlineUsers.getOnlineUsers().disconnectUser(this.user);
                    break;
            }
        }
    }
}
