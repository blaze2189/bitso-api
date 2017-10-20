/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jorge
 */
@Getter
@Setter
 @JsonTypeInfo(use = Id.NONE)
    @JsonSubTypes({
        @JsonSubTypes.Type(value = RestOrderBookPayload.class, name = "RetOrderBookPayload"),
        @JsonSubTypes.Type(value = RestTickerPayload.class, name = "RestTickerPayload")
    })
public class RestPayload {

    
}
