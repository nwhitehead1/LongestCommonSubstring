package org.comcast.lcs.exception;

import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author WHITEHEADN
 */
@Provider
public class WebAppExceptionHandler implements ExceptionMapper<Exception> {

    public WebAppExceptionHandler() {
        super();
    }

    @Override
    public Response toResponse(Exception ex) {
        // Wrapping exception in Json Array for front end consistency.
        JsonArray errorArray = Json.createArrayBuilder()
                .add(
                        Json.createObjectBuilder()
                                .add("message", ex.getMessage())
                ).build();
        if (ex instanceof LCSException) {
            int status = ((LCSException) ex).getResponse().getStatus();
            return Response.status(status).entity(errorArray).build();
        } else {
            // Catch-all error to avoid exposing system internals (unless required).
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Unexpected error occurred.").build();
        }
    }
}
