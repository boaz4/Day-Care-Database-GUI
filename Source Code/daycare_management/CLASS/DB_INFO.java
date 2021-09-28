package CLASS;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author boazd_000
 */
public class DB_INFO {
    
    private static String dbname = "balems1db";
    private static String username = "balems1";
    private static String password = "Cosc*wxmp";
    private static String SERVER = "jdbc:mysql://triton.towson.edu:3360/?serverTimezone=EST#balems1db";
    
       static Connection con=null;
    public static Connection getConnection()
    {
        if (con != null) return con;
        // get db, user, pass from settings file
        return getConnection(dbname, username, password);
    }

    private static Connection getConnection(String SERVER,String username,String password)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/?serverTimezone=EST#balems1db",
                        "balems1", "Cosc*wxmp");
            System.out.println("connected");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return con;        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  /*  
    public static Connection getConnection(){
        
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/"+dbname, username, password);
            System.out.println("connected");
        } catch (Exception ex) {
            System.out.println("not connected");
        }
        
        return con;
        
    }
    
   */ 
}
