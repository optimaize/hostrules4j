package com.optimaize.hostrules4j;

import com.optimaize.hostrules4j.rules.ExactIpAddressV4RuleFactory;
import com.optimaize.hostrules4j.rules.RangeIpAddressV4RuleFactory;
import com.optimaize.hostrules4j.rules.WildcardIpAddressV4RuleFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Arrays;
import static org.testng.Assert.*;

/**
 * Tests the HostRuleMatcher, the HostRuleFactory, as well as several specialized rule and factory implementations.
 *
 *
 */
public class HostRuleMatcherTest {

    private static HostRuleMatcher matcherAuto;
    private static HostRuleMatcher matcherManual;
    static {
        HostRuleFactory hostRuleFactory = new HostRuleFactory(
                new ExactIpAddressV4RuleFactory(),
                new RangeIpAddressV4RuleFactory(),
                new WildcardIpAddressV4RuleFactory()
        );
        matcherAuto = hostRuleFactory.matcher(
                "192.168.1.1",
                "192.168.1.1", //duplicate on purpose
                "190.190.0-100",
                "210.210.*"
        );

        matcherManual = new HostRuleMatcher(Arrays.asList(
                new ExactIpAddressV4RuleFactory().make("192.168.1.1"),
                new ExactIpAddressV4RuleFactory().make("192.168.1.1"), //duplicate on purpose
                new RangeIpAddressV4RuleFactory().make("190.190.0-100"),
                new WildcardIpAddressV4RuleFactory().make("210.210.*")
        ));
    }


    @Test(dataProvider="matches_true")
    public void matches_true(String ip) throws Exception {
        assertTrue(matcherAuto.matches(new IpAddressV4(ip)));
        assertTrue(matcherManual.matches(new IpAddressV4(ip)));
    }
    @DataProvider(name="matches_true")
    protected Object[][] matches_true() {
        return new Object[][] {
                {"192.168.1.1"},
                {"190.190.0.255"},
                {"190.190.99.255"},
                {"190.190.100.255"},
                {"210.210.0.255"},
                {"210.210.254.255"},
                {"210.210.255.255"},
        };
    }

    @Test(dataProvider="matches_false")
    public void matches_false(String ip) throws Exception {
        assertFalse(matcherAuto.matches(new IpAddressV4(ip)));
        assertFalse(matcherManual.matches(new IpAddressV4(ip)));
    }
    @DataProvider(name="matches_false")
    protected Object[][] matches_false() {
        return new Object[][] {
                {"192.168.1.2"},
                {"190.190.101.0"},
                {"210.211.0.0"},
        };
    }
}
