/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jorge
 */
@Getter
@Setter
public class WebSocketPayload {

    @JsonProperty(value = "i", required = false)
    private Integer identifier;//	Number	A unique number identifying the transaction	-
    @JsonProperty(value = "a", required = false)
    private String amount;//	String	Amount	Major
    @JsonProperty(value = "r", required = false)
    private String rate;//	String	Rate	Minor
    @JsonProperty(value = "v", required = false)
    private String value;//	String	Value	Minor
    @JsonProperty(value = "t", required = false)
    private Integer markerSide;//	Number	Maker side, 0 indicates buy 1, indicates sell	-
    @JsonProperty(value = "mo", required = false)
    private String markerOrderId;//	String	Maker Order ID	-
    @JsonProperty(value = "to", required = false)
    private String traderOrderID;//	String	Taker Order ID
    @JsonProperty(value = "d", required = false)
    private Long timeStamp;//	Number	Unix timestamp	Milliseconds
    @JsonProperty(value = "o", required = false)
    private String orderId;
    @JsonProperty(value = "s", required = false)
    private String s;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(50);
        stringBuilder.append("\ni :").append(identifier).
                append("\na :").append(amount).
                append("\nr :").append(rate).
                append("\nv :").append(value).
                append("\nt :").append(markerSide).
                append("\nmo :").append(markerOrderId).
                append("\nto :").append(traderOrderID).
                append("\nd :").append(timeStamp).
                append("\no :").append(orderId);
        return stringBuilder.toString();
    }

}
