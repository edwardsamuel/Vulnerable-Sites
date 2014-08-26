package id.ac.itb.cs.vuln.xss1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Edward Samuel on 25/08/14.
 */
public class DirectXSS1Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>XSS Type-1</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("Hello " + req.getParameter("name") + "!");

        out.println("</body>");
        out.println("</html>");
    }
}
