package CLASS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author boazd_000
 */
public class Category {
    
    
    Connection connection;
    
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCategoryId(String name) {
        return this.id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    public Category(){}
    
    public Category(Integer ID, String NAME)
    {
        this.id = ID;
        this.name = NAME;
    }
    
    
    // get all the categories
    public ArrayList<Category> categoriesList(){
        
        ArrayList<Category> category_list = new ArrayList<>();
        connection = DB_INFO.getConnection();
        ResultSet rs;
        PreparedStatement ps;
        
               String query = "SELECT * FROM balems1db.Employee";
        
        try {

                ps = connection.prepareStatement(query);
                rs = ps.executeQuery();

                Category category;

                while(rs.next()){
                    category = new Category(rs.getInt("id"), 
                                     rs.getString("name")
                                     );

                    category_list.add(category);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category_list;
        
    }
     
    
    // populate a HashMap with categories name and id
    public HashMap<String, Integer> populateCombo(){

      HashMap<String, Integer> map = new HashMap<>();

      connection = DB_INFO.getConnection();

      Statement st;

      ResultSet rs;

       try {

           st = connection.createStatement();

           rs = st.executeQuery("SELECT * FROM balems1db.Employee");

           Category category;

           

           while(rs.next()){

               category = new Category(rs.getInt(1), rs.getString(2));

               map.put(category.getName(), category.getId());

           }

       } catch (SQLException ex) {

           Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);

       }
       
       return map;

   }
    
    
    // insert a new category function
    public static void insertCategory(Category category)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO `category`(`name`) VALUES (?)");

            ps.setString(1, category.getName());


            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "New Category Inserted");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // update a category function
    public static void updateCategory(Category category)
    {
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("UPDATE `category` SET `name`=? WHERE `id` = ?");

            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Category Updated");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // delete category function
    public static void deleteCategory(Integer categoryId)
    {
        
        Connection con = DB_INFO.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM `category` WHERE `id` = ?");

            ps.setInt(1, categoryId);

            // show a confirmation message before deleting the Category
            int YesOrNo = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete This Category","Delete Category", JOptionPane.YES_NO_OPTION);
            if(YesOrNo == 0){
                
               if(ps.executeUpdate() != 0){
                  JOptionPane.showMessageDialog(null, "Category Deleted");
                }
               
                else{
                    JOptionPane.showMessageDialog(null, "Something Wrong");
                } 
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
  
}
