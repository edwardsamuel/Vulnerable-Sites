package id.ac.itb.cs.vuln.xpathinjection;

import id.ac.itb.cs.injection.CleanerType;
import id.ac.itb.cs.injection.Vulnerability;
import id.ac.itb.cs.injection.database.Cleaner;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Edward Samuel on 10/09/14.
 */
public class ValidatedXPathInjectionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

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

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            out.println("Username: " + StringEscapeUtils.escapeHtml4(username) + "<br />");
            out.println("Password: " + StringEscapeUtils.escapeHtml4(password) + "<br />");

            if (checkValueForXpathInjection(username) && checkValueForXpathInjection(password)) {
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
            } else {
                out.println("You have entered dangerous value.");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");
    }

    @Cleaner(type = CleanerType.VALIDATOR, vulnerabilities = {Vulnerability.XPATH_INJECTION})
    public boolean checkValueForXpathInjection(String value) throws Exception {
        boolean isValid = true;
        if ((value != null) && !"".equals(value)) {
            String xpathCharList = "()='[]:,*/ ";
            // Always to avoid encoding evading....
            String decodedValue = URLDecoder.decode(value, Charset.defaultCharset().name());
            for (char c : decodedValue.toCharArray()) {
                if (xpathCharList.indexOf(c) != -1) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }
}
