/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.exception;

/**
 *
 * @author Jorge
 */
public class SocketDisconnectedException extends Exception {

    public SocketDisconnectedException() {
    }

    public SocketDisconnectedException(String message) {
        super(message);
    }

    public SocketDisconnectedException(Throwable cause) {
        super(cause);
    }

    public SocketDisconnectedException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
