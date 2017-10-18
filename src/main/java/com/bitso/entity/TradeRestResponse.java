package com.bitso.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeRestResponse {

	@JsonProperty("success")
	private Boolean success;
	@JsonProperty("payload")
	private List<TradePayload> tradePayload;
}
