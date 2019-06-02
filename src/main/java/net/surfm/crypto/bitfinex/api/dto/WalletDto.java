package net.surfm.crypto.bitfinex.api.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.surfm.infrastructure.ReflectionTool;

public class WalletDto {

	private WalletType walletType;
	private String currency;
	private float balance;

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "WalletDto [walletType=" + walletType + ", currency=" + currency + ", balance=" + balance + "]";
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ReflectionTool.getFieldNames(WalletDto.class).forEach(f -> {
			System.out.println(f);
		});

		String p = "{\"balance\":0.89934609,\"walletType\":\"exchange\",\"currency\":\"DAT\"}";
		ObjectMapper jsonMapper = new ObjectMapper();
		WalletDto d = jsonMapper.readValue(p, WalletDto.class);
		System.out.println("d="+d);
	}
}
