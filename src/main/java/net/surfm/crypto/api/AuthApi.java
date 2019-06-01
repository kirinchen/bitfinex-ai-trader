package net.surfm.crypto.api;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.surfm.crypto.api.dto.SortMapper;
import net.surfm.crypto.api.dto.WalletDto;

@Scope("prototype")
@Service
public class AuthApi {

	private AuthClient client;

	AuthApi init(AuthClient c) {
		client = c;
		return this;
	}

	public List<WalletDto> Wallets() {
		return SortMapper.convertList(client.post("/v2/auth/r/wallets"), WalletDto.class);
	}

	public AuthClient getClient() {
		return client;
	}

}
