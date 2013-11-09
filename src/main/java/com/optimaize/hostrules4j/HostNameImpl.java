package com.optimaize.hostrules4j;

import org.jetbrains.annotations.NotNull;

/**
 * @author aa
 */
public class HostNameImpl implements HostName {

    @NotNull
    private final String hostName;

    public HostNameImpl(@NotNull String hostName) {
        this.hostName = hostName;
    }

    @NotNull
    public String getHostName() {
        return hostName;
    }

    @NotNull @Override
    public String asString() {
        return hostName;
    }

    @Override
    public String toString() {
        return "HostName["+asString()+"]";
    }
}
