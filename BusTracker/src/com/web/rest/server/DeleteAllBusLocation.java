package com.web.rest.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.rest.model.BusLocation;
import com.web.rest.model.EntityManagerService;


@SuppressWarnings("serial")
public class DeleteAllBusLocation extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("posKey");
		if (name != null){
			int id = Integer.parseInt( name );
			deleteRecord(id);
		}
		resp.getWriter().println("Deleated Bus Location index :"+ name);
		
		resp.sendRedirect("/list");
	}

	@SuppressWarnings("unchecked")
	protected void deleteRecord(int id){
		EntityManager entityManager = EntityManagerService.get().createEntityManager();
		List<BusLocation> ducks = null;

		try {
			Query duckQuery = entityManager.createQuery("select d from BusLocation d");
			ducks = new ArrayList<BusLocation>(duckQuery.getResultList());
			
			if (ducks != null & !ducks.isEmpty()){
				for (BusLocation d : ducks) {		
					if(d.getDuckId() == id){
						entityManager.remove(d);
					}
				}
			} else {
				System.out.println("No recoud to delete");
			}
		} finally {
			entityManager.close();
		}
	}
}
