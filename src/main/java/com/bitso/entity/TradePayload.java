package com.bitso.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradePayload {

	@JsonProperty("book")
	private String book;
	@JsonProperty("created_at")
	private Date createdAt;
	@JsonProperty("amount")
	private String amount;
	@JsonProperty("maker_side")
	private String makerSide;
	@JsonProperty("price")
	private String price;
	@JsonProperty("tid")
	private String tid;
	@JsonProperty(value="real",required=false)
	private boolean real;

}
