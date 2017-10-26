package com.bitso.api.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.bitso.api.service.TradeService;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.TradePayload;
import com.bitso.entity.TradeRestResponse;
import com.bitso.rest.client.BitsoTrade;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TradeServiceImplTest {

	public BitsoTrade bitsoTrade = mock(BitsoTrade.class);

	public TradeService tradeService = mock(TradeServiceImpl.class);

	public DataConfiguration dataConfiguration = mock(DataConfiguration.class);

	public ApplicationContext applicationContext;

	@Before
	public void setUp() {
		// applicationContext = new AnnotationConfigApplicationContext();
		// ((AnnotationConfigApplicationContext)
		// applicationContext).register(ConfigurationTest.class);
		// ((AnnotationConfigApplicationContext) applicationContext).refresh();
		// tradeService = applicationContext.getBean(TradeServiceImpl.class);

	}

	private TradeRestResponse createMockTradeResponse() {
		String jsonRestResponse = "{\"success\":true,\"payload\":[{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:41:00+0000\",\"amount\":\"0.00054309\",\"maker_side\":\"sell\",\"price\":\"114459.97\",\"tid\":1770448},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:41:00+0000\",\"amount\":\"0.03177399\",\"maker_side\":\"sell\",\"price\":\"114453.95\",\"tid\":1770447},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:41:00+0000\",\"amount\":\"0.01398998\",\"maker_side\":\"sell\",\"price\":\"114451.86\",\"tid\":1770446},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:40:38+0000\",\"amount\":\"0.00498891\",\"maker_side\":\"sell\",\"price\":\"114453.95\",\"tid\":1770444},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:40:13+0000\",\"amount\":\"0.00280000\",\"maker_side\":\"buy\",\"price\":\"114200.01\",\"tid\":1770443},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:40:09+0000\",\"amount\":\"0.18568669\",\"maker_side\":\"buy\",\"price\":\"114200.01\",\"tid\":1770442},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:39:43+0000\",\"amount\":\"0.00900000\",\"maker_side\":\"buy\",\"price\":\"114200.00\",\"tid\":1770441},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:39:34+0000\",\"amount\":\"0.02543510\",\"maker_side\":\"buy\",\"price\":\"114200.00\",\"tid\":1770440},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:39:29+0000\",\"amount\":\"0.00500000\",\"maker_side\":\"buy\",\"price\":\"114200.00\",\"tid\":1770439},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:39:25+0000\",\"amount\":\"0.00087566\",\"maker_side\":\"buy\",\"price\":\"114200.00\",\"tid\":1770438},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:38:26+0000\",\"amount\":\"0.00176900\",\"maker_side\":\"buy\",\"price\":\"114200.00\",\"tid\":1770436},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:38:06+0000\",\"amount\":\"0.00500000\",\"maker_side\":\"buy\",\"price\":\"114200.00\",\"tid\":1770435},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:37:45+0000\",\"amount\":\"0.01747275\",\"maker_side\":\"sell\",\"price\":\"114463.95\",\"tid\":1770434},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:37:10+0000\",\"amount\":\"0.01384700\",\"maker_side\":\"buy\",\"price\":\"114200.00\",\"tid\":1770433},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:36:17+0000\",\"amount\":\"0.00087668\",\"maker_side\":\"buy\",\"price\":\"114088.06\",\"tid\":1770425},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:36:12+0000\",\"amount\":\"0.00120045\",\"maker_side\":\"sell\",\"price\":\"114448.85\",\"tid\":1770424},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:35:56+0000\",\"amount\":\"0.03704173\",\"maker_side\":\"buy\",\"price\":\"114071.78\",\"tid\":1770423},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:35:56+0000\",\"amount\":\"0.04891998\",\"maker_side\":\"buy\",\"price\":\"114071.81\",\"tid\":1770422},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:35:56+0000\",\"amount\":\"0.01222998\",\"maker_side\":\"buy\",\"price\":\"114079.92\",\"tid\":1770421},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:35:56+0000\",\"amount\":\"0.05500000\",\"maker_side\":\"buy\",\"price\":\"114100.00\",\"tid\":1770420},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:35:56+0000\",\"amount\":\"0.09581399\",\"maker_side\":\"buy\",\"price\":\"114106.05\",\"tid\":1770419},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:35:56+0000\",\"amount\":\"0.04105399\",\"maker_side\":\"buy\",\"price\":\"114110.07\",\"tid\":1770418},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:35:42+0000\",\"amount\":\"0.02511908\",\"maker_side\":\"sell\",\"price\":\"114452.87\",\"tid\":1770417},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:35:42+0000\",\"amount\":\"0.00982998\",\"maker_side\":\"sell\",\"price\":\"114450.78\",\"tid\":1770416},{\"book\":\"btc_mxn\",\"created_at\":\"2017-10-26T16:34:38+0000\",\"amount\":\"0.04630727\",\"maker_side\":\"sell\",\"price\":\"114452.87\",\"tid\":1770414}]}";
		

		ObjectMapper objectMapper = new ObjectMapper();
		TradeRestResponse tradeRestResponse=new TradeRestResponse();
		try {
			 tradeRestResponse=objectMapper.readValue(jsonRestResponse, TradeRestResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tradeRestResponse;
	}

	@Test
	public void testGetNRecentTrades() {
		((TradeServiceImpl) tradeService).bitsoTrade = this.bitsoTrade;
		((TradeServiceImpl) tradeService).dataConfiguration = this.dataConfiguration;
		int totalTrades=15;
		int upTickets=1;
		int downTickets=2;
		when(tradeService.getNRecentTrades()).thenCallRealMethod();
		when(dataConfiguration.getTotalRecentTrades()).thenReturn(totalTrades);
		when(dataConfiguration.getUpTicketsStrategy()).thenReturn(upTickets);
		when(dataConfiguration.getDownTicketsStrategy()).thenReturn(downTickets);
		when(bitsoTrade.getRecentTrades()).thenReturn(createMockTradeResponse());
		Set<TradePayload> setResult=tradeService.getNRecentTrades();
		assertTrue(setResult.size()==totalTrades);

	}

}
