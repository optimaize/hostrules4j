package com.optimaize.hostrules4j;

import com.google.common.base.Splitter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An IPv4 address.
 *
 * <p>See: http://en.wikipedia.org/wiki/IPv4</p>
 *
 *
 */
public class IpAddressV4 implements IpAddress {

    @NotNull
    private final List<Integer> ipParts;

    /**
     *
     * @param ipV4 May contain zero padding. Both examples are valid: "10.10.10.10", "010.010.010.010".
     */
    public IpAddressV4(@NotNull String ipV4) {
        List<Integer> ipParts = new ArrayList<>();
        for (String s : Splitter.on(".").split(ipV4)) {
            try {
                int networkPart = Integer.parseInt(s, 10);
                if (networkPart<0 || networkPart>255) {
                    throw new IllegalArgumentException("Not a valid IPv4 address ("+networkPart+"): >>>"+ipV4+"<<<!");
                }
                ipParts.add(networkPart);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Not a valid IPv4 address: >>>"+ipV4+"<<<!", e);
            }
        }
        if (ipParts.size()!=4) {
            throw new IllegalArgumentException("Not a valid IPv4 address: >>>"+ipV4+"<<<!");
        }
        this.ipParts = Collections.unmodifiableList(ipParts);
    }

    @NotNull
    public List<Integer> getParts() {
        return ipParts;
    }

    /**
     * @return Without zero padding. Example: "10.10.10.10" (not "010.010.010.010").
     */
    @NotNull @Override
    public String asString() {
        return ipParts.get(0)+"."+ipParts.get(1)+"."+ipParts.get(2)+"."+ipParts.get(3);
    }

    @Override
    public String toString() {
        return "IpAddressV4["+asString()+"]";
    }

    @Override //@GeneratedCode
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IpAddressV4 that = (IpAddressV4) o;

        if (!ipParts.equals(that.ipParts)) return false;

        return true;
    }

    @Override //@GeneratedCode
    public int hashCode() {
        return ipParts.hashCode();
    }
}
