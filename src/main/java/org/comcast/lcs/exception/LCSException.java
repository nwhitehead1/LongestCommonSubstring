package org.comcast.lcs.exception;

import javax.ws.rs.WebApplicationException;

/**
 * Avoiding directly calling {@link WebApplicationException} from semantic validation in the service classes.
 *
 * @author WHITEHEADN
 */
public class LCSException extends WebApplicationException {

    public LCSException(String message, int status) {
        super(message, status);
    }
}
