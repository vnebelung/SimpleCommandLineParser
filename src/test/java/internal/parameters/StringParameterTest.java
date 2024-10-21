/*
 * This file is part of ProDisFuzz, modified on 21.10.24, 08:43.
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

public class StringParameterTest {

    @Test
    public void testSetValue() throws ParameterException {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        parameter.setValue("false");
        assertEquals(parameter.getValue(), "false");
    }

    @Test
    public void testSetValue1() throws ParameterException {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        parameter.setValue("true");
        assertEquals(parameter.getValue(), "true");
    }

    @Test
    public void testSetValue2() throws ParameterException {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        parameter.setValue("-1");
        assertEquals(parameter.getValue(), "-1");
    }

    @Test
    public void testSetValue3() throws ParameterException {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        parameter.setValue("0");
        assertEquals(parameter.getValue(), "0");
    }

    @Test
    public void testSetValue4() throws ParameterException {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        parameter.setValue("1");
        assertEquals(parameter.getValue(), "1");
    }

    @Test
    public void testSetValue5() throws ParameterException {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        parameter.setValue("a");
        assertEquals(parameter.getValue(), "a");
    }

    @Test
    public void testSetValue6() {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue(""));
    }

    @Test
    public void testSetValue7() throws ParameterException {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        parameter.setValue("    ");
    }

    @Test
    public void testSetValue8() {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue(null));
    }

    @Test
    public void testSetValue9() throws ParameterException {
        AbstractParameter<String> parameter =
                new StringParameter("parametername", "parameterdescription", "test1", "test2");
        parameter.setValue("test1");
    }

    @Test
    public void testSetValue10() {
        AbstractParameter<String> parameter =
                new StringParameter("parametername", "parameterdescription", "test1", "test2");
        assertThrows(ParameterException.class, () -> parameter.setValue("test3"));
    }

    @Test
    public void testCopy() {
        AbstractParameter<String> parameter =
                new StringParameter("parametername", "parameterdescription").makeOptional("a");
        AbstractParameter<String> copy = parameter.copy();
        assertEquals(copy.getClass(), StringParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
    }

    @Test
    public void testCopy1() throws ParameterException {
        AbstractParameter<String> parameter =
                new StringParameter("parametername", "parameterdescription", "test1", "test2");
        AbstractParameter<String> copy = parameter.copy();
        assertEquals(copy.getClass(), StringParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
        copy.setValue("test1");
        copy.setValue("test2");
        assertThrows(ParameterException.class, () -> copy.setValue("test3"));
    }

    @Test
    public void testGetAllowedValues() {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        assertEquals(parameter.getAllowedValues(), "");
    }

    @Test
    public void testGetAllowedValues1() {
        AbstractParameter<String> parameter =
                new StringParameter("parametername", "parameterdescription", "test3", "  ", "test1");
        assertEquals(parameter.getAllowedValues(), "  |test1|test3");
    }
}
