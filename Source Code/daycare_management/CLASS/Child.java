/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author boazd_000
 */
package CLASS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import CLASS.DB_INFO;

/**
 *
 * @author boazd_000
 */
public class Child {
    
    Connection connection;
    
    private String c_num;
    private String fname;
    private String lname;
    private String gender;
    private String dateofbirth;

    
    public Child(){}
    
    
    public Child(String c_num, String fname, String lname, String gender, String dateofbirth)
    {
        this.c_num = c_num;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
    }
    
    
    public String getcnum() {
        return c_num;
    }

    public void setcnum(String id) {
        this.c_num = id;
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

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public String getdateofbirth() {
        return dateofbirth;
    }

    public void setdateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }
    
    // get the customers list
    public ArrayList<Child> childList(){
        
        ArrayList<Child> child_list = new ArrayList<>();
        connection = DB_INFO.getConnection();
        
        Statement st;
        ResultSet rs;
        PreparedStatement ps;

               String query = "SELECT * FROM balems1db.Child";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           
            Child child;
            // query column
            while(rs.next()){
                child = new Child(rs.getString("c_num"), 
                                 rs.getString("fname"),
                                 rs.getString("lname"),
                                 rs.getString("gender"),
                                 rs.getString("dateofbirth")
                                 );
                
                child_list.add(child);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
        return child_list;
        
    }
    
    
    
    // insert a new customer
     public static void insertChild(Child child)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO balems1db.Child(c_num, fname, lname, gender, dateofbirth) VALUES (?,?,?,?,?)");

            ps.setString(1, child.getcnum());
            ps.setString(2, child.getfname());
            ps.setString(3, child.getlname());
            ps.setString(4, child.getgender());
            ps.setString(5, child.getdateofbirth());
            

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New Child Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     // update customer data
    public static void updateChild(Child child)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("UPDATE balems1db.Child SET fname=?,lname=?,gender=?,dateofbirth=? WHERE c_num=?");

            ps.setString(1, child.getfname());
            ps.setString(2, child.getlname());
            ps.setString(3, child.getgender());
            ps.setString(4, child.getdateofbirth());
            ps.setString(5, child.getcnum());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Child Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // delete customer by id
    public static void deleteChild(String cnum)
    {
        
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM balems1db.Child WHERE c_num = ?");

            ps.setString(1, cnum);

            // show a confirmation message before deleting the Customer
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete This Child","Delete Child", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
                if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Child Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    
    
    
}
