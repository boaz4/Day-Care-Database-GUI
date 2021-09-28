package CLASS;

import java.sql.Connection;
import java.sql.Date;
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
public class THE_ORDER {
    
    Connection connection = DB_INFO.getConnection();
    
    
    private Integer id;
    private Date orderDate;
    private Integer customerId;
    
    public THE_ORDER(){}
    
    public THE_ORDER(Integer ID, Date ORDER_DATE)
    {
        this.id = ID;
        this.orderDate = ORDER_DATE;
    }
    
    public THE_ORDER(Integer ID, Date ORDER_DATE, Integer CUSTOMER_ID)
    {
        this.id = ID;
        this.orderDate = ORDER_DATE;
        this.customerId = CUSTOMER_ID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer Cid) {
        this.customerId = Cid;
    }
    
    
    // get the orders list
    public ArrayList<THE_ORDER> ordersList(){
        
        ArrayList<THE_ORDER> order_list = new ArrayList<>();
        ResultSet rs;
        PreparedStatement ps;

               String query = "SELECT * from balems1db.Medicine";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           
            THE_ORDER ord;
            // Integer ID, String NAME, Integer CATEGORY_ID, String PRICE, byte[] PICTURE, Integer QUANTITY, String DESCRIPTION
            while(rs.next()){
                ord = new THE_ORDER(rs.getInt("id"),
                                    rs.getDate("order_date"),
                                    rs.getInt("customer_id")
                                 );
                
                order_list.add(ord);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(THE_ORDER.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order_list;
        
    }
    
    
    // get the customer orders count by his id
    public String getCustomerOrdersCount(Integer customerId)
    {

            String query = "SELECT  customer.id,COUNT(*) AS NUMBER_OF_ORDERS FROM  customer, order_tbl WHERE customer.id = ? AND order_tbl.customer_id = customer.id GROUP BY customer.id ORDER BY customer.id";
            ResultSet rs;
            PreparedStatement ps;
            String ordersCount = "";
        
        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                ordersCount = String.valueOf(rs.getInt("NUMBER_OF_ORDERS"));
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(THE_ORDER.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ordersCount;
    }
    
    
    // get the order total amount by id
    public static String getOrderTotalAmount(Integer orderId)
    {
           String query = "select SUM(cast(total AS DECIMAL(10,2))) AS Total_Amount from order_detail where order_id = ?";
            Connection connection = DB_INFO.getConnection();
            ResultSet rs;
            PreparedStatement ps;
            String total = "";
        
        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                total = String.valueOf(rs.getInt("Total_Amount"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(THE_ORDER.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return total;
    }
    
    
    // get all the customer's orders total amount
    public String getCustomerOrdersTotalAmount(Integer customerId)
    {
           String query = "SELECT O.customer_id, SUM(cast(total AS DECIMAL(10,2))) AS Total_Amount FROM order_tbl O INNER JOIN order_detail OD ON O.id = OD.order_id where O.customer_id = ? GROUP BY O.customer_id";
            ResultSet rs;
            PreparedStatement ps;
            String total = "";
        
        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                total = String.valueOf(rs.getInt("Total_Amount"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(THE_ORDER.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return total;
    }
    
    
    
    // get customer last order date by id
    public String getCustomerLastOrderDate(Integer customerId)
    {
        String query = "select order_date from order_tbl where customer_id = ? order by order_date desc LIMIT 1";
            ResultSet rs;
            PreparedStatement ps;
            String lastOrderDate = "";
        
        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                lastOrderDate = String.valueOf(rs.getDate("order_date").toString());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(THE_ORDER.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lastOrderDate;
    }
    
    
    // get the max order id
    public Integer getMaxOrderId()
    {
        String query = "SELECT MAX(id) from order_tbl";
            ResultSet rs;
            Statement st;
            Integer maxOrderId = 0;
            
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            
            if(rs.next())
            {
                maxOrderId = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(THE_ORDER.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return maxOrderId;

    }
    
    
    // insert a new order
     public static void insertOrder(Integer orderId, String orderDate, Integer customerId)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO order_tbl (id,order_date,customer_id) VALUES (?, ?, ?)");

            ps.setInt(1, orderId);
            ps.setString(2, orderDate);
            ps.setInt(3, customerId);
            

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New Order Added");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(THE_ORDER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
     
     // insert order details
     public static void insertOrderDetails(Integer productId, Integer orderId, Integer qt, String price, String totalAmount)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement InsertPs;
        PreparedStatement UpdatePs;
        
        try {
            InsertPs = con.prepareStatement("INSERT INTO order_detail (product_id, order_id, quantity, price, total) VALUES (?, ?, ?, ?, ?)");

            InsertPs.setInt(1, productId);
            InsertPs.setInt(2, orderId);
            InsertPs.setInt(3, qt);
            InsertPs.setString(4, price);
            InsertPs.setString(5, totalAmount);

            if(InsertPs.executeUpdate() != 0){
                //JOptionPane.showMessageDialog(null, "Order Details Added");
                
                UpdatePs = con.prepareStatement("update PRODUCT set quantity = quantity - ? where id = ?");
                UpdatePs.setInt(1, qt);
                UpdatePs.setInt(2, productId);
                UpdatePs.executeUpdate();
                
            }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong","Add Details",2);
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(THE_ORDER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
