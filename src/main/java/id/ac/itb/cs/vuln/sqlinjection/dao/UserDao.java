package id.ac.itb.cs.vuln.sqlinjection.dao;

import java.util.Collection;

/**
 * Created by Edward Samuel on 01/09/14.
 */
public interface UserDao {

    public Collection<User> getByUsername(String username);

}
