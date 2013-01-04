package com.web.rest.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.rest.model.BusLocation;
import com.web.rest.model.EntityManagerService;

@SuppressWarnings("serial")
public class ListBusLocation extends HttpServlet {
	
	@SuppressWarnings("unchecked")
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        EntityManager entityManager = EntityManagerService.get().createEntityManager();
        List<BusLocation> ducks = null;
        
        try {
            Query duckQuery = entityManager.createQuery("select d from BusLocation d");
            ducks = new ArrayList<BusLocation>(duckQuery.getResultList());
        } finally {
            entityManager.close();
        }
        resp.setContentType("html");
        if (ducks != null & !ducks.isEmpty()){
        	resp.getWriter().println("<html>");
        	resp.getWriter().println("<style>.MyClass table, th, td {border: 1px solid black;padding:15px;</style>");
            resp.getWriter().println("<h1>Bus Locations:</h1>");
            resp.getWriter().println("<table class=\"MyClass\">");
            resp.getWriter().println("<thead>");
            resp.getWriter().println("<tr>");
        	
        	resp.getWriter().println("<td>");
        	resp.getWriter().println("Delete");
        	resp.getWriter().println("</td>");
        	
        	resp.getWriter().println("<td>");
        	resp.getWriter().println("Position ID");
        	resp.getWriter().println("</td>");

        	resp.getWriter().println("<td>");
        	resp.getWriter().println("Latitude");
        	resp.getWriter().println("</td>");

        	resp.getWriter().println("<td>");
        	resp.getWriter().println("Logitude");
        	resp.getWriter().println("</td>");

        	resp.getWriter().println("<td>");
        	resp.getWriter().println("Address");
        	resp.getWriter().println("</td>");
        	
        	resp.getWriter().println("</tr>");


            resp.getWriter().println("</thead>");
            for (BusLocation d : ducks) {
            	resp.getWriter().println("<tr>");
            	resp.getWriter().println("<td>");
            	resp.getWriter().println("<form id=\"deleteForm\" action=\"/deleteAll\" method=\"post\" accept-charset=\"utf-8\">");
            	resp.getWriter().println("<input type=\"hidden\" name=\"posKey\" value=\""+d.getDuckId()+"\" />");
            	resp.getWriter().println("<input type=\"submit\" value=\"Delete\" onClick=\"window.location.reload()\">");
            	resp.getWriter().println("</form>");
            	resp.getWriter().println("</td>");
            	
            	resp.getWriter().println("<td>");
                resp.getWriter().println("Point "+d.getDuckId());
             	resp.getWriter().println("</td>");
             	
             	resp.getWriter().println("<td>");
                resp.getWriter().println(d.getLatitude());
             	resp.getWriter().println("</td>");
             	
             	resp.getWriter().println("<td>");
                resp.getWriter().println(d.getLongitude());
             	resp.getWriter().println("</td>");
             	
             	resp.getWriter().println("<td>");
                resp.getWriter().println(d.getAddress());
             	resp.getWriter().println("</td>");
                
                resp.getWriter().println("</tr>");
            }
            resp.getWriter().println("</table>");
        	resp.getWriter().println("</html>");
        } else {
            resp.getWriter().println("No points yet.");
        }
    }
}
