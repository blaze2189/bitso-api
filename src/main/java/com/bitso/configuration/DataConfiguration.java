package com.bitso.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bitso.api.util.PayloadSocketComparator;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.RestResponse;
import com.bitso.entity.WebSocketPayload;

@Component
public class DataConfiguration {

	@Autowired
	private PayloadSocketComparator payloadSocketComparator;

	@Bean(name="lastSequenceTrade")
	public Integer lastSequenceTrade() {
		return 0;
	}
	
	@Bean(name = "tradesList")
	public List<RestResponse> listBitsoRespone() {
		return Collections.synchronizedList(new ArrayList<RestResponse>());
	}

	@Bean(name = "topAsks")
	public Set<WebSocketPayload> listTopAsk() {
		return Collections.synchronizedSet(new TreeSet<>(payloadSocketComparator));
	}

	@Bean(name = "topBids")
	public Set<WebSocketPayload> listTopBid() {
		return Collections.synchronizedSet(new TreeSet<>(payloadSocketComparator));
	}
	
	@Bean(name="orderBook")
	public RestResponse orderBook(){
		return new RestResponse();
	}
	
	@Bean(name="recentBids")
	public List<DiffOrdersWocketResponse> recentBids(){
		return  Collections.synchronizedList(new ArrayList<>());
	}
	
	@Bean(name="recentAsks")
	public List<DiffOrdersWocketResponse> recentAsks(){
		return  Collections.synchronizedList(new ArrayList<>());
	}
	
}
