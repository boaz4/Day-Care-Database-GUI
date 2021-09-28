package CLASS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author boazd_00
 */


public class Parent {


    Connection connection;
    
    private String p_num ;
    private String fname;
    private String lname ;
    private Integer phone ;
    private String address ;

    public Parent(){}
    
    public Parent(String p_num, String fname, String lname, Integer phone, String address)
    {
    	this.p_num = p_num;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.address = address;
    }
    
    
    
    // insert a new product
    public static void insertParent(Parent parent)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO balems1db.Parent(p_num, fname, lname, phone, address) VALUES (?,?,?,?,?)");
            ps.setString(1, parent.getpnum());
            ps.setString(2, parent.getfname());
            ps.setString(3, parent.getlname());
            ps.setInt(4, parent.getphone());
            ps.setString(5, parent.getaddress());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New Parent Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Parent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     // update customer data
    public static void updateParent(Parent parent)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("UPDATE balems1db.Parent SET fname=?,lname=?,phone=?,address=? WHERE p_num=?");

            ps.setString(1, parent.getpnum());
            ps.setString(2, parent.getfname());
            ps.setString(3, parent.getlname());
            ps.setInt(4, parent.getphone());
            ps.setString(5, parent.getaddress());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Parent Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void deleteParent(String pnum)
    {
        
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM balems1db.Parent WHERE p_num = ?");

            ps.setString(1, pnum);

            // show a confirmation message before deleting the Customer
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete This Parent","Delete Parent", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
                if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Parent Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
   
     // get parents list using arraylist
    public ArrayList<Parent> parentList(){
        
        ArrayList<Parent> parent_list = new ArrayList<>();
        connection = DB_INFO.getConnection();
        ResultSet rs;
        PreparedStatement ps;
        
        String query = "SELECT* FROM balems1db.Parent";
        
        try {
        	ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           
            Parent parent;
            while(rs.next()){
                parent = new Parent(rs.getString("p_num"), 
                                 rs.getString("fname"), 
                                 rs.getString("lname"),
                                 rs.getInt("phone"),
                                 rs.getString("address")
                                 );
                
                parent_list.add(parent);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Parent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parent_list;
        
    }
     
     
     
     
  
     
    public String getpnum() {
        return p_num;
    }
    
    public void setpnum(String pnum) {
        this.p_num = pnum;
    }
    
    public String getfname() {
        return fname;
    }

    public void setfname(String fname) {
        this.fname = fname;
    }
    
    public String getlname() {
        return lname;
    }

    public void setlname(String lname) {
        this.lname = lname;
    }
    
    public Integer getphone() {
        return phone;
    }

    public void setphone(Integer phone) {
        this.phone = phone;
    }
    
    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }
   
    

    
}