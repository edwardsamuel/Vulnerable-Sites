package id.ac.itb.cs.vuln.hrs;

import id.ac.itb.cs.injection.CleanerType;
import id.ac.itb.cs.injection.Vulnerability;
import id.ac.itb.cs.injection.database.Cleaner;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Edward Samuel on 10/09/14.
 */
public class EscapedHttpResponseSplittingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = new Cookie("author", encodeValue(request.getParameter("author")));
        response.addCookie(cookie);
        response.getWriter().println("Done");
    }

    @Cleaner(type = CleanerType.SANITIZER, vulnerabilities = {Vulnerability.HTTP_RESPONSE_SPLITTING})
    public String encodeValue(String value) {
        value = value.replace('\r', ' ');
        value = value.replace('\n', ' ');
        return value;
    }
}
