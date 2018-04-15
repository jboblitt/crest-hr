package edu.vt.crest.hr.rest;

import edu.vt.crest.hr.entity.DepartmentEntity;
import edu.vt.crest.hr.services.DepartmentService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
	 * @param department the DepartmentEntity to create
	 * @return a Response containing the new DepartmentEntity
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(DepartmentEntity department) {
		try {
			return Response.ok(departmentService.createDepartment(department)).build();
		} catch (IllegalArgumentException e){
			System.err.println("Argument was not an instance of DepartmentEntity class: " + department);
			throw(e);
		} catch (EntityExistsException e){
			System.err.println("Database already contains entity: " + department);
			throw(e);
		}
	}

	/**
	 * @param id of the DepartmentEntity to return
	 * @return a Response containing the matching DepartmentEntity
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		DepartmentEntity department = departmentService.findById(id);
		if (department != null)
			return Response.ok(department).build();
		else {
			throw (new NoResultException("Error occurred finding DepartmentEntity by id: [" + id + "]"));
		}
	}

	/**
	 * @param startPosition the index of the first DepartmentEntity to return
	 * @param maxResult the maximum number of DepartmentEntity(s) to return
	 *                  beyond the startPosition
	 * @return a list of DepartmentEntity(s)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DepartmentEntity> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		return departmentService.listAll(startPosition, maxResult);
	}

	/**
	 * @param id the id of the DepartmentEntity to update
	 * @param department the entity used to update
	 * @return a Response containing the updated DepartmentEntity
	 */
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, DepartmentEntity department) {
		DepartmentEntity updatedDepartment = departmentService.update(id, department);
		if (updatedDepartment != null) {
			return Response.ok(updatedDepartment).build();
		}
		else {
			throw (new NoResultException("Error during update: could not find DepartmentEntity by id: [" + id + "]"));
		}
	}

}
