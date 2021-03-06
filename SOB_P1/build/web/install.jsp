<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%!
    private List<String> resetDatabase(boolean force) throws Exception {
        LinkedList<String> messages = new LinkedList();
        /* How to customize:
         * 1. Update the database name on dbname.
         * 2. Create the list of tables, under tablenames[].
         * 3. Create the list of table definition, under tables[].
         * 4. Create the data into the above table, under data[]. 
         * 
         * If there is any problem, it will exit at the very first error.
         */
        String dbname = "sob_grup_05";
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        /* this will generate database if not exist */
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbname + ";create=true", "user", "pwd");
        Statement stmt = con.createStatement();
        String[] sqlCreatesTable = new String[3];
        String[] sqlInsertInto = new String[12];
        String[] sqlAlterInto = new String[2];

        try {
            stmt.execute("CREATE SCHEMA " + dbname);
            // schema was created; it will appear into: Other schemas -> "dbname"
        } catch (SQLException e) {
            // schema already exists; do nothing.
            if (!force) {
                messages.add("Database exists. Doing nothing. Visit install.jsp on your browser to reset your database content.");
                return messages;
            }
        }

        /* drop tables if they exist */
        String tablenames[] = new String[]{
            "PROFESSORS", "TFG", "DIRIGEIX"};
        for (String tablename : tablenames) {
            try {
                stmt.executeUpdate("DROP TABLE " + dbname + "." + tablename);
                messages.add("<pre> -> DROP TABLE " + dbname + "." + tablename + "<pre>");
            } catch (SQLException e) {
                // table didn't exist; it is the first time
            }
        }

        /* creating tables */
        sqlCreatesTable[0] = "CREATE TABLE " + dbname + ".PROFESSORS ("
                + " USERNAME VARCHAR(30) NOT NULL,"
                + " PASSWORD VARCHAR(30),"
                + " COMPLETNAME VARCHAR(30),"
                + " PRIMARY KEY(USERNAME) "
                + // " PROJECTLIST LIST() REFERENCES DIRIGEIX(TFG)"+
                ")";

        sqlCreatesTable[1] = "CREATE TABLE " + dbname + ".TFG ("
                + " TITLE VARCHAR(30) NOT NULL,"
                + " DESCR VARCHAR(30),"
                + " STATE VARCHAR(30),"
                + " ESTUDIANTS VARCHAR(100),"
                + " ESTUDIS VARCHAR(100),"
                + " RECURSOS VARCHAR(100),"
                + " DATADEFENSA DATE,"
                + " QUALIFICACIO VARCHAR(2),"
                + " DATACREACIO DATE,"
                + " DATAMODIFICACIO DATE,"
                + " PRIMARY KEY(TITLE)"
                + ")";

        sqlCreatesTable[2] = "CREATE TABLE " + dbname + ".DIRIGEIX("
                + " PROFESSOR_FK VARCHAR(30),"
                + " TFG_FK VARCHAR(30)"
                + ")";

        sqlAlterInto[0] = "ALTER TABLE " + dbname + ".DIRIGEIX ADD FOREIGN KEY (PROFESSOR_FK) REFERENCES "
                + dbname + ".PROFESSORS(USERNAME)";

        sqlAlterInto[1] = "ALTER TABLE " + dbname + ".DIRIGEIX ADD FOREIGN KEY (TFG_FK) REFERENCES "
                + dbname + ".TFG(TITLE)";


        /*String[] sqlAlterTable = new String[2];
        sqlAlterTable[0]="ALTER TABLE " + dbname + ".DIRIGEIX("
            + " ADD CONSTRAINT PROFESSOR REFERENCES PROFESSORS(USERNAME),"
            + " TFG VARCHAR(12) REFERENCES TFG(TITLE)"+
            ")";*/
        for (String create : sqlCreatesTable) {
            try {
                stmt.execute(create);
            } catch (SQLException e) {
                messages.add("<span class='error'>Error creating table: " + e.toString() + "</span>");
                return messages;
            }
            messages.add("<pre> -> " + create + "<pre>");
        }


        /* inserting data */
 /* you have to exclude the id autogenerated from the list of columns if you have use it. */
        sqlInsertInto[0] = "INSERT INTO " + dbname + ".PROFESSORS(USERNAME, PASSWORD, COMPLETNAME) VALUES ('sob', 'sob', 'Iden Versio')";

        sqlInsertInto[1] = "INSERT INTO " + dbname + ".PROFESSORS(USERNAME, PASSWORD, COMPLETNAME) VALUES ('mihai', 'copil', 'Mihai Copil')";

        sqlInsertInto[2] = "INSERT INTO " + dbname + ".PROFESSORS(USERNAME, PASSWORD, COMPLETNAME) VALUES ('jordi', 'borrell', 'Jordi Borrell')";

        sqlInsertInto[3] = "INSERT INTO " + dbname + ".TFG(TITLE, DESCR, STATE, ESTUDIANTS, ESTUDIS, RECURSOS, DATADEFENSA, QUALIFICACIO, DATACREACIO, DATAMODIFICACIO) VALUES ('seguretat de xarxes', '', 'Proposat', '', 'informatica', '', Date('1000-01-01'), '', Date('1000-01-01') , Date('2017-10-20') )";      //CURRENT_DATE
        sqlInsertInto[4] = "INSERT INTO " + dbname + ".TFG(TITLE, DESCR, STATE, ESTUDIANTS, ESTUDIS, RECURSOS, DATADEFENSA, QUALIFICACIO, DATACREACIO, DATAMODIFICACIO) VALUES ('motor electric', '', 'Assignat', 'Mihai', 'electrica', 'creacio de motor electric', Date('1000-01-01'), '', Date('2017-10-21'), Date('2017-10-30') )";
        sqlInsertInto[5] = "INSERT INTO " + dbname + ".TFG(TITLE, DESCR, STATE, ESTUDIANTS, ESTUDIS, RECURSOS, DATADEFENSA, QUALIFICACIO, DATACREACIO, DATAMODIFICACIO) VALUES ('supercomputador', '', 'Acabat', 'Jordi', 'informatica', 'exemple de supercomputador', Date('1000-01-01'), '', Date('2017-03-22'), Date('2017-04-20') )";
        sqlInsertInto[6] = "INSERT INTO " + dbname + ".TFG(TITLE, DESCR, STATE, ESTUDIANTS, ESTUDIS, RECURSOS, DATADEFENSA, QUALIFICACIO, DATACREACIO, DATAMODIFICACIO) VALUES ('mips', '', 'Pendent', 'Zarco', 'informatica', 'exemple de mips', Date('2017-12-15'), '', Date('2017-02-21'), Date('2017-11-23') )";
        sqlInsertInto[7] = "INSERT INTO " + dbname + ".TFG(TITLE, DESCR, STATE, ESTUDIANTS, ESTUDIS, RECURSOS, DATADEFENSA, QUALIFICACIO, DATACREACIO, DATAMODIFICACIO) VALUES ('cotxe teledirigit', '', 'Defensat', 'David', 'informatica, electrica', 'exemple de cotxe', Date('2017-11-15'), 'MH', Date('2017-01-19'), Date('2017-11-16') )";

        sqlInsertInto[8] = "INSERT INTO " + dbname + ".DIRIGEIX(PROFESSOR_FK, TFG_FK) VALUES ('sob', 'seguretat de xarxes')";
        sqlInsertInto[9] = "INSERT INTO " + dbname + ".DIRIGEIX(PROFESSOR_FK, TFG_FK) VALUES ('sob', 'motor electric')";
        sqlInsertInto[10] = "INSERT INTO " + dbname + ".DIRIGEIX(PROFESSOR_FK, TFG_FK) VALUES ('mihai', 'mips')";
        sqlInsertInto[11] = "INSERT INTO " + dbname + ".DIRIGEIX(PROFESSOR_FK, TFG_FK) VALUES ('mihai', 'supercomputador')";

        for (String insert : sqlInsertInto) {
            if (stmt.executeUpdate(insert) <= 0) {
                messages.add("<span class='error'>Error inserting data: " + insert + "</span>");
                return messages;
            }
            messages.add("<pre> -> " + insert + "<pre>");
        }
        return messages;
    }

    public void jspInit() {
        try {
            List<String> messages = resetDatabase(false);
            for (String message : messages) {
                System.out.println("install.jsp: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Force database installation</title>
    </head>
    <style>
        .error {
            color: red;
        }
        pre {
            color: green;
        }
    </style>
    <body>
        <h2>Database initialization in progress</h2>
        <%
            List<String> messages = resetDatabase(true);
            for (String message : messages) {
                out.println(message);
            }
        %>
        <button onclick="window.location = '<%=request.getSession().getServletContext().getContextPath()%>'">Go home</button>
    </body>
</html>
