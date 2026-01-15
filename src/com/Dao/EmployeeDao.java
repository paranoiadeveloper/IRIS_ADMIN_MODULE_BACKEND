package com.Dao;

import com.beanClass.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

	public List<Employee> getEmployees(
	        int page, int size, String sortField, String sortOrder) {

	    List<Employee> list = new ArrayList<>();
	    int offset = page * size;

	    // DEFAULT SORT
	    if (sortField == null || sortField.isEmpty()) {
	        sortField = "id";
	    }

	    if (sortOrder == null || sortOrder.isEmpty()) {
	        sortOrder = "asc";
	    }

	    // FIELD MAPPING
	    if ("dateCreated".equals(sortField)) {
	        sortField = "date_created";
	    }

	    String sql =
	        "SELECT * FROM employees ORDER BY " + sortField +
	        " " + sortOrder + " LIMIT ? OFFSET ?";

	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, size);
	        ps.setInt(2, offset);

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Employee e = new Employee();
	            e.setId(rs.getLong("id"));
	            e.setName(rs.getString("name"));
	            e.setPhoneno(rs.getString("phoneno"));
	            e.setAddress(rs.getString("address"));
	            e.setRating(rs.getInt("rating"));
	            e.setCountry(rs.getString("country"));
	            e.setDateCreated(rs.getString("date_created"));
	            e.setIsAdmin(rs.getInt("is_admin"));
	            list.add(e);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}


    public long getEmployeeCount() {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps =
                 con.prepareStatement("SELECT COUNT(*) FROM employees")) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
