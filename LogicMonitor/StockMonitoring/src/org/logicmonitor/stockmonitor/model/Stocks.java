package org.logicmonitor.stockmonitor.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Stocks {

	private BigDecimal price;
	private Timestamp lastUpdated;
	
	public Stocks(){}
	
	public Stocks(BigDecimal price, Timestamp lastUpdated) {
		super();
		this.price = price;
		this.lastUpdated = lastUpdated;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "Stocks [price=" + price + ", lastUpdated=" + lastUpdated + "]";
	}
}
