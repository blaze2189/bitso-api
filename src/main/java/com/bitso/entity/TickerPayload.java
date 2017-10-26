package com.bitso.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickerPayload extends RestPayload {

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

	@Override
	public String toString() {
		StringBuilder stringPayload = new StringBuilder();
		stringPayload.append("high:").append(high).append("\n").append("last:").append(last).append("\n")
				.append("createdAt:").append(createdAt).append("\n").append("book:").append(book).append("\n")
				.append("volume:").append(volume).append("\n").append("vwap:").append(vwap).append("\n").append("low:")
				.append(low).append("\n").append("ask:").append(ask).append("\n").append("bid:").append(bid)
				.append("\n");
		return stringPayload.toString(); 
	}

}
