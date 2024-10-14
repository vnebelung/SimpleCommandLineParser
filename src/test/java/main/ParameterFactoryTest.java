/*
 * This file is part of ProDisFuzz, modified on 14.10.24, 08:57.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ParameterFactoryTest {

    @Test
    public void testCreateBooleanParameter() {
        Parameter<Boolean> parameter = ParameterFactory.createBooleanParameter("parametername", "parameterdescription");
        assertEquals(parameter.getName(), "parametername");
        assertEquals(parameter.getDescription(), "parameterdescription");
    }

    @Test
    public void testCreateIntegerParameter() {
        Parameter<Integer> parameter = ParameterFactory.createIntegerParameter("parametername", "parameterdescription");
        assertEquals(parameter.getName(), "parametername");
        assertEquals(parameter.getDescription(), "parameterdescription");
    }

    @Test
    public void testCreateIntegerParameter1() {
        Parameter<Integer> parameter =
                ParameterFactory.createIntegerParameter("parametername", "parameterdescription", 0, 1);
        assertEquals(parameter.getName(), "parametername");
        assertEquals(parameter.getDescription(), "parameterdescription");
    }

    @Test
    public void testCreateIntegerParameter2() {
        Parameter<Integer> parameter =
                ParameterFactory.createIntegerParameter("parametername", "parameterdescription", 0, 0);
        assertEquals(parameter.getName(), "parametername");
        assertEquals(parameter.getDescription(), "parameterdescription");
    }

    @Test
    public void testCreateIntegerParameter3() {
        assertThrows(IllegalArgumentException.class,
                () -> ParameterFactory.createIntegerParameter("parametername", "parameterdescription", 1, 0));
    }

    @Test
    public void testCreateStringParameter() {
        Parameter<String> parameter = ParameterFactory.createStringParameter("parametername", "parameterdescription");
        assertEquals(parameter.getName(), "parametername");
        assertEquals(parameter.getDescription(), "parameterdescription");
    }
}
