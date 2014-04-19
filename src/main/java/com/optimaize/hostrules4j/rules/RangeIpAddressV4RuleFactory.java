package com.optimaize.hostrules4j.rules;

import org.jetbrains.annotations.NotNull;import java.lang.IllegalArgumentException;import java.lang.Override;import java.lang.String;

/**
 * Factory for {@link RangeIpAddressV4Rule}.
 *
 *
 */
public final class RangeIpAddressV4RuleFactory implements SpecializedHostRuleFactory {

    @Override
    public int getPriority() {
        return 95;
    }

    @Override
    public boolean canHandle(@NotNull String string) {
        //this is hacky... but works for now.
        try {
            new RangeIpAddressV4Rule(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override @NotNull
    public HostRule make(@NotNull String string) {
        return new RangeIpAddressV4Rule(string);
    }

}
