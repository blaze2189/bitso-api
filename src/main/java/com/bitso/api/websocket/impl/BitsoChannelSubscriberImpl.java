/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import com.bitso.api.exception.SocketDisconnectedException;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.api.websocket.WebSocketConnection;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jorge
 */
public class BitsoChannelSubscriberImpl implements BitsoChannelSubscriber {
    
    @Autowired
    WebSocketConnection webSocketOrder;
    
    protected String channelName;
    
    @Override
      public void subscribeBitsoChannel() throws SocketDisconnectedException{
        String message;
        if(webSocketOrder.isConeccted()){
            message = "{ \"action\": \"subscribe\", \"book\": \"btc_mxn\", \"type\": \""+this.channelName+"\" }";
           DefaultByteBufHolder textWebSocketFrame = new TextWebSocketFrame(message);
          webSocketOrder.getChannel().writeAndFlush(textWebSocketFrame);
        }else{
            message="Web Socket not connected";
            throw new SocketDisconnectedException (message);
        }
    }
    
}
