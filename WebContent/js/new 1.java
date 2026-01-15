package com.gb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.gb.common.DateFormatDAO;
import com.gb.jdbc.StudentRowMapper;
import com.gb.model.Circular;
import com.gb.model.ClassD;
import com.gb.model.ClassTeacher;
import com.gb.model.Division;
import com.gb.model.Login;
import com.gb.model.Message;
import com.gb.model.Student;
import com.gb.model.StudentSession;
import com.gb.model.Subjects;
import com.gb.model.TimeTableSchedule;
import com.gb.model.Timetable_Master;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class StudentDaoImpl implements StudentDao {
	@Autowired
    DataSource dataSource;
	 @Autowired
	  JdbcTemplate template ;
	
	 /*student details */
	@Override
	public List<Student> getStudentdetail(int id) {
		List<Student> studentDetail = new ArrayList();
		String sql = "SELECT * FROM add_school_students where sid='"+id+"'";
		studentDetail= template.query(sql, new ClassdRowMapper());
		return studentDetail;
	}
	
	/*circular*/
	public List<Circular> getCircular(int id) {
		List<Circular> circular = new ArrayList();
		Division div=new Division();
		String sql = "SELECT a.div_id,b.class_id from student_division a, student_class b where a.div_id=(SELECT division_id FROM add_school_students where sid='"+id+"') and a.class_id=b.class_id ";
		div= template.queryForObject(sql, new DivisionRowMapper());
		 sql = "SELECT circular_id, content, circular_input_time, userid, circular_heading, type, type_value, expiry_date FROM circular where type=1 and type_value='"+div.getClass_id()+"' and (date(expiry_date)+INTERVAL 1 DAY)>DATE(NOW())  union "
		 		+ "SELECT circular_id, content, circular_input_time, userid, circular_heading, type, type_value, expiry_date  FROM circular where type=2 and type_value='"+div.getDiv_id()+"' and (date(expiry_date)+INTERVAL 1 DAY)>DATE(NOW())  union "
		 		+ "SELECT circular_id, content, circular_input_time, userid, circular_heading, type, type_value, expiry_date  FROM circular where type=3 and (date(expiry_date)+INTERVAL 1 DAY)>DATE(NOW()) ;";
		 circular= template.query(sql, new circularRowMapper());
		 return circular;
	}
	
	/*message*/
	public List<Message> getMessage(int id) {
		List<Message> message = new ArrayList();
		String sql = "select * from messages where receiver_type=\"Student\" and receiver_id='"+id+"'";
		message= template.query(sql, new messageRowMapper());
		return message;
	}
	
	/*class teacher*/
	public ClassTeacher getclassTeacher(int id) {
		ClassTeacher classTeacher = new ClassTeacher();
		String sql = "SELECT a.div_id,b.class_id from student_division a, student_class b where a.div_id=(SELECT division_id FROM add_school_students where sid='"+id+"') and a.class_id=b.class_id ";
		Division div=new Division();
		div= template.queryForObject(sql, new DivisionRowMapper());
		String sql2="select concat(b.fname,\" \",b.mname,\" \",b.lname) as name,a.classteacher_id, a.div_id, a.time, a.user_id, a.teacher_id from class_teacher a,employee_registration b where a.teacher_id=b.empid and a.div_id='"+div.getDiv_id()+"'";
		classTeacher= template.queryForObject(sql2, new classteacherRowMapper());
		return classTeacher;
	}
	
	/*login authentication*/
	public StudentSession authenticateUser(Login login) {
		String s=login.getUser_name();
		StudentSession session=new StudentSession();
		char a=s.charAt(0);
		String username=s.substring(1);
		if (a=='S')
		{
			
		String sql = "select * from add_school_students where password= '"+login.getUser_password()+"' and sid= '"+username+"'";
		try{
		session=template.queryForObject(sql, new authenticateRowMapper());
		}
		catch (EmptyResultDataAccessException e) {
			session.setType("Z");
			return session;
		}
		}
		else if (a=='E')
		{
			
		String sql = "select a.empid,a.fname,a.mname,a.lname,b.usertype from employee_registration a,usertype b where a.usertype=b.id and password= '"+login.getUser_password()+"' and empid= '"+username+"'";
		try{
		session=template.queryForObject(sql, new authenticateRowMapper1());
		
		}
		catch (EmptyResultDataAccessException e) {
			session.setType("Z");
			return session;
		}
		}
		else{
			session.setType("Z");
		}
		return session;
		
				
	}		
		
	//submit Timetable
	public void submitTimetable(TimeTableSchedule schedule) {
		String day=null;
		DateFormatDAO dformat=new DateFormatDAO();
		List<Integer> slist=schedule.getDay();
		List<Integer> slist1=schedule.getSubjectid_1();
		
			String sql = "INSERT INTO timetable_schedule "
					+ "(schedule_id, periodname,starttime, endtime,day_title) VALUES (?,?,?,?,?)";

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			if(schedule.getRadoigroup().equals("day"))
			{
		    	day="Sunday";
		    	for(int j=0;j<slist1.size();j++)
		    	{
		    	jdbcTemplate.update(
						sql,
						new Object[] { 0,schedule.getSubjectid_1().get(j),schedule.getStart().get(j),schedule.getEnd().get(j),day
								});
		    	
		    }
		 
		    	day="Monday";
		    	for(int j=0;j<slist1.size();j++)
		    	{
		    	jdbcTemplate.update(
						sql,
						new Object[] { 0,schedule.getSubjectid_2().get(j),schedule.getStart().get(j),schedule.getEnd().get(j),day
								});
		    	}
		   
		   
		    	day="Tuesday";
		    	for(int j=0;j<slist1.size();j++)
		    	{
		    	jdbcTemplate.update(
						sql,
						new Object[] { 0,schedule.getSubjectid_3().get(j),schedule.getStart().get(j),schedule.getEnd().get(j),day
								});
		    	}
		    
		    
		    	day="Wednesday";
		    	for(int j=0;j<slist1.size();j++)
		    	{
		    	jdbcTemplate.update(
						sql,
						new Object[] { 0,schedule.getSubjectid_4().get(j),schedule.getStart().get(j),schedule.getEnd().get(j),day
								});
		    	}
		    
		    
		    	day="Thursday";
		    	for(int j=0;j<slist1.size();j++)
		    	{
		    	jdbcTemplate.update(
						sql,
						new Object[] { 0,schedule.getSubjectid_5().get(j),schedule.getStart().get(j),schedule.getEnd().get(j),day
								});
		    	}
		    
		   
		    	day="Friday";
		    	for(int j=0;j<slist1.size();j++)
		    	{
		    	jdbcTemplate.update(
						sql,
						new Object[] { 0,schedule.getSubjectid_6().get(j),schedule.getStart().get(j),schedule.getEnd().get(j),day
								});
		    	}
		    
		    
		    	day="Saturday";
		    	for(int j=0;j<slist1.size();j++)
		    	{
		    	jdbcTemplate.update(
						sql,
						new Object[] { 0,schedule.getSubjectid_7().get(j),schedule.getStart().get(j),schedule.getEnd().get(j),day
								});
		    	}
		    
	
		  
			}
		
		
	}		
			
	
	public List<Subjects> getTeacheravailability(int start_time,  int hour_id, String title,int division) {
		List<Subjects> sub=new ArrayList<>();
		String sql = "SELECT * FROM timetable_master where timetablemaster_id='"+start_time+"' ";
		Timetable_Master master=new Timetable_Master();
		master= template.queryForObject(sql, new TimetablemasterRowMapper());
		List<Timetable_Master> masterList=new ArrayList<>();
		String sql2="SELECT * FROM timetable_master where '"+master.getStart_date()+"' between start_date and end_date";
		masterList= template.query(sql2, new masterListRowMapper());
		
		for(int i=0;i<masterList.size();i++)
		{
			
		String sql3="SELECT a.teacher_id,a.subject_id,b.subject_Name,concat(c.fname,\" \",c.mname,\" \",c.lname) as teacher FROM teacher_allocation a,subject_table b,employee_registration c where division_id='"+division+"' and a.id not in (SELECT periodname FROM timetable_schedule where timetablemaster_id='"+masterList.get(i).getTimetablemaster_id()+"' and hour_id='"+hour_id+"' and day_title='"+title+"') and a.teacher_id=c.empid and a.subject_id=b.subject_id";
		sub= template.query(sql2, new subListRowMapper());
		}
		return sub;
	}
	
	private static final class ClassdRowMapper implements RowMapper<Student>{

		@Override
		public Student mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			DateFormatDAO format= new DateFormatDAO();
			Student student = new Student();
			student.setSid(resultSet.getInt("sid"));
			student.setSfname(resultSet.getString("sfname"));
			student.setSlastname(resultSet.getString("slastname"));
			student.setGender(resultSet.getString("gender"));
			student.setDob(format.displayDate(resultSet.getString("dob")));
			student.setGuardian_name(resultSet.getString("guardian_name"));
			student.setFather_name(resultSet.getString("father_name"));
			student.setFather_cont_no(resultSet.getString("father_cont_no"));
			student.setFather_work_no(resultSet.getString("father_work_no"));
			student.setMother_name(resultSet.getString("mother_name"));
			student.setMother_cont_no(resultSet.getString("mother_cont_no"));
			student.setMother_work_no(resultSet.getString("mother_work_no"));
			student.setRes_address(resultSet.getString("res_address"));
			student.setRes_post_code(resultSet.getString("res_post_code"));
			student.setRstate(resultSet.getString("rstate"));
			student.setRnationality(resultSet.getString("rnationality"));
			student.setPer_address(resultSet.getString("per_address"));
			student.setPer_post_code(resultSet.getString("per_post_code"));
			student.setPstate(resultSet.getString("pstate"));
			student.setPnationality(resultSet.getString("pnationality"));
			student.setBlood_grp(resultSet.getString("blood_grp"));
			student.setDoj(format.displayDate(resultSet.getString("doj")));
			student.setNotes(resultSet.getString("notes"));
            return student;
		}
	}
		private static final class circularRowMapper implements RowMapper<Circular>{

			@Override
			public Circular mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				DateFormatDAO format= new DateFormatDAO();
				Circular circular = new Circular();
				circular.setCircular_heading(resultSet.getString("circular_heading"));
				circular.setContent(resultSet.getString("content"));
				circular.setCircular_id(resultSet.getInt("circular_id"));
				circular.setCircular_input_time(format.displayDate(resultSet.getString("circular_input_time")));
				circular.setUserid(resultSet.getString("userid"));
				circular.setType(resultSet.getInt("type"));
				circular.setType_value(resultSet.getInt("type_value"));
				circular.setExpiry_date(resultSet.getString("expiry_date"));
				return circular; 
}
		}	
		
		
		private static final class messageRowMapper implements RowMapper<Message>{

			@Override
			public Message mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				DateFormatDAO format= new DateFormatDAO();
				Message message=new Message();
				message.setContent(resultSet.getString("content"));
				message.setInput_time(format.displayDate(resultSet.getString("input_time")));
				message.setMessage_heading(resultSet.getString("message_heading"));
				message.setMessage_id(resultSet.getInt("message_id"));
				message.setReceiver_id(resultSet.getString("receiver_id"));
				message.setReceiver_type(resultSet.getString("receiver_type"));
				message.setSender_id(resultSet.getString("sender_id"));
				return message; 
}
		}

		private static final class DivisionRowMapper implements RowMapper<Division>{

			@Override
			public Division mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				Division div=new Division();
				div.setClass_id(resultSet.getInt("class_id"));
				div.setDiv_id(resultSet.getInt("div_id"));
				
				return div; 
}
		}
		
		private static final class classteacherRowMapper implements RowMapper<ClassTeacher>{

			@Override
			public ClassTeacher mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				ClassTeacher class_teacher=new ClassTeacher();
				class_teacher.setTeachername(resultSet.getString("name"));
				class_teacher.setClassteacher_id(resultSet.getInt("classteacher_id"));
				class_teacher.setDiv_id(resultSet.getInt("div_id"));
				class_teacher.setTeacher_id(resultSet.getInt("teacher_id"));
				class_teacher.setTime(resultSet.getString("time"));
				class_teacher.setUser_id(resultSet.getString("user_id"));
				return class_teacher; 
}
		}
		
		
		private static final class authenticateRowMapper implements RowMapper<StudentSession>{

			@Override
			public StudentSession mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				StudentSession session=new StudentSession();
			
			    session.setDiv_id(resultSet.getInt("division_id"));
				session.setStudent_id(resultSet.getInt("sid"));
				session.setType("S");
				
				return session; 
}
		}
		private static final class authenticateRowMapper1 implements RowMapper<StudentSession>{

			@Override
			public StudentSession mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				
				StudentSession session=new StudentSession();
			
				
					session.setEmpid(resultSet.getInt("empid"));
				    session.setUsertype(resultSet.getString("usertype"));
					session.setType("E");
					
				return session; 
}
		}
		
		
		private static final class TimetablemasterRowMapper implements RowMapper<Timetable_Master>{

			@Override
			public Timetable_Master mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				
				Timetable_Master master=new Timetable_Master();
			     master.setTimetablemaster_id(resultSet.getInt("timetablemaster_id"));
			     master.setDiv_id(resultSet.getInt("div_id"));
			     master.setStart_date(resultSet.getString("start_date"));
			     master.setEnd_date(resultSet.getString("end_date"));
			     master.setUser_id(resultSet.getString("user_id"));
			     master.setTime(resultSet.getString("time"));
				 return master; 
}
		}
		
		
		private static final class masterListRowMapper implements RowMapper<Timetable_Master>{

			@Override
			public Timetable_Master mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				Timetable_Master masterList=new Timetable_Master();
				masterList.setTimetablemaster_id(resultSet.getInt("timetablemaster_id"));
				return masterList; 
}
		}
		
		
		private static final class subListRowMapper implements RowMapper<Subjects>{

			@Override
			public Subjects mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				Subjects sub=new Subjects();
				sub.setId(resultSet.getInt("id"));
				sub.setTeacher_name(resultSet.getString("teacher"));
				sub.setSubject_Name(resultSet.getString("subject_Name"));
				return sub; 
}
		}
		
		
}

