package com.optimaize.hostrules4j.rules;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 *
 */
public class RangeIpAddressV4RuleTest {

    @Test(dataProvider="testConstructor")
    public void testConstructor(String s) throws Exception {
        new RangeIpAddressV4Rule(s);
    }
    @DataProvider(name="testConstructor")
    protected Object[][] testConstructor() {
        return new Object[][] {
                {"123-126"},
                {"123.123-126"},
                {"123.123.123-126"},
                {"123.123.123.123-126"},
                {"123.123.123-126.0-99"},
        };
    }

    @Test(dataProvider="testConstructor_ex", expectedExceptions=IllegalArgumentException.class)
    public void testConstructor_ex(String s) throws Exception {
        new RangeIpAddressV4Rule(s);
    }
    @DataProvider(name="testConstructor_ex")
    protected Object[][] testConstructor_ex() {
        return new Object[][] {
                {""}, //would be ambiguous
                {"."},
                {"*"},
                {"-"},
                {"123-"},
                {"-123"},
                {"123 - 126"},
                {"123--126"},
                {"123.123.123.-"},
                {"123.123.123-126.-"},
                {"123.123.123.123.123-126"}, //too long
                {"123.123.123.123"}, //not a range
        };
    }


    @Test(dataProvider="testEquals")
    public void testEquals(String one, String two) throws Exception {
        assertTrue(new RangeIpAddressV4Rule(one).equals(new RangeIpAddressV4Rule(two)));
    }
    @DataProvider(name="testEquals")
    protected Object[][] testEquals() {
        return new Object[][] {
                {"10.10.10.20-30", "10.10.10.20-30"},
                {"123-126", "123-126.0-255"},
                {"123-126", "123-126.0-255.0-255.0-255"},
        };
    }

    @Test(dataProvider="testNotEquals")
    public void testNotEquals(String one, String two) throws Exception {
        assertFalse(new RangeIpAddressV4Rule(one).equals(new RangeIpAddressV4Rule(two)));
    }
    @DataProvider(name="testNotEquals")
    protected Object[][] testNotEquals() {
        return new Object[][] {
                {"10.10.10.20-30", "10.10.10.20-40"},
                {"123-126", "123-127.0-255"},
                {"123-126", "123-126.1-255"},
        };
    }


    @Test
    public void testToString() throws Exception {
        assertTrue(new RangeIpAddressV4Rule("123.123.123-125").toString().contains("123.123.123-125.0-255"));
    }

}
