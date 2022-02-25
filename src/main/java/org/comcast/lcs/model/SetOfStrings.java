package org.comcast.lcs.model;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author WHITEHEADN
 */
public class SetOfStrings implements Serializable {

    private static final long serialVersionUID = 1;

    @JsonbProperty("setOfStrings")
    @NotEmpty(message = "Provided values must not be missing, or null.")
    List<@Valid Value> values;

    public SetOfStrings() {
    }

    public SetOfStrings(List<Value> values) {
        this.values = values;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }
}
