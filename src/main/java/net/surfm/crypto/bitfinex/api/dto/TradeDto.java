package net.surfm.crypto.bitfinex.api.dto;

public class TradeDto {

	private int id;
	private long timestamp;
	private float amount;
	private float price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "TradeDto [id=" + id + ", timestamp=" + timestamp + ", amount=" + amount + ", price=" + price + "]";
	}

}
