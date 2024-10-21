/*
 * This file is part of ProDisFuzz, modified on 20.10.24, 15:58.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
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
    public void testSetValue9() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription", -1, 1);
        assertThrows(ParameterException.class, () -> parameter.setValue("-2"));
    }

    @Test
    public void testSetValue10() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription", -1, 1);
        parameter.setValue("-1");
        assertEquals(parameter.getValue().intValue(), -1);
    }

    @Test
    public void testSetValue11() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription", -1, 1);
        parameter.setValue("1");
        assertEquals(parameter.getValue().intValue(), 1);
    }

    @Test
    public void testSetValue12() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription", -1, 1);
        assertThrows(ParameterException.class, () -> parameter.setValue("2"));
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
    public void testCopy1() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription", -1, 1);
        AbstractParameter<Integer> copy = parameter.copy();
        assertEquals(copy.getClass(), IntegerParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
        assertThrows(ParameterException.class, () -> copy.setValue("-2"));
        copy.setValue("-1");
        copy.setValue("0");
        copy.setValue("1");
        assertThrows(ParameterException.class, () -> copy.setValue("2"));
    }

    @Test
    public void testGetAllowedValues() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription");
        assertEquals(parameter.getAllowedValues(), "");
    }

    @Test
    public void testGetAllowedValues1() {
        AbstractParameter<Integer> parameter = new IntegerParameter("parametername", "parameterdescription", -1, 1);
        assertEquals(parameter.getAllowedValues(), "-1 to 1");
    }
}
