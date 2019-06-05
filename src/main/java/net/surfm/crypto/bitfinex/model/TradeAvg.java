package net.surfm.crypto.bitfinex.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import net.surfm.crypto.bitfinex.api.dto.Currency;

@SuppressWarnings("serial")
@Entity
public class TradeAvg implements Serializable {

	
	private int id;
	private Currency inCurrency;
	private Currency outCurrency;
	private long timestamp;
	private Date recordAt;
	private float sellAmount;
	private float buyAmount;
	private float sellPrice;
	private float buyPrice;

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(50)")
	public Currency getInCurrency() {
		return inCurrency;
	}

	public void setInCurrency(Currency inCurrency) {
		this.inCurrency = inCurrency;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "varchar(50)")
	public Currency getOutCurrency() {
		return outCurrency;
	}

	public void setOutCurrency(Currency outCurrency) {
		this.outCurrency = outCurrency;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Date getRecordAt() {
		return recordAt;
	}

	public void setRecordAt(Date recordAt) {
		this.recordAt = recordAt;
	}

	public float getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(float sellAmount) {
		this.sellAmount = sellAmount;
	}

	public float getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(float buyAmount) {
		this.buyAmount = buyAmount;
	}

	public float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(float buyPrice) {
		this.buyPrice = buyPrice;
	}

}
