package com.optimaize.hostrules4j.rules;

import com.optimaize.hostrules4j.Host;
import com.optimaize.hostrules4j.rules.HostRule;
import com.optimaize.hostrules4j.IpAddress;
import com.optimaize.hostrules4j.IpAddressType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * A specialized {@link HostRule} that only matches for {@link Host hosts} of type {@link IpAddress ip address}.
 *
 * @author aa
 */
public interface IpAddressRule extends HostRule {

    /**
     * Tells for which {@link IpAddressType ip address types} this rule may match.
     */
    @NotNull
    Set<IpAddressType> getTypes();

}
