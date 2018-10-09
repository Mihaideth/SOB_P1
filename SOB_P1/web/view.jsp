<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cat.urv.deim.sob.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    </head>
    <body>
        
        <jsp:useBean id="user" scope="request" class="cat.urv.deim.sob.User"></jsp:useBean>

         <form method="post" action="login.do">
            <center>
                <table border="2" width="15%" cellpadding="5">
                    <thead>
                        <tr>
                            <th colspan="2">Login</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>    
                            <td>Username : </td>
                            <td><input type="text" name="user" value="<jsp:getProperty name="user" property="user"/>"></td>
                        </tr>
                        <tr>
                            <td>Password : </td>
                            <td><input type="password" name="pwd" value="<jsp:getProperty name="user" property="pwd"/>"></td>
                        </tr>
                        <tr>  
                            <td><!--<center><a href="registre.jsp">Register</a></center>--></td>
                            <td><center><input type="submit" value="Login"></center></td>
                        </tr>
                    </tbody>             
                </table>
            </center>
        </form>
         
                
    </body>
</html>
