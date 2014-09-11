package id.ac.itb.cs.vuln.sqlinjection;

import id.ac.itb.cs.vuln.sqlinjection.dao.User;
import id.ac.itb.cs.vuln.sqlinjection.dao.UserSqlDao;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by Edward Samuel on 01/09/14.
 */
public class DaoSqlInjectionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>SQL Injection</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Result</h1>");
        out.println("<table>");
        out.println("<thead><tr><td>ID</td><td>Name</td><td>Description</td></tr></thead>");
        out.println("<tbody>");

        String query = request.getParameter("username");
        UserSqlDao dao = new UserSqlDao();
        Collection<User> users = dao.getByUsername(request.getParameter("username"));
        if (users != null) {
            for (User user : users) {
                out.println("<tr>");
                out.println("<td>");
                out.println(user.getId());
                out.println("</td>");
                out.println("<td>");
                out.println(StringEscapeUtils.escapeHtml4(user.getName()));
                out.println("</td>");
                out.println("<td>");
                out.println(StringEscapeUtils.escapeHtml4(user.getDescription()));
                out.println("</td>");
                out.println("</tr>");
            }
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
