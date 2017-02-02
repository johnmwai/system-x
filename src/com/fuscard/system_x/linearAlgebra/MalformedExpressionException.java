package com.fuscard.system_x.linearAlgebra;

/**
 *
 * @author John Mwai <johnmwai1000@gmail.com>
 */
public class MalformedExpressionException extends Exception {

    /**
     * Creates a new instance of <code>MalformedExpressionException</code>
     * without detail message.
     */
    public MalformedExpressionException() {
    }

    /**
     * Constructs an instance of <code>MalformedExpressionException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public MalformedExpressionException(String msg) {
        super(msg);
    }
}
