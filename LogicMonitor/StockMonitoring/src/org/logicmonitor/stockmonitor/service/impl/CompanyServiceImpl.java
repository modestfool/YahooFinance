package org.logicmonitor.stockmonitor.service.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.logicmonitor.stockmonitor.model.Company;
import org.logicmonitor.stockmonitor.model.Stocks;
import org.logicmonitor.stockmonitor.service.CompanyService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class CompanyServiceImpl implements CompanyService {

	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	@Override
	public void addCompany(String name, String code) {

		Company company = new Company(name, code);

		Stock stock = null;
		try {
			stock = YahooFinance.get(code);
			company.setStockPrice(stock.getQuote().getPrice());
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
		
		String sql = "INSERT INTO COMPANY (name, code, lastUpdated, stockPrice)"
                + " VALUES (?, ?, ?, ?)";
		try{
			template.update(sql, company.getName(), company.getCode(),
	            company.getLastUpdated(), company.getStockPrice());
		}
		catch(Exception e){
			return;
		}
	}

	@Override
	public List<Stocks> getStockHistory(String company) {

		String query = "SELECT * FROM STOCK where COMPANY = \""+company+"\"";
	    List<Stocks> stocks = template.query(query, new RowMapper<Stocks>() {
	        public Stocks mapRow(ResultSet rs, int rowNum) throws SQLException {
	            Stocks stock = new Stocks();
	            stock.setPrice(rs.getBigDecimal("price"));
	            stock.setLastUpdated(rs.getTimestamp("lastUpdated"));
	            return stock;
	        }
	    });
	 
		return stocks;
	}

}
