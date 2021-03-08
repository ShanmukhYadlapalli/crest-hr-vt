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

import edu.vt.crest.hr.entity.DepartmentEntity;
import edu.vt.crest.hr.services.DepartmentService;

/**
 * Serves as a RESTful endpoint for manipulating DepartmentEntity(s)
 */
@Stateless
@Path("/departments")
public class DepartmentResource {

	//Used to interact with DepartmentEntity(s)
	@Inject
	DepartmentService departmentService;

	/**
	 * TODO - Implement this method
	 * @param department the DepartmentEntity to create
	 * @return a Response containing the new DepartmentEntity
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(DepartmentEntity department) {
//		System.out.println("In create dept resource");
		if(department == null) {
	        return Response.serverError().entity("Department cannot be null").build();
	    }
		try{
			DepartmentEntity departmentEntity = departmentService.createDepartment(department);
			if(departmentEntity == null) {
//		    	System.out.println("In create dept resource DE is null");
		        return Response.status(Response.Status.NOT_FOUND).entity("Entity failed to create").build();
		    }
//			System.out.println("In create dept resource DE is not null");
			return Response.status(200).entity(departmentEntity).build();
		}catch(Exception e) {
	    	e.printStackTrace(); 
//            System.out.println(e); 
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }
	}

	/**
	 * TODO - Implement this method
	 * @param id of the DepartmentEntity to return
	 * @return a Response containing the matching DepartmentEntity
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
//		System.out.println("In findById dept resource");
		if(id == null) {
	        return Response.serverError().entity("ID cannot be blank").build();
	    }
	    try{
	    	DepartmentEntity departmentEntity = departmentService.findById(id);
	    	if(departmentEntity == null) {
//		    	System.out.println("In findById dept resource DE is null");
		        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
		    }
//		    System.out.println("In findById dept resource DE is not null");
			return Response.status(200).entity(departmentEntity).build();
	    }catch(Exception e) {
	    	e.printStackTrace(); 
//            System.out.println(e); 
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }
	}

	/**
	 * TODO - Implement this method
	 * @param startPosition the index of the first DepartmentEntity to return
	 * @param maxResult the maximum number of DepartmentEntity(s) to return
	 *                  beyond the startPosition
	 * @return a list of DepartmentEntity(s)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DepartmentEntity> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
//		System.out.println("In listAll dept resource");
		//start and max values always seem null
//		System.out.println("startPosition:" + startPosition + ",maxResult:" + maxResult);
//		if(maxResult == null) maxResult = 10;
//		if(startPosition == null) startPosition = 0;
		try{
			List<DepartmentEntity> departmentEntities = departmentService.listAll(startPosition,maxResult);
			Collections.sort(departmentEntities, (d1, d2) -> d1.getId() < d2.getId() ? -1 : d1.getId() == d2.getId() ? 0 : 1);
			return departmentEntities;
		}catch(Exception e) {
	    	e.printStackTrace(); 
//          System.out.println(e); 
            return null;
	    }
	}

	/**
	 * TODO - Implement this method
	 * @param id the id of the DepartmentEntity to update
	 * @param department the entity used to update
	 * @return a Response containing the updated DepartmentEntity
	 */
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, DepartmentEntity department) {
//		System.out.println("In update dept resource");
		if(id == null) {
	        return Response.serverError().entity("ID cannot be blank").build();
	    }
		if(department == null) {
	        return Response.serverError().entity("Department cannot be null").build();
	    }
	    try{
	    	DepartmentEntity departmentEntity = departmentService.update(id, department);
	    	if(departmentEntity == null) {
//		    	System.out.println("In update dept resource DE is null");
		        return Response.status(Response.Status.NOT_FOUND).entity("Entity failed to update for ID: " + id).build();
		    }
//		    System.out.println("In update dept resource DE is not null");
			return Response.status(200).entity(departmentEntity).build();
	    }catch(Exception e) {
	    	e.printStackTrace(); 
//          System.out.println(e); 
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	    }    
	}

}
