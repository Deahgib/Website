/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.algorithm;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import sorter.UserWindow;
import sorter.color.SorterColor;
import sorter.core.Statistics;
import sorter.panels.Panels;

/**
 *
 * @author Deahgib
 */
public class QuickSort extends Algorithm {
    private QuickSortThread qs;
    private Thread quickSortThread;
    
    public QuickSort(UserWindow userwindow) {
        super(userwindow);
        stats = new Statistics("Quicksort");
        looper = new Timer(TIME_INTERVAL, this);
        qs = new QuickSortThread();
        quickSortThread = new Thread(qs);
    }

    @Override
    public void start() {
        toneMaker.startSoundLine();
        animationUnderway = true;
        quickSortThread.start();
        looper.start();
    }

    @Override
    public void stop() {
        toneMaker.stopSoundLine();
        animationUnderway = false;
        looper.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        qs.resumeThread();
    }
    
    private synchronized Thread getQuickSortThread(){
        return quickSortThread;
    }
    
    private class QuickSortThread implements Runnable{

        private boolean running;
        
        public QuickSortThread(){
            running = true;
        }
        
        @Override
        public void run() {
            threadWait();
            sort(0, Panels.panelsLength-1);
            stop();
            userWindow.updatePanelArrayToBlue();
        }
        
        private void sort(int low, int high){
            if((high-low) <= 0){
                return;
            }
            int splitPoint = split(low, high);
            sort(low, splitPoint-1);
            sort(splitPoint+1, high);
            
        }
        
        private int split(int low, int high){
            int left = low + 1;
            int right = high;
            int pivot = Panels.numbersPanel[low].size();
            
            Panels.numbersPanel[low].setColour(SorterColor.DARK_GREEN);
            Panels.numbersPanel[high].setColour(SorterColor.DARK_GREEN);
            
            while (true) {
                while (left <= right) {
                    stats.compMade();
                    if (Panels.numbersPanel[left].size() < pivot) {
                        Panels.numbersPanel[left].setColour(SorterColor.RED);
                        toneMaker.play(Panels.numbersPanel[left].size(), Panels.panelsLength, TIME_INTERVAL);
                        left++;
                        if(left < Panels.panelsLength && left != high){
                            Panels.numbersPanel[left].setColour(SorterColor.GREEN);
                        }
                        Panels.updatePanelArray();
                        threadWait();
                    } else {
                        break;
                    }
                }
                Panels.updatePanelArray();

                while (right > left) {
                    stats.compMade();
                    if (Panels.numbersPanel[right].size() > pivot) {
                        toneMaker.play(Panels.numbersPanel[right].size(), Panels.panelsLength, TIME_INTERVAL);
                        if(right != high)
                            Panels.numbersPanel[right].setColour(SorterColor.RED);
                        right--;
                        Panels.numbersPanel[right].setColour(SorterColor.GREEN);
                        Panels.updatePanelArray();
                        threadWait();
                    } else {
                        break;
                    }
                }
                Panels.updatePanelArray();

                if (left >= right) {
                    break;
                }
      
                if (left <= right) {
                    Panels.numbersPanel[left].setColour(SorterColor.RED);
                    Panels.numbersPanel[low].setColour(SorterColor.DARK_GREEN);
                    Panels.numbersPanel[high].setColour(SorterColor.DARK_GREEN);
                    stats.swapMade();
                    Panels.swapPanelsAt(left, right);
                    Panels.updatePanelArray();
                    left++;
                    right--;
                }
            }

            threadWait();
          
            stats.swapMade();
            Panels.swapPanelsAt(low, (left - 1));
            if (left < Panels.panelsLength) {
                Panels.numbersPanel[left].setColour(SorterColor.LIGHT_BLUE);
            }
            Panels.numbersPanel[left-1].setColour(SorterColor.LIGHT_BLUE);
            Panels.numbersPanel[right].setColour(SorterColor.LIGHT_BLUE);
            Panels.numbersPanel[low].setColour(SorterColor.LIGHT_BLUE);
            Panels.numbersPanel[high].setColour(SorterColor.LIGHT_BLUE);

            Panels.updatePanelArray();
            return (left-1);
        }
        
        public void resumeThread(){
            running = true;
        }
        
        private synchronized void threadWait(){
            running = false;
            while(!running){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(QuickSort.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
