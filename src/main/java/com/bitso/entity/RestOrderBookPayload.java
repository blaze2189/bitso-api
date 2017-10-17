package com.bitso.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestOrderBookPayload extends RestPayload {

	@JsonProperty("asks")
	private List<TradeInformation> asks;
	@JsonProperty("bids")
	private List<TradeInformation> bids;
	@JsonProperty("updated_at")
	private Date updatedAt;
	@JsonProperty("sequence")
	private Integer sequence;

}