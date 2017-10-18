package com.bitso.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBookRestResponse {

	@JsonProperty("success")
	private Boolean success;
	@JsonProperty("payload")
	private OrderBookPayload orderPayload;

}
