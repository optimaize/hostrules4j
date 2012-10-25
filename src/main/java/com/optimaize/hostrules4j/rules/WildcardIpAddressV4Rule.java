package com.optimaize.hostrules4j.rules;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.optimaize.hostrules4j.Host;
import com.optimaize.hostrules4j.IpAddressType;
import com.optimaize.hostrules4j.IpAddressV4;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * A rule that matches multiple IPv4 addresses based on a wildcard.
 *
 * @author aa
 */
final class WildcardIpAddressV4Rule implements IpAddressRule {

    /**
     * Using <code>null</code> for a wildcard such as "123.123.123.*".
     */
    @NotNull
    private final List<Integer> wildcardParts;

    /**
     * @param ipV4Wildcard The "*" sign may be used one to 4 times.<br/>
     *                     Network items may be left out from the right side as an abbreviation.<br/>
     *                     Examples: "*", "123.*", "123.123.*", "123.123.123.*", "123.123.*.123", "123.*.123.*", ...<br/>
     *                     But not: "123*", "123.1*", "123.123.123*" ...<br/>
     *                     Parsing is not lenient; any ambiguous syntax, or syntax that hints at hasty possibly wrong
     *                     user input (for example spaces).
     * @throws IllegalArgumentException
     */
    public WildcardIpAddressV4Rule(@NotNull String ipV4Wildcard) throws IllegalArgumentException{
        boolean hadWildcard = false;
        wildcardParts = new ArrayList<>();
        for (String s : Splitter.on(".").split(ipV4Wildcard)) {
            if (s.equals("*")) {
                wildcardParts.add(null);
                hadWildcard = true;
            } else {
                int networkPart = Integer.parseInt(s, 10);
                if (networkPart<0 || networkPart>255) {
                    throw new IllegalArgumentException("Not a valid IPv4 wildcard ("+networkPart+"): >>>"+ipV4Wildcard+"<<<!");
                }
                wildcardParts.add(networkPart);
            }
        }
        if (wildcardParts.size()>4) {
            throw new IllegalArgumentException("Not a valid IPv4 wildcard (too many parts): >>>"+ipV4Wildcard+"<<<!");
        } else if (wildcardParts.size()<1) {
            throw new IllegalArgumentException("Not a valid IPv4 wildcard (empty): >>>"+ipV4Wildcard+"<<<!");
        }
        if (wildcardParts.size()<4) {
            for (int i= wildcardParts.size(); i<4; i++) {
                wildcardParts.add(null);
                hadWildcard = true;
            }
        }
        if (!hadWildcard) {
            throw new IllegalArgumentException("At least one wildcard is mandatory, otherwise use ExactIpAddressV4Rule for address: >>>"+ipV4Wildcard+"<<<!");
        }
    }

    @NotNull @Override
    public Set<IpAddressType> getTypes() {
        return EnumSet.of(IpAddressType.IPV4);
    }

    @Override
    public boolean matches(@NotNull Host host) {
        if (host instanceof IpAddressV4) {
            List<Integer> ipParts = ((IpAddressV4) host).getParts();
            for (int i=0; i<4; i++) {
                if (wildcardParts.get(i)==null) continue;
                if (!wildcardParts.get(i).equals(ipParts.get(i))) return false;
            }
            return true;
        }
        return false;
    }

    @Override //@GeneratedCode
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WildcardIpAddressV4Rule that = (WildcardIpAddressV4Rule) o;

        if (!wildcardParts.equals(that.wildcardParts)) return false;

        return true;
    }

    @Override //@GeneratedCode
    public int hashCode() {
        return wildcardParts.hashCode();
    }

    @Override
    public String toString() {
        return "WildcardIpAddressV4Rule[" +Joiner.on('.').useForNull("*").join(wildcardParts) +']';
    }
}
