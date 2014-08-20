package id.ac.itb.cs.vuln;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Edward Samuel on 20/08/14.
 */
public abstract class ConnectionManager {

    public static Connection getSqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/progin_vuln", "progin", "progin");
    }
}
