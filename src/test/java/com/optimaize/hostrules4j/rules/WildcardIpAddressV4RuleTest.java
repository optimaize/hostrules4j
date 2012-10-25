package com.optimaize.hostrules4j.rules;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author aa
 */
public class WildcardIpAddressV4RuleTest {

    @Test(dataProvider="testConstructor")
    public void testConstructor(String s) throws Exception {
        new WildcardIpAddressV4Rule(s);
    }
    @DataProvider(name="testConstructor")
    protected Object[][] testConstructor() {
        return new Object[][] {
                {"*"},
                {"123.*"},
                {"123.123.*"},
                {"123.123.123.*"},
                {"123.123.*.123"},
                {"123.*.123.*"},
        };
    }

    @Test(dataProvider="testConstructor_ex", expectedExceptions=IllegalArgumentException.class)
    public void testConstructor_ex(String s) throws Exception {
        new WildcardIpAddressV4Rule(s);
    }
    @DataProvider(name="testConstructor_ex")
    protected Object[][] testConstructor_ex() {
        return new Object[][] {
                {""}, //would be ambiguous
                {"."},
                {"123*"},
                {"123.1*"},
                {"123.123.123*"},
                {"123.123..*"},
                {"123.123.*."}, //by definition
                {"123 .123.123.*"}, //space not allowed
                {"123.123.1 23.*"}, //space not allowed
                {"123.123.123."}, //missing wildcard
                {"123.123.123.123.*"}, //too long
                {"123.123.123.123"}, //not a wildcard
        };
    }


    @Test(dataProvider="testEquals")
    public void testEquals(String one, String two) throws Exception {
        assertTrue(new WildcardIpAddressV4Rule(one).equals(new WildcardIpAddressV4Rule(two)));
    }
    @DataProvider(name="testEquals")
    protected Object[][] testEquals() {
        return new Object[][] {
                {"*", "*"},
                {"*", "*.*"},
                {"*", "*.*.*.*"},
                {"123.*", "123.*"},
        };
    }

    @Test(dataProvider="testNotEquals")
    public void testNotEquals(String one, String two) throws Exception {
        assertFalse(new WildcardIpAddressV4Rule(one).equals(new WildcardIpAddressV4Rule(two)));
    }
    @DataProvider(name="testNotEquals")
    protected Object[][] testNotEquals() {
        return new Object[][] {
                {"*", "123.*"},
                {"123.123.*", "123.*"},
                {"123.123.*", "123.1.*"},
        };
    }

    @Test
    public void testToString() throws Exception {
        assertTrue(new WildcardIpAddressV4Rule("*").toString().contains("*.*.*.*"));
        assertTrue(new WildcardIpAddressV4Rule("123.123.*").toString().contains("123.123.*.*"));
    }

}
