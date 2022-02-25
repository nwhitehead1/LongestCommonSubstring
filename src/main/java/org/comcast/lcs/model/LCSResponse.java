package org.comcast.lcs.model;

import javax.json.bind.annotation.JsonbProperty;
import java.util.Set;

/**
 * @author WHITEHEADN
 */
public class LCSResponse {

    @JsonbProperty("lcs")
    private Set<Value> values;

    public LCSResponse(Set<Value> lcs) {
        this.values = lcs;
    }

    public Set<Value> getValues() {
        return values;
    }

    public void setValues(Set<Value> values) {
        this.values = values;
    }
}
