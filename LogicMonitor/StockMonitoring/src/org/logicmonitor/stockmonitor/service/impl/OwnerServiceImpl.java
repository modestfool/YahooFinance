package org.logicmonitor.stockmonitor.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.logicmonitor.stockmonitor.model.Company;
import org.logicmonitor.stockmonitor.model.Owner;
import org.logicmonitor.stockmonitor.service.OwnerService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class OwnerServiceImpl implements OwnerService {

	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public boolean validateUser(Owner owner) {

		String query = "SELECT * FROM users WHERE Username = ?";
		try
		{
			Owner actualUser = template.queryForObject(
					query, new Object[] { owner.getUsername() },
					new BeanPropertyRowMapper<Owner>(Owner.class));
		
			if(owner.getPassword().equalsIgnoreCase(actualUser.getPassword()))
				return true;
			return false;
		}
		catch(EmptyResultDataAccessException e){
			return false;
		}
	}

	@Override
	public void addCompany(String user, String company) {
		String sql = "INSERT INTO USERCOMPANY (user, company)"
                + " VALUES (?, ?)";
		try{
			// Normalizing before feeding it to the DB.
			company = company.toUpperCase();
			template.update(sql, user, company);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCompany(String user, String company) {
		String sql = "DELETE FROM USERCOMPANY WHERE user=? and company=?";
		// Normalizing before feeding it to the DB.
		company = company.toUpperCase();
	    template.update(sql, user, company);
	}

	@Override
	public List<Company> listCompanies(String user) {
		String query = "SELECT * FROM COMPANY WHERE name IN (SELECT COMPANY FROM USERCOMPANY WHERE user = \'"+user+"\')";
		System.out.println(query);
	    List<Company> companies = template.query(query, new RowMapper<Company>() {
	 
	        public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
	            Company company = new Company();
	            company.setName(rs.getString("name"));
	            company.setCode(rs.getString("code"));
	            company.setLastUpdated(rs.getTimestamp("lastUpdated"));
	            company.setStockPrice(rs.getBigDecimal("stockPrice"));            
	 
	            return company;
	        }
	    });
	 
		return companies;
	}

	@Override
	public void addUser(Owner user) {
		String sql = "INSERT INTO USERS (username, password)"
                + " VALUES (?, ?)";
		try{
			template.update(sql, user.getUsername(), user.getPassword());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(String user) {
		String sql = "DELETE FROM USERS WHERE user=?";
	    template.update(sql, user);
	}

}
