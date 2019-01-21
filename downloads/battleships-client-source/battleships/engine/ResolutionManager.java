package battleships.engine;

import battleships.engine.Window;
import battleships.scene.components.grid.AbstractGrid;

/**
 *
 * @author Louis Bennette
 */
public class ResolutionManager {
    
    private double leftMargin;
    private double topMargin;
    private double rightMargin;
    private double bottomMargin;
    
    private double gridCellHeight;
    
    
    public ResolutionManager(double x, double y, double width, double height){
        this.leftMargin = x + percentageAmountForValue(width,5);
        this.topMargin = y + percentageAmountForValue(height,10);
        this.rightMargin = x + width - this.leftMargin;
        this.bottomMargin = y + height - this.topMargin;
        
        double gridHeight = percentageAmountForValue(height,80);
        this.gridCellHeight = gridHeight / AbstractGrid.getGRID_SIZE();
    }

    public double getLeftMargin() {
        return leftMargin;
    }

    public double getTopMargin() {
        return topMargin;
    }

    public double getRightMargin() {
        return rightMargin;
    }

    public double getBottomMargin() {
        return bottomMargin;
    }

    public double getGridCellHeight() {
        return gridCellHeight;
    }
    
    /**
     * This vaguely named method calculates the percentage equivalent of the 
     * given value and percentage.
     * Example: if we give value 50 and ask for 10% of it we return 5.
     * In code: percentageAmountForValue(50,10); this returns 5.
     * @param value The initial value i.e. our 100% equivalent.
     * @param percentage The amount as a percentage that we want out of value
     * @return the amount that percentage represents out of value.
     */
    public static double percentageAmountForValue(double value, double percentage){
        return value / 100 * percentage;
    }
    
}
