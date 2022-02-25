package org.comcast.lcs;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author WHITEHEADN
 */
@Provider
public class JsonRequestInterceptor implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        InputStream inStream = requestContext.getEntityStream();
        final byte[] requestBytes = inStream.readAllBytes();
        if (requestBytes.length == 0) {
            JsonArray errorEntity = Json.createArrayBuilder()
                    .add(
                            Json.createObjectBuilder()
                                    .add("message", "Empty request payload.")
                    ).build();
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).entity(errorEntity).build());
            return;
        }
        try (JsonReader reader = Json.createReader(new ByteArrayInputStream(requestBytes))) {
            reader.readObject();
        } catch (JsonParsingException ex) {
            // Unable to parse JSON parsing exception, abort request as bad request.
            // This covers cases if syntactically incorrect JSON requests.
            JsonArray errorEntity = Json.createArrayBuilder()
                    .add(
                            Json.createObjectBuilder()
                                    .add("messgae", "Malformed JSON object, unable to parse.")
                    ).build();
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).entity(errorEntity).build());
        }
        requestContext.setEntityStream(new ByteArrayInputStream(requestBytes));
    }
}
