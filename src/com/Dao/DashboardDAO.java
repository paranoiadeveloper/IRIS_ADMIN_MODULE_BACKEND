package com.Dao;

import java.sql.*;
import java.util.ArrayList;

import org.apache.commons.dbutils.DbUtils;

import com.beanClass.LabBean;
import com.ibm.icu.text.DecimalFormat;

public class DashboardDAO {

//    public static ArrayList<LabBean> getDashboardReport(Connection con, String from) {
//
//        ArrayList<LabBean> list = new ArrayList<>();
//        PreparedStatement stmt = null;
//        PreparedStatement stmt1 = null;
//        ResultSet rs = null;
//        ResultSet rs1 = null;
//
//        try {
//
//            /* ---- Get bank account ids ---- */
//            String bankSql =
//                "SELECT id FROM headsofaccount WHERE parenthead='5' AND is_parent=0";
//            stmt1 = con.prepareStatement(bankSql);
//            rs1 = stmt1.executeQuery();
//
//            StringBuilder stat = new StringBuilder("0");
//            while (rs1.next()) {
//                stat.append(",").append(rs1.getInt("id"));
//            }
//
//            /* ---- SINGLE QUERY ---- */
//            String sql =
//                "SELECT " +
//                " CASE narration " +
//                "  WHEN 'Consultation_Charge' THEN 'Consultation' " +
//                "  WHEN 'Registration_Charge' THEN 'Registration' " +
//                "  WHEN 'Lab_Charge' THEN 'Laboratory' " +
//                "  WHEN 'Scan_Charge' THEN 'Scanning' " +
//                "  WHEN 'ECG_Charge' THEN 'ECG' " +
//                "  WHEN 'Physiotherapy_Charge' THEN 'Physiotherapy' " +
//                "  WHEN 'MedicalCertificate_Charge' THEN 'Medical Certificate' " +
//                "  WHEN 'Injection Procedure Charge' THEN 'Injection Procedure' " +
//                "  WHEN 'Xray_Charge' THEN 'X-ray' " +
//                "  WHEN 'Online_Consultation_Charge' THEN 'Online Consultation' " +
//                "  ELSE 'Client Lab Charge' " +
//                " END AS name, " +
//                " IFNULL(SUM(b.debit),0) AS sumamount " +
//                "FROM journal a " +
//                "JOIN journals_book b ON a.jid=b.jid " +
//                "JOIN headsofaccount c ON b.hid=c.id " +
//                "WHERE a.date1='2026-01-06'  " +
//                "AND ( " +
//                " narration IN ( " +
//                "  'Consultation_Charge','Registration_Charge','Lab_Charge','Scan_Charge', " +
//                "  'ECG_Charge','Physiotherapy_Charge','MedicalCertificate_Charge', " +
//                "  'Injection Procedure Charge','Xray_Charge','Online_Consultation_Charge' " +
//                " ) OR narration LIKE 'Client_Lab_Charge%' " +
//                ") " +
//                "AND b.hid IN (68," + stat + ",755,1534) " +
//                "GROUP BY name";
//
//            stmt = con.prepareStatement(sql);
//            stmt.setString(1, from);
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                LabBean lb = new LabBean();
//                lb.setNames(rs.getString("name"));
//                lb.setSumamount(rs.getFloat("sumamount"));
//                list.add(lb);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            close(rs);
//            close(stmt);
//            close(rs1);
//            close(stmt1);
//        }
//
//        return list;
//    }
	
	
	  public static ArrayList<LabBean> getDashboardReport(Connection con,String from ) 
	  {
	   // TODO Auto-generated method stub
	  LabBean lb;
	  ArrayList<LabBean> lablist = new ArrayList<LabBean>();
	  PreparedStatement stmt= null; 
	  PreparedStatement stmt1 = null; 
	  ResultSet rs = null; 
	  ResultSet rs1 =null;
	  //DateFormatDAO df = new DateFormatDAO();
	  DecimalFormat df = new DecimalFormat("0.00");
	  LabBean lb1 = null;	  
	  try {
	  String sqlbankdetails = "SELECT id FROM headsofaccount where parenthead='5' and is_parent=0";	  
	  stmt1 = con.prepareStatement(sqlbankdetails);
	  rs1 = stmt1.executeQuery();
	  int bankdetails=0;
	  String stat = "0";
	  while (rs1.next()) {
	   bankdetails = rs1.getInt("id");
	   stat = stat +","+ bankdetails;
	  }
	  
	  
	  
	  String sqlaccdetails ="select  'Consultation' as name ,ifnull(sum(debit),0) as sumamount from journal a,journals_book b,headsofaccount c where a.jid=b.jid "
	  + "and date1='2026-01-06' and narration ='Consultation_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) "
	  + "union "
	  + "select  'Registration' as name ,ifnull(sum(debit),0) as sumamount from journal a,journals_book b,headsofaccount c where a.jid=b.jid and date1='2026-01-06'"
	  + "and narration ='Registration_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) "
	  + "union select  'Laboratory' as name ,ifnull(sum(debit),0) as sumamount from journal a,journals_book b,headsofaccount c where a.jid=b.jid\r\n"
	  + "and date1='2026-01-06' and narration ='Lab_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) union select  'Scanning' as name ,ifnull(sum(debit),0) as sumamount "
	  + "from journal a,journals_book b,headsofaccount c where a.jid=b.jid and date1='2026-01-06' and narration ='Scan_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) union "
	  + "select  'ECG' as name ,ifnull(sum(debit),0) as sumamount from journal a,journals_book b,headsofaccount c where a.jid=b.jid and "
	  + "date1='2026-01-06' and narration ='ECG_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) union "
	  + "select  'Physiotherapy' as name ,ifnull(sum(debit),0) as sumamount from journal a,journals_book b,headsofaccount c where a.jid=b.jid "
	  + "and date1='2026-01-06' and narration ='Physiotherapy_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) union select 'Medical Certificate' as name ,ifnull(sum(debit),0) as sumamount "
	  + "from journal a,journals_book b,headsofaccount c where a.jid=b.jid and date1='2026-01-06' and narration ='MedicalCertificate_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) "
	  + "union select  'Injection Procedure' as name ,ifnull(sum(debit),0) as sumamount from journal a,journals_book b,headsofaccount c "
	  + "where a.jid=b.jid and date1='2026-01-06' and narration ='Injection Procedure Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) "
	  + "union select  'X-ray' as name ,ifnull(sum(debit),0) as sumamount from journal a,journals_book b,headsofaccount c where a.jid=b.jid "
	  + "and date1='2026-01-06' and narration ='Xray_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714) union select  'Online Consultation' as name ,ifnull(sum(debit),0) as sumamount "
	  + "from journal a,journals_book b,headsofaccount c where a.jid=b.jid and date1='2026-01-06' and narration ='Online_Consultation_Charge' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714)"
	  		+ "union\r\n"
	  		+ " select  'Client Lab Charge' as name ,ifnull(sum(debit),0) as sumamount\r\n"
	  		+ " from journal a,journals_book b,headsofaccount c where a.jid=b.jid and date1='2026-01-06' and\r\n"
	  		+ " narration like'Client_Lab_Charge%' and b.hid=c.id and hid in(68,0,70,755,759,1257,1534,1624,1714)";
	  
	  stmt = con.prepareStatement(sqlaccdetails); 
	  rs = stmt.executeQuery();
	  
	  
	  while (rs.next()) {
	  
	  lb1 = new LabBean();
	  
	  lb1.setNames(rs.getString("name"));
	  lb1.setSumamount(rs.getFloat("sumamount"));
	  
	  lablist.add(lb1); } }
	  
	  
	  catch(Exception e) {
		  
		  e.printStackTrace();
		  }
	  
	  finally { 
		  DbUtils.closeQuietly(rs);
		  DbUtils.closeQuietly(stmt);
	      DbUtils.closeQuietly(rs1);
	      DbUtils.closeQuietly(stmt1); 
	  }
	  
	  return lablist; 
	  }

    private static void close(AutoCloseable c) {
        try { if (c != null) c.close(); } catch (Exception ignored) {}
    }
}
