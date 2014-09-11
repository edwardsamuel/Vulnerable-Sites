package id.ac.itb.cs.vuln.xpathinjection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Edward Samuel on 23/08/14.
 */
public class DirectXPathInjectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", "/xpath/direct");
        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>XPath Injection</title>");
        out.println("</head>");
        out.println("<body>");

        try {
            // For the sample we load the XML Document at each request but this not a good way for real application.....
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(getServletContext().getResourceAsStream("/WEB-INF/data.xml")));

            String username = req.getParameter("username");
            String password = req.getParameter("password");
            out.println("Username: " + StringEscapeUtils.escapeHtml4(username) + "<br />");
            out.println("Password: " + StringEscapeUtils.escapeHtml4(password) + "<br />");

            String xpathExpr = "//user[username/text()='" + username + "' and password/text()='" + password + "']";
            out.println("XPath Query: " + StringEscapeUtils.escapeHtml4(xpathExpr) + "<br />");

            // Create XPATH expression
            XPath expression = new DOMXPath(xpathExpr);

            // Apply expression on XML document
            List nodes = expression.selectNodes(doc);
            if (!nodes.isEmpty()) {
                Element node = (Element) nodes.get(0);
                Node fullnameNode = node.getElementsByTagName("fullname").item(0);
                Node usernameNode = node.getElementsByTagName("username").item(0);
                out.println("Logged in as  " + fullnameNode.getTextContent() + "(" + usernameNode.getTextContent() + ")");
            } else {
                out.println("User not found.");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");
    }
}
