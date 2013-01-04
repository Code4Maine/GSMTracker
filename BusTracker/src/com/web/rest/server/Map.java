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
public class Map extends HttpServlet{
	
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
        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println("<title>Bus Tracking</title>");
        resp.getWriter().println("<meta charset=\"utf-8\">");
        resp.getWriter().println("<script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=false\"></script>");
        resp.getWriter().println("<script type=\"text/javascript\">");
        resp.getWriter().println("var map;");
        resp.getWriter().println("var flightPlanCoordinates;");
        resp.getWriter().println("var initialLocation = new google.maps.LatLng(25.13086,75.84264);");
        resp.getWriter().println("function init() {");
        resp.getWriter().println("var duckOptions = {");
        resp.getWriter().println("zoom: 12,");
        resp.getWriter().println("center: initialLocation,");
        resp.getWriter().println("mapTypeId: google.maps.MapTypeId.HYBRID");
        resp.getWriter().println("};");
        resp.getWriter().println("map = new google.maps.Map(document.getElementById(\"map_canvas\"), duckOptions);");
        resp.getWriter().println("var marker = new google.maps.Marker({");
        resp.getWriter().println("position: initialLocation,"); 
        resp.getWriter().println("map: map");
        resp.getWriter().println("})");
        
        resp.getWriter().println("deleteOverlays();");
        resp.getWriter().println("flightPlanCoordinates = [");
        if (ducks != null & !ducks.isEmpty()){
        	int count =1;
        	for (BusLocation d : ducks) {
        		if(ducks.size() == count){
        			resp.getWriter().println("new google.maps.LatLng("+d.getLatitude() +","+d.getLongitude() +")");
        		}else{
        			resp.getWriter().println("new google.maps.LatLng("+d.getLatitude() +","+d.getLongitude()+"),");
        		}
        	}
        }else{
        	
        }
        resp.getWriter().println("];");
        
        resp.getWriter().println("var flightPath = new google.maps.Polyline({");
        resp.getWriter().println("path: flightPlanCoordinates,");
        resp.getWriter().println("trokeColor: \"#FF0000\",");
        resp.getWriter().println("strokeOpacity: 1.0,");
        resp.getWriter().println("strokeWeight: 2");
        resp.getWriter().println("});");

        resp.getWriter().println("flightPath.setMap(map);");
        
        resp.getWriter().println("}");
        
        resp.getWriter().println("function deleteOverlays() {");
        resp.getWriter().println("if (flightPlanCoordinates) {");
        resp.getWriter().println("for (i in flightPlanCoordinates) {");
        resp.getWriter().println("flightPlanCoordinates[i].setMap(null);");
        resp.getWriter().println("}");
        resp.getWriter().println("flightPlanCoordinates.length = 0;");
        resp.getWriter().println("}");
        resp.getWriter().println("}");
        
        resp.getWriter().println("</script>");
        resp.getWriter().println("</head>");
        resp.getWriter().println("<body  onload=\"init()\">");
        resp.getWriter().println("<h1>Bus Track</h1>");
        resp.getWriter().println("<div id=\"map_canvas\" style=\"width:100%;height:800px\"></div>"); 
        resp.getWriter().println("</body>");
        resp.getWriter().println("</html>");
        }
	
}
