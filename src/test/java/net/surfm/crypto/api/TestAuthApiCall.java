package net.surfm.crypto.api;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.surfm.crypto.api.AuthApiCall;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAuthApiCall {

	@Inject
	private AuthApiCall apiCall;

	@Test
	public void testOne() {
		apiCall.run(c -> {
			System.out.println(c.Wallets());
			System.out.println(c.getClient().post("/v2/auth/r/orders"));
		});
	}

}
