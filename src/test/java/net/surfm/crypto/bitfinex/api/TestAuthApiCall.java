package net.surfm.crypto.bitfinex.api;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.surfm.crypto.bitfinex.api.AuthApiCall;
import net.surfm.infrastructure.CommUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAuthApiCall {

	@Inject
	private AuthApiCall apiCall;

	@Test
	public void testOne() {
		apiCall.run(c -> {
			CommUtils.pl(c.Wallets());
			CommUtils.pl(c.getClient().post("/v2/auth/r/orders"));
		});
	}

}
