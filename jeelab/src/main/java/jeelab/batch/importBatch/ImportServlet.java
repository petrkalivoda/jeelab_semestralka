package jeelab.batch.importBatch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

@WebServlet("/import")
public class ImportServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Import Chunk Job</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Import Chunk Job</h1>");
            JobOperator jo = BatchRuntime.getJobOperator();
            long jid = jo.start("importJob", new Properties());
            Logger.getLogger(ImportServlet.class).info("Started import job on " + jid);
            out.println("Job submitted: " + jid + "<br/>");
            out.println("</body>");
            out.println("</html>");
        } catch (JobStartException | JobSecurityException ex) {
        	Logger.getLogger(ImportServlet.class).error(ex);
        }
    }
}
