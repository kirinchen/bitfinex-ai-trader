package net.surfm.crypto.bitfinex.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.surfm.crypto.bitfinex.api.dto.Currency;

@SuppressWarnings("serial")
@Entity
public class TradeRow implements Serializable {

	
	private int rowId;
	private int id;
	private Currency inCurrency;
	private Currency outCurrency;
	private long timestamp;
	private Date doneAt;
	private float amount;
	private float price;

	
	@Id
	@Column(nullable = false, columnDefinition = "int(12) unsigned")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}


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

	public Date getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(Date doneAt) {
		this.doneAt = doneAt;
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

	
	

}
