/**
 * Package that contains classes related to voucher type
 **/
 package  voucher.vouchertype;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import db.Db;

/**
 * @author	Saranya C
 * @email	saranyachidambaram11@gmail.com
 * @date	13/01/2013
 * 
 * Policy details of the voucher assigned by the company
 */

public class Policy {
	/**
	 * Name of the table to which the class is mapped to
	 * 
	 * @var String
	 */
	private static String t_name = "VOUCHERTYPE_POLICY";
	/**
	 * Unique identity of the vouchertype_Policy.
	 * 
	 * @var Integer
	 */
	private int id;
	
	/**
	 * Unique identity  of the policy.
	 * 
	 * @var Integer
	 */
	private int policyid;
	/**
	 * Unique identity  of the voucher type.
	 * 
	 * @var Integer
	 */
	
	private int vtypeid;
	
	
	/**
	 * Creates an empty object
	 */
	public Policy(){}
	
	/**
	 * Fetches necessary data and initializes the variables
	 * 
	 * @param Integer
	 * Id of the policy to be fetched (Optional)
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Policy(int id)throws ClassNotFoundException,SQLException{
		Db db = new Db();
		db.connect();
		
		ResultSet rs = db.executeQuery("SELECT * FROM " + t_name + " WHERE id = " + id);
		rs.next();
		
		this.id = id;
		this.policyid = rs.getInt("POLICYID");
		this.vtypeid=rs.getInt("VTYPEID");
		
		db.disconnect();
	}
	
	/**
	 * Gets the id of the vouchertype_policy
	 * 
	 * @return Integer
	 */
	public int getId() {
		return this.id;
	}
	
	
	/**
	 * Gets the id of the voucher type
	 * 
	 * @return Integer
	 */
	public int getVtypeid() {
		return this.vtypeid;
	}
	
	/**
	 * Sets the id of the voucher type
	 * 
	 * @param Integer
	 */
	public void setVtypeid(int vtid) {
		this.vtypeid = vtid;
	}
	
	/**
	 * Gets the id of the policy
	 * 
	 * @return Integer
	 */
	public int getPolicyid() {
		return this.policyid;
	}
	
	/**
	 * Sets the id of the policy
	 * 
	 * @param Integer
	 */
	public void setPolicyid(int pid) {
		this.policyid = pid;
	}
	
	/**
	 * Returns a list of policies
	 * 
	 * @param String - Column name as the filter parameter
	 * @param String - Value
	 * 
	 * @return Array[voucher.Status]
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static Policy[] list(String column,String value) throws ClassNotFoundException, SQLException {
		Db db = new Db();
		db.connect();
		
		ResultSet rs = db.executeQuery("SELECT COUNT(*) FROM"+ t_name +" WHERE " + column + " = '" + value);
		rs.next();
		
		Policy[] list = new Policy[rs.getInt(1)];
		
		rs = db.executeQuery("SELECT * FROM"+ t_name +"WHERE " + column + " = '" + value);
		
		int i=0;
		while(rs.next()) {
			list[i++] = new Policy(rs.getInt("ID"));
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
		
		
		int n =0;
		if(this.id == 0) {
			String values[] = {Integer.toString(this.vtypeid),Integer.toString(this.policyid)};
			this.id = Integer.parseInt(db.insert(t_name, values, true,true).toString());
			n=1;
		}
		else {
			HashMap<String,String> map = new HashMap<String,String>();
			
			map.put("POLICYID",Integer.toString(this.policyid));
			map.put("VTYPEID",Integer.toString(this.vtypeid));
			
			n = db.update(t_name,map,"ID",Integer.toString(this.id));
		}
		
		db.disconnect();
		
		if(n > 0) return true;
		else return false;
	}

}
