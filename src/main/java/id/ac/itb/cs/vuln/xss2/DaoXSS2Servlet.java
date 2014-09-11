package id.ac.itb.cs.vuln.xss2;

import id.ac.itb.cs.vuln.sqlinjection.dao.User;
import id.ac.itb.cs.vuln.sqlinjection.dao.UserDao;
import id.ac.itb.cs.vuln.sqlinjection.dao.UserPsSqlDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by Edward Samuel on 25/08/14.
 */
public class DaoXSS2Servlet extends HttpServlet {
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

        UserDao dao = new UserPsSqlDao();
        Collection<User> users = dao.getByUsername(req.getParameter("username"));

        for (User user : users) {
            out.println("Full name : " + user.getName() + "<br />");
            out.println("Description : <br />");
            out.println(user.getDescription());
        }

        out.println("</body>");
        out.println("</html>");
    }
}
