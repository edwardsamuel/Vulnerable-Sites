package id.ac.itb.cs.vuln.sqlinjection.dao;

import id.ac.itb.cs.vuln.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Edward Samuel on 01/09/14.
 */
public class UserSqlDao implements UserDao {

    @Override
    public Collection<User> getByUsername(String username) {
        try {
            Connection connection = ConnectionManager.getSqlConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE username LIKE '" + username + "'");
            Collection<User> users = new ArrayList<User>();
            while (rs.next()) {
                users.add(new User(rs.getLong("id"), rs.getString("username"), rs.getString("description")));
            }
            return users;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
