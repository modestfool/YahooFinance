package org.logicmonitor.stockmonitor.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Company {

	private String name;
	private String code;
	private Timestamp lastUpdated;
	private BigDecimal stockPrice;
	
	public Company(){
		
	}
	
	public Company(String companyName, String companyCode){
		this.name = companyName;
		this.code = companyCode;
	}
	
	public BigDecimal getStockPrice(){
		return stockPrice;
	}
	public void setStockPrice(BigDecimal stockPrice){
		this.stockPrice = stockPrice;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String companyName) {
		this.name = companyName;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String companyCode) {
		this.code = companyCode;
	}
	
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp timestamp) {
		this.lastUpdated = timestamp;
	}
	
	@Override
	public String toString() {
		return "Company [name=" + name + ", code=" + code + ", price=" + stockPrice + ", lastUpdated=" + lastUpdated +"]";
	}
}
