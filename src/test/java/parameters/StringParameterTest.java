/*
 * This file is part of ProDisFuzz, modified on 12/22/18 1:51 AM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package parameters;

import main.ParameterException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class StringParameterTest {

    @Test
    public void testSetValue1() throws ParameterException {
        Parameter<String> parameter = new StringParameter("", "");
        parameter.setValue("false");
        assertEquals(parameter.getValue(), "false");
    }

    @Test
    public void testSetValue2() throws ParameterException {
        Parameter<String> parameter = new StringParameter("", "");
        parameter.setValue("true");
        assertEquals(parameter.getValue(), "true");
    }

    @Test
    public void testSetValue3() throws ParameterException {
        Parameter<String> parameter = new StringParameter("", "");
        parameter.setValue("-1");
        assertEquals(parameter.getValue(), "-1");
    }

    @Test
    public void testSetValue4() throws ParameterException {
        Parameter<String> parameter = new StringParameter("", "");
        parameter.setValue("0");
        assertEquals(parameter.getValue(), "0");
    }

    @Test
    public void testSetValue5() throws ParameterException {
        Parameter<String> parameter = new StringParameter("", "");
        parameter.setValue("1");
        assertEquals(parameter.getValue(), "1");
    }

    @Test
    public void testSetValue6() throws ParameterException {
        Parameter<String> parameter = new StringParameter("", "");
        parameter.setValue("a");
        assertEquals(parameter.getValue(), "a");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue7() throws ParameterException {
        Parameter<String> parameter = new StringParameter("", "");
        parameter.setValue("");
    }

    @Test
    public void testGetDescription1() {
        Parameter<String> parameter = new StringParameter("", "testdescription");
        assertEquals(parameter.getDescription(), "testdescription");
    }

    @Test
    public void testGetDescription2() {
        Parameter<String> parameter = new StringParameter("", "testdescription");
        assertEquals(parameter.getDescription(), "testdescription");
    }

    @Test
    public void testGetValue1() {
        Parameter<String> parameter = new StringParameter("", "");
        assertNull(parameter.getValue());
    }

    @Test
    public void testGetValue2() {
        Parameter<String> parameter = new StringParameter("", "", "testValue");
        assertEquals(parameter.getValue(), "testValue");
    }

    @Test
    public void testCopy() {
        Parameter<String> parameter = new StringParameter("testname", "testdescription", "test");
        Parameter<String> copy = parameter.copy();
        assertEquals(copy.getName(), "testname");
        assertEquals(copy.getDescription(), "testdescription");
        assertEquals(copy.getValue(), "test");
    }

}
