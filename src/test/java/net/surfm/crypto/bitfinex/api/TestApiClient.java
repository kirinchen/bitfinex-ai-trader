package net.surfm.crypto.bitfinex.api;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApiClient {

	@Inject
	private ApiCall apiCall;

	@Test
	public void testOne() {
		apiCall.run(c -> {
			System.out.println(c.Wallets());
			System.out.println(c.getClient().post("/v2/auth/r/orders"));
		});
	}

}
