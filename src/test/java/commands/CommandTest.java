/*
 * This file is part of ProDisFuzz, modified on 12/14/18 6:19 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package commands;

import org.testng.annotations.Test;
import parameters.BooleanParameter;
import parameters.StringParameter;

import static org.testng.Assert.*;

public class CommandTest {

    @Test
    public void testGetSubcommands1() {
        Command command = new Command("", "");
        assertEquals(command.getSubcommands().size(), 0);
    }

    @Test
    public void testGetSubcommands2() {
        Command command = new Command("", "");
        command.add(new Subcommand("testname", ""));
        assertNotNull(command.getSubcommand("testname"));
    }

    @Test
    public void testGetSubcommands3() {
        Command command = new Command("", "");
        command.add(new Subcommand("testname", ""));
        command.add(new Subcommand("testname", ""));
        assertEquals(command.getSubcommands().size(), 1);
    }

    @Test
    public void testGetName() {
        Command command = new Command("testname", "");
        assertEquals(command.getName(), "testname");
    }

    @Test
    public void testAdd1() {
        Command command = new Command("", "");
        command.add(new Subcommand("testname", ""));
        assertEquals(command.getSubcommands().size(), 1);
        assertNotNull(command.getSubcommand("testname"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd2() {
        Command command = new Command("", "");
        command.add((Subcommand) null);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testAdd3() {
        Command command = new Command("", "");
        command.add(new StringParameter("", ""));
        command.add(new Subcommand("", ""));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testAdd4() {
        Command command = new Command("", "");
        command.add(new Subcommand("", ""));
        command.add(new BooleanParameter("", ""));
    }

    @Test
    public void testGetSubcommand1() {
        Command command = new Command("", "");
        assertNull(command.getSubcommand("testname"));
    }

    @Test
    public void testGetSubcommand2() {
        Command command = new Command("", "");
        command.add(new Subcommand("testname", ""));
        assertNotNull(command.getSubcommand("testname"));
    }

}
