package net.surfm.crypto.api;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPublicApi {

	@Inject
	private PublicApi api;

	@Test
	public void testOne() {
		long now = System.currentTimeMillis();
		long late = now - 1000000;
		
		System.out.println(api.listTrades("BTCUSD", late, now));

	}

}
