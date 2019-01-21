package battleships.scene.view.multiplayer;

/**
 *
 * @author Louis Bennette
 */
public class Parser {
    public static final int CHAT = 0;
    public static final int PLAYERtoPLAYER = 1;
    public static final int PLAYERtoSERVER = 2;
    public static final int SERVERtoPLAYER = 3;
    
    
    public static int getMessageType(String message){
        String typeStr = message.substring(0, 1);
        int type = Integer.parseInt(typeStr);
        switch(type){
            case CHAT:
                return CHAT;
            case PLAYERtoPLAYER:
                return PLAYERtoPLAYER;
            case PLAYERtoSERVER:
                return PLAYERtoSERVER;
            case SERVERtoPLAYER:
                return SERVERtoPLAYER;
        }
        throw new IllegalArgumentException("Suspected 3rd party tampering.");
    }

    public static String getMessage(String message) {
        return message.substring(1, message.length());
    }
    
    public static boolean doesMessageHaveVariable(String message){
        return message.contains("=");
    }
    
    public static String getMessageVariable(String message){
        return message.split("=")[1];
    }
    
    public static String truncateVariable(String message){
        return message.split("=")[0];
    }
}
