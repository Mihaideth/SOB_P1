/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.urv.deim.sob.command;

import cat.urv.deim.sob.Relacio;
import cat.urv.deim.sob.User;
import cat.urv.deim.sob.Tfg;
import cat.urv.deim.sob.Tfgs;
import cat.urv.deim.sob.command.Command;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jordi Borrell
 */
public class PendentCommand implements Command {

    @Override
     public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Tfg tfg = new Tfg();

            tfg.setTitol(request.getParameter("titol"));
            tfg.setDescripcio(request.getParameter("descripcio"));
            LinkedList ll = new LinkedList(Arrays.asList(request.getParameter("professors")));
            tfg.setProfessors(ll);
            tfg.setEstat("Pendent");
            tfg.setEstudiants(request.getParameter("estudiants"));
            tfg.setEstudis(request.getParameter("estudis"));
            tfg.setRecursos(request.getParameter("recursos"));
            SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
            String parameter = request.getParameter("dataDefensa");
            java.util.Date date = in.parse(parameter);
            
            java.sql.Date data;
            data = new java.sql.Date(date.getTime());
            
            tfg.setDataDefensa(data);
            
            HttpSession sessionUser = request.getSession();
                request.setAttribute("user", sessionUser.getAttribute("user"));
            try {
                //dataCreacio i dataModificacio
                
                insertTfg(tfg, sessionUser, ll);
            } catch (SQLException | ClassNotFoundException | NamingException ex) {
                Logger.getLogger(PendentCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            if (true) {         //comprovar si titol es únic, etc
                
                
                
                //Carregar la taula de tfgs de nou
                String sqlString = "SELECT * FROM sob_grup_05.TFG";
                LinkedList<Tfg> tfgAux = null;
                try {
                    tfgAux = selectSQL(sqlString);
                } catch (SQLException | ClassNotFoundException | NamingException | NullPointerException e) {
                    System.out.println(e);
                }
                
                String sqlStringProfe;
                sqlStringProfe = "SELECT * FROM sob_grup_05.DIRIGEIX WHERE PROFESSOR_FK = '"+sessionUser.getAttribute("user")+"'";
                LinkedList<Relacio> tfgprofe = null;
                try {
                    tfgprofe = selectSQLProfe(sqlStringProfe);
                } catch (SQLException | ClassNotFoundException | NamingException | NullPointerException e) {
                    System.out.println(e);
                }
                
                LinkedList<Tfg> tfgresultat = new LinkedList();
                for(Relacio tfgrel: tfgprofe){
                    String nom;
                    nom=tfgrel.getTfg();
                    for(Tfg tfgaux: tfgAux){
                        String nom2;
                        nom2=tfgaux.getTitol();
                        if((nom!=null)&&(nom2!=null)&&(nom.equals(nom2))){
                            tfgresultat.add(tfgaux);
                        }
                    }
                }

                Tfgs tfgs = new Tfgs(tfgresultat);

                request.setAttribute("tfgActius", tfgs);

                RequestDispatcher rd1 = request.getRequestDispatcher("actius2.jsp");
                rd1.forward(request, response);
            } else {
                out.println("El títol ja existeix!");
                out.println("<a href=\"registreTfg.jsp\">Prova de nou...</a>");
            }
        } catch (ParseException ex) {
            Logger.getLogger(PendentCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        return tfg;
    }
    
    private LinkedList<Relacio> selectSQLProfe(String sqlStringProfe) throws SQLException, ClassNotFoundException, NamingException {
        LinkedList<Relacio> tfgprofe = new LinkedList();

        ResultSet resultados;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sob_grup_05", "user", "pwd");
        Statement stmt = con.createStatement();

        resultados = stmt.executeQuery(sqlStringProfe);
        int i = 0;
        while (resultados.next()) {
            Relacio relacio = new Relacio();
            relacio.setProfessor(resultados.getString("PROFESSOR_FK"));
            relacio.setTfg(resultados.getString("TFG_FK"));

            tfgprofe.add(relacio);
        }

        return tfgprofe;
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

    private void insertTfg(Tfg tfg, HttpSession sessionUser, LinkedList ll) throws SQLException, ClassNotFoundException, NamingException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        
        
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sob_grup_05", "user", "pwd");
        try (Statement stmt = con.createStatement()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate data = LocalDate.now();
            stmt.execute("INSERT INTO sob_grup_05.TFG(TITLE, DESCR, STATE, ESTUDIANTS, ESTUDIS, RECURSOS, DATADEFENSA, QUALIFICACIO, DATACREACIO, DATAMODIFICACIO) VALUES ('"+tfg.getTitol()+"', '"+tfg.getDescripcio()+"', '"+tfg.getEstat()+"', '"+tfg.getEstudiants()+"', '"+tfg.getEstudis()+"', '"+tfg.getRecursos()+"', '"+tfg.getDataDefensa()+"', '', '"+data+"' , '"+data+"')");
            stmt.execute("INSERT INTO sob_grup_05.DIRIGEIX(PROFESSOR_FK, TFG_FK) VALUES ('"+sessionUser.getAttribute("user")+"', '"+tfg.getTitol()+"')");
            for (Iterator it = ll.iterator(); it.hasNext();) {
                String prof = (String) it.next();
                if(!prof.equals("")){
                    stmt.execute("INSERT INTO sob_grup_05.DIRIGEIX(PROFESSOR_FK, TFG_FK) VALUES ('"+prof+"', '"+tfg.getTitol()+"')");
                }
            }
            
        }
    }

}
