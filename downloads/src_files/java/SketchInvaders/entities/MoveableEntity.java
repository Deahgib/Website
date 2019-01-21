/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Louis
 */
public interface MoveableEntity extends Entity{
    public double getDX();
    public double getDY();
    public void setDX(double dx);
    public void setDY(double dy);
}
