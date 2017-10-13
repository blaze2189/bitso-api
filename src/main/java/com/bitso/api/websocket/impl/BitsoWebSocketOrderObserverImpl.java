/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoWebSocketOrderObserverImpl implements BitsoWebSocketOrderObserver {

     private List<String> messageReceived;
     private Boolean isConnected;
    
    {
        messageReceived=new ArrayList<>();
        isConnected=false;
    }
    
    @Override
    public List<String> getMessageReceived() {
        return messageReceived;
    }

    @Override
    public Boolean isConnected() {
        return isConnected;
    }

    @Override
    public void update(Observable o, Object arg) {
       if(arg instanceof String){
            String message = (String)arg;
            messageReceived.add(message);
        }
        if(arg instanceof Boolean){
            isConnected=(Boolean)arg;
        }
    }
    
    
    
    
}
