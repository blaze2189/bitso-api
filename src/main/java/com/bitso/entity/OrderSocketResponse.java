/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jorge
 */
@Getter
@Setter
public class OrderSocketResponse {
    
    @JsonProperty("type")
    private String type;
    @JsonProperty("book")
    private String book;
    @JsonProperty("payload")
    private OrderPayloadSocketResponse orderPayloadSocketResponse;

    @Override
    public String toString() {
        StringBuilder orderSocketResponseBuilder = new StringBuilder(50);
        orderSocketResponseBuilder.append("\ntype: ").append(type).
                append("\nbook: ").append(book).
                append("\npayload: ").append(orderPayloadSocketResponse);
        return orderSocketResponseBuilder.toString();
    }
    
    
}
