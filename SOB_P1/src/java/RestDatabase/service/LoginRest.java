/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestDatabase.service;

import cat.urv.deim.sob.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.naming.NamingException;

/**
 *
 * @author Mihai Serban Copil
 */
public class LoginRest {

    public User professorloginJSON(HashMap<String, Object> login) {
        if (login.get("user") == null || login.get("pwd") == null) {
            return null;
        }
        String user = (String) login.get("user");
        String pwd = (String) login.get("pwd");
        try {

            String sqlStringSelectProfe = null;
            sqlStringSelectProfe = "SELECT * FROM sob_grup_05.PROFESSORS WHERE USERNAME= '" + user + "' AND PASSWORD = '" + pwd + "'";

            User professor = new User();

            try {
                professor = selectSQLSelectProfe(sqlStringSelectProfe);
            } catch (SQLException | ClassNotFoundException | NamingException | NullPointerException e) {
                System.out.println(e);
            }

            return professor;
        } catch (Exception e) {

        }
        return null;
    }

    private static LoginRest instance;

    public static LoginRest getInstance() {
        if (instance == null) {
            instance = new LoginRest();
        }
        return instance;
    }

    private User selectSQLSelectProfe(String sqlStringProfes) throws SQLException, ClassNotFoundException, NamingException {
        User professor = new User();

        ResultSet resultados;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sob_grup_05", "user", "pwd");
        Statement stmt = con.createStatement();

        resultados = stmt.executeQuery(sqlStringProfes);
        int i = 0;
        while (resultados.next()) {
            professor.setUser(resultados.getString("USERNAME"));
        }

        return professor;
    }

}
