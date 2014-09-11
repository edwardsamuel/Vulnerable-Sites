package id.ac.itb.cs.vuln.sqlinjection;

import id.ac.itb.cs.vuln.ConnectionManager;
import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.MySQLCodec;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Edward Samuel on 01/09/14.
 */
public class EsapiSqlInjectionServlet extends HttpServlet {

    private static final Codec MYSQL_CODEC = new MySQLCodec(MySQLCodec.Mode.STANDARD);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", "/sql/direct");
        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>SQL Injection</title>");
        out.println("</head>");
        out.println("<body>");

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getSqlConnection();
            st = con.createStatement();

            String username = req.getParameter("username");
            String password = req.getParameter("password");
            out.println("Username: " + StringEscapeUtils.escapeHtml4(username) + "<br />");
            out.println("Password: " + StringEscapeUtils.escapeHtml4(password) + "<br />");

            String query = "SELECT * FROM users WHERE username = '" + ESAPI.encoder().encodeForSQL(MYSQL_CODEC, username) + "' AND password = MD5('" + ESAPI.encoder().encodeForSQL(MYSQL_CODEC, password) + "')";

            out.println("Query: " + StringEscapeUtils.escapeHtml4(query) + "<br />");

            rs = st.executeQuery(query);
            if (rs.next()) {
                out.println("Logged in as  " + StringEscapeUtils.escapeHtml4(rs.getString("full_name")));
            } else {
                out.println("User not found.");
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            out.println("Oops. Something error.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            out.println("Oops. Something error.");
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (st != null) { st.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        out.println("</body>");
        out.println("</html>");
    }
}
