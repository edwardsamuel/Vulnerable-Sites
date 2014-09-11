package id.ac.itb.cs.vuln.xss2;

import id.ac.itb.cs.vuln.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Edward Samuel on 25/08/14.
 */
public class DirectXSS2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>XSS Type-2</title>");
        out.println("</head>");
        out.println("<body>");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            con = ConnectionManager.getSqlConnection();

            String username = req.getParameter("username");
            String query = "SELECT * FROM users WHERE username = ?";

            ps = con.prepareStatement(query);
            ps.setString(1, username);

            if (rs.next()) {
                out.println("Full name : " + rs.getString("full_name") + "<br />");
                out.println("Description : <br />");
                out.println(rs.getString("description"));
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
                if (ps != null) { ps.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        out.println("</body>");
        out.println("</html>");
    }
}
