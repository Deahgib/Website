/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.algorithm.misc;

import java.awt.event.ActionEvent;
import javax.swing.Timer;
import sorter.UserWindow;
import static sorter.UserWindow.stopButton;
import static sorter.UserWindow.updateLabel;
import sorter.algorithm.Algorithm;
import sorter.color.SorterColor;
import sorter.panels.Panels;

/**
 *
 * @author Deahgib
 */
public class BlueCheck extends Algorithm {

    private int x = 0;
    
    public BlueCheck(UserWindow userwindow){
        super(userwindow);
        looper = new Timer(7, this);
    }
    
    @Override
    public void start() {
        toneMaker.startSoundLine();
        animationUnderway = true;
        looper.start();
    }

    @Override
    public void stop() {
        toneMaker.stopSoundLine();
        stopButton.setVisible(false);
        animationUnderway = false;
        updateLabel.setText(" ");
        looper.stop();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // Perform a check to see if the sort worked...
        // Mostly a nice animation but it has it's uses for debuging.

        if (Panels.numbersPanel[x].size() > Panels.numbersPanel[x + 1].size()) {
            animationUnderway = false;
            updateLabel.setText(new String("ERROR: at " + x + " and " + (x + 1)));
            looper.stop();
        }

        toneMaker.play(x, Panels.panelsLength, 7);
        
        if((x+1) < Panels.panelsLength){
            Panels.numbersPanel[x + 1].setColour(SorterColor.GREEN);
        }
        Panels.numbersPanel[x].setColour(SorterColor.BLUE);
        Panels.updatePanelArray();
        
        x++;
        if (x >= Panels.panelsLength - 1) {
            Panels.numbersPanel[x].setColour(SorterColor.BLUE);
            stop();
        }
    }

}
