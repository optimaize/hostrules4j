package com.optimaize.hostrules4j.rules;

import org.jetbrains.annotations.NotNull;import java.lang.IllegalArgumentException;import java.lang.Override;import java.lang.String;

/**
 * Factory for {@link WildcardIpAddressV4Rule}.
 *
 * @author aa
 */
public final class WildcardIpAddressV4RuleFactory implements SpecializedHostRuleFactory {

    @Override
    public int getPriority() {
        return 95;
    }

    @Override
    public boolean canHandle(@NotNull String string) {
        //this is hacky... but works for now.
        try {
            new WildcardIpAddressV4Rule(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override @NotNull
    public HostRule make(@NotNull String string) {
        return new WildcardIpAddressV4Rule(string);
    }

}
