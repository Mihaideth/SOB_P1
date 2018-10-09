/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.urv.deim.sob.command;

import cat.urv.deim.sob.User;
import cat.urv.deim.sob.Tfg;
import cat.urv.deim.sob.Tfgs;
import cat.urv.deim.sob.command.Command;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jordi Borrell
 */
public class TotsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sqlString = "SELECT * FROM sob_grup_05.TFG";
        LinkedList<Tfg> tfg = null;
        try {
            tfg = selectSQL(sqlString);
        } catch (SQLException | ClassNotFoundException | NamingException | NullPointerException e) {
            System.out.println(e);
        }

        Tfgs tfgs = new Tfgs(tfg);

        request.setAttribute("tfgActius", tfgs);

        ServletContext context = request.getSession().getServletContext();
        context.getRequestDispatcher("/actius.jsp").forward(request, response);
    }

    private LinkedList<Tfg> selectSQL(String sqlString) throws SQLException, ClassNotFoundException, NamingException {
        LinkedList<Tfg> tfg = new LinkedList();

        ResultSet resultados;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sob_grup_05", "user", "pwd");
        Statement stmt = con.createStatement();

        resultados = stmt.executeQuery(sqlString);
        int i = 0;
        while (resultados.next()) {
            Tfg treball = new Tfg();
            treball.setTitol(resultados.getString("TITLE"));
            treball.setDescripcio(resultados.getString("DESCR"));
            
            String sqlStringProfes;
            sqlStringProfes = "SELECT * FROM sob_grup_05.DIRIGEIX WHERE TFG_FK = '" + resultados.getString("TITLE") + "'";
            LinkedList<String> tfgprofes = null;
            try {
                tfgprofes = selectSQLProfes(sqlStringProfes);
            } catch (SQLException | ClassNotFoundException | NamingException | NullPointerException e) {
                System.out.println(e);
            }

            treball.setProfessors(tfgprofes);

            treball.setEstat(resultados.getString("STATE"));
            treball.setEstudiants(resultados.getString("ESTUDIANTS"));
            treball.setEstudis(resultados.getString("ESTUDIS"));
            treball.setRecursos(resultados.getString("RECURSOS"));
            treball.setDataDefensa(resultados.getDate("DATADEFENSA"));
            treball.setQualificacio(resultados.getString("QUALIFICACIO"));
            treball.setDataCreacio(resultados.getDate("DATACREACIO"));
            treball.setDataModificacio(resultados.getDate("DATAMODIFICACIO"));
            tfg.add(treball);
        }
        Collections.sort(tfg, new Comparator<Tfg>() {
            @Override
            public int compare(Tfg o1, Tfg o2) {
                return o1.getTitol().compareTo(o2.getTitol());
            }
        });

        return tfg;
    }

    private LinkedList<String> selectSQLProfes(String sqlStringProfes) throws SQLException, ClassNotFoundException, NamingException {
        LinkedList<String> profes = new LinkedList();

        ResultSet resultados;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sob_grup_05", "user", "pwd");
        Statement stmt = con.createStatement();

        resultados = stmt.executeQuery(sqlStringProfes);
        int i = 0;
        while (resultados.next()) {
            profes.add(resultados.getString("PROFESSOR_FK"));
        }

        return profes;
    }

}
