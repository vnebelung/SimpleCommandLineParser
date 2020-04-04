/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:50.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class IntegerParameterTest {

    @Test
    public void testSetValue() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("false"));
    }

    @Test
    public void testSetValue1() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("true"));
    }

    @Test
    public void testSetValue2() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        parameter.setValue("-1");
        assertEquals(parameter.getValue().intValue(), -1);
    }

    @Test
    public void testSetValue3() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        parameter.setValue("0");
        assertEquals(parameter.getValue().intValue(), 0);
    }

    @Test
    public void testSetValue4() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        parameter.setValue("1");
        assertEquals(parameter.getValue().intValue(), 1);
    }

    @Test
    public void testSetValue5() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue(""));
    }

    @Test
    public void testSetValue6() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("a"));
    }

    @Test
    public void testSetValue7() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("    "));
    }

    @Test
    public void testSetValue8() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue(null));
    }

    @Test
    public void testCopy() {
        AbstractParameter<Integer> parameter =
                new IntegerParameter("parametername", "parameterdescription").makeOptional(4);
        AbstractParameter<Integer> copy = parameter.copy();
        assertEquals(copy.getClass(), IntegerParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
    }

    @Test
    public void testCopy1() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        AbstractParameter<Integer> copy = parameter.copy();
        assertEquals(copy.getClass(), IntegerParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
    }

}
