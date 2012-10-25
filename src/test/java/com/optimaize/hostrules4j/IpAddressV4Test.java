package com.optimaize.hostrules4j;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author aa
 */
public class IpAddressV4Test {

    @Test(dataProvider="testConstructor")
    public void testConstructor(String s) throws Exception {
        new IpAddressV4(s);
    }
    @DataProvider(name="testConstructor")
    protected Object[][] testConstructor() {
        return new Object[][] {
                {"123.123.123.123"},
                {"127.0.0.1"},
                {"255.255.255.255"},
                {"0.0.0.0"},
                {"000.000.000.000"},
                {"010.010.010.010"},
                {"010.10.010.10"},
        };
    }


    @Test(dataProvider="testConstructor_ex", expectedExceptions=IllegalArgumentException.class)
    public void testConstructor_ex(String s) throws Exception {
        new IpAddressV4(s);
    }
    @DataProvider(name="testConstructor_ex")
    protected Object[][] testConstructor_ex() {
        return new Object[][] {
                {""},
                {"."},
                {"123"},
                {"123.123"},
                {"123.123.123.123.123"},
                {"123.123.abc.123"},
                {"255.255.255.256"},
                {"255.-1.255.255"},
        };
    }

    @Test(dataProvider="testEquals")
    public void testEquals(String one, String two) throws Exception {
        assertTrue(new IpAddressV4(one).equals(new IpAddressV4(two)));
    }
    @DataProvider(name="testEquals")
    protected Object[][] testEquals() {
        return new Object[][] {
                {"255.255.255.255", "255.255.255.255"},
                {"0.0.0.0", "000.000.000.000"},
                {"90.80.10.70", "090.080.010.070"},
        };
    }

    @Test(dataProvider="testNotEquals")
    public void testNotEquals(String one, String two) throws Exception {
        assertFalse(new IpAddressV4(one).equals(new IpAddressV4(two)));
    }
    @DataProvider(name="testNotEquals")
    protected Object[][] testNotEquals() {
        return new Object[][] {
                {"255.255.255.255", "255.255.255.0"},
        };
    }

    @Test
    public void testToString() throws Exception {
        assertTrue(new IpAddressV4("255.255.255.255").toString().contains("255.255.255.255"));
        assertTrue(new IpAddressV4("090.080.010.070").toString().contains("90.80.10.70"));
    }

}
