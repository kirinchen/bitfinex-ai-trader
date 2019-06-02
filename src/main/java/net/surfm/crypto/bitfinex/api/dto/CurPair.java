package net.surfm.crypto.bitfinex.api.dto;

public class CurPair {

	private Currency in;
	private Currency out;

	public CurPair() {
	}

	public CurPair(Currency in, Currency out) {
		super();
		this.in = in;
		this.out = out;
	}

	public Currency getIn() {
		return in;
	}

	public void setIn(Currency in) {
		this.in = in;
	}

	public Currency getOut() {
		return out;
	}

	public void setOut(Currency out) {
		this.out = out;
	}

	@Override
	public String toString() {
		return in.toString()+out.toString();
	}
	
	public static CurPair gen(Currency in, Currency out) {
		return new CurPair(in, out);
	}

}
