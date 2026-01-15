package com.action;

import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.Dao.DBUtil;
import com.Dao.DashboardDAO;
import com.beanClass.LabBean;
import com.opensymphony.xwork2.ActionSupport;

public class DashboardAction extends ActionSupport {

    private ArrayList<LabBean> result;
    private String from;

    public String dashboardReport() {

        

        Connection con = null;

        try {
            con = DBUtil.getConnection();
            result = DashboardDAO.getDashboardReport(con, from);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return SUCCESS;
    }

    public ArrayList<LabBean> getResult() {
        return result;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
