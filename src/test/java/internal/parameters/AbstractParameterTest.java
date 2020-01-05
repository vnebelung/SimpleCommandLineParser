/*
 * This file is part of ProDisFuzz, modified on 05.01.20, 10:25.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

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
    public void testGetValue() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        assertNull(parameter.getValue());
    }

    @Test
    public void testSetValue() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("true");
        assertTrue(parameter.getValue());
    }

    @Test
    public void testSetValue1() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "").withDefaultValue(6);
        parameter.setValue("1");
        assertEquals(parameter.getValue().intValue(), 1);
    }

    @Test
    public void testWithDefaultValue() {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "").withDefaultValue(6);
        assertEquals(parameter.getValue().intValue(), 6);
        assertFalse(parameter.isMandatory());
    }

    @Test
    public void testWithInternalValue() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setInternalValue(true);
        assertTrue(parameter.getValue());
    }

    @Test
    public void testIsMandatory() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        assertTrue(parameter.isMandatory());
    }

    @Test
    public void testIsMandatory1() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "").withDefaultValue(false);
        assertFalse(parameter.isMandatory());
    }

    @Test
    public void testGetName() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("testname", "");
        assertEquals(parameter.getName(), "testname");
    }

}
