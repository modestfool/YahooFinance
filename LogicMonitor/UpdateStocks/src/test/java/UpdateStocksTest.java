import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.logicmonitor.main.java.UpdateStocks;

public class UpdateStocksTest {

	@Test
	public void testGetDbConnection() {
		UpdateStocks fetcher = new UpdateStocks();
		Connection conn = null;
		try {
			conn = fetcher.getDbConnection("./config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(conn);
	}
	
	@Test
	public void getStockQuote(){
		String code = "AAPL";
		UpdateStocks fetcher = new UpdateStocks();
		BigDecimal price = null;
		try {
			price = fetcher.getStockQuote(code);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(price);
	}

}
