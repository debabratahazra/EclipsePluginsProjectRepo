/**
 * 
 */
package com.zealcore.se.core;

/**
 * @author mala
 * 
 */
public class NotImplementedException extends RuntimeException {

    private static final long serialVersionUID = -6991158313572233609L;

    public NotImplementedException() {
        super("Not implemented yet!");
    }

    public NotImplementedException(final String string) {
        super(string);
    }
}
