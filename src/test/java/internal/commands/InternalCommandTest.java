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
import main.Subcommand;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InternalCommandTest {

    @Test
    public void testGetName() {
        InternalCommand internalCommand = new InternalCommand("testname", "");
        assertEquals(internalCommand.getName(), "testname");
    }

    @Test
    public void testAdd() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new InternalSubcommand("testname", ""));
        assertEquals(internalCommand.getSubcommands().size(), 1);
        assertNotNull(internalCommand.getSubcommand("testname"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd1() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add((InternalSubcommand) null);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testAdd2() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new StringParameter("", ""));
        internalCommand.add(new InternalSubcommand("", ""));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testAdd3() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new InternalSubcommand("", ""));
        internalCommand.add(new BooleanParameter("", ""));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testAdd4() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new InternalSubcommand("", ""));
        internalCommand.add(new StringParameter("", ""));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testAdd5() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new InternalSubcommand("", ""));
        internalCommand.add(new IntegerParameter("", ""));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd6() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new DummySubcommand());
    }

    @Test
    public void testCopy() {
        InternalCommand internalCommand = new InternalCommand("testname", "testdescription");
        internalCommand.add(new StringParameter("paramname", "paramdescription").withDefaultValue("paramvalue"));
        InternalCommand copy = internalCommand.copy();
        assertEquals(copy.getName(), "testname");
        assertEquals(copy.getDescription(), "testdescription");
        assertEquals(copy.namesToStringParameters.size(), 1);
        assertNotNull(copy.namesToStringParameters.get("paramname"));
        assertEquals(copy.namesToStringParameters.get("paramname").getDescription(), "paramdescription");
        assertEquals(copy.namesToStringParameters.get("paramname").getValue(), "paramvalue");
    }

    @Test
    public void testGetSubcommands() {
        InternalCommand command = new InternalCommand("", "");
        assertEquals(command.getSubcommands().size(), 0);
    }

    @Test
    public void testGetSubcommands1() {
        InternalCommand command = new InternalCommand("", "");
        InternalSubcommand internalSubcommand1 = new InternalSubcommand("name1", "description1");
        InternalSubcommand internalSubcommand2 = new InternalSubcommand("name2", "description2");
        command.add(internalSubcommand1);
        command.add(internalSubcommand2);
        assertEquals(command.getSubcommands().size(), 2);
        assertTrue(command.getSubcommands().contains(internalSubcommand1));
        assertTrue(command.getSubcommands().contains(internalSubcommand2));
    }

    @Test
    public void testGetSubcommands2() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new InternalSubcommand("testname", ""));
        internalCommand.add(new InternalSubcommand("testname", ""));
        assertEquals(internalCommand.getSubcommands().size(), 1);
    }

    @Test
    public void testGetSubcommand() {
        InternalCommand internalCommand = new InternalCommand("", "");
        assertNull(internalCommand.getSubcommand("testname"));
    }

    @Test
    public void testGetSubcommand1() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new InternalSubcommand("testname", ""));
        assertNotNull(internalCommand.getSubcommand("testname"));
    }

    @Test
    public void testGetSubcommand3() {
        InternalCommand internalCommand = new InternalCommand("", "");
        assertNull(internalCommand.getSubcommand());
    }

    @Test
    public void testGetSubcommand4() {
        InternalCommand internalCommand = new InternalCommand("", "");
        internalCommand.add(new InternalSubcommand("testname", ""));
        assertEquals(internalCommand.getSubcommand().getName(), "testname");
    }

    class DummySubcommand implements Subcommand {

        @Override
        public void add(Parameter<?> parameter) {
        }
    }
}
