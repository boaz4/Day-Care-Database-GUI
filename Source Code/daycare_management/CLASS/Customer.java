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

/**
 *
 * @author boazd_000
 */
public class Customer {
    
    Connection connection;
    
    private Integer id;
    private String first_name;
    private String last_name;
    private String tel;
    private String email;

    
    public Customer(){}
    
    
    public Customer(Integer ID, String FNAME, String LNAME, String TEL, String EMAIL)
    {
        this.id = ID;
        this.first_name = FNAME;
        this.last_name = LNAME;
        this.tel = TEL;
        this.email = EMAIL;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    // get the customers list
    public ArrayList<Customer> customersList(){
        
        ArrayList<Customer> customer_list = new ArrayList<>();
        connection = DB_INFO.getConnection();
        Statement st;
        ResultSet rs;
        PreparedStatement ps;

               String query = "SELECT `id`, `first_name`, `last_name`, `tel`, `email` FROM `customer`";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           
            Customer customer;
            // Integer ID, String NAME, Integer CATEGORY_ID, String PRICE, byte[] PICTURE, Integer QUANTITY, String DESCRIPTION
            while(rs.next()){
                customer = new Customer(rs.getInt("id"), 
                                 rs.getString("first_name"),
                                 rs.getString("last_name"),
                                 rs.getString("tel"),
                                 rs.getString("email")
                                 );
                
                customer_list.add(customer);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer_list;
        
    }
    
    
    
    // insert a new customer
     public static void insertCustomer(Customer customer)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO `customer`(`first_name`, `last_name`, `tel`, `email`) VALUES (?,?,?,?)");

            ps.setString(1, customer.getFirst_name());
            ps.setString(2, customer.getLast_name());
            ps.setString(3, customer.getTel());
            ps.setString(4, customer.getEmail());
            

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New Customer Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     // update customer data
    public static void updateCustomer(Customer customer)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("UPDATE `customer` SET `first_name`=?,`last_name`=?,`tel`=?,`email`=? WHERE `id`=?");

            ps.setString(1, customer.getFirst_name());
            ps.setString(2, customer.getLast_name());
            ps.setString(3, customer.getTel());
            ps.setString(4, customer.getEmail());
            ps.setInt(5, customer.getId());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Customer Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // delete customer by id
    public static void deleteCustomer(Integer customerId)
    {
        
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM `customer` WHERE `id` = ?");

            ps.setInt(1, customerId);

            // show a confirmation message before deleting the Customer
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete This Customer","Delete Customer", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
                if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Customer Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    
    
    
}
