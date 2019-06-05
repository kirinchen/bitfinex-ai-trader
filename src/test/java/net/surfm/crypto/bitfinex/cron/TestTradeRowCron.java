package net.surfm.crypto.bitfinex.cron;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTradeRowCron {

	@Inject
	private TradeRowCron corn;

	@Test
	public void testFixedLoad() {
		corn.fixedLoad();

	}

}
