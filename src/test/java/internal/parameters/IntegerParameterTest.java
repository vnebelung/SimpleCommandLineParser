/*
 * This file is part of ProDisFuzz, modified on 3/10/19 8:00 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.ParameterException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class IntegerParameterTest {

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("false");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue1() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("true");
    }

    @Test
    public void testSetValue2() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("-1");
        assertEquals(parameter.getValue().intValue(), -1);
    }

    @Test
    public void testSetValue3() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("0");
        assertEquals(parameter.getValue().intValue(), 0);
    }

    @Test
    public void testSetValue4() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("1");
        assertEquals(parameter.getValue().intValue(), 1);
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue5() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue6() throws ParameterException {
        AbstractParameter<Integer> parameter = new IntegerParameter("", "");
        parameter.setValue("a");
    }

}
