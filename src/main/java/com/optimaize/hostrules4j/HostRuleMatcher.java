package com.optimaize.hostrules4j;

import com.google.common.collect.ImmutableSet;
import com.optimaize.hostrules4j.rules.HostRule;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

/**
 * Matcher to check {@link Host hosts} against 0-n {@link HostRule rules}.
 *
 * @author aa
 */
public class HostRuleMatcher {

    private final Set<HostRule> hostRules;

    /**
     * @param hostRules May be empty by definition.
     */
    public HostRuleMatcher(@NotNull Collection<HostRule> hostRules) {
        this.hostRules = ImmutableSet.copyOf(hostRules);
    }

    /**
     * Tells if any of the rules within this matcher matches the given <code>host</code>.
     */
    public boolean matches(@NotNull Host host) {
        for (HostRule hostRule : hostRules) {
            if (hostRule.matches(host)) return true;
        }
        return false;
    }

}
