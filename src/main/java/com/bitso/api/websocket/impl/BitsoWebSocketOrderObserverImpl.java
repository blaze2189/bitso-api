/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.entity.BitsoResponse;
import com.bitso.rest.client.BitsoTicker;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoWebSocketOrderObserverImpl implements BitsoWebSocketOrderObserver {

    @Autowired
    protected BitsoTicker bitsoTicker;
    
     private List<String> messageReceived;
     @Autowired
     @Qualifier("tradesList")
     private List<BitsoResponse> listBitsoResponse;
     private Boolean isConnected;
    
    {
        messageReceived=new ArrayList<>();
        //listBitsoResponse = new Vector<>();
        isConnected=false;
    }
    
    @Override
    public List<String> getMessageReceived() {
        return messageReceived;
    }

    @Override
    public List<BitsoResponse> getListBitsoRespone(){
        return listBitsoResponse;
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
            BitsoResponse bitsoResponse=bitsoTicker.getTrades();
            if(listBitsoResponse.size()<6){
                listBitsoResponse.add(bitsoResponse);
            }else{
                listBitsoResponse.remove(0);
                listBitsoResponse.add(bitsoResponse);
            }
        }
        if(arg instanceof Boolean){
            isConnected=(Boolean)arg;
        }
    }
    
}
