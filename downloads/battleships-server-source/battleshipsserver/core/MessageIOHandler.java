package battleshipsserver.core;

import battleshipsserver.users.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author Louis
 */
public class MessageIOHandler {
    public static void sendCommandMessage(String message, User u){
        message = Parser.SERVERtoPLAYER + message;
        sendMessage(message, u.getSocket());
    }
    
    public static void relayCommandMessage(String message, User u){
        message = Parser.PLAYERtoPLAYER + message;
        sendMessage(message, u.getSocket());
    }
    
    public static void sendStringMessage(String message, User u){
        message = Parser.CHAT + message;
        sendMessage(message, u.getSocket());
    }

    private static void sendMessage(String message, Socket s) {
        try {
            if (!s.isClosed()) {
                PrintStream serverMessageStream = new PrintStream(s.getOutputStream());
                serverMessageStream.println(message);
                Printer.println(Thread.currentThread().getName() + ": \"" + message + "\" sent to " + s.getInetAddress().getHostAddress());
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }
    
    public static String getMessage(Socket s){
        try {
            if (!s.isClosed()) {
                BufferedReader clientMessageStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String msg = clientMessageStream.readLine();
                Printer.println(Thread.currentThread().getName() + ": \"" + msg + "\" recieved from " + s.getInetAddress().getHostAddress());
                return msg;
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
        return null;
    }
}