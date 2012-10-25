package com.optimaize.hostrules4j;

import com.optimaize.hostrules4j.rules.HostRule;
import com.optimaize.hostrules4j.rules.SpecializedHostRuleFactory;
import com.optimaize.hostrules4j.util.PrioritySet;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * Creates {@link HostRule host rules} for strings.
 *
 * @author aa
 */
public class HostRuleFactory {

    @NotNull
    private final List<SpecializedHostRuleFactory> factories;

    /**
     * @param factories The order of the factories doesn't matter, it's the {@link com.optimaize.hostrules4j.rules.SpecializedHostRuleFactory#getPriority()}
     *                  that matters.
     */
    public HostRuleFactory(@NotNull List<SpecializedHostRuleFactory> factories) {
        this.factories = toSortedList(factories);
    }
    /**
     * Overloaded method.
     */
    public HostRuleFactory(@NotNull SpecializedHostRuleFactory... factories) {
        this(Arrays.asList(factories));
    }
    @NotNull
    private static List<SpecializedHostRuleFactory> toSortedList(@NotNull Collection<SpecializedHostRuleFactory> priorityContributions) {
        PrioritySet<SpecializedHostRuleFactory,Integer> set = PrioritySet.desc();
        for (SpecializedHostRuleFactory configProvider : priorityContributions) {
            set.add(configProvider, configProvider.getPriority());
        }
        return set.toList();
    }

    /**
     * @param string The host rule as a string, such as an IPv4 wildcard rule.
     * @throws IllegalArgumentException If no factory can handle the input string.
     * @throws AssertionError If rule creation fails when it should not.
     */
    @NotNull
    public HostRule forString(@NotNull String string) throws IllegalArgumentException, AssertionError {
        for (SpecializedHostRuleFactory factory : factories) {
            if (factory.canHandle(string)) {
                try {
                    return factory.make(string);
                } catch (Exception e) {
                    throw new AssertionError("Unexpected failure when making rule for string >>>"+string+"<<< with rule-factory "+factory+"!", e);
                }
            }
        }
        throw new IllegalArgumentException("Unsupported host rule: >>>"+string+"<<<!");
    }

    /**
     * @see #forString(String)
     */
    @NotNull
    public Set<HostRule> forStrings(@NotNull Iterable<String> string) throws IllegalArgumentException, AssertionError {
        Set<HostRule> ret = new HashSet<>();
        for (String s : string) {
            ret.add(forString(s));
        }
        return ret;
    }
    /**
     * @see #forStrings(Iterable)
     */
    @NotNull
    public Set<HostRule> forStrings(@NotNull String ... strings) throws IllegalArgumentException, AssertionError {
        return forStrings(Arrays.asList(strings));
    }

    /**
     * @see #forString(String)
     */
    @NotNull
    public HostRuleMatcher matcher(@NotNull Iterable<String> string) throws IllegalArgumentException, AssertionError {
        Set<HostRule> ret = new HashSet<>();
        for (String s : string) {
            ret.add(forString(s));
        }
        return new HostRuleMatcher(ret);
    }
    /**
     * @see #matcher(Iterable)
     */
    @NotNull
    public HostRuleMatcher matcher(@NotNull String ... strings) throws IllegalArgumentException, AssertionError {
        return matcher(Arrays.asList(strings));
    }

}
