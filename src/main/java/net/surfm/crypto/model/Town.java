package net.surfm.crypto.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the town database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@NamedQuery(name="Town.findAll", query="SELECT t FROM Town t")
public class Town implements Serializable {
	private int townId;
	private int leaderId;
	private String leaderName;
	private String name;
	private int salesMoney;
	private int salesMoneyYesterday;
	private int taxRate;
	private int taxRateReserved;
	private int townFixTax;
	private int townTax;
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getTownId() {
		return townId;
	}
	public void setTownId(int townId) {
		this.townId = townId;
	}
	public int getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalesMoney() {
		return salesMoney;
	}
	public void setSalesMoney(int salesMoney) {
		this.salesMoney = salesMoney;
	}
	public int getSalesMoneyYesterday() {
		return salesMoneyYesterday;
	}
	public void setSalesMoneyYesterday(int salesMoneyYesterday) {
		this.salesMoneyYesterday = salesMoneyYesterday;
	}
	public int getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}
	public int getTaxRateReserved() {
		return taxRateReserved;
	}
	public void setTaxRateReserved(int taxRateReserved) {
		this.taxRateReserved = taxRateReserved;
	}
	public int getTownFixTax() {
		return townFixTax;
	}
	public void setTownFixTax(int townFixTax) {
		this.townFixTax = townFixTax;
	}
	public int getTownTax() {
		return townTax;
	}
	public void setTownTax(int townTax) {
		this.townTax = townTax;
	}



}