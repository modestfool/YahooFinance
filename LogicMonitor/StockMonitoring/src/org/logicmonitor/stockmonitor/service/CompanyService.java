package org.logicmonitor.stockmonitor.service;

import java.util.List;

import org.logicmonitor.stockmonitor.model.Stocks;

public interface CompanyService {

	public void addCompany(String company, String code);
	public List<Stocks> getStockHistory(String company);
}
