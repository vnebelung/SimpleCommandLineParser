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

public class IntegerParameterTest {

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue1() throws ParameterException {
        Parameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("false");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue2() throws ParameterException {
        Parameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("true");
    }

    @Test
    public void testSetValue3() throws ParameterException {
        Parameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("-1");
        assertEquals(parameter.getValue().intValue(), -1);
    }

    @Test
    public void testSetValue4() throws ParameterException {
        Parameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("0");
        assertEquals(parameter.getValue().intValue(), 0);
    }

    @Test
    public void testSetValue5() throws ParameterException {
        Parameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("1");
        assertEquals(parameter.getValue().intValue(), 1);
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue6() throws ParameterException {
        Parameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue7() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("a");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue8() throws ParameterException {
        Parameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("");
    }

    @Test
    public void testGetDescription1() {
        Parameter<Integer> parameter = new IntegerParameter("", "testdescription");
        assertEquals(parameter.getDescription(), "testdescription");
    }

    @Test
    public void testGetDescription2() {
        Parameter<Integer> parameter = new IntegerParameter("", "testdescription", 2);
        assertEquals(parameter.getDescription(), "testdescription");
    }

    @Test
    public void testGetValue1() {
        Parameter<Integer> parameter = new IntegerParameter("", "testdescription");
        assertNull(parameter.getValue());
    }

    @Test
    public void testGetValue2() {
        Parameter<Integer> parameter = new IntegerParameter("", "testdescription", 2);
        assertEquals(parameter.getValue().intValue(), 2);
    }

    @Test
    public void testCopy() {
        Parameter<Integer> parameter = new IntegerParameter("testname", "testdescription", 5);
        Parameter<Integer> copy = parameter.copy();
        assertEquals(copy.getName(), "testname");
        assertEquals(copy.getDescription(), "testdescription");
        assertEquals(copy.getValue(), Integer.valueOf(5));
    }

}
