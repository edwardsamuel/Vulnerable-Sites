package id.ac.itb.cs.vuln.hrs;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Edward Samuel on 26/08/14.
 */
public class DirectHttpResponseSplittingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = new Cookie("author", request.getParameter("author"));
        cookie.setMaxAge(3600);

        response.addCookie(cookie);
    }
}
