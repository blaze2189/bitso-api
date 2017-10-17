package com.bitso.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeInformation {

	@JsonProperty("book")
	private String book;
	@JsonProperty("price")
	private String price;
	@JsonProperty("amount")
	private String amount;

}
