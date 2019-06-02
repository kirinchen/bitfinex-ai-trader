package net.surfm.crypto.bitfinex.api.dto;

public class TradeBookDto {

	private float price;
	private int count;
	private float amount;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TradeBookDto [price=" + price + ", count=" + count + ", amount=" + amount + "]";
	}

}
