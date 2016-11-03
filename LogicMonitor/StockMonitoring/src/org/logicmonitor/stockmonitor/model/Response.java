/**
 * 
 */
package org.logicmonitor.stockmonitor.model;

/**
 * @author sindhu
 *
 */
public class Response {

	private String companies;
	private String stocks;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCompanies() {
		return companies;
	}
	public void setCompanies(String companies) {
		this.companies = companies;
	}
	public String getStocks() {
		return stocks;
	}
	public void setStocks(String stocks) {
		this.stocks = stocks;
	}
}