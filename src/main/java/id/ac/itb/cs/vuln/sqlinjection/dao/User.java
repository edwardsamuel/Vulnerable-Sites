package id.ac.itb.cs.vuln.sqlinjection.dao;

/**
 * Created by Edward Samuel on 01/09/14.
 */
public class User {

    private long id;

    private String name;

    private String description;

    public User(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
