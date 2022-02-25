package org.comcast.lcs;

import org.comcast.lcs.model.LCSResponse;
import org.comcast.lcs.model.SetOfStrings;
import org.comcast.lcs.model.Value;
import org.comcast.lcs.service.LongestCommonSubstringService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * @author WHITEHEADN
 */
@Path("/")
public class LCSResource {

    @Inject
    LongestCommonSubstringService service;

    /**
     * By default, the {@link SetOfStrings} object will be serialized/deserialized by JSON-B.
     * <p>
     * A request is considered malformed if it violates one of the following:
     * - {@param setOfStrings} is empty.
     * - {@param setOfStrings} is not in the correct format. (Handled in interceptor)
     * - {@param setOfStrings} is not a set.
     *
     * @param setOfStrings - Serialized JSON object containing at least one valid string.
     * @return - A JSON response containing the longest common substring of a set of Strings.
     */
    @Path("lcs")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLongestCommonSubstrings(@Valid SetOfStrings setOfStrings) {
        Set<Value> lcsStrings = service.getLongestCommonSubstring(setOfStrings.getValues());
        return Response.ok(new LCSResponse(lcsStrings)).build();
    }
}