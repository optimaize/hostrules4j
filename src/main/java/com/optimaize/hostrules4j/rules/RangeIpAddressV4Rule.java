package com.optimaize.hostrules4j.rules;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.optimaize.hostrules4j.Host;
import com.optimaize.hostrules4j.IpAddressType;
import com.optimaize.hostrules4j.IpAddressV4;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * A rule that matches multiple IPv4 addresses based on a range such as "123.123.123.80-90".
 *
 *
 */
final class RangeIpAddressV4Rule implements IpAddressRule {

    /**
     * No <code>null</code> entries.
     */
    @NotNull
    private final List<Range> wildcardParts;

    private static class Range {
        int minIncl;
        int maxIncl;
        public Range(int minIncl, int maxIncl) {
            checkRange(minIncl);
            checkRange(maxIncl);
            this.minIncl = minIncl;
            this.maxIncl = maxIncl;
        }
        public Range(int b) {
            this(b,b);
        }
        private void checkRange(int val) {
            if (val<0 || val>255) {
                throw new IllegalArgumentException("Not a valid IPv4 address part (out of range 0-255): "+val+"!");
            }
        }
        public String toString() {
            //have to use asString() because i'm using it in a Joiner(). And that's ok cause otherwise
            //the toString() isn't used anyway.
            //return "Range["+minIncl+"-"+maxIncl+"]";
            return asString();
        }
        public String asString() {
            if (minIncl==maxIncl) return ""+minIncl;
            return minIncl+"-"+maxIncl;
        }

        @Override //@GeneratedCode
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Range range = (Range) o;

            if (maxIncl != range.maxIncl) return false;
            if (minIncl != range.minIncl) return false;

            return true;
        }
        @Override //@GeneratedCode
        public int hashCode() {
            int result = (int) minIncl;
            result = 31 * result + (int) maxIncl;
            return result;
        }
    }

    /**
     * @param ipV4Range The "-" sign must be used one to four times.<br/>
     *                  Network items may be left out from the right side, which means allow the whole subnet.<br/>
     *                  Examples: "123-126", "123.123-126", "123.123.123-126", "123.123.123.123-126", "123.123.123-126.0-99"<br/>
     *                  Parsing is not lenient; any ambiguous syntax, or syntax that hints at hasty possibly wrong
     *                  user input (for example spaces).
     * @throws IllegalArgumentException
     */
    public RangeIpAddressV4Rule(@NotNull String ipV4Range) throws IllegalArgumentException {
        boolean hadWildcard = false;
        wildcardParts = new ArrayList<>();
        try {
            for (String s : Splitter.on(".").split(ipV4Range)) {
                if (s.contains("-")) {
                    Iterable<String> split = Splitter.on('-').split(s);
                    if (Iterables.size(split) != 2) throw new IllegalArgumentException("Invalid range");
                    wildcardParts.add( new Range(Integer.parseInt(Iterables.get(split, 0), 10), Integer.parseInt(Iterables.get(split, 1), 10)) );
                    hadWildcard = true;
                } else {
                    wildcardParts.add( new Range(Integer.parseInt(s, 10)) );
                }
            }
        } catch (IllegalArgumentException e) { //includes NumberFormatException
            throw new IllegalArgumentException("Not a valid IPv4 range: >>>"+ipV4Range+"<<<!", e);
        }
        if (wildcardParts.size()>4 || wildcardParts.size()<1) {
            throw new IllegalArgumentException("Not a valid IPv4 range: >>>"+ipV4Range+"<<<!");
        }
        if (wildcardParts.size()<4) {
            for (int i= wildcardParts.size(); i<4; i++) {
                wildcardParts.add( new Range(0, 255) );
                hadWildcard = true;
            }
        }
        if (!hadWildcard) {
            throw new IllegalArgumentException("At least one range is mandatory, otherwise use ExactIpAddressV4Rule for address: >>>"+ipV4Range+"<<<!");
        }
    }

    @NotNull @java.lang.Override
    public Set<IpAddressType> getTypes() {
        return EnumSet.of(IpAddressType.IPV4);
    }

    @Override
    public boolean matches(@NotNull Host host) {
        if (host instanceof IpAddressV4) {
            List<Integer> ipParts = ((IpAddressV4) host).getParts();
            for (int i=0; i<4; i++) {
                if (ipParts.get(i) < wildcardParts.get(i).minIncl) return false;
                if (ipParts.get(i) > wildcardParts.get(i).maxIncl) return false;
            }
            return true;
        }
        return false;
    }

    @Override //@GeneratedCode
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RangeIpAddressV4Rule that = (RangeIpAddressV4Rule) o;

        if (!wildcardParts.equals(that.wildcardParts)) return false;

        return true;
    }

    @Override //@GeneratedCode
    public int hashCode() {
        return wildcardParts.hashCode();
    }

    @Override
    public String toString() {
        return "RangeIpAddressV4Rule[" + Joiner.on('.').join(wildcardParts) + ']';
    }

}
