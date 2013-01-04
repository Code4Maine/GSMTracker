package com.web.rest.server;

import java.io.IOException;
import com.web.rest.model.*;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.rest.model.EntityManagerService;

@SuppressWarnings("serial")
public class AddBusLocation extends HttpServlet  {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        EntityManager entityManager = EntityManagerService.get().createEntityManager();
        BusLocation busLocation = new BusLocation();
        busLocation.setTitle(req.getParameter("title"));
        busLocation.setDescription(req.getParameter("description"));
        busLocation.setLatitude(Double.parseDouble(req.getParameter("latitude")));
        busLocation.setLongitude(Double.parseDouble(req.getParameter("longitude")));
        busLocation.setAddress(req.getParameter("markerAddress"));
        try {
            entityManager.persist(busLocation);
        } finally {
            entityManager.close();
        }
        resp.getWriter().println("Data points added.");
    }

}
