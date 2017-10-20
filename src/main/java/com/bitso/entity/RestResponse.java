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
public class RestResponse {

    @JsonProperty("success")
    private Boolean success;
    
    @JsonProperty("payload")
    private List<RestPayload> payload;

}
