/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sacn4j.exceptions;

/**
 *
 * @author lbennette
 */
public class InvalidUniverseException extends Exception {
    public InvalidUniverseException(String message) {
        super(message);
    }
    public InvalidUniverseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
