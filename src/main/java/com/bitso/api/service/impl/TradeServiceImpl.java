package com.bitso.api.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitso.api.service.TradeService;
import com.bitso.api.util.TradePayloadComparator;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.TradePayload;
import com.bitso.entity.TradeRestResponse;
import com.bitso.rest.client.BitsoTrade;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	protected BitsoTrade bitsoTrade;
	
	@Autowired
	protected Integer totalRecentTrades;

	@Autowired
	protected DataConfiguration dataConfiguration;

	@Override
	public Set<TradePayload> getNRecentTrades() {

		Set<TradePayload> newPayload = new TreeSet<>(new TradePayloadComparator());
		TradeRestResponse bitsoTradeResponse = bitsoTrade.getRecentTrades();
		List<TradePayload> oldPayload = bitsoTradeResponse.getTradePayload();
		// TableView<TradePayload> tableView =
		// dataConfiguration.getTradePayloadTableView();
		int upTicketsLimit = dataConfiguration.getUpTicketsStrategy();
		int downTicketsLimit = dataConfiguration.getDownTicketsStrategy();
		AtomicInteger counterUpTickets = new AtomicInteger(0);
		AtomicInteger counterDownTickets = new AtomicInteger(0);
		AtomicInteger counter = new AtomicInteger(0);
		// if (tableView != null) {

		oldPayload.forEach(item -> {
			boolean addPayload = newPayload.size() <= dataConfiguration.getTotalRecentTrades();
			if (addPayload) {
				int nextItem = counter.incrementAndGet();
				if (nextItem < oldPayload.size()) {
					TradePayload nextPayload = oldPayload.get(nextItem);
					Double itemPrice = Double.valueOf(item.getPrice());
					Double lastPrice = Double.valueOf(nextPayload.getPrice());

					if (itemPrice < lastPrice) {
						counterDownTickets.set(0);
						if (counterUpTickets.getAndIncrement() >= upTicketsLimit) {
							TradePayload falseTrade = new TradePayload();
							falseTrade.setBook("btc_mxn");
							falseTrade.setCreatedAt(nextPayload.getCreatedAt());
							// falseTrade.setCreatedAt(new Date());
							falseTrade.setAmount("1");
							falseTrade.setMakerSide("buy");
							falseTrade.setPrice(nextPayload.getPrice());
							falseTrade.setTid("N/A");
							newPayload.add(falseTrade);
						}
					} else if (itemPrice > lastPrice) {
						counterUpTickets.set(0);
						if (counterDownTickets.getAndIncrement() >= downTicketsLimit) {
							TradePayload falseTrade = new TradePayload();
							falseTrade.setBook("btc_mxn");
							falseTrade.setCreatedAt(nextPayload.getCreatedAt());
							// falseTrade.setCreatedAt(new Date());
							falseTrade.setAmount("1");
							falseTrade.setMakerSide("sell");
							falseTrade.setPrice(nextPayload.getPrice());
							falseTrade.setTid("N/A");
							newPayload.add(falseTrade);
						}
					}
				}
				if (newPayload.size() <= dataConfiguration.getTotalRecentTrades()) {
					newPayload.add(item);
				}
			}
		});
		return newPayload;
	}

}
