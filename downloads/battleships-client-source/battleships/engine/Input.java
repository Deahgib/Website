package battleships.engine;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Louis Bennette
 */
public class Input {
    public static final int KEY_LSHIFT = Keyboard.KEY_LSHIFT;
    public static final int KEY_RSHIFT = Keyboard.KEY_RSHIFT;
    public static final int KEY_R = Keyboard.KEY_R;
    public static final int KEY_M = Keyboard.KEY_M;
    public static final int KEY_P = Keyboard.KEY_P;
    public static final int KEY_DEL = Keyboard.KEY_DELETE;
    public static final int KEY_SPACE = Keyboard.KEY_SPACE;
    public static final int KEY_ESC = Keyboard.KEY_ESCAPE;
    public static final int KEY_BACK = Keyboard.KEY_BACK;
    public static final int KEY_ENTER = Keyboard.KEY_RETURN;
    
    
    public static final int MOUSE_LEFT = 0;
    public static final int MOUSE_RIGHT = 1;
    
    public static final int NUM_KEYCODES = 256;
    public static final int NUM_MOUSE_CODES = 5;
    
    private static ArrayList<Integer> lastKeys = new ArrayList<Integer>();
    private static ArrayList<Integer> lastMouseButtons = new ArrayList<Integer>();
    
    public static void update(){
        lastMouseButtons.clear();
        for(int i = 0; i < NUM_MOUSE_CODES; i++){
            if(getMouse(i)){
                lastMouseButtons.add(i);
            }
        }
        // This just stores all the keys that are currently pressed in this frame.
        lastKeys.clear();
        for(int i = 0; i < NUM_KEYCODES; i++){
            if(getKey(i)){
                lastKeys.add(i);
            }
        }
    }
    
    /**
     * Returns a boolean value if the tested key is currently held down live.
     * @param keyCode the key we want to check that is being held down.
     * @return true is the keyboard key is being held down false is not.
     */
    public static boolean getKey(int keyCode){
        return Keyboard.isKeyDown(keyCode);
    }
    
    /**
     * Checks if the requested button has started being pressed in the current 
     * frame, we know because if the key was previously not pressed and now is
     * then it's just been pressed.
     * @param keyCode the key we want to check that has just been pressed.
     * @return true if the key has just stared being pressed in this frame.
     */
    public static boolean getKeyDown(int keyCode){
        return getKey(keyCode) && !lastKeys.contains(keyCode);
    }
    
    /**
     * Checks if the requested button has just been released in the current 
     * frame, we know because if the key is currently now held down but 
     * was held down in the previous frame, then it has been released.
     * @param keyCode the key we want to check that has just been released.
     * @return true if the key has just been released in this frame.
     */
    public static boolean getKeyUp(int keyCode){
        return !getKey(keyCode) && lastKeys.contains(keyCode);
    }
    
    
    public static boolean getMouse(int keyCode){
        return Mouse.isButtonDown(keyCode);
    }
    
    public static boolean getMouseDown(int keyCode){
        return getMouse(keyCode) && !lastMouseButtons.contains(keyCode);
    }
    
    public static boolean getMouseUp(int keyCode){
        return !getMouse(keyCode) && lastMouseButtons.contains(keyCode);
    }
    
    public static boolean isInput(){
        return !lastKeys.isEmpty();
    }
    
    public static int[] getInput(){
        int[] tmp = new int[lastKeys.size()];
        int x = 0;
        for(int i: lastKeys){
            tmp[x++] = i;
        }
        return tmp;
    }
}
