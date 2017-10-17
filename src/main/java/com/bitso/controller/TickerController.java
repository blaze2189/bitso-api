/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.controller;

import com.bitso.entity.RestResponse;
import com.bitso.rest.client.BitsoTicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Jorge
 */
@Controller
public class TickerController {

    @Autowired
    private BitsoTicker bitsoTicker;

    public RestResponse getBistoResponse() {

        return bitsoTicker.getTrades();

    }

}
