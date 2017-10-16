/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoTradesChannel extends BitsoChannelSubscriberImpl{

    @Value("${bitso.channel.difforders}")
    private String diffOrderChannel;

    public BitsoTradesChannel() {
        this.channelName="trades";
    }

    
    
}
