package org.comcast.lcs.service;

import org.comcast.lcs.exception.LCSException;
import org.comcast.lcs.model.Value;

import java.util.List;
import java.util.Set;

/**
 * @author WHITEHEADN
 */
public interface LongestCommonSubstringService {

    /**
     * Service to find the set of LCS values given a non-empty list of values.
     *
     * @param values - the requested {@link List} of strings to find LCS on.
     * @return - {@link Set} of longest common substring as {@link Value} objects.
     * @throws LCSException
     */
    Set<Value> getLongestCommonSubstring(List<Value> values) throws LCSException;
}
