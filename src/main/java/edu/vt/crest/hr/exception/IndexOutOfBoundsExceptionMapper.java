package edu.vt.crest.hr.exception;

import edu.vt.crest.hr.rest.RestApplication;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Justin Boblitt on 2018-04-14.
 *
 * @description - provides default response when a "NoResultException" is thrown during the processing of a request.
 */
@Provider
public class IndexOutOfBoundsExceptionMapper implements ExceptionMapper<IndexOutOfBoundsException> {

    @Override
    public Response toResponse(IndexOutOfBoundsException e) {
        Response.ResponseBuilder rb = Response.status(Response.Status.BAD_REQUEST);
        if (RestApplication.IS_SERVER_DEBUG_MODE)
            rb = rb.header("reason", e.getMessage());
        return rb.build();
    }
}