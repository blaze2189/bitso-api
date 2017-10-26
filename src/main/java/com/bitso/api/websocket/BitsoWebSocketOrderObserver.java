/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Observer;

import com.bitso.entity.TradePayload;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 *
 * @author Jorge
 */
public interface BitsoWebSocketOrderObserver extends Observer {
    
    Boolean isConnected();
     void tradeSubscribeAction()  throws JsonParseException, JsonMappingException, IOException;
     void orderSubscribeAction(String message)throws JsonParseException, JsonMappingException, IOException;
     void diffOrderSubscribeAction(String message)throws JsonParseException, JsonMappingException, IOException;
    
}
