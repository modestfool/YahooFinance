package com.logicmonitor.main.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import java.sql.Connection;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

/**
 * @author bkanaparthi
 *
 */
public class UpdateStocks {
	
	static Connection conn = null;

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SQLException, IOException{		   
		 
		final UpdateStocks fetcher = new UpdateStocks();
		String filename = "";
		if(args.length > 0)
			filename = args[0];
		// Bundled the config properties needed to read/write to the DB.
		filename = "config.properties";
		conn = fetcher.getDbConnection(filename);
		Timer t = new Timer();

		t.scheduleAtFixedRate(
		    new TimerTask()
		    {
		        public void run()
		        {
		    		try {
						fetcher.updateStocks();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		    },
		    0,      
		    300000);
		    
	}
	
	public Connection getDbConnection(String filename) throws IOException, SQLException{
		Properties prop = new Properties();
		String propFileName = filename;

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		String driver = prop.getProperty("jdbcdriver");

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String user = prop.getProperty("username");
		String password = prop.getProperty("password");
		String dburl = prop.getProperty("dburl");
		return conn = DriverManager.getConnection(dburl, user, password);
		
	}
	
	public BigDecimal getStockQuote(String companyCode) throws IOException, SQLException{
		Stock stock = YahooFinance.get(companyCode);		
		StockQuote sq = stock.getQuote();		
		
		String insertQuery = "INSERT INTO STOCK (company, ask, bid, prevClose, price, volume) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(insertQuery);
		
		pstmt.setString(1, companyCode);
		pstmt.setBigDecimal(2, sq.getAsk());
		pstmt.setBigDecimal(3, sq.getBid());
		pstmt.setBigDecimal(4, sq.getPreviousClose());
		pstmt.setBigDecimal(5, sq.getPrice());
		pstmt.setLong(6, sq.getVolume());
		
		pstmt.executeUpdate();
		
		return sq.getPrice();
	}
	
	public void updateStocks() throws SQLException, IOException{
		Statement stmt = conn.createStatement();
		String sql = "SELECT name, code FROM COMPANY";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			String coName = rs.getString("name");
			String coCode = rs.getString("code");
			
			BigDecimal price = getStockQuote(coCode);
			
			String updateQuery = "UPDATE COMPANY SET stockPrice=? WHERE name=?";
			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			pstmt.setBigDecimal(1, price);
			pstmt.setString(2, coName);
			
			pstmt.executeUpdate();
		}
	}
}
