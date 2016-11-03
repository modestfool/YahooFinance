package org.logicmonitor.stockmonitor.web;

import java.util.List;

import org.logicmonitor.stockmonitor.model.Company;
import org.logicmonitor.stockmonitor.model.Owner;
import org.logicmonitor.stockmonitor.model.Response;
import org.logicmonitor.stockmonitor.service.CompanyService;
import org.logicmonitor.stockmonitor.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class OwnerController {

	@Autowired
	OwnerService ownerService;
	
	@Autowired
	CompanyService companyService;
	
	static String user;
	@RequestMapping(value="addUser", method=RequestMethod.POST)
	public ModelAndView addOwner(@RequestParam("username") String username, @RequestParam("password") String password){
		Owner owner = new Owner();
		owner.setUsername(username);
		owner.setPassword(password);
		ownerService.addUser(owner);
		
		return new ModelAndView("redirect:/companies");
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public ModelAndView login(@RequestParam("user") String username, @RequestParam("pass") String password) {

		String message = "";
		boolean valid = false;
		
		user = username;
		Owner user = new Owner(username, password);
		
		valid = ownerService.validateUser(user);
		System.out.println(user + " " +valid);
		if (valid) {
			return new ModelAndView("redirect:/companies");
		}
		else {
			message = "Username or Password are invalid.Please try again !!";
			return new ModelAndView("index", "message", message);
		}
	}
	
	@RequestMapping(value="/companies")
	public ModelAndView getUserLIst() {
		List<Company> coList = ownerService.listCompanies(user);
		for(Company co : coList)
			System.out.println(co);
		return new ModelAndView("companies", "companiesList", coList);
	}
	
//	@RequestMapping(value="/Companies")
//	public ModelAndView home() 
//	{       
//		return new ModelAndView("Companies");
//	}
	
	@RequestMapping(value="DeleteUser", method=RequestMethod.GET)
	public @ResponseBody Response deleteOwner(@RequestParam("name") String username){
	
		ownerService.deleteUser(username);
		Response response = new Response();
		response.setMessage("Deleted Owner Successfully.");
		return response;
	}
	

	@RequestMapping(value="deleteCompany")
	public String deleteCompany(@RequestParam("name") String companyName){

		ownerService.deleteCompany(user, companyName);
		return "redirect:/companies";
	}

	
	@RequestMapping(value="AddCompany", method=RequestMethod.GET)
	public String addCompany(@RequestParam("name") String companyName, @RequestParam("code") String code){

		code = code.toUpperCase();
		
		System.out.println("add "+code);
		ownerService.addCompany(user, companyName);
		companyService.addCompany(companyName, code);
		return "redirect:/companies";

//		Response response = new Response();
//		response.setMessage("Added New Company Successfully.");
//		return response;
	}
	
//	@RequestMapping(value = "ListCompanies", method=RequestMethod.GET)
//	public @ResponseBody Response listCompanies()
//	{       
//		List<Company> list = ownerService.listCompanies(user);
//		
//		String res = new Gson().toJson(list);
//		Response response = new Response();
//		response.setCompanies(res);
//		return response;
//	}
	
}
