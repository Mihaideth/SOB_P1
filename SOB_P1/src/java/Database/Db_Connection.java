package Database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Db_Connection 
{
    public Connection Connection()
    {
        try 
        {
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String sob = "jdbc:derby://localhost:1527/sob_grup_05;create=true";
            Connection myConnection = DriverManager.getConnection(sob, "user", "pwd");    
          
            return myConnection;
            
        } catch (ClassNotFoundException | SQLException ex) {Logger.getLogger(Db_Connection.class.getName()).log(Level.SEVERE, null, ex);}
        return null;
    }
}