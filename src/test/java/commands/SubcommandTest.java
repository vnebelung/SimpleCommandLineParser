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
        assertNotNull(subcommand.getParameter("testname"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAdd2() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(null);
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
        assertNotNull(subcommand.getParameter("--testname"));
    }

    @Test
    public void testGetParameters3() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(new BooleanParameter("--testname", ""));
        subcommand.add(new BooleanParameter("--testname", ""));
        assertEquals(subcommand.getParameters().size(), 1);
    }

    @Test
    public void testGetParameter1() {
        Subcommand subcommand = new Subcommand("", "");
        assertNull(subcommand.getParameter("testname"));
    }

    @Test
    public void testGetParameter2() {
        Subcommand subcommand = new Subcommand("", "");
        subcommand.add(new BooleanParameter("testname", ""));
        assertNotNull(subcommand.getParameter("testname"));
    }

    @Test
    public void testGetName() {
        Subcommand subcommand = new Subcommand("testname", "");
        assertEquals(subcommand.getName(), "testname");
    }
}
