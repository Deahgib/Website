package battleships.engine;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Louis Bennette
 */
public class InputReader {
    private static ArrayList<Integer> characterKeys = new ArrayList<Integer>();
    private static ArrayList<Integer> numericalKeys = new ArrayList<Integer>();
    
    public static void initValidKeys(){
        numericalKeys.add(Keyboard.KEY_0);
        numericalKeys.add(Keyboard.KEY_1);
        numericalKeys.add(Keyboard.KEY_2);
        numericalKeys.add(Keyboard.KEY_3);
        numericalKeys.add(Keyboard.KEY_4);
        numericalKeys.add(Keyboard.KEY_5);
        numericalKeys.add(Keyboard.KEY_6);
        numericalKeys.add(Keyboard.KEY_7);
        numericalKeys.add(Keyboard.KEY_8);
        numericalKeys.add(Keyboard.KEY_9);
        
        characterKeys.add(Keyboard.KEY_A);
        characterKeys.add(Keyboard.KEY_B);
        characterKeys.add(Keyboard.KEY_C);
        characterKeys.add(Keyboard.KEY_D);
        characterKeys.add(Keyboard.KEY_E);
        characterKeys.add(Keyboard.KEY_F);
        characterKeys.add(Keyboard.KEY_G);
        characterKeys.add(Keyboard.KEY_H);
        characterKeys.add(Keyboard.KEY_I);
        characterKeys.add(Keyboard.KEY_J);
        characterKeys.add(Keyboard.KEY_K);
        characterKeys.add(Keyboard.KEY_L);
        characterKeys.add(Keyboard.KEY_M);
        characterKeys.add(Keyboard.KEY_N);
        characterKeys.add(Keyboard.KEY_O);
        characterKeys.add(Keyboard.KEY_P);
        characterKeys.add(Keyboard.KEY_Q);
        characterKeys.add(Keyboard.KEY_R);
        characterKeys.add(Keyboard.KEY_S);
        characterKeys.add(Keyboard.KEY_T);
        characterKeys.add(Keyboard.KEY_U);
        characterKeys.add(Keyboard.KEY_V);
        characterKeys.add(Keyboard.KEY_W);
        characterKeys.add(Keyboard.KEY_X);
        characterKeys.add(Keyboard.KEY_Y);
        characterKeys.add(Keyboard.KEY_Z);
        
        characterKeys.add(Keyboard.KEY_SPACE);
        characterKeys.add(Keyboard.KEY_PERIOD);
    }
    
    public static String getInput(){
        String in = "";
        if(Input.isInput()){
            int[] keyPresses = Input.getInput();
            for(int i = 0; i < keyPresses.length; i++){
                if(Input.getKeyUp(keyPresses[i]) && (characterKeys.contains(keyPresses[i]) || numericalKeys.contains(keyPresses[i]))){
                    if( keyPresses[i] == Keyboard.KEY_SPACE ){
                        in = in + " ";
                    }
                    else if( keyPresses[i] == Keyboard.KEY_PERIOD ){
                        in = in + ".";
                    }
                    else if( characterKeys.contains(keyPresses[i]) ){
                        String tmp = Keyboard.getKeyName(keyPresses[i]);
                        if( Input.getKey(Input.KEY_LSHIFT) || Input.getKey(Input.KEY_RSHIFT) ){
                            tmp = tmp.toUpperCase();
                            in = in + tmp;
                        }
                        else
                        {
                            tmp = tmp.toLowerCase();
                            in = in + tmp;
                        }
                    }
                    else if( numericalKeys.contains(keyPresses[i]) ){
                        in = in + Keyboard.getKeyName(keyPresses[i]);
                    }
                }
            }
        }
        return in;
    }
}