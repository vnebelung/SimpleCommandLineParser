/*
 * This file is part of ProDisFuzz, modified on 3/13/19 12:17 AM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.commands;

import internal.parameters.BooleanParameter;
import internal.parameters.IntegerParameter;
import internal.parameters.StringParameter;
import main.Parameter;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InternalSubcommandTest {

    @Test
    public void testAdd() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        Parameter<String> parameter1 = new StringParameter("testname", "");
        Parameter<Boolean> parameter2 = new BooleanParameter("testname", "");
        internalSubcommand.add(parameter1);
        internalSubcommand.add(parameter2);
        assertEquals(internalSubcommand.namesToBooleanParameters.size(), 1);
        assertEquals(internalSubcommand.namesToIntegerParameters.size(), 0);
        assertEquals(internalSubcommand.namesToStringParameters.size(), 0);
    }

    @Test
    public void testAdd1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        Parameter<Boolean> parameter1 = new BooleanParameter("testname", "");
        Parameter<Integer> parameter2 = new IntegerParameter("testname", "");
        internalSubcommand.add(parameter1);
        internalSubcommand.add(parameter2);
        assertEquals(internalSubcommand.namesToBooleanParameters.size(), 0);
        assertEquals(internalSubcommand.namesToIntegerParameters.size(), 1);
        assertEquals(internalSubcommand.namesToStringParameters.size(), 0);
    }

    @Test
    public void testAdd2() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        Parameter<Integer> parameter1 = new IntegerParameter("testname", "");
        Parameter<String> parameter2 = new StringParameter("testname", "");
        internalSubcommand.add(parameter1);
        internalSubcommand.add(parameter2);
        assertEquals(internalSubcommand.namesToBooleanParameters.size(), 0);
        assertEquals(internalSubcommand.namesToIntegerParameters.size(), 0);
        assertEquals(internalSubcommand.namesToStringParameters.size(), 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd3() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        internalSubcommand.add(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd4() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        internalSubcommand.add(new DummyParameter());
    }

    @Test
    public void testGetDescription() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "testdescription");
        assertEquals(internalSubcommand.getDescription(), "testdescription");
    }

    @Test
    public void testGetParameters() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        assertEquals(internalSubcommand.getParameters().size(), 0);
    }

    @Test
    public void testGetParameters1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        Parameter<Boolean> parameter1 = new BooleanParameter("testname1", "");
        internalSubcommand.add(parameter1);
        Parameter<Integer> parameter2 = new IntegerParameter("testname2", "");
        internalSubcommand.add(parameter2);
        Parameter<String> parameter3 = new StringParameter("testname3", "");
        internalSubcommand.add(parameter3);
        Parameter<String> parameter4 = new StringParameter("testname4", "");
        internalSubcommand.add(parameter4);

        assertEquals(internalSubcommand.getParameters().size(), 4);
        assertTrue(internalSubcommand.getParameters().contains(parameter1));
        assertTrue(internalSubcommand.getParameters().contains(parameter2));
        assertTrue(internalSubcommand.getParameters().contains(parameter3));
        assertTrue(internalSubcommand.getParameters().contains(parameter4));
    }

    @Test
    public void testGetIntegerParameter() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        assertNull(internalSubcommand.getIntegerParameter("testname"));
    }

    @Test
    public void testGetIntegerParameter1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        internalSubcommand.add(new IntegerParameter("testname", ""));
        assertNotNull(internalSubcommand.getIntegerParameter("testname"));
    }

    @Test
    public void testGetBooleanParameter() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        assertNull(internalSubcommand.getBooleanParameter("testname"));
    }

    @Test
    public void testGetBooleanParameter1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        internalSubcommand.add(new BooleanParameter("testname", ""));
        assertNotNull(internalSubcommand.getBooleanParameter("testname"));
    }

    @Test
    public void testGetStringParameter() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        assertNull(internalSubcommand.getStringParameter("testname"));
    }

    @Test
    public void testGetStringParameter1() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("", "");
        internalSubcommand.add(new StringParameter("testname", ""));
        assertNotNull(internalSubcommand.getStringParameter("testname"));
    }

    @Test
    public void testGetName() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("testname", "");
        assertEquals(internalSubcommand.getName(), "testname");
    }

    @Test
    public void testCopy() {
        InternalSubcommand internalSubcommand = new InternalSubcommand("testname", "testdescription");
        internalSubcommand.add(new BooleanParameter("paramname1", "paramdescription1"));
        internalSubcommand.add(new StringParameter("paramname2", "paramdescription2"));
        internalSubcommand.add(new IntegerParameter("paramname3", "paramdescription3").withDefaultValue(4));
        InternalSubcommand copy = internalSubcommand.copy();
        assertEquals(copy.getName(), "testname");
        assertEquals(copy.getDescription(), "testdescription");
        assertEquals(copy.getParameters().size(), 3);
        assertEquals(copy.namesToBooleanParameters.get("paramname1").getDescription(), "paramdescription1");
        assertEquals(copy.namesToStringParameters.get("paramname2").getDescription(), "paramdescription2");
        assertEquals(copy.namesToIntegerParameters.get("paramname3").getDescription(), "paramdescription3");
        assertEquals(copy.getIntegerParameter("paramname3").getValue().intValue(), 4);
    }

    class DummyParameter implements Parameter<String> {

        @Override
        public DummyParameter withDefaultValue(String value) {
            return this;
        }
    }
}
