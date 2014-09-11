package id.ac.itb.cs.vuln.sqlinjection.dao;

import id.ac.itb.cs.vuln.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Edward Samuel on 01/09/14.
 */
public class UserPsSqlDao implements UserDao {

    @Override
    public Collection<User> getByUsername(String username) {
        try {
            Connection connection = ConnectionManager.getSqlConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username LIKE ?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
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
