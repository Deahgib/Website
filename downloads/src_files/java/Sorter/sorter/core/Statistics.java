/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.core;

/**
 *
 * @author Louis
 */
public class Statistics {
    private int comps;
    private int swaps;
    private String type;
    
    public Statistics(String type){
        this.type = type;
        comps = 0;
        swaps = 0;
    }

    public int getStatsComps() {
        return comps;
    }

    public void setStatsComps(int statsComps) {
        this.comps = statsComps;
    }
    
    public void compMade(){
        comps++;
    }

    public int getStatsSwaps() {
        return swaps;
    }

    public void setStatsSwaps(int statsSwaps) {
        this.swaps = statsSwaps;
    }
    
    public void swapMade(){
        swaps++;
    }

    public String getType() {
        return this.type;
    }
}
