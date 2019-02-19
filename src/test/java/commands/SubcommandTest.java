/*
 * This file is part of ProDisFuzz, modified on 2/19/19 9:45 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package commands;

import org.testng.annotations.Test;
import parameters.BooleanParameter;
import parameters.IntegerParameter;
import parameters.StringParameter;

import static org.testng.Assert.*;

public class SubcommandTest {

    @Test
    public void testGetDescription() {
        Subcommand subcommand = new Subcommand("", "testdescription");
        assertEquals(subcommand.getDescription(), "testdescription");
    }

    @Test
    public void testAdd1() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(new BooleanParameter("testname", ""));
        assertEquals(subcommand.getParameters().size(), 1);
        assertNotNull(subcommand.getBooleanParameter("testname"));
        assertNull(subcommand.getStringParameter("testname"));
        assertNull(subcommand.getIntegerParameter("testname"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd2() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add((BooleanParameter) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd3() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add((IntegerParameter) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd4() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add((StringParameter) null);
    }

    @Test
    public void testGetParameters1() {
        Subcommand subcommand = new Subcommand("", "");
        assertEquals(subcommand.getParameters().size(), 0);
    }

    @Test
    public void testGetParameters2() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(new BooleanParameter("--testname", ""));
        assertNotNull(subcommand.getBooleanParameter("--testname"));
    }

    @Test
    public void testGetParameters3() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(new BooleanParameter("--testname", ""));
        subcommand.add(new BooleanParameter("--testname", ""));
        assertEquals(subcommand.getParameters().size(), 1);
    }

    @Test
    public void testGetIntegerParameter1() {
        Subcommand subcommand = new Subcommand("", "");
        assertNull(subcommand.getIntegerParameter("testname"));
    }

    @Test
    public void testGetIntegerParameter2() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(new IntegerParameter("testname", ""));
        assertNotNull(subcommand.getIntegerParameter("testname"));
    }

    @Test
    public void testGetBooleanParameter1() {
        Subcommand subcommand = new Subcommand("", "");
        assertNull(subcommand.getBooleanParameter("testname"));
    }

    @Test
    public void testGetBooleanParameter2() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(new BooleanParameter("testname", ""));
        assertNotNull(subcommand.getBooleanParameter("testname"));
    }

    @Test
    public void testGetStringParameter1() {
        Subcommand subcommand = new Subcommand("", "");
        assertNull(subcommand.getStringParameter("testname"));
    }

    @Test
    public void testGetStringParameter2() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(new StringParameter("testname", ""));
        assertNotNull(subcommand.getStringParameter("testname"));
    }

    @Test
    public void testGetName() {
        Subcommand subcommand = new Subcommand("testname", "");
        assertEquals(subcommand.getName(), "testname");
    }

    @Test
    public void testCopy() {
        Subcommand subcommand = new Subcommand("testname", "testdescription");
        subcommand.add(new BooleanParameter("paramname", "paramdescription", true));
        Subcommand copy = subcommand.copy();
        assertEquals(copy.getName(), "testname");
        assertEquals(copy.getDescription(), "testdescription");
        assertEquals(copy.getParameters().size(), 1);
        assertEquals(copy.getBooleanParameter("paramname").getDescription(), "paramdescription");
        assertTrue(copy.getBooleanParameter("paramname").getValue());
    }
}
