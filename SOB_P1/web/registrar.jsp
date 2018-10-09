<jsp:useBean id="user" class="cat.urv.deim.sob.User" scope="request" />

<html>
    <head>
        <title>User details</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

    </head>
    <style>
        .register{
            margin: auto;
            border-style: double;
            width: 400px;
            padding: 50px;
        }
    </style>
    <body>
        <center><h2>Nou Registre</h2></center>
        <form method="post" action="registre.do">
           
            <table class="register">
                <tr>
                    <td colspan="2" style="color:red">
                         * Camps obligatoris
                    </td>
                </tr>
                <tr>
                    <td>
                        Usuari: *
                    </td>
                    <td>
                        <input type="text" name="user_name" value="" />
                    </td>
                </tr>
                <tr>
                    <td>
                        Contrasenya: *  
                    </td>
                    <td>
                        <input type="password" name="password" value="" />
                    </td>
                </tr>
                <tr>
                    <td>
                        Nom Complet: *
                    </td>
                    <td>
                        <input type="text" name="completname" value="" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" name="enter_button" value="Registrar usuario" />
                    </td>
                </tr>

                
            </table>
             
        </form>
    </body>
</html>
