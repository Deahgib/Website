/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipsserver.core;

import battleshipsserver.BattleshipsServerMain;
import battleshipsserver.game.GameInstance;

/**
 *
 * @author Deahgib
 */
public class Printer {
    public static void println(String m){
        m = m + "\n";
        
        if(ServerGlobals.USE_GUI){
            BattleshipsServerMain.gui.promptTextArea.append(m);
        }else{
            System.out.print(m);
        }
    }
    public static void print(String m){
        if(ServerGlobals.USE_GUI){
            BattleshipsServerMain.gui.promptTextArea.append(m);
        }else{
            System.out.print(m);
        }
    }
    
    public static void println(){
        Printer.println("");
    }
    public static void print(){
        Printer.print("");
    }
}
