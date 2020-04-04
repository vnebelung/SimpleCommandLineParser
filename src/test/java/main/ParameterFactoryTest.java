/*
 * This file is part of ProDisFuzz, modified on 03.04.20, 20:38.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

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
    public void testCreateStringParameter() {
        Parameter<String> parameter = ParameterFactory.createStringParameter("parametername", "parameterdescription");
        assertEquals(parameter.getName(), "parametername");
        assertEquals(parameter.getDescription(), "parameterdescription");
    }
}
