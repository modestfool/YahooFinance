package org.logicmonitor.stockmonitor.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jws.WebParam.Mode;

import org.json.JSONException;
import org.json.JSONObject;
import org.logicmonitor.stockmonitor.model.Response;
import org.logicmonitor.stockmonitor.model.Stocks;
import org.logicmonitor.stockmonitor.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;


@Controller
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	@RequestMapping(value="viewCompany")
	public ModelAndView getStockHistory(@RequestParam("code") String companyCode) throws JSONException, ParseException{
	
		SimpleDateFormat dt = new SimpleDateFormat("yy-MM-dd hh:mm");
        
		List<Stocks> stocks = companyService.getStockHistory(companyCode);
		int n = stocks.size();
		String[] times = new String[n];
		BigDecimal[] prices = new BigDecimal[n];
		
		JSONObject js = new JSONObject();
		int index = 0;
		for(Stocks c : stocks){
			times[index] = dt.format(c.getLastUpdated());
			prices[index] = c.getPrice();
			index++;
		}
		js.put("x", times);
		js.put("y", prices);
		
		String res = new Gson().toJson(js);
		System.out.println(res);
		
		return new ModelAndView("viewCompany", "stocks", res);
//		Response response = new Response();
//		response.setStocks(res);
//		return response;
	}
}
