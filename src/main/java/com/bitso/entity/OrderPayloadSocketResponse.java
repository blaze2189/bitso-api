/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jorge
 */
@Getter
@Setter
public class OrderPayloadSocketResponse {
    
    @JsonProperty(value="bids",required=true)
    private List<WebSocketPayload> bids;
    @JsonProperty(value="asks",required=true)
    private List<WebSocketPayload> asks;

    @Override
    public String toString() {
        StringBuilder orderPaylodSocketResponseString = new StringBuilder(100);
        orderPaylodSocketResponseString.append("\nbids:");
        bids.forEach(wSP -> orderPaylodSocketResponseString.append("{").append(wSP).append("}"));
        orderPaylodSocketResponseString.append("\nasks");
        asks.forEach(wSP -> orderPaylodSocketResponseString.append("{").append(wSP).append("}"));
        return orderPaylodSocketResponseString.toString();
    }
    
    
    
}
