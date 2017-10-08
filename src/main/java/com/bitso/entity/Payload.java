/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jorge
 */
@Getter
@Setter
public class Payload {

    @JsonProperty("high")
    private String high;
    @JsonProperty("last")
    private String last;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("book")
    private String book;
    @JsonProperty("volume")
    private String volume;
    @JsonProperty("vwap")
    private String vwap;
    @JsonProperty("low")
    private String low;
    @JsonProperty("ask")
    private String ask;
    @JsonProperty("bid")
    private String bid;

}
