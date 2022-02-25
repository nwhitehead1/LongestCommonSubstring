package org.comcast.lcs.model;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * JSON-B will deserialize Integers to Strings. For the sake of the activity, and going off potentially sorting alphabetically,
 * I will assume that JSON objects will have alphabetical String values with no whitespaces, or be the empty String*.
 * <p>
 * * We may want to throw an exception for the empty String, however, it isn't semantically incorrect to include it.
 *
 * @author WHITEHEADN
 */
public class Value implements Comparable<Value> {

    @JsonbProperty("value")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Invalid field, expected at least one alphabetical character (or an empty string).")
    @NotNull(message = "At least one value field is null or malformed.")
    private String value;

    public Value() {
    }

    public Value(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(Value other) {
        return this.getValue().compareTo(other.getValue());
    }
}
