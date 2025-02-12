import java.sql.*;

public class DBConnect {
    //Attribut paramètre BDD localhost si absence de Env.java
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TaskDB";
    private static final String USERNAME = "root"; // Change if necessary
    private static final String PASSWORD = ""; // Change if necessary

    private static Connection connexion;

    static Connection getConnect() {
        //Connexion à la BDD
        try {
            if (Env.DB_URL.isEmpty()) {
                connexion = DriverManager.getConnection(Env.DB_URL, Env.USERNAME, Env.PASSWORD);
            } else {
                connexion = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connexion;
    }
}
