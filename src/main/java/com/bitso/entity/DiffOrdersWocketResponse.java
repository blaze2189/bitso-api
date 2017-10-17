package com.bitso.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter	
public class DiffOrdersWocketResponse {

		@JsonProperty("type")
		private String type;
		@JsonProperty("book")
		private String book;
		@JsonProperty("sequence")
		private Integer sequence;
		@JsonProperty("payload")
		private List<WebSocketPayload> payload;
	
}
