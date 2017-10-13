/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket;

import io.netty.channel.Channel;

/**
 *
 * @author Jorge
 */
public interface WebSocketConnection extends AutoCloseable {

    void openConnection() throws InterruptedException;

    void closeConnection() throws InterruptedException;
    
    boolean isConeccted();
    
    Channel getChannel();
}
