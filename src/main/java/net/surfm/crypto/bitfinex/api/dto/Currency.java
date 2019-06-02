package net.surfm.crypto.bitfinex.api.dto;

public enum Currency {
	BTC , USD;
	
	public static String pair(Currency in,Currency out) {
		return in.toString() + out.toString();
	}
}
