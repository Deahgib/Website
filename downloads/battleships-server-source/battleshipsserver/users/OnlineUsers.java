/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver.users;

/**
 *
 * @author Louis
 */
public class OnlineUsers {
    private static UserList users;
    public static void initOnlineUsers(){
        if(getOnlineUsers() == null){
            users = new UserList();
        }
    }
    
    public static synchronized UserList getOnlineUsers(){
        return users;
    }
}
