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

import static org.testng.Assert.*;

public class BooleanParameterTest {

    @Test
    public void testSetValue() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        parameter.setValue("FaLsE");
        assertFalse(parameter.getValue());
    }

    @Test
    public void testSetValue1() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        parameter.setValue("tRuE");
        assertTrue(parameter.getValue());
    }

    @Test
    public void testSetValue2() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("-1"));
    }

    @Test
    public void testSetValue3() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("0"));
    }

    @Test
    public void testSetValue4() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("1"));
    }

    @Test
    public void testSetValue5() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("a"));
    }

    @Test
    public void testSetValue6() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue(""));
    }

    @Test
    public void testSetValue7() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue("    "));
    }

    @Test
    public void testSetValue8() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        assertThrows(ParameterException.class, () -> parameter.setValue(null));
    }

    @Test
    public void testCopy() {
        AbstractParameter<Boolean> parameter =
                new BooleanParameter("parametername", "parameterdescription").makeOptional(true);
        AbstractParameter<Boolean> copy = parameter.copy();
        assertEquals(copy.getClass(), BooleanParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
    }

    @Test
    public void testCopy1() {
        AbstractParameter<Boolean> parameter = new BooleanParameter("parametername", "parameterdescription");
        AbstractParameter<Boolean> copy = parameter.copy();
        assertEquals(copy.getClass(), BooleanParameter.class);
        assertEquals(parameter.getName(), copy.getName());
        assertEquals(copy.getDescription(), parameter.getDescription());
        assertEquals(copy.getValue(), parameter.getValue());
    }
}
