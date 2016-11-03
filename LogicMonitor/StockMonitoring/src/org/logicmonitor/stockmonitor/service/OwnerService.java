package org.logicmonitor.stockmonitor.service;

import java.util.List;

import org.logicmonitor.stockmonitor.model.Company;
import org.logicmonitor.stockmonitor.model.Owner;

public interface OwnerService {
	
	public void addUser(Owner user);
	public void deleteUser(String user);
	public boolean validateUser(Owner user);
	
	public void addCompany(String user, String company);
	public void deleteCompany(String user, String company);
	public List<Company> listCompanies(String user);
}
