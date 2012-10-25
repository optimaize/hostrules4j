package com.optimaize.hostrules4j;

import org.jetbrains.annotations.NotNull;import java.lang.String;

/**
 * A host such as a host name or an {@link IpAddress ip address}.
 *
 * <p>See http://en.wikipedia.org/wiki/Host_(network)</p>
 *
 * <p>Implementations must be immutable.</p>
 *
 * @author aa
 */
public interface Host {

    @NotNull
    String asString();

}
