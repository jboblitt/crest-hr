package edu.vt.crest.hr.rest;

import edu.vt.crest.hr.entity.EmployeeEntity;
import edu.vt.crest.hr.services.EmployeeService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
	 * @param employee the EmployeeEntity to create
	 * @return a Response containing the new EmployeeEntity
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(EmployeeEntity employee) {
		try {
			return Response.ok(employeeService.createEmployee(employee)).build();
		} catch (IllegalArgumentException e){
			System.err.println("Argument was not an instance of EmployeeEntity class: " + employee);
			throw(e);
		} catch (EntityExistsException e){
			System.err.println("Database already contains entity: " + employee);
			throw(e);
		}
	}

	/**
	 * @param id of the EmployeeEntity to return
	 * @return a Response containing the matching EmployeeEntity
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		EmployeeEntity employee = employeeService.findById(id);
		if (employee != null)
			return Response.ok(employee).build();
		else {
			throw (new NoResultException("Error occurred finding EmployeeEntity by id: [" + id + "]"));
		}
	}

	/**
	 * @param startPosition the index of the first EmployeeEntity to return
	 * @param maxResult the maximum number of EmployeeEntity(s) to return
	 *                  beyond the startPosition
	 * @return a list of EmployeeEntity(s)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmployeeEntity> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		return employeeService.listAll(startPosition, maxResult);
	}

	/**
	 * @param id the id of the EmployeeEntity to update
	 * @param employee the entity used to update
	 * @return a Response containing the updated EmployeeEntity
	 */
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, EmployeeEntity employee) {
		EmployeeEntity updatedEmployee = employeeService.update(id, employee);
		if (updatedEmployee != null) {
			return Response.ok(updatedEmployee).build();
		}
		else {
			throw (new NoResultException("Error during update: could not find EmployeeEntity by id: [" + id + "]"));
		}
	}
}
