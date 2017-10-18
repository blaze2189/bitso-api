package com.bitso.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickerRestResponse {

	@JsonProperty("success")
	private Boolean success;
	@JsonProperty("payload")
	private TickerPayload tickerPayload;
}
