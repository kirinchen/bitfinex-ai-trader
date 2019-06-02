package net.surfm.crypto.bitfinex.api;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.surfm.crypto.bitfinex.api.PublicApi;
import net.surfm.crypto.bitfinex.api.PublicApi.Precision;
import net.surfm.crypto.bitfinex.api.dto.CurPair;
import net.surfm.crypto.bitfinex.api.dto.Currency;
import net.surfm.infrastructure.CommUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPublicApi {

	@Inject
	private PublicApi api;

	@Test
	public void testOne() {
		long now = System.currentTimeMillis();
		long late = now - 1000000;
		CurPair cp = CurPair.gen(Currency.BTC, Currency.USD);
		
		CommUtils.pl(api.listTrades(cp, late, now));
		CommUtils.pl(api.listTradeBook(cp, Precision.P0, -1));

	}

}
