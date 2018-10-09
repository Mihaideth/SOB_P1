<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cat.urv.deim.sob.Tfg"%>
<%@page import="cat.urv.deim.sob.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registre d'un nou TFG</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    </head>
    <body>
        
        <jsp:useBean id="tfg" scope="request" class="cat.urv.deim.sob.Tfg"></jsp:useBean>
        <jsp:useBean id="user" scope="request" class="cat.urv.deim.sob.User"></jsp:useBean>

         <form method="post" action="registrePendent.do">
            <center>
                <table border="2" width="15%" cellpadding="5">
                    <thead>
                        <tr>
                            <th colspan="2">Registre d'un nou TFG</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>    
                            <td>Títol : </td>
                            <td><input type="text" name="titol" value="<jsp:getProperty name="tfg" property="titol"/>"></td>
                        </tr>
                        <tr>
                            <td>Descripció : </td>
                            <td><input type="text" name="descripcio" value="<jsp:getProperty name="tfg" property="descripcio"/>"></td>
                        </tr>
                        <tr>    
                            <td>Professors : </td>
                            <td><input type="text" name="professors" value="<jsp:getProperty name="tfg" property="professors"/>"></td>
                        </tr>
                        <tr>    
                            <td>Estudiants : </td>
                            <td><input type="text" name="estudiants" value="<jsp:getProperty name="tfg" property="estudiants"/>"></td>
                        </tr>
                        <tr>    
                            <td>Estudis : </td>
                            <td><input type="text" name="estudis" value="<jsp:getProperty name="tfg" property="estudis"/>"></td>
                        </tr>
                        <tr>    
                            <td>Recursos : </td>
                            <td><input type="text" name="recursos" value="<jsp:getProperty name="tfg" property="recursos"/>"></td>
                        </tr>
                        <tr>    
                            <td>Data de defensa (yyyy-MM-DD): </td>
                            <td><input type="date" name="dataDefensa" value="<jsp:getProperty name="tfg" property="dataDefensa" />"></td>
                        </tr>
                        <tr>  
                            <td><!--<center><a href="registre.jsp">Register</a></center>--></td>
                            <td><center><input type="submit" value="Registrar"></center></td>
                        </tr>
                    </tbody>             
                </table>
            </center>
        </form>
         
                
    </body>
</html>
