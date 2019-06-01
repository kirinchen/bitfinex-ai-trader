package net.surfm.crypto.bitfinex.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.surfm.crypto.bitfinex.api.dto.SortMapper;
import net.surfm.crypto.bitfinex.api.dto.WalletDto;

@Scope("prototype")
@Service
public class ApiMethod {

	private ApiClient client;

	ApiMethod init(ApiClient c) {
		client = c;
		return this;
	}

	public List<WalletDto> Wallets() {
		List<WalletDto> ans = new ArrayList<WalletDto>();
		JSONArray ja = new JSONArray(client.post("/v2/auth/r/wallets"));
		for (int i = 0; i < ja.length(); i++) {
			ans.add(SortMapper.convert(ja.getJSONArray(i), WalletDto.class));
		}
		return ans;
	}

	public ApiClient getClient() {
		return client;
	}

}
