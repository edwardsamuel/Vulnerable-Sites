package id.ac.itb.cs.vuln.pathtraversal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * Created by Edward Samuel on 10/09/14.
 */
public class RegexPathTraversalServlet extends HttpServlet {

    private static final Pattern FILENAME = Pattern.compile("^\\w{10,10}$");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        out.println("<html>");
        out.println("<head>");
        out.println("<title>SQL Injection</title>");
        out.println("</head>");
        out.println("<body>");

        String fileName = request.getParameter("name");
        if (FILENAME.matcher(fileName).matches()) {
            File file = new File(fileName);
            if (file.exists()) {
                out.println("Found");
            } else {
                out.println("Not Found");
            }
        }


        out.println("</body>");
        out.println("</html>");
    }
}
