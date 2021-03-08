package edu.vt.crest.hr.rest;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.vt.crest.hr.entity.EmployeeEntity;
import edu.vt.crest.hr.services.EmployeeService;

/**
 * Serves as a RESTful endpoint for manipulating EmployeeEntity(s)
 */
@Stateless
@Path("/employees")
public class EmployeeResource {

	//Used to interact with EmployeeEntity(s)
	@Inject
	EmployeeService employeeService;

	/**
	 * TODO - Implement this method
	 * @param employee the EmployeeEntity to create
	 * @return a Response containing the new EmployeeEntity
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(EmployeeEntity employee) {
//		System.out.println("In create emp resource");
		if(employee == null) {
	        return Response.serverError().entity("Employee cannot be null").build();
	    }
		try{
			EmployeeEntity employeeEntity = employeeService.createEmployee(employee);
			if(employeeEntity == null) {
//		    	System.out.println("In create emp resource EE is null");
		        return Response.status(Response.Status.NOT_FOUND).entity("Entity failed to create").build();
		    }
//			System.out.println("In create emp resource EE is not null");
			return Response.status(200).entity(employeeEntity).build();
		}catch(Exception e) {
	    	e.printStackTrace(); 
//          System.out.println(e); 
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }
	}

	/**
	 * TODO - Implement this method
	 * @param id of the EmployeeEntity to return
	 * @return a Response containing the matching EmployeeEntity
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
//		System.out.println("In findById emp resource");
		if(id == null) {
	        return Response.serverError().entity("ID cannot be blank").build();
	    }
	    try{
	    	EmployeeEntity employeeEntity = employeeService.findById(id);
	    	if(employeeEntity == null) {
//		    	System.out.println("In findById emp resource EE is null");
		        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
		    }
//		    System.out.println("In findById emp resource EE is not null");
			return Response.status(200).entity(employeeEntity).build();
	    }catch(Exception e) {
	    	e.printStackTrace(); 
//          System.out.println(e); 
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }
	}

	/**
	 * TODO - Implement this method
	 * @param startPosition the index of the first EmployeeEntity to return
	 * @param maxResult the maximum number of EmployeeEntity(s) to return
	 *                  beyond the startPosition
	 * @return a list of EmployeeEntity(s)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmployeeEntity> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
//		System.out.println("In listAll emp resource");
		//start and max values always seem null
//		System.out.println("startPosition:" + startPosition + ",maxResult:" + maxResult);
//		if(maxResult == null) maxResult = 10;
//		if(startPosition == null) startPosition = 0;
		try{
			List<EmployeeEntity> employeeEntities = employeeService.listAll(startPosition,maxResult);
			Collections.sort(employeeEntities, (e1, e2) -> e1.getId() < e2.getId() ? -1 : e1.getId() == e2.getId() ? 0 : 1);
			return employeeEntities;
		}catch(Exception e) {
	    	e.printStackTrace(); 
//	    	System.out.println(e); 
            return null;
	    }
	}

	/**
	 * TODO - Implement this method
	 * @param id the id of the EmployeeEntity to update
	 * @param employee the entity used to update
	 * @return a Response containing the updated EmployeeEntity
	 */
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, EmployeeEntity employee) {
//		System.out.println("In update emp resource");
		if(id == null) {
	        return Response.serverError().entity("ID cannot be blank").build();
	    }
		if(employee == null) {
	        return Response.serverError().entity("Employee cannot be null").build();
	    }
	    try{
	    	EmployeeEntity EmployeeEntity = employeeService.update(id, employee);
	    	if(EmployeeEntity == null) {
//		    	System.out.println("In update emp resource DE is null");
		        return Response.status(Response.Status.NOT_FOUND).entity("Entity failed to update for ID: " + id).build();
		    }
//		    System.out.println("In update emp resource DE is not null");
			return Response.status(200).entity(EmployeeEntity).build();
	    }catch(Exception e) {
	    	e.printStackTrace(); 
//	    	System.out.println(e); 
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }
	}
}
