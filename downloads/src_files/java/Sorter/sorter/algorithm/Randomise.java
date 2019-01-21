/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.algorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;
import sorter.UserWindow;
import sorter.color.SorterColor;
import sorter.core.GeneralManager;
import sorter.panels.Panels;

/**
 *
 * @author Deahgib
 */
public class Randomise extends Algorithm {
    public static int howRandom = 2;
    
    public Randomise(){
        super(null);
        howRandom = UserWindow.randomSlider.getValue();
        looper = new Timer(1, this);
    }
    
    @Override
    public void start(){
        UserWindow.updateLabel.setText(new String("Randomising with: " + howRandom));
        animationUnderway = true;
        looper.start();
    }

    @Override
    public void stop() {
        animationUnderway = false;
        looper.stop();
    }
    
    int counter = 0;
    int totalCycles = 0;
    Random generator = new Random();
    public void actionPerformed(ActionEvent e) {
        int i, j;

        i = generator.nextInt(Panels.panelsLength);
        j = generator.nextInt(Panels.panelsLength);

        // We swap the value size for the item object that has been randomly selected
        Panels.swapPanelsAt(i, j);
        
        Panels.numbersPanel[i].setColour(SorterColor.RED);
        Panels.numbersPanel[j].setColour(SorterColor.RED);
        Panels.updatePanelArray();

        GeneralManager.sleep(2);

        counter++;
        if (totalCycles >= howRandom) {
            this.stop();
            animationUnderway = false;
            UserWindow.updateLabel.setText(" ");
        } else {
            if (counter >= Panels.panelsLength * 2) {
                UserWindow.updateLabel.setText(new String("Randomising with: " + (howRandom - (totalCycles + 1))));
                totalCycles++;
                counter = 0;
            }
        }
    }
}
