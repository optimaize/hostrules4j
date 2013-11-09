package com.optimaize.hostrules4j.util;

import com.optimaize.hostrules4j.*;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;

/**
 * ...
 *
 * @author aa
 */
public class HostConverter {

    @NotNull
    public static IpAddress toIpAddress(@NotNull InetAddress inetAddress) {
        return toIpAddress( inetAddress.getHostAddress() );
    }

    @NotNull
    public static IpAddress toIpAddress(@NotNull String ip) {
        //TODO this is very hacky.
        try {
            return new IpAddressV4(ip);
        } catch (IllegalArgumentException e) {
//            new IpAddressV6(inetAddress.getHostAddress());
            throw new UnsupportedOperationException("IPv6 not supported yet!");
        }
    }

    public static HostName toHostName(@NotNull InetAddress inetAddress) {
        return new HostNameImpl(inetAddress.getHostName());
    }

}
