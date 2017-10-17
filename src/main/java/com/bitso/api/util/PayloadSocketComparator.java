package com.bitso.api.util;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.bitso.entity.WebSocketPayload;

@Component
public class PayloadSocketComparator implements Comparator<WebSocketPayload> {

	@Override
	public int compare(WebSocketPayload payloadOne, WebSocketPayload payloadTwo) {
		int order = 0;
		Integer amountOne = Integer.parseInt(payloadOne.getAmount());
		Integer rateOne = Integer.parseInt(payloadOne.getRate());
		Integer valueOne = Integer.parseInt(payloadOne.getValue());

		Integer amountTwo = Integer.parseInt(payloadTwo.getAmount());
		Integer rateTwo = Integer.parseInt(payloadTwo.getRate());
		Integer valueTwo = Integer.parseInt(payloadTwo.getValue());

		if (amountOne > amountTwo) {
			order = 1;
		} else if (amountOne == amountTwo) {
			if (rateOne > rateTwo) {
				order = 1;
			} else if (rateOne == rateTwo) {
				if (valueOne > valueTwo) {
					order = 1;
				} else if (valueOne < valueTwo) {
					order = -1;
				}
			} else {
				order = -1;
			}
		} else {
			order = -1;
		}
		return order;
	}

}
