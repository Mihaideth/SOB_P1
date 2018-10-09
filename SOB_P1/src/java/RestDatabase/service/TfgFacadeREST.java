package RestDatabase.service;

import cat.urv.deim.sob.Tfgs;
import cat.urv.deim.sob.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1/tfg")
public class TfgFacadeREST {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTfgs(@QueryParam("state") List<String> state, @HeaderParam("Content-Type") String contentType) throws Exception {
        String sqlString = "SELECT * FROM sob_grup_05.TFG";
        LinkedList<cat.urv.deim.sob.Tfg> tfg = null;
        try {
            tfg = selectSQL(sqlString);
        } catch (SQLException | ClassNotFoundException | NamingException | NullPointerException e) {
            System.out.println(e);
        }
        
        if (state != null && !state.isEmpty()) {
            LinkedList<cat.urv.deim.sob.Tfg> treballs = new LinkedList<>();
            for( String s : state) {
                for (cat.urv.deim.sob.Tfg t : tfg) {
                    if (t.getEstat().equalsIgnoreCase(s)) {
                        treballs.add(t);
                    }
                }
            }
            tfg = treballs;
        }

        Tfgs tfgs = new Tfgs(tfg);

        return Jackson.getInstance().asResource(tfgs, contentType);
    }

    private LinkedList<cat.urv.deim.sob.Tfg> selectSQL(String sqlString) throws SQLException, ClassNotFoundException, NamingException {
        LinkedList<cat.urv.deim.sob.Tfg> tfg = new LinkedList();

        ResultSet resultados;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sob_grup_05", "user", "pwd");
        Statement stmt = con.createStatement();

        resultados = stmt.executeQuery(sqlString);
        int i = 0;
        while (resultados.next()) {
            cat.urv.deim.sob.Tfg treball = new cat.urv.deim.sob.Tfg();
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
        Collections.sort(tfg, new Comparator<cat.urv.deim.sob.Tfg>() {
            @Override
            public int compare(cat.urv.deim.sob.Tfg o1, cat.urv.deim.sob.Tfg o2) {
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

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTfg(@PathParam("id") String id, @HeaderParam("Content-Type") String contentType) throws Exception {
        
        String sqlString = "SELECT * FROM sob_grup_05.TFG WHERE TITLE = '" + id + "'";
        LinkedList<cat.urv.deim.sob.Tfg> tfg = null;
        try {
            tfg = selectSQL(sqlString);
        } catch (SQLException | ClassNotFoundException | NamingException | NullPointerException e) {
            System.out.println(e);
        }

        cat.urv.deim.sob.Tfg treball = tfg.getFirst();

        return Jackson.getInstance().asResource(treball, contentType);
    }

    @Path("/{id}/assign")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assign(@PathParam("id") String id, JsonObject proj) throws Exception {
       Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/sob_grup_05", "sob", "sob");
        Statement stmt = con.createStatement();
        
        String sqlString = "INSERT INTO sob_grup_05.DIRIGEIX (PROFESSOR_FK, TFG_FK) VALUES ('"+proj.get("professor").toString().substring(1, proj.get("professor").toString().length()-1)+"', '"+id+"')";

        try {
        stmt.executeUpdate(sqlString);
        con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return Response.status(Response.Status.CREATED).build();
    }

}
