/*
 * This file is part of ProDisFuzz, modified on 12/14/18 6:19 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package parameters;

import main.ParameterException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AbstractParameterTest {

    @Test
    public void testGetDescription() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "testdescription");
        assertEquals(parameter.getDescription(), "testdescription");
    }

    @Test
    public void testGetValue1() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        assertNull(parameter.getValue());
    }

    @Test
    public void testGetValue2() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("true");
        assertTrue(parameter.getValue());
    }

    @Test
    public void testGetValue3() {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "", 6);
        assertEquals(parameter.getValue().intValue(), 6);
    }

    @Test
    public void testGetValue4() throws ParameterException {
        Parameter<Integer> parameter = new IntegerParameter("", "", 6);
        parameter.setValue("1");
        assertEquals(parameter.getValue().intValue(), 1);
    }

    @Test
    public void testSetInternalValue() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setInternalValue(true);
        assertTrue(parameter.getValue());
    }

    @Test
    public void testIsMandatory1() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        assertTrue(parameter.isMandatory());
    }

    @Test
    public void testIsMandatory2() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "", true);
        assertFalse(parameter.isMandatory());
    }

    @Test
    public void testGetName() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("testname", "", true);
        assertEquals(parameter.getName(), "testname");
    }

}
