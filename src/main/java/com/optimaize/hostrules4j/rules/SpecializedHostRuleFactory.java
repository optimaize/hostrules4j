package com.optimaize.hostrules4j.rules;

import org.jetbrains.annotations.NotNull;import java.lang.String;

/**
 * Factory for a specific {@link IpAddressRule}.
 *
 *
 */
public interface SpecializedHostRuleFactory {

    /**
     * When converting from String to IpAddressRule then those with the higher priority are
     * checked first.
     */
    int getPriority();

    /**
     * Tells if the string can be converted to a IpAddressRule using this factory.
     */
    boolean canHandle(@NotNull String string);

    /**
     * May not fail if {@link #canHandle} returns <code>true</code>, otherwise it's a bug.
     */
    @NotNull
    HostRule make(@NotNull String string);

}
