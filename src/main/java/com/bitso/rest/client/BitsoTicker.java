/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.rest.client;

import com.bitso.entity.BitsoResponse;

/**
 *
 * @author Jorge
 */

public interface BitsoTicker {

    BitsoResponse getTrades();
    
}
