package com.optimaize.hostrules4j.rules;

import com.optimaize.hostrules4j.IpAddressV4;
import com.optimaize.hostrules4j.util.IpUtil;
import org.jetbrains.annotations.NotNull;

/**
 * A rule that only matches a single IPv4 address.
 *
 * @author aa
 */
public final class ExactIpAddressV4RuleFactory implements SpecializedHostRuleFactory {

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public boolean canHandle(@NotNull String string) {
        return IpUtil.isIpV4(string);
    }

    @Override @NotNull
    public HostRule make(@NotNull String string) {
        return new ExactIpAddressV4Rule(new IpAddressV4(string));
    }

}
