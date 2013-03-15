/**
 * Package that contains classes related to user
 */
package user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import db.Db;

/**
 * @author	Srivathsa PV
 * @email	pv.srivathsa@gmail.com
 * @date	12/01/2013
 * 
 * Departments are classifications under a company. Each department has a certain number of
 * employees working who are headed by a CEO.
 */

public class Department {

	/**
	 * Name of the table to which the class is mapped to
	 * 
	 * @var String
	 */
	private static String t_name = "DEPARTMENT";
	
	/**
	 * Unique identifier for the department
	 * 
	 * @var Integer
	 */
	private int deptid = 0;
	
	/**
	 * Name of the department
	 * 
	 * @var String
	 */
	private String deptname;
	
	/**
	 * Shortname of the department
	 * 
	 * @var String
	 */
	private String shortname;
	
	/**
	 * An optional description about the department
	 * 
	 * @var String
	 */
	private String description;
	
	/**
	 * User id of the CEO of the department
	 * 
	 * @var String
	 */
	private String userid;
	
	/**
	 * Constructs an empty object
	 */
	public Department() {}
	
	/**
	 * Fetches necessary data and initializes the variables
	 * 
	 * @param Integer
	 * Id of the department to be fetched (Optional)
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Department(int deptid) throws ClassNotFoundException, SQLException {
		Db db = new Db();
		db.connect();
		
		ResultSet rs = db.executeQuery("SELECT * FROM " + t_name + " WHERE DEPTID = " + deptid);
		rs.next();
		
		this.deptid = deptid;
		this.deptname = rs.getString("DEPTNAME");
		this.shortname = rs.getString("SHORTNAME");
		this.description = rs.getString("DESCRIPTION");
		this.userid = rs.getString("USERID");
		
		db.disconnect();
	}
	
	/**
	 * Gets the id of the department
	 * 
	 * @return Integer
	 */
	public int getDeptid() {
		return this.deptid;
	}
	
	/**
	 * Gets the name of the department
	 * 
	 * @return String
	 */
	public String getDeptname() {
		return this.deptname;
	}
	
	/**
	 * Sets the name of the department
	 * 
	 * @param String
	 */
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	/**
	 * Gets the short name of the department
	 * 
	 * @return String
	 */
	public String getShortname() {
		return this.shortname;
	}
	
	/**
	 * Sets the short name of the department
	 * 
	 * @param String
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	
	/**
	 * Gets the description of the department
	 * 
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Sets the description of the department
	 * 
	 * @param String
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the user id of the CEO of the department
	 * 
	 * @return String
	 */
	public String getUserid() {
		return this.userid;
	}
	
	/**
	 * Sets the user id of the CEO of the department
	 * 
	 * @param String
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	/**
	 * Saves the local values to the database
	 * 
	 * @return Boolean - Returns true on success
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean save() throws ClassNotFoundException, SQLException {
		Db db = new Db();
		db.connect();
		
		
		int n = 0;
		if(this.deptid == 0) {
			String values[] = {this.deptname,this.shortname,this.description,this.userid};
			this.deptid = Integer.parseInt(db.insert(t_name,values,true,true).toString());
			n=1;
		}
		else {
			HashMap<String,String> map = new HashMap<String,String>();
			
			map.put("DEPTNAME",this.deptname);
			map.put("SHORTNAME",this.shortname);
			map.put("DESCRIPTION",this.description);
			map.put("USERID", this.userid);
			
			n=db.update(t_name, map, "DEPTID", Integer.toString(this.deptid));
		}
		
		db.disconnect();
		
		if(n > 0) return true;
		else return false;
	}
	
	/**
	 * Returns a list of department objects
	 * 
	 * @param String - Column name as the filter parameter
	 * @param String - Value
	 * 
	 * @return Array[user.Department]
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static Department[] list(String column,String value) throws ClassNotFoundException, SQLException {
		Db db = new Db();
		db.connect();
		
		String cnt_query = "";
		String query = "";
		if(column.equals("")){
			query = "SELECT * FROM " + t_name;
			cnt_query = "SELECT COUNT(*) FROM " + t_name;
			
		}
		else {
			query = "SELECT * FROM " + t_name + " WHERE " + column + " = '" + value + "'";
			cnt_query = "SELECT COUNT(*) FROM " + t_name + " WHERE " + column + " = '" + value + "'";
		}
		
		ResultSet rs = db.executeQuery(cnt_query);
		rs.next();
		
		Department[] list = new Department[rs.getInt(1)];
		
		rs = db.executeQuery(query);
		
		int i=0;
		while(rs.next()) {
			list[i++] = new Department(rs.getInt("DEPTID"));
		}
		
		return list;
	}
	
	/**
	 * Returns a count based on the values
	 * 
	 * @param String - Column name as filter parameter
	 * @param String - Value
	 * 
	 * @return Integer - The count
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int count(String column,String value) throws ClassNotFoundException, SQLException {
		Db db = new Db();
		db.connect();
		
		ResultSet rs = db.executeQuery("SELECT COUNT(*) FROM " + t_name + " WHERE " + column + " = '" + value);
		rs.next();
		
		return rs.getInt(1);
	}	
	
	/**
	 * Deletes the department
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void delete() throws ClassNotFoundException, SQLException {
		Db db = new Db();
		db.connect();
		
		db.delete("VOUCHERTYPE_DEPT","DEPTID",Integer.toString(this.deptid));
		db.delete("DEPARTMENT","DEPTID",Integer.toString(this.deptid));
		
		db.disconnect();
		
	}
}
