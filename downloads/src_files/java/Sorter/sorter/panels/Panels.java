/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.panels;

import java.util.Random;
import sorter.UserWindow;
import sorter.core.GeneralManager;

/**
 *
 * @author Deahgib
 */
public class Panels {
    public static Item[] numbersPanel;
    public static int panelsLength;
    public static final int TRUE_MAX_SIZE_OF_PANEL_ARRAY = 200;
    public static int spacing;
    
    public static void initPanels(){
        numbersPanel = new Item[100];
    }
    
    public static void makePanels(int x){
        
        for(int i=0; i < panelsLength; i++){
            numbersPanel[i].getJPanel().setVisible(false);
            numbersPanel[i] = null;
        }
        
        numbersPanel = new Item[x];
        panelsLength = x;
        remakePanelList();
    }
    
    private static void remakePanelList(){
        int x = 0;
        while (numbersPanel[x] != null){
            numbersPanel[x] = null;
            x++;
        }
        numbersPanel = new Item[panelsLength];
        
        spacing = TRUE_MAX_SIZE_OF_PANEL_ARRAY / panelsLength;
        
        Item.setModifiers(panelsLength, spacing, 800);
        for (int i = 0; i < panelsLength; i++) {
            if(GeneralManager.useOrderedList){
                numbersPanel[i] = new Item(i+1);
            }else{
                Random r = new Random();
                numbersPanel[i] = new Item(r.nextInt(panelsLength)+1);
            }
            UserWindow.mainPanel.add(numbersPanel[i].getJPanel());
        }
    }
    
    // Used to re update the array in the display to show the user...
    // Called every time any change is made to the array.
    public static void updatePanelArray(){
        for (int x = 0; x < panelsLength; x++) 
        {
            numbersPanel[x].movePanelToX(x);
        }
    }
    
    
    public static void swapPanelsAt(int x, int y){
        Item tmp = Panels.numbersPanel[x];
        Panels.numbersPanel[x]=Panels.numbersPanel[y];
        Panels.numbersPanel[y]=tmp;
        /*
        int tmp = Panels.numbersPanel[x].size();
        Panels.numbersPanel[x].setSize(Panels.numbersPanel[y].size());
        Panels.numbersPanel[y].setSize(tmp);
        */
    }
}
