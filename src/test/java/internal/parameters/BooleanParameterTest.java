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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BooleanParameterTest {

    @Test
    public void testSetValue() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("false");
        assertFalse(parameter.getValue());
    }

    @Test
    public void testSetValue1() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("true");
        assertTrue(parameter.getValue());
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue2() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("-1");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue3() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("0");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue4() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("1");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue5() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("a");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSetValue6() throws ParameterException {
        AbstractParameter<Boolean> parameter = new BooleanParameter("", "");
        parameter.setValue("");
    }

}
