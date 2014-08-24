package id.ac.itb.cs.vuln.ldapinjection;

import com.novell.ldap.*;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Edward Samuel on 24/08/14.
 */
public class DirectLdapInjectionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>XPath Injection</title>");
        out.println("</head>");
        out.println("<body>");

        int ldapPort = LDAPConnection.DEFAULT_PORT;
        int searchScope = LDAPConnection.SCOPE_ONE;
        int ldapVersion = LDAPConnection.LDAP_V3;

        String ldapHost = "EDWARD-PC";
        String loginDN = "cn=Manager,dc=maxcrc,dc=com";
        String ldapPassword = "secret";
        String searchBase = "ou=People,dc=maxcrc,dc=com";

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        out.println("Username: " + StringEscapeUtils.escapeHtml4(username) + "<br />");
        out.println("Password: " + StringEscapeUtils.escapeHtml4(password) + "<br />");

        String searchFilter = "(&(uid=" + username + ")(userPassword=" + password + "))";
        out.println("LDAP Search Filter: " + StringEscapeUtils.escapeHtml4(searchFilter) + "<br />");


        LDAPConnection lc = new LDAPConnection();
        try {
            // connect to the server
            lc.connect(ldapHost, ldapPort);

            // bind to the server
            lc.bind(ldapVersion, loginDN, ldapPassword.getBytes("UTF8"));

            LDAPSearchResults searchResults =
                    lc.search(searchBase,
                            searchScope,
                            searchFilter,
                            null,         // return all attributes
                            false);       // return attrs and values

            if (searchResults.hasMore()) {
                LDAPEntry nextEntry = null;
                try {
                    nextEntry = searchResults.next();
                } catch (LDAPException e) {
                    out.println("Error: " + e.toString());
                }

                out.println("Logged in as: " + nextEntry.getDN());
            } else {
                out.println("User not found.");
            }

            // disconnect with the server
            lc.disconnect();
        } catch (LDAPException e) {
            out.println("Error: " + e.toString());
        } catch (UnsupportedEncodingException e) {
            out.println("Error: " + e.toString());
        }

        out.println("</body>");
        out.println("</html>");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public static void main(String[] args) {
        args = new String[4];
        args[0] = "EDWARD-PC";
        args[1] = "cn=Manager,dc=maxcrc,dc=com";
        args[2] = "secret";
        args[3] = "ou=People,dc=maxcrc,dc=com";

        if (args.length != 4) {
            System.err.println("Usage:   java AddEntry <host name> <login dn>"
                    + " <password> <container>");
            System.err.println("Example: java AddEntry Acme.com"
                    + " \"cn=admin,o=Acme\" secret \"ou=Sales,o=Acme\"");
            System.exit(1);
        }

        int ldapPort = LDAPConnection.DEFAULT_PORT;
        int ldapVersion = LDAPConnection.LDAP_V3;
        String ldapHost = args[0];
        String loginDN = args[1];
        String password = args[2];
        String containerName = args[3];

        LDAPConnection lc = new LDAPConnection();
        LDAPAttributeSet attributeSet = new LDAPAttributeSet();

        /* To Add an entry to the directory,
         *  - Create the attributes of the entry and add them to an attribute set
         *  - Specify the DN of the entry to be created
         *  - Create an LDAPEntry object with the DN and the attribute set
         *  - Call the LDAPConnection add method to add it to the directory
         */
//        attributeSet.add(new LDAPAttribute("objectclass", "inetOrgPerson"));
//        attributeSet.add(new LDAPAttribute("cn", new String[] {"Edward Samuel Pasaribu", "edwardsp"}));
//        attributeSet.add(new LDAPAttribute("uid", "edwardsp"));
//        attributeSet.add(new LDAPAttribute("givenName", "Edward Samuel"));
//        attributeSet.add(new LDAPAttribute("sn", "Pasaribu"));
//        attributeSet.add(new LDAPAttribute("userPassword", "lalalala"));
//        String dn = "uid=edwardsp," + containerName;
//        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);

        attributeSet.add(new LDAPAttribute("objectclass", "inetOrgPerson"));
        attributeSet.add(new LDAPAttribute("cn", new String[] {"Raymond Lukanta", "raymondl"}));
        attributeSet.add(new LDAPAttribute("uid", "raymondl"));
        attributeSet.add(new LDAPAttribute("givenName", "Raymond"));
        attributeSet.add(new LDAPAttribute("sn", "Lukanta"));
        attributeSet.add(new LDAPAttribute("userPassword", "lalalala"));
        String dn = "uid=raymondl," + containerName;
        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);

        try {
            // connect to the server
            lc.connect(ldapHost, ldapPort);
            System.out.println("Connected.");

            // authenticate to the server
            lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));
            System.out.println("Binded.");

            lc.add(newEntry);
            System.out.println("\nAdded object: " + dn + " successfully.");

            // disconnect with the server
            lc.disconnect();
        } catch (LDAPException e) {
            System.out.println("Error:  " + e.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: " + e.toString());
        }
        System.exit(0);
    }
}
