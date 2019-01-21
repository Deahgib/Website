/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.scene.view.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Louis
 */
public class MessageIOHandler {
    public static void sendServerCommand(String message, Socket s){
        message = Parser.PLAYERtoSERVER + message;
        sendMessage(message, s);
    }
    
    public static void sendPlayerCommand(String message, Socket s){
        message = Parser.PLAYERtoPLAYER + message;
        sendMessage(message, s);
    }
    
    public static void sendStringMessage(String message, Socket s){
        message = Parser.CHAT + message;
        sendMessage(message, s);
    }
    
    private static void sendMessage(String message, Socket s){
        try {
            PrintStream serverMessageStream = new PrintStream(s.getOutputStream());
            serverMessageStream.println(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getMessage(Socket s){
        try {
            BufferedReader clientMessageStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
            return clientMessageStream.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
