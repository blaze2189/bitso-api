/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket;

import com.bitso.api.exception.SocketDisconnectedException;

/**
 *
 * @author Jorge
 */
public interface BitsoChannelSubscriber {
    
    //implement Exception
    void subscribeBitsoChannel() throws SocketDisconnectedException;
}
