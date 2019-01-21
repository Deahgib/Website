/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.panels;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Louis
 */
public class Item {
    
    final static int margin = 5;
    final static int numerator = 3;
    final static int denominator = 4;
    // Modifers control the height and width of the array's panels.
    private static int widthModifier = 0;
    private static int heightModifier = 0;
    private static int spacing = 6;
    
    
    private int _size;

    private javax.swing.JPanel jPanel;
    final Color BLUE = new Color(11, 146, 213);
    
    public Item(int s){
        jPanel = new javax.swing.JPanel();
        _size = s;
        
        jPanel.setSize(widthModifier-spacing, _size*heightModifier);
        
        jPanel.setBackground(BLUE);
    }
    
    public javax.swing.JPanel getJPanel()
    {
        return jPanel;
    }
    
    public void setColour(Color c)
    {
            jPanel.setBackground(c);
    }
    
    public void movePanelToX(int x)
    {
        jPanel.setLocation(margin + x*(widthModifier), 610 - (_size*heightModifier));
    }
    
    public int size()
    {
        return _size;
    }
    
    public static void setModifiers(int sizeOfArray, int sizeOfSpacing, int panelXDimention)
    {
        widthModifier = panelXDimention / sizeOfArray;
        heightModifier = widthModifier * numerator / denominator;
        spacing = sizeOfSpacing;
    }
    
    public void setSize(int s)
    {
        _size = s;
        jPanel.setSize(widthModifier-spacing, _size*heightModifier);
    }
}
