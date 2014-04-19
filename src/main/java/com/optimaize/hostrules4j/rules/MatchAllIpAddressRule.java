package com.optimaize.hostrules4j.rules;

import com.optimaize.hostrules4j.Host;
import com.optimaize.hostrules4j.IpAddress;
import com.optimaize.hostrules4j.IpAddressType;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Set;

/**
 * A rule that matches any ip address.
 *
 *
 */
final class MatchAllIpAddressRule implements IpAddressRule {

    /**
     */
    public MatchAllIpAddressRule() {
    }

    @NotNull @Override
    public Set<IpAddressType> getTypes() {
        return EnumSet.of(IpAddressType.IPV4, IpAddressType.IPV6);
    }

    @Override
    public boolean matches(@NotNull Host host) {
        return host instanceof IpAddress;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "MatchAllIpAddressRule";
    }

}
