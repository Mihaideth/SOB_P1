package cat.urv.deim.sob;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import Database.Db_Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {

    private String completname, user, pwd;
    private int id;
    
    
    public User(int id ,String user, String pwd, String completname){
        this.user = user;
        this.pwd = pwd;
        this.completname = completname;
    }
    
    
    public User(){
    }

    //----------------------------------//
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompletname() {
        return completname;
    }

    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }

    //----------------------------------//
    public void setCompletname(String completname) {
        this.completname = completname;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    //----------------------------------//
    public static boolean LoginUser(String user, String pwd) {
        boolean check = false;
        try {
            Db_Connection dbconn = new Db_Connection();
            Connection myconnection = dbconn.Connection();

            PreparedStatement ps1 = myconnection.prepareStatement("select * from sob_grup_05.PROFESSORS where username=? and password=?");

            ps1.setString(1, user);
            ps1.setString(2, pwd);
            ResultSet rs1 = ps1.executeQuery();
            check = rs1.next();

            myconnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check;
    }

    //----------------------------------//
    public void GetUser() {
        try {
            Db_Connection dbconn = new Db_Connection();
            Connection myconnection = dbconn.Connection();

            String sqlString = "SELECT * FROM sob_grup_05.PROFESSORS WHERE username = '" + user + "'";
            Statement myStatement = myconnection.createStatement();
            ResultSet rs = myStatement.executeQuery(sqlString);

            while (rs.next()) {

                completname = rs.getString("completname");
                user = rs.getString("username");
                pwd = rs.getString("password");
            }

            myStatement.close();
            myconnection.close();

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //----------------------------------//
}
