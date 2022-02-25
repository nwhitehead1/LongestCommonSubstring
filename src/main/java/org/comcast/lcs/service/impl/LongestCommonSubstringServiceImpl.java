package org.comcast.lcs.service.impl;

import org.comcast.lcs.exception.LCSException;
import org.comcast.lcs.model.Value;
import org.comcast.lcs.service.LongestCommonSubstringService;

import javax.enterprise.context.ApplicationScoped;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Algorithm:
 * 1. Iterate on boundaries of the smallest suffix, starting with the full substring.
 * 2. Generate a stem of the suffix, then check if the other words in the list contain that stem.
 * 3. If a string doesn't contain that suffix then break, and recreate a new stem with a smaller boundary.
 * 4. If a string does contain that suffix then it is obvious substrings of that string will contain that suffix as well.
 * Move the initial pointer forward and iterate again as long as the new difference between start and end is still less than
 * the current LCS strings length.
 * 5. Keep track of the length of the longest found common suffix, and use that to determine membership of new suffixes.
 * <p>
 * Time Complexity: O(n^3)
 *
 * @author WHITEHEADN
 */
@ApplicationScoped
public class LongestCommonSubstringServiceImpl implements LongestCommonSubstringService {

    @Override
    public Set<Value> getLongestCommonSubstring(List<Value> values) throws LCSException {
        Set<Value> result = new TreeSet<>();
        // While uniqueness of values in the array is not a syntactic constraint, it does validate the semantics of the longest common substring calculation.
        Set<String> uniqueValues = values.stream().map(Value::getValue).collect(Collectors.toSet());
        if (uniqueValues.size() < values.size()) {
            throw new LCSException("Supplied request must contain a unique set of values.", 422);
        }
        // If we have one value to process then it will be the longest common substring.
        if (values.size() < 2) {
            result.addAll(values);
            return result;
        }
        // Start with the smallest string in the array as the initial suffix. It doesn't save much, but it can prevent large calculations when an empty string may exist.
        String[] uniqueStringsArr = uniqueValues.toArray(new String[0]);
        String suffix = smallest(uniqueStringsArr);
        // If the smallest suffix is the empty string, then it will be the longest common substring.
        if (suffix.length() == 0) {
            result.add(new Value(suffix));
            return result;
        }
        // To avoid comparing the suffix to itself we remove the suffix from the unique set.
        uniqueValues.remove(suffix);
        // Iterate on the boundaries of the suffix, see if all other values contain the substring.
        int suffixLength = suffix.length();
        int longestSuffix = 0;
        for (int i = 0; i < suffixLength; i++) {
            for (int j = suffixLength; j > i; j--) {
                // Iterate through each string to check if the stem matches.
                String stem = suffix.substring(i, j);
                // If our boundaries are smaller than the previously largest seen suffix then break and find a new starting bound.
                if (longestSuffix > j - i) {
                    break;
                }
                boolean allMatches = true;
                // Iterate though the non-suffix words to see if they contain our stem.
                for (String s : uniqueValues) {
                    // If we find a word that doesn't contain our stem, break and move to a new suffix boundary.
                    if (!s.contains(stem)) {
                        allMatches = false;
                        break;
                    }
                }
                // If all words contained the suffix, and the stem is at least as large as the previous longest suffix
                if (allMatches && stem.length() >= longestSuffix) {
                    longestSuffix = stem.length();
                    result.add(new Value(stem));
                }
            }
        }
        // Resulting array will have values from prior stem matches that satisfy the LCS requirement, however they aren't the longest in the set.
        // Easy enough to filter out all stems that are less than our final longest suffix.
        Iterator<Value> resultIter = result.iterator();
        while (resultIter.hasNext()) {
            if (resultIter.next().getValue().length() < longestSuffix) {
                resultIter.remove();
            }
        }
        return result;
    }

    private String smallest(String[] strings) {
        if (strings == null || strings.length == 0) {
            return null;
        }
        String shortest = strings[0];
        for (String str : strings) {
            if (str.length() < shortest.length()) {
                shortest = str;
            }
        }
        return shortest;
    }
}
