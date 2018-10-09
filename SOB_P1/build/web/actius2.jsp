<%@page import="cat.urv.deim.sob.Tfgs"%>
<%@page import="cat.urv.deim.sob.Tfg"%>
<jsp:useBean id="tfgActius" class="cat.urv.deim.sob.Tfgs" scope="request" />
<%@ page import = "java.util.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Llista de TFGs</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    </head>
    <body>
        <h1>Benvingut ${requestScope['user']}!</h1>
        <a class="btn btn-outline-success float-right" href="registreProposat.jsp">Registrar TFG Proposat</a>
        <a class="btn btn-outline-success float-right" href="registreAssignat.jsp">Registrar TFG Assignat</a>
        <a class="btn btn-outline-success float-right" href="registreAcabat.jsp">Registrar TFG Acabat</a>
        <a class="btn btn-outline-success float-right" href="registrePendent.jsp">Registrar TFG Pendent</a>
        <a class="btn btn-outline-success float-right" href="registreDefensat.jsp">Registrar TFG Defensat</a>
        <a href="logout.do">
            Cerrar Sesión
        </a>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Títol</th>
                    <th scope="col">Descripció</th>
                    <th scope="col">Professors</th>
                    <th scope="col">Estat</th>
                    <th scope="col">Estudiants</th>
                    <th scope="col">Estudis</th>
                    <th scope="col">Recursos</th>
                    <th scope="col">Data Defensa</th>
                    <th scope="col">Qualificació</th>
                    <th scope="col">Data Creació</th>
                    <th scope="col">Data Modificació</th>
                </tr>
            </thead>
            <tbody>
                <%  LinkedList<Tfg> tfgs = tfgActius.getLlista();
                    Iterator iter = tfgs.iterator();
                    Tfg tfg = new Tfg();

                    while (iter.hasNext()) {
                        tfg = (Tfg) iter.next();
                %>
                <tr>
                    <td><% out.print(tfg.getTitol()); %></td>
                    <td><% out.print(tfg.getDescripcio()); %></td>
                    <td><% LinkedList<String> profes = tfg.getProfessors();
                        Iterator iterator = profes.iterator();
                        while (iterator.hasNext()) {
                            out.print(iterator.next());
                        }
                        %></td>
                    <td><% out.print(tfg.getEstat()); %></td>
                    <td><% out.print(tfg.getEstudiants()); %></td>
                    <td><% out.print(tfg.getEstudis()); %></td>
                    <td><% out.print(tfg.getRecursos()); %></td>
                    <td><% out.print(tfg.getDataDefensa()); %></td>
                    <td><% out.print(tfg.getQualificacio()); %></td>
                    <td><% out.print(tfg.getDataCreacio()); %></td>
                    <td><% out.print(tfg.getDataModificacio()); %></td>
                </tr>
                <% }%>
            </tbody>           
        </table>
    </body>
</html>