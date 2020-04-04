/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:47.
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

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue6() throws ParameterException {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        parameter.setValue("");
    }

    @Test
    public void testSetValue7() {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("    "));
    }

    @Test
    public void testSetValue8() {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue(null));
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
    public void testCopy1() {
        AbstractParameter<String> parameter = new StringParameter("parametername", "parameterdescription");
        AbstractParameter<String> copy = parameter.copy();
        assertEquals(copy.getClass(), StringParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
    }

}
