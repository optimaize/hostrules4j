package com.optimaize.hostrules4j.rules;

import com.optimaize.hostrules4j.Host;
import org.jetbrains.annotations.NotNull;

/**
 * A rule that defines what {@link com.optimaize.hostrules4j.Host} matches the rule.
 *
 * <p>Implementations must be immutable.</p>
 *
 *
 */
public interface HostRule {

    /**
     * Tells if the given <code>host</code> matches this rule.
     */
    boolean matches(@NotNull Host host);

}
