package com.optimaize.hostrules4j.util;

import org.jetbrains.annotations.NotNull;import java.lang.IllegalArgumentException;import java.lang.String;

/**
 * Static utility methods for IP addresses.
 *
 *
 */
public class IpUtil {

    private IpUtil() {}

    /**
     * @param string such as "10.10.10.10" or "010.10.010.10"
     */
    public static boolean isIpV4(@NotNull String string) {
        return string.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
    }
    /**
     * @param string such as "10.10.10.10" or "010.10.010.10"
     */
    public static void verifyIpV4(@NotNull String string) {
        if (!isIpV4(string)) {
            throw new IllegalArgumentException("Not a valid ip v4 address: >>>"+string+"<<<!");
        }
    }


}
