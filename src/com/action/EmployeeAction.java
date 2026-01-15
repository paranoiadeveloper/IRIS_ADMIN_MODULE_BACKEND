package com.action;

import com.Dao.EmployeeDao;
import com.beanClass.Employee;
import com.beanClass.PageResponse;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;

public class EmployeeAction extends ActionSupport {

    private int page;
    private int size;
    private String sortField;
    private String sortOrder;

    private PageResponse<Employee> result;

    public String execute() {

        EmployeeDao dao = new EmployeeDao();

        List<Employee> list =
            dao.getEmployees(page, size, sortField, sortOrder);

        long total = dao.getEmployeeCount();

        result = new PageResponse<>();
        result.setContent(list);
        result.setTotalElements(total);

        return SUCCESS;
    }

    public PageResponse<Employee> getResult() {
        return result;
    }

    public void setPage(int page) { this.page = page; }
    public void setSize(int size) { this.size = size; }
    public void setSortField(String sortField) { this.sortField = sortField; }
    public void setSortOrder(String sortOrder) { this.sortOrder = sortOrder; }
}
