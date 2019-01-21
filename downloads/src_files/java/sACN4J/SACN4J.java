/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sacn4j;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sacn4j.exceptions.InvalidUniverseException;

/**
 *
 * @author lbennette
 */
public class SACN4J {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            LiveOutput lo = new LiveOutput(1, "sACN4J testing");
            OutputStreamer os = new OutputStreamer(lo);
            lo.setPriority((byte) 0x65);
            lo.setDMXVal(3, (byte) 0xFF);
            os.sendLiveOutput();
            Thread.sleep(2000);
            int i = 0;
            while(i < 250){
                lo.setDMXVal(0, (byte) (lo.getDMXVal(0)+0x01));
                i++;
                os.sendLiveOutput();
                Thread.sleep(50);
            }
        } catch (IOException ex) {
            Logger.getLogger(SACN4J.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidUniverseException ex) {
            Logger.getLogger(SACN4J.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SACN4J.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
