package com.optimaize.hostrules4j.rules;

import org.jetbrains.annotations.NotNull;import java.lang.IllegalArgumentException;import java.lang.Override;import java.lang.String;

/**
 * A rule that only matches a single IPv4 address.
 *
 *
 */
public final class MatchAllIpAddressRuleFactory implements SpecializedHostRuleFactory {

    @Override
    public int getPriority() {
        //highest priority, must be higher than WildcardIpAddressV4RuleFactory for example because
        //this one also matches IPv6.
        return 1000;
    }

    @Override
    public boolean canHandle(@NotNull String string) {
        return string.equals("*");
    }

    @Override @NotNull
    public HostRule make(@NotNull String string) {
        //checking again in case someone did not call canHandle(), it would not be detected otherwise and
        //would have fatal consequences.
        if (!canHandle(string)) {
            throw new IllegalArgumentException("Can't handle >>>"+string+"<<<!");
        }
        return new MatchAllIpAddressRule();
    }

}
