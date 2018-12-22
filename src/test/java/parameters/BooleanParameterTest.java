/*
 * This file is part of ProDisFuzz, modified on 12/22/18 1:50 AM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package parameters;

import main.ParameterException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BooleanParameterTest {

    @Test
    public void testSetValue1() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("false");
        assertFalse(parameter.getValue());
    }

    @Test
    public void testSetValue2() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("true");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue3() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("-1");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue4() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("0");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue5() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("1");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue6() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("a");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue7() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("");
    }

    @Test
    public void testGetDescription1() {
        Parameter<Boolean> parameter = new BooleanParameter("", "testdescription");
        assertEquals(parameter.getDescription(), "testdescription");
    }

    @Test
    public void testGetDescription2() {
        Parameter<Boolean> parameter = new BooleanParameter("", "testdescription", true);
        assertEquals(parameter.getDescription(), "testdescription");
    }

    @Test
    public void testGetValue1() {
        Parameter<Boolean> parameter = new BooleanParameter("", "testdescription");
        assertNull(parameter.getValue());
    }

    @Test
    public void testGetValue2() {
        Parameter<Boolean> parameter = new BooleanParameter("", "testdescription", true);
        assertTrue(parameter.getValue());
    }

    @Test
    public void testCopy() {
        Parameter<Boolean> parameter = new BooleanParameter("testname", "testdescription", true);
        Parameter<Boolean> copy = parameter.copy();
        assertEquals(copy.getName(), "testname");
        assertEquals(copy.getDescription(), "testdescription");
        assertEquals(copy.getValue(), Boolean.TRUE);
    }

}
