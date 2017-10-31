package com.bitso.api.util;

import java.util.Comparator;
import java.util.Date;

import com.bitso.entity.TradePayload;

public class TradePayloadComparator implements Comparator<TradePayload>{

	@Override
	public int compare(TradePayload tradePayloadOne, TradePayload tradePayloadTwo) {
		int compare = 0;
		Date dateOne = tradePayloadOne.getCreatedAt();
		Date dateTwo = tradePayloadTwo.getCreatedAt();
		compare = dateOne.compareTo(dateTwo);
		if(compare==0) {
			if(tradePayloadOne.getPrice().equals(tradePayloadTwo.getPrice()) ) {
				if(tradePayloadOne.getTid().equals("N/A") && tradePayloadTwo.getTid().equals("N/A")) {
					compare=0;
				}else if(tradePayloadOne.getTid().equals("N/A") && 
						!tradePayloadTwo.getTid().equals("N/A")) {
					compare=-1;
				}else if(!tradePayloadOne.getTid().equals("N/A") && 
						tradePayloadTwo.getTid().equals("N/A")) {
					compare=1;
				}
			}
			
			if(tradePayloadTwo.getTid().equals("N/A") && tradePayloadOne.getTid().equals("N/A")) {
				compare=0;
			}
			if(tradePayloadTwo.getTid().equals("N/A") && !tradePayloadOne.equals("N/A")) {
				compare=-1;
			}else if(tradePayloadOne.getTid().equals("N/A") && !tradePayloadTwo.equals("N/A")) {
				compare=1;
			}else {
				Integer tidOne=Integer.parseInt(tradePayloadOne.getTid());
				Integer tidTwo=Integer.parseInt(tradePayloadTwo.getTid());
				compare=tidOne.compareTo(tidTwo);
			}
		}
		return compare*-1;
	}

}
