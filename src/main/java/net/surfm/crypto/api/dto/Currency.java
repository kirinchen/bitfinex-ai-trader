package net.surfm.crypto.api.dto;

public enum Currency {
	BTC , USD;
	
	public static String pair(Currency in,Currency out) {
		return in.toString() + out.toString();
	}
}
