package id.ac.itb.cs.vuln.commandinjection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.WindowsCodec;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Edward Samuel on 10/09/14.
 */
public class EsapiCommandInjectionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>SQL Injection</title>");
        out.println("</head>");
        out.println("<body>");

        String directory = ESAPI.encoder().encodeForOS(new WindowsCodec(), request.getParameter("dir"));

        Runtime runtime = Runtime.getRuntime();
        String[] cmd = new String[3];
        cmd[0] = "cmd.exe" ;
        cmd[1] = "/C";
        cmd[2] = "dir " + directory;
        Process proc = runtime.exec(cmd);

        InputStream is = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        out.println("<pre>");
        String line;
        while ((line = br.readLine()) != null) {
            out.println(StringEscapeUtils.escapeHtml4(line));
        }
        out.println("</pre>");

        out.println("</body>");
        out.println("</html>");
    }
}
