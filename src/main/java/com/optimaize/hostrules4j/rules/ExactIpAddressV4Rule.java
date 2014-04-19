package com.optimaize.hostrules4j.rules;

import com.optimaize.hostrules4j.Host;
import com.optimaize.hostrules4j.IpAddressType;
import com.optimaize.hostrules4j.IpAddressV4;
import org.jetbrains.annotations.NotNull;

import java.lang.Object;import java.lang.Override;import java.lang.String;import java.util.EnumSet;
import java.util.Set;

/**
 * A rule that only matches a single IPv4 address.
 *
 *
 */
final class ExactIpAddressV4Rule implements IpAddressRule {

    @NotNull
    private final IpAddressV4 ipV4;

    public ExactIpAddressV4Rule(@NotNull IpAddressV4 ipV4) {
        this.ipV4 = ipV4;
    }

    @NotNull @Override
    public Set<IpAddressType> getTypes() {
        return EnumSet.of(IpAddressType.IPV4);
    }


    @Override
    public boolean matches(@NotNull Host host) {
        return ipV4.equals(host);
    }

    @Override
    public String toString() {
        return "ExactIpAddressV4Rule[" +ipV4.asString() + ']';
    }


    @Override //@GeneratedCode
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExactIpAddressV4Rule that = (ExactIpAddressV4Rule) o;

        if (!ipV4.equals(that.ipV4)) return false;

        return true;
    }

    @Override //@GeneratedCode
    public int hashCode() {
        return ipV4.hashCode();
    }
}
