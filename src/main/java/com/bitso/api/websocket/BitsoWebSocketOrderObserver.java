/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket;

import com.bitso.entity.RestResponse;
import java.util.List;
import java.util.Observer;

/**
 *
 * @author Jorge
 */
public interface BitsoWebSocketOrderObserver extends Observer {
    
    List<String> getMessageReceived();
    List<RestResponse> getListBitsoRespone();
    Boolean isConnected();
    
}
