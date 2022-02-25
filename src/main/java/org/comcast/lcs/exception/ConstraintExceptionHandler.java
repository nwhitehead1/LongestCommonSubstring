package org.comcast.lcs.exception;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author WHITEHEADN
 */
@Provider
public class ConstraintExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    public ConstraintExceptionHandler() {
        super();
    }

    @Override
    public Response toResponse(ConstraintViolationException ex) {
        JsonArray errorResponseEntity = buildErrorMessage(ex);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponseEntity)
                .build();
    }

    private JsonArray buildErrorMessage(ConstraintViolationException exceptions) {
        JsonArrayBuilder errorArrayBuilder = Json.createArrayBuilder();
        Set<String> violationMessages = exceptions.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        for (String message : violationMessages) {
            errorArrayBuilder.add(
                    Json.createObjectBuilder()
                            .add("message", message)
            );
        }
        return errorArrayBuilder.build();
    }
}
